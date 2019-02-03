package pl.coderstrust.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.model.validators.InvoiceValidator;
import pl.coderstrust.service.InvoiceService;

@RestController
@RequestMapping("/invoices/")
public class InvoiceController {

  private InvoiceService invoiceService;

  @Autowired
  public InvoiceController(InvoiceService invoiceService) {
    this.invoiceService = invoiceService;
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
  }

  @GetMapping
  public ResponseEntity<?> getAll() {
    try {
      Optional<List<Invoice>> optionalInvoicesList = invoiceService.getAllInvoices();
      if (optionalInvoicesList.isPresent()) {
        return new ResponseEntity<>(optionalInvoicesList.get(), HttpStatus.OK);
      }
      return new ResponseEntity<>(new ArrayList<Invoice>(), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(new ErrorMessage("Internal server error while getting invoices."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("{id}")
  public ResponseEntity<?> getById(@PathVariable("id") Long id) {
    try {
      Optional<Invoice> optionalInvoice = invoiceService.getInvoiceById(id);
      if (optionalInvoice.isPresent()) {
        return new ResponseEntity<>(optionalInvoice.get(), HttpStatus.OK);
      }
      return new ResponseEntity<>(new ErrorMessage(String.format("Invoice not found for passed id: %d", id)), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new ErrorMessage(String.format("Internal server error while getting invoice by id: %d", id)), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("number/{number}")
  public ResponseEntity<?> getByNumber(@PathVariable("number") String number) {
    try {
      Optional<List<Invoice>> optionalInvoicesList = invoiceService.getAllInvoices();
      if (optionalInvoicesList.isPresent()) {
        Optional<Invoice> optionalInvoice = optionalInvoicesList.get()
            .stream()
            .filter(invoiceToGet -> invoiceToGet.getNumber().equals(number))
            .findFirst();
        if (optionalInvoice.isPresent()) {
          return new ResponseEntity<>(optionalInvoice.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ErrorMessage(String.format("Invoice not found for passed number: %s", number)), HttpStatus.NOT_FOUND);
      }
      return new ResponseEntity<>(new ErrorMessage(String.format("Invoice not found for passed number: %s", number)), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new ErrorMessage(String.format("Internal server error while getting invoice by number: %s", number)), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping
  public ResponseEntity<?> add(@RequestBody(required = false) Invoice invoice) {
    try {
      List<String> resultOfValidation = InvoiceValidator.validate(invoice, false);
      if (resultOfValidation.size() > 0) {
        return new ResponseEntity<>(new ErrorMessage("Passed invoice is invalid.", resultOfValidation), HttpStatus.BAD_REQUEST);
      }

      if (invoice.getId() == null || !invoiceService.invoiceExistsById(invoice.getId())) {
        Optional<Invoice> addedInvoice = invoiceService.addInvoice(invoice);
        HttpHeaders responseHeaders = new HttpHeaders();
        if (addedInvoice.isPresent()) {
          responseHeaders.setLocation(URI.create(String.format("/invoices/%d", addedInvoice.get().getId())));
          return new ResponseEntity<>(addedInvoice.get(), responseHeaders, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new ErrorMessage("Internal server error while adding invoice."), HttpStatus.INTERNAL_SERVER_ERROR);
      }
      return new ResponseEntity<>(new ErrorMessage("Invoice already exist."), HttpStatus.CONFLICT);
    } catch (Exception e) {
      return new ResponseEntity<>(new ErrorMessage("Internal server error while adding invoice."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("{id}")
  public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody(required = false) Invoice invoice) {
    try {
      List<String> resultOfValidation = InvoiceValidator.validate(invoice, true);

      if (resultOfValidation.size() > 0) {
        return new ResponseEntity<>(new ErrorMessage("Passed invoice is invalid.", resultOfValidation), HttpStatus.BAD_REQUEST);
      }

      if (!id.equals(invoice.getId())) {
        return new ResponseEntity<>(new ErrorMessage(String.format("Invoice to update has different id than %d.", id)), HttpStatus.BAD_REQUEST);
      }
      if (!invoiceService.invoiceExistsById(id)) {
        return new ResponseEntity<>(new ErrorMessage(String.format("Invoice with %d id does not exist.", id)), HttpStatus.NOT_FOUND);
      }
      invoiceService.updateInvoice(invoice);
      return new ResponseEntity<>(invoice, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(new ErrorMessage("Internal server error while updating invoice."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> remove(@PathVariable("id") Long id) {
    try {
      if (!invoiceService.invoiceExistsById(id)) {
        return new ResponseEntity<>(new ErrorMessage(String.format("Invoice with %d id does not exist.", id)), HttpStatus.NOT_FOUND);
      }
      invoiceService.deleteInvoiceById(id);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(new ErrorMessage("Internal server error while removing invoice."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
