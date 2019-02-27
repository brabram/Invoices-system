package pl.coderstrust.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.coderstrust.database.DatabaseOperationException;
import pl.coderstrust.database.InvoiceDatabase;
import pl.coderstrust.model.Invoice;

@Service
public class InvoiceService {
  private static Logger log = LoggerFactory.getLogger(InvoiceService.class);
  private InvoiceDatabase invoiceDatabase;

  @Autowired
  public InvoiceService(InvoiceDatabase invoiceDatabase) {
    this.invoiceDatabase = invoiceDatabase;
  }

  public Optional<List<Invoice>> getAllInvoices() throws ServiceOperationException {
    try {
      log.debug("Getting all invoices from database.");
      return invoiceDatabase.findAll();
    } catch (DatabaseOperationException e) {
      String message = "An error while getting all invoices from database.";
      log.error(message, e);
      throw new ServiceOperationException(message, e);
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
    log.debug("Getting all invoices from database in the specified range: from date: {} - to date: {}", fromDate, toDate);
    return Optional.of(invoicesInDataRange);
  }

  public Optional<Invoice> getInvoiceById(Long id) throws ServiceOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null.");
    }
    try {
      log.debug("Getting invoice by id from database: {}", id);
      return invoiceDatabase.findById(id);
    } catch (DatabaseOperationException e) {
      String message = String.format("An error while getting invoice by id: %d", id);
      log.error(message, e);
      throw new ServiceOperationException(message, e);
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
      log.debug("Adding invoice to database: {}", invoice);
      return invoiceDatabase.save(invoice);
    } catch (DatabaseOperationException e) {
      String message = String.format("An error while adding invoice: %s", invoice);
      log.error(message, e);
      throw new ServiceOperationException(message, e);
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
      log.debug("Updating invoice. id: {}, invoice: {}", id, invoice);
      invoiceDatabase.save(invoice);
    } catch (DatabaseOperationException e) {
      String message = String.format("An error while updating invoice %d id, %s invoice", invoice.getId(), invoice);
      log.error(message, e);
      throw new ServiceOperationException(message, e);
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
      log.debug("Removing invoice by id: {}", id);
      invoiceDatabase.deleteById(id);
    } catch (DatabaseOperationException e) {
      String message = String.format("An error while deleting invoice by id: %d", id);
      log.error(message, e);
      throw new ServiceOperationException(message, e);
    }
  }

  public void deleteAll() throws ServiceOperationException {
    try {
      log.debug("All invoices was deleted");
      invoiceDatabase.deleteAll();
    } catch (DatabaseOperationException e) {
      String message = "An error while deleting all invoices.";
      log.error(message, e);
      throw new ServiceOperationException(message, e);
    }
  }

  public boolean invoiceExistsById(Long id) throws ServiceOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null.");
    }
    try {
      log.debug("Checked if invoice exist by id: {}", id);
      return invoiceDatabase.existsById(id);
    } catch (DatabaseOperationException e) {
      String message = String.format("An error while checking if invoice exist by id: %d", id);
      log.error(message, e);
      throw new ServiceOperationException(message, e);
    }
  }
}
