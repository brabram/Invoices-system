package pl.coderstrust.rest;

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

  public InvoiceController(InvoiceService invoiceService) {
    this.invoiceService = invoiceService;
  }

  @GetMapping
  public Optional<List<Invoice>> getAllInvoices() {
    return invoiceService.getAllInvoices();
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

//  @GetMapping("/{number}")
//  public Optional<Invoice> getInvoiceByNumber(@PathVariable("number") String number) {
//    //do something here
//    return invoiceService.getInvoiceById(id);
//  }

  @PostMapping
  public Optional<Invoice> addInvoice(@RequestBody Invoice invoice) {
    return invoiceService.addInvoice(invoice);
  }

  @PutMapping("/{id}")
  public void updateInvoice(@PathVariable("id") Long id, @RequestBody Invoice invoice) {
    invoiceService.updateInvoice(invoice);
  }

  @DeleteMapping("/{id}")
  public void removeInvoice(@PathVariable("id") Long id) {
    invoiceService.deleteInvoiceById(id);
  }
}
