package pl.coderstrust.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import pl.coderstrust.database.DatabaseOperationException;
import pl.coderstrust.database.InvoiceDatabase;
import pl.coderstrust.model.Invoice;

public class InvoiceService {

  private InvoiceDatabase invoiceDatabase;

  public InvoiceService(InvoiceDatabase invoiceDatabase) {
    this.invoiceDatabase = invoiceDatabase;
  }

  public Optional<List<Invoice>> getAllInvoices() throws ServiceOperationException {
    try {
      return invoiceDatabase.findAll();
    } catch (DatabaseOperationException e) {
      throw new ServiceOperationException("An error while getting all invoices.", e);
    }
  }

  public Optional<List<Invoice>> getAllInvoicesInGivenDateRange(LocalDate fromDate, LocalDate toDate) throws ServiceOperationException {
    if (fromDate == null) {
      throw new IllegalArgumentException("fromDate cannot be null");
    }
    if (toDate == null) {
      throw new IllegalArgumentException("toDate cannot be null");
    }
    if (toDate.isBefore(fromDate)) {
      throw new IllegalArgumentException("toDate cannot be before fromDate.");
    }
    Optional<List<Invoice>> allInvoicesOptional = getAllInvoices();
    List<Invoice> invoicesInDataRange = new ArrayList<>();
    if (allInvoicesOptional.isPresent()) {
      invoicesInDataRange = allInvoicesOptional
          .get()
          .stream()
          .filter(invoice -> invoice.getIssueDate().compareTo(fromDate) >= 0 && invoice.getIssueDate().compareTo(toDate) <= 0)
          .collect(Collectors.toList());
    }
    return Optional.of(invoicesInDataRange);
  }

  public Optional<Invoice> getInvoiceById(Long id) throws ServiceOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null.");
    }
    try {
      return invoiceDatabase.findById(id);
    } catch (DatabaseOperationException e) {
      throw new ServiceOperationException("An error while getting invoice.", e);
    }
  }

  public Optional<Invoice> addInvoice(Invoice invoice) throws ServiceOperationException {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice cannot be null.");
    }
    try {
      Long id = invoice.getId();
      if (id != null && invoiceDatabase.existsById(id)) {
        throw new ServiceOperationException(String.format("Invoice with id %s already exists", id));
      }
      return invoiceDatabase.save(invoice);
    } catch (DatabaseOperationException e) {
      throw new ServiceOperationException("An error while adding invoice.", e);
    }
  }

  public void updateInvoice(Invoice invoice) throws ServiceOperationException {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice cannot be null.");
    }
    try {
      Long id = invoice.getId();
      if (id == null || !invoiceDatabase.existsById(id)) {
        throw new ServiceOperationException(String.format("Invoice with id %s does not exist", id));
      }
      invoiceDatabase.save(invoice);
    } catch (DatabaseOperationException e) {
      throw new ServiceOperationException("An error while updating invoice.", e);
    }
  }

  public void deleteInvoiceById(Long id) throws ServiceOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null.");
    }
    try {
      if (!invoiceDatabase.existsById(id)) {
        throw new ServiceOperationException(String.format("Invoice with id %s does not exist", id));
      }
      invoiceDatabase.deleteById(id);
    } catch (DatabaseOperationException e) {
      throw new ServiceOperationException("An error while deleting invoice.", e);
    }
  }

  public void deleteAll() throws ServiceOperationException {
    try {
      invoiceDatabase.deleteAll();
    } catch (DatabaseOperationException e) {
      throw new ServiceOperationException("An error while deleting invoices.", e);
    }
  }
}
