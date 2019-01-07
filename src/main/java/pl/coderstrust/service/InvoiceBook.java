package pl.coderstrust.service;

import java.util.List;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.repository.InvoiceRepository;
import pl.coderstrust.repository.InvoiceRepositoryOperationException;

public class InvoiceBook {

  private InvoiceRepository invoiceRepository;

  public InvoiceBook(InvoiceRepository invoiceRepository) {
    this.invoiceRepository = invoiceRepository;
  }

  public List<Invoice> getAllInvoices() throws InvoiceRepositoryOperationException {
    return (List<Invoice>) invoiceRepository.findAll();
  }

  public List<Invoice> getAllInvoicesInGivenDateRange() throws InvoiceRepositoryOperationException {
    return (List<Invoice>) invoiceRepository.findAll();
  }

  public Invoice getInvoiceById(Integer id) throws InvoiceRepositoryOperationException {
    return (Invoice) invoiceRepository.findById(id);
  }

  public Invoice addInvoice(Invoice invoice) throws InvoiceRepositoryOperationException {
    return (Invoice) invoiceRepository.save(invoice);
  }

  public void updateInvoice(Invoice invoice) {
    invoiceRepository.update(invoice);
  }

  public void deleteInvoice(Integer id) throws InvoiceRepositoryOperationException {
    invoiceRepository.deleteById(id);
  }
}
