package pl.coderstrust.logic;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.repository.InvoiceRepository;
import pl.coderstrust.repository.InvoiceRepositoryOperationException;

public class InvoiceService {

  private InvoiceRepository<Invoice, Integer> invoiceRepository;

  public InvoiceService(InvoiceRepository<Invoice, Integer> invoiceRepository) {
    this.invoiceRepository = invoiceRepository;
  }

  public List<Invoice> getAllInvoices() throws InvoiceRepositoryOperationException {
    return invoiceRepository.findAll();
  }

  public List<Invoice> getAllInvoicesInGivenDateRange(LocalDate fromDate, LocalDate toDate) throws InvoiceRepositoryOperationException {
    if (fromDate == null) {
      throw new IllegalArgumentException("FromDate cannot be null");
    }
    if (toDate == null) {
      throw new IllegalArgumentException("ToDate cannot be null");
    }
    return getAllInvoices().stream().filter(invoice -> invoice.getIssueDate().compareTo(fromDate) >= 0 && invoice.getIssueDate().compareTo(toDate) <= 0).collect(Collectors.toList());
  }

  public Invoice getInvoiceById(Integer id) throws InvoiceRepositoryOperationException {
    if (id < 0) {
      throw new IllegalArgumentException("Id cannot be less then zero.");
    }
    return invoiceRepository.findById(id);
  }

  public Invoice addInvoice(Invoice invoice) throws InvoiceRepositoryOperationException {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice cannot be null.");
    }
    return invoiceRepository.save(invoice);
  }

  public void updateInvoice(Invoice invoice) throws InvoiceRepositoryOperationException {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice cannot be null.");
    }
    invoiceRepository.save(invoice);
  }

  public void deleteInvoiceById(Integer id) throws InvoiceRepositoryOperationException {
    if (id < 0) {
      throw new IllegalArgumentException("Id cannot be less then zero.");
    }
    invoiceRepository.deleteById(id);
  }

  public void deleteInvoice(Invoice invoice) throws InvoiceRepositoryOperationException {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice cannot be null.");
    }
    if (invoiceRepository.existsById(invoice.getId())) {
      deleteInvoiceById(invoice.getId());
    }
  }

  public void deleteAll() throws InvoiceRepositoryOperationException {
    invoiceRepository.deleteAll();
  }
}
