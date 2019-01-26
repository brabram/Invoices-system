package pl.coderstrust.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.List;
import java.util.Optional;
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
import pl.coderstrust.service.InvoiceService;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

  private InvoiceService invoiceService;
  private ObjectMapper mapper = new ObjectMapper();

  public InvoiceController(InvoiceService invoiceService) {
    this.invoiceService = invoiceService;
  }

  @GetMapping
  public ResponseEntity<?> getAllInvoices() {
    try {
      Optional<List<Invoice>> optionalInvoicesList = invoiceService.getAllInvoices();
      if (optionalInvoicesList.isPresent()) {
        return new ResponseEntity<>(optionalInvoicesList.get(), HttpStatus.OK);
      }
      return new ResponseEntity<>(new ErrorMessage("Not found any invoices."), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new ErrorMessage("Internal Server Error."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getInvoiceById(@PathVariable("id") Long id) {
    if (id == null) {
      return new ResponseEntity<>(new ErrorMessage("Invalid id."), HttpStatus.BAD_REQUEST);
    }
    try {
      Optional<Invoice> optionalInvoice = invoiceService.getInvoiceById(id);
      if (optionalInvoice.isPresent()) {
        return new ResponseEntity<>(optionalInvoice.get(), HttpStatus.OK);
      }
      return new ResponseEntity<>(new ErrorMessage("Invoice not found."), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new ErrorMessage("Internal Server Error."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/number={number}")
  public ResponseEntity<?> getInvoiceByNumber(@PathVariable("number") String number) {
    if (number == null) {
      return new ResponseEntity<>(new ErrorMessage("Invalid number."), HttpStatus.BAD_REQUEST);
    }
    try {
      Optional<List<Invoice>> optionalInvoicesList = invoiceService.getAllInvoices();
      if (optionalInvoicesList.isPresent()) {
        Optional<Invoice> optionalInvoice = optionalInvoicesList.get()
            .stream()
            .filter(invoiceToGet -> invoiceToGet.getNumber().equals(number))
            .findAny();
        if (optionalInvoice.isPresent()) {
          return new ResponseEntity<>(optionalInvoice.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ErrorMessage("Invoice not found."), HttpStatus.NOT_FOUND);
      }
      return new ResponseEntity<>(new ErrorMessage("Not found any invoices."), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new ErrorMessage("Internal Server Error."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping
  public ResponseEntity<?> addInvoice(@RequestBody Invoice invoice) {
    if (invoice == null) {
      return new ResponseEntity<>(new ErrorMessage("Invoice cannot be null."), HttpStatus.BAD_REQUEST);
    }
    try {
      Optional<Invoice> optionalInvoice = invoiceService.addInvoice(invoice);
      if (optionalInvoice.isPresent()) {
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return new ResponseEntity<>(mapper.writeValueAsString(optionalInvoice.get()), HttpStatus.CREATED);
      }
      return new ResponseEntity<>(new ErrorMessage("Invoice already exist."), HttpStatus.CONFLICT);
    } catch (Exception e) {
      return new ResponseEntity<>(new ErrorMessage("Internal Server Error."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateInvoice(@PathVariable("id") Long id, @RequestBody Invoice invoice) {
    if (id == null) {
      return new ResponseEntity<>(new ErrorMessage("Invalid id"), HttpStatus.BAD_REQUEST);
    }
    try {
      if (invoice == null) {
        return new ResponseEntity<>(new ErrorMessage("Invoice does not exist."), HttpStatus.NOT_FOUND);
      }
      invoice.setId(id);
      invoiceService.updateInvoice(invoice);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(new ErrorMessage("Internal Server Error."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> removeInvoice(@PathVariable("id") Long id) {
    if (id == null) {
      return new ResponseEntity<>(new ErrorMessage("Invalid id"), HttpStatus.BAD_REQUEST);
    }
    try {
      Optional<Invoice> optionalInvoice = invoiceService.getInvoiceById(id);
      if (optionalInvoice.isPresent()) {
        invoiceService.deleteInvoiceById(id);
        return new ResponseEntity<>(HttpStatus.OK);
      }
      return new ResponseEntity<>(new ErrorMessage("Invoice does not exist."), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new ErrorMessage("Internal Server Error."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
