package pl.coderstrust.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;
import pl.coderstrust.database.InMemoryInvoiceDatabase;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.service.InvoiceService;

@RequestMapping("/invoiceApplication")
@RestController
public class InvoiceController {

  private InvoiceService invoiceService = new InvoiceService(new InMemoryInvoiceDatabase());

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
