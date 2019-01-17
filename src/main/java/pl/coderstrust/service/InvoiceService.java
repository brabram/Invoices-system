package pl.coderstrust.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import pl.coderstrust.database.InvoiceDatabase;
import pl.coderstrust.database.InvoiceDatabaseOperationException;
import pl.coderstrust.model.Invoice;

public class InvoiceService {

  private InvoiceDatabase invoiceDatabase;

  public InvoiceService(InvoiceDatabase invoiceDatabase) {
    this.invoiceDatabase = invoiceDatabase;
  }

  public Optional<List<Invoice>> getAllInvoices() throws InvoiceServiceOperationException {
    try {
      return invoiceDatabase.findAll();
    } catch (InvoiceDatabaseOperationException e) {
      throw new InvoiceServiceOperationException("An error while getting invoices.");
    }
  }

  Optional<List<Invoice>> getAllInvoicesInGivenDateRange(LocalDate fromDate, LocalDate toDate) throws InvoiceServiceOperationException {
    if (fromDate == null) {
      throw new IllegalArgumentException("fromDate cannot be null");
    }
    if (toDate == null) {
      throw new IllegalArgumentException("toDate cannot be null");
    }
    if (toDate.isBefore(fromDate)) {
      throw new IllegalArgumentException("toDate cannot be before fromDate.");
    }
    if (getAllInvoices().isPresent()) {
      return Optional.of(getAllInvoices()
          .get()
          .stream()
          .filter(invoice -> invoice.getIssueDate().compareTo(fromDate) >= 0 && invoice.getIssueDate().compareTo(toDate) <= 0)
          .collect(Collectors.toList()));
    }
    return Optional.of(new ArrayList<>());
  }

  Optional<Invoice> getInvoiceById(Long id) throws InvoiceServiceOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null.");
    }
    try {
      return invoiceDatabase.findById(id);
    } catch (InvoiceDatabaseOperationException e) {
      throw new InvoiceServiceOperationException("An error while getting invoice.");
    }
  }

  Optional<Invoice> addInvoice(Invoice invoice) throws InvoiceServiceOperationException {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice cannot be null.");
    }
    try {
      return invoiceDatabase.save(invoice);
    } catch (InvoiceDatabaseOperationException e) {
      throw new InvoiceServiceOperationException("An error while adding invoice.");
    }
  }

  void updateInvoice(Invoice invoice) throws InvoiceServiceOperationException {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice cannot be null.");
    }
    try {
      if (invoiceDatabase.existsById(invoice.getId())) {
        invoiceDatabase.save(invoice);
      }
    } catch (InvoiceDatabaseOperationException e) {
      throw new InvoiceServiceOperationException("An error while updating invoice.");
    }
  }

  void deleteInvoiceById(Long id) throws InvoiceServiceOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null.");
    }
    try {
      if (invoiceDatabase.existsById(id)) {
        invoiceDatabase.deleteById(id);
      }
    } catch (InvoiceDatabaseOperationException e) {
      throw new InvoiceServiceOperationException("An error while deleting invoice.");
    }
  }

  void deleteAll() throws InvoiceServiceOperationException {
    try {
      invoiceDatabase.deleteAll();
    } catch (InvoiceDatabaseOperationException e) {
      throw new InvoiceServiceOperationException("An error while deleting invoices.");
    }
  }
}
