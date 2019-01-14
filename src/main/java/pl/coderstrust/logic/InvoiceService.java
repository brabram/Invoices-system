package pl.coderstrust.logic;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.repository.InvoiceRepository;
import pl.coderstrust.repository.InvoiceRepositoryOperationException;

public class InvoiceService {

  private InvoiceRepository<Invoice, String> invoiceRepository;

  public InvoiceService(InvoiceRepository<Invoice, String> invoiceRepository) {
    this.invoiceRepository = invoiceRepository;
  }

  public List<Invoice> getAllInvoices() throws InvoiceServiceOperationException {
    try {
      return invoiceRepository.findAll();
    } catch (InvoiceRepositoryOperationException e) {
      throw new InvoiceServiceOperationException("An error while getting invoices.");
    }
  }

  public List<Invoice> getAllInvoicesInGivenDateRange(LocalDate fromDate, LocalDate toDate) throws InvoiceServiceOperationException {
    if (fromDate == null) {
      throw new IllegalArgumentException("FromDate cannot be null");
    }
    if (toDate == null) {
      throw new IllegalArgumentException("ToDate cannot be null");
    }
    return getAllInvoices()
        .stream()
        .filter(invoice -> invoice.getIssueDate().compareTo(fromDate) >= 0 && invoice.getIssueDate().compareTo(toDate) <= 0)
        .collect(Collectors.toList());
  }

  public Invoice getInvoiceById(String id) throws InvoiceServiceOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null.");
    }
    try {
      return invoiceRepository.findById(id);
    } catch (InvoiceRepositoryOperationException e) {
      throw new InvoiceServiceOperationException("An error while getting invoice.");
    }
  }

  public Invoice addInvoice(Invoice invoice) throws InvoiceServiceOperationException {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice cannot be null.");
    }
    try {
      return invoiceRepository.save(invoice);
    } catch (InvoiceRepositoryOperationException e) {
      throw new InvoiceServiceOperationException("An error while adding invoice.");
    }
  }

  public void updateInvoice(Invoice invoice) throws InvoiceServiceOperationException {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice cannot be null.");
    }
    try {
      invoiceRepository.save(invoice);
    } catch (InvoiceRepositoryOperationException e) {
      throw new InvoiceServiceOperationException("An error while updating invoice.");
    }
  }

  public void deleteInvoiceById(String id) throws InvoiceServiceOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null.");
    }
    try {
      if (invoiceRepository.existsById(id)) {
        invoiceRepository.deleteById(id);
      }
    } catch (InvoiceRepositoryOperationException e) {
      throw new InvoiceServiceOperationException("An error while deleting invoice.");
    }
  }

  public void deleteInvoice(Invoice invoice) throws InvoiceServiceOperationException {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice cannot be null.");
    }
    deleteInvoiceById(invoice.getId());
  }

  public void deleteAll() throws InvoiceServiceOperationException {
    try {
      invoiceRepository.deleteAll();
    } catch (InvoiceRepositoryOperationException e) {
      throw new InvoiceServiceOperationException("An error while deleting invoices.");
    }
  }
}
