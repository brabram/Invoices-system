package pl.coderstrust.rest;

import java.util.List;
import java.util.Optional;
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
@RequestMapping("/invoicesService")
public class InvoiceController {

  private InvoiceService invoiceService;

  public InvoiceController(InvoiceService invoiceService) {
    this.invoiceService = invoiceService;
  }

  @GetMapping
  public Optional<List<Invoice>> getAllInvoices() {
    return invoiceService.getAllInvoices();
  }

  @GetMapping("/{id")
  public Optional<Invoice> getInvoiceById(@PathVariable("id") Long id) {
    return invoiceService.getInvoiceById(id);
  }

  @GetMapping("/{number}")
  public Optional<Invoice> getInvoiceByNumber(@PathVariable("number") String number) {
    //do something here
    Long id = 0L;
    return invoiceService.getInvoiceById(id);
  }

  @PostMapping
  public Optional<Invoice> addInvoice(@RequestBody Invoice invoice) {
    return invoiceService.addInvoice(invoice);
  }

  @PutMapping
  public void updateInvoice(@PathVariable("id") Long id, @RequestBody Invoice invoice) {
    invoiceService.updateInvoice(invoice);
  }

  @DeleteMapping
  public void removeInvoice(@PathVariable("id") Long id) {
    invoiceService.deleteInvoiceById(id);
  }
}
