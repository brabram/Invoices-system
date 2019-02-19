package pl.coderstrust.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.model.validators.InvoiceValidator;
import pl.coderstrust.pdfsevice.PdfService;
import pl.coderstrust.service.InvoiceService;

@Api(value = "/invoices", description = "Available operations for invoice application", tags = {"Invoices"})
@RestController
@RequestMapping("/invoices")
public class InvoiceController {
  private static Logger log = LoggerFactory.getLogger(InvoiceController.class);
  private InvoiceService invoiceService;
  private PdfService pdfService;

  @Autowired
  public InvoiceController(InvoiceService invoiceService, PdfService pdfService) {
    this.invoiceService = invoiceService;
    this.pdfService = pdfService;
  }

  @GetMapping
  @ApiOperation(
      value = "Get all invoices",
      response = Invoice.class,
      responseContainer = "List")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = Invoice.class),
      @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)})
  public ResponseEntity<?> getAll() {
    try {
      log.debug("Getting all invoices");
      Optional<List<Invoice>> optionalInvoicesList = invoiceService.getAllInvoices();
      if (optionalInvoicesList.isPresent()) {
        return new ResponseEntity<>(optionalInvoicesList.get(), HttpStatus.OK);
      }
      return new ResponseEntity<>(new ArrayList<Invoice>(), HttpStatus.OK);
    } catch (Exception e) {
      String message = "Internal server error while getting all invoices.";
      log.error(message, e);
      return new ResponseEntity<>(new ErrorMessage(message), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/{id}")
  @ApiOperation(
      value = "Get invoice by id.",
      response = Invoice.class)
  @ApiImplicitParam(name = "id", value = "Only digits possible, e.g. 7865", example = "7865", dataType = "Long")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = Invoice.class),
      @ApiResponse(code = 404, message = "Invoice not found for passed id.", response = ErrorMessage.class),
      @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)})
  public ResponseEntity<?> getById(@PathVariable("id") Long id) {
    try {
      log.debug("Getting invoice by id: {}", id);
      Optional<Invoice> optionalInvoice = invoiceService.getInvoiceById(id);
      if (optionalInvoice.isPresent()) {
        return new ResponseEntity<>(optionalInvoice.get(), HttpStatus.OK);
      }
      return new ResponseEntity<>(new ErrorMessage(String.format("Invoice not found for passed id: %d", id)), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      String message = String.format("Internal server error while getting invoice by id: %d", id);
      log.error(message, e);
      return new ResponseEntity<>(new ErrorMessage(message), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/byNumber")
  @ApiOperation(
      value = "Get invoice by number.",
      response = Invoice.class)
  @ApiImplicitParam(name = "number", value = "Possible letters, numbers and sign '/'  e.g. 'FV/789006a'", example = "FV/789006a")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = Invoice.class),
      @ApiResponse(code = 404, message = "Invoice not found for passed number.", response = ErrorMessage.class),
      @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)})
  public ResponseEntity<?> getByNumber(@RequestParam("number") String number) {
    try {
      log.debug("Getting invoice by number: {}", number);
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
      String message = String.format("Internal server error while getting invoice by number: %s", number);
      log.error(message, e);
      return new ResponseEntity<>(new ErrorMessage(message), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping
  @ApiOperation(
      value = "Add invoice.",
      response = Invoice.class)
  @ResponseStatus(HttpStatus.CREATED)
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Created", response = Invoice.class),
      @ApiResponse(code = 400, message = "Passed invoice is invalid", response = ErrorMessage.class),
      @ApiResponse(code = 409, message = "Invoice already exists", response = ErrorMessage.class),
      @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)})
  public ResponseEntity<?> add(@RequestBody(required = false) Invoice invoice) {
    try {
      log.debug("Adding invoice: {}", invoice);
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
      String message = String.format("Internal server error while adding invoice: %s", invoice);
      log.error(message, e);
      return new ResponseEntity<>(new ErrorMessage(message), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/{id}")
  @ApiOperation(
      value = "Update invoice.",
      response = Invoice.class)
  @ApiImplicitParam(name = "id", value = "Only digits possible, e.g. 7865", example = "7865", dataType = "Long")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = Invoice.class),
      @ApiResponse(code = 400, message = "Passed data is invalid.", response = ErrorMessage.class),
      @ApiResponse(code = 404, message = "Invoice not found for passed id.", response = ErrorMessage.class),
      @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)})
  public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody(required = false) Invoice invoice) {
    try {
      log.debug("Updating invoice. id: {}, invoice: {}", id, invoice);
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
      String message = String.format("Internal server error while updating invoice %d id, %s invoice", id, invoice);
      log.error(message, e);
      return new ResponseEntity<>(new ErrorMessage(message), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/{id}")
  @ApiOperation(
      value = "Delete invoice.",
      response = Invoice.class)
  @ApiImplicitParam(name = "id", value = "Only digits possible, e.g. 7865", example = "7865", dataType = "Long")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = Invoice.class),
      @ApiResponse(code = 404, message = "Invoice not found for passed id.", response = ErrorMessage.class),
      @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)})
  public ResponseEntity<?> remove(@PathVariable("id") Long id) {
    try {
      log.debug("Removing invoice. id: {}", id);
      Optional<Invoice> optionalInvoice = invoiceService.getInvoiceById(id);
      if (!optionalInvoice.isPresent()) {
        return new ResponseEntity<>(new ErrorMessage(String.format("Invoice with %d id does not exist.", id)), HttpStatus.NOT_FOUND);
      }
      invoiceService.deleteInvoiceById(id);
      return new ResponseEntity<>(optionalInvoice.get(), HttpStatus.OK);
    } catch (Exception e) {
      String message = String.format("Internal server error while removing invoice. id: %d", id);
      log.error(message, e);
      return new ResponseEntity<>(new ErrorMessage(message), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/pdf/{id}")
  @ApiOperation(
      value = "Get invoice by id.",
      response = Invoice.class)
  @ApiImplicitParam(name = "id", value = "Only digits possible, e.g. 7865", example = "7865", dataType = "Long")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = Invoice.class),
      @ApiResponse(code = 404, message = "Invoice not found for passed id.", response = ErrorMessage.class),
      @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)})
  public ResponseEntity<?> createPdf(@PathVariable("id") Long id) {
    try {
      Optional<Invoice> optionalInvoice = invoiceService.getInvoiceById(id);
      if (optionalInvoice.isPresent()) {
        byte[] invoiceAsPdf = pdfService.createPdf(optionalInvoice.get());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_PDF);
        return new ResponseEntity<>(invoiceAsPdf, responseHeaders, HttpStatus.OK);
      }
      return new ResponseEntity<>(new ErrorMessage(String.format("Invoice not found for passed id: %d", id)), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new ErrorMessage(String.format("Internal server error while getting invoice by id: %d", id)), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
