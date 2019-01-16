package pl.coderstrust.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import pl.coderstrust.model.Invoice;
import pl.coderstrust.repository.InvoiceRepository;
import pl.coderstrust.repository.InvoiceRepositoryOperationException;

public class InvoiceService {

  private InvoiceRepository invoiceRepository;

  public InvoiceService(InvoiceRepository invoiceRepository) {
    this.invoiceRepository = invoiceRepository;
  }

  List<Invoice> getAllInvoices() throws InvoiceServiceOperationException {
    try {
      return invoiceRepository.findAll();
    } catch (InvoiceRepositoryOperationException e) {
      throw new InvoiceServiceOperationException("An error while getting invoices.");
    }
  }

  List<Invoice> getAllInvoicesInGivenDateRange(LocalDate fromDate, LocalDate toDate) throws InvoiceServiceOperationException {
    if (fromDate == null) {
      throw new IllegalArgumentException("fromDate cannot be null");
    }
    if (toDate == null) {
      throw new IllegalArgumentException("toDate cannot be null");
    }
    if (toDate.isBefore(fromDate)) {
      throw new IllegalArgumentException("toDate cannot be before fromDate.");
    }
    return getAllInvoices()
        .stream()
        .filter(invoice -> invoice.getIssueDate().compareTo(fromDate) >= 0 && invoice.getIssueDate().compareTo(toDate) <= 0)
        .collect(Collectors.toList());
  }

  Invoice getInvoiceById(String id) throws InvoiceServiceOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null.");
    }
    try {
      return invoiceRepository.findById(id);
    } catch (InvoiceRepositoryOperationException e) {
      throw new InvoiceServiceOperationException("An error while getting invoice.");
    }
  }

  Invoice addInvoice(Invoice invoice) throws InvoiceServiceOperationException {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice cannot be null.");
    }
    try {
      return invoiceRepository.save(invoice);
    } catch (InvoiceRepositoryOperationException e) {
      throw new InvoiceServiceOperationException("An error while adding invoice.");
    }
  }

  void updateInvoice(Invoice invoice) throws InvoiceServiceOperationException {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice cannot be null.");
    }
    try {
      if (invoiceRepository.existsById(invoice.getId())) {
        invoiceRepository.save(invoice);
      }
    } catch (InvoiceRepositoryOperationException e) {
      throw new InvoiceServiceOperationException("An error while updating invoice.");
    }
  }

  void deleteInvoiceById(String id) throws InvoiceServiceOperationException {
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

  void deleteAll() throws InvoiceServiceOperationException {
    try {
      invoiceRepository.deleteAll();
    } catch (InvoiceRepositoryOperationException e) {
      throw new InvoiceServiceOperationException("An error while deleting invoices.");
    }
  }
}
