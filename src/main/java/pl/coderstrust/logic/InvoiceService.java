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

  public List<Invoice> getAllInvoices() throws InvoiceBookOperationException {
    try {
      return invoiceRepository.findAll();
    } catch (InvoiceRepositoryOperationException e) {
      throw new InvoiceBookOperationException("An error while getting invoices.");
    }
  }

  public List<Invoice> getAllInvoicesInGivenDateRange(LocalDate fromDate, LocalDate toDate) throws InvoiceBookOperationException {
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

  public Invoice getInvoiceById(Integer id) throws InvoiceBookOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null.");
    }
    if (id < 0) {
      throw new IllegalArgumentException("Id cannot be less then zero.");
    }
    try {
      return invoiceRepository.findById(id);
    } catch (InvoiceRepositoryOperationException e) {
      throw new InvoiceBookOperationException("An error while getting invoices.");
    }
  }

  public Invoice addInvoice(Invoice invoice) throws InvoiceBookOperationException {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice cannot be null.");
    }
    try {
      return invoiceRepository.save(invoice);
    } catch (InvoiceRepositoryOperationException e) {
      throw new InvoiceBookOperationException("An error while getting invoices.");
    }
  }

  public void updateInvoice(Invoice invoice) throws InvoiceBookOperationException {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice cannot be null.");
    }
    try {
      invoiceRepository.save(invoice);
    } catch (InvoiceRepositoryOperationException e) {
      throw new InvoiceBookOperationException("An error while getting invoices.");
    }
  }

  public void deleteInvoiceById(Integer id) throws InvoiceBookOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null.");
    }
    if (id < 0) {
      throw new IllegalArgumentException("Id cannot be less then zero.");
    }
    try {
      if (invoiceRepository.existsById(id)) {
        invoiceRepository.deleteById(id);
      }
    } catch (InvoiceRepositoryOperationException e) {
      throw new InvoiceBookOperationException("An error while getting invoices.");
    }
  }

  public void deleteInvoice(Invoice invoice) throws InvoiceBookOperationException {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice cannot be null.");
    }
    deleteInvoiceById(invoice.getId());
  }

  public void deleteAll() throws InvoiceBookOperationException {
    try {
      invoiceRepository.deleteAll();
    } catch (InvoiceRepositoryOperationException e) {
      throw new InvoiceBookOperationException("An error while getting invoices.");
    }
  }
}
