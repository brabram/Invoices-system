package pl.coderstrust.service.rest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
      log.debug("Getting all invoices");
      return invoiceDatabase.findAll();
    } catch (DatabaseOperationException e) {
      String message = "An error while getting all invoices";
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
    log.debug("Getting all invoices in the specified range: from: {} - to: {}", fromDate, toDate);
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
      log.debug("Getting invoice by id: {}", id);
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
      log.debug("Adding invoice: {}", invoice);
      Long id = invoice.getId();
      if (id != null && invoiceDatabase.existsById(id)) {
        throw new ServiceOperationException(String.format("Invoice with id %s already exists", id));
      }
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
      log.debug("Updating invoice. id: {}, invoice: {}", invoice.getId(), invoice);
      Long id = invoice.getId();
      if (id == null || !invoiceDatabase.existsById(id)) {
        throw new ServiceOperationException(String.format("Invoice with id %s does not exist", id));
      }
      invoiceDatabase.save(invoice);
    } catch (DatabaseOperationException e) {
      String message = String.format("An error while updating invoice id: %d, invoice: %s", invoice.getId(), invoice);
      log.error(message, e);
      throw new ServiceOperationException(message, e);
    }
  }

  public void deleteInvoiceById(Long id) throws ServiceOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null.");
    }
    try {
      log.debug("Removing invoice by id: {}", id);
      if (!invoiceDatabase.existsById(id)) {
        throw new ServiceOperationException(String.format("Invoice with id: %s does not exist", id));
      }
      invoiceDatabase.deleteById(id);
    } catch (DatabaseOperationException e) {
      String message = String.format("An error while removing invoice by id: %d", id);
      log.error(message, e);
      throw new ServiceOperationException(message, e);
    }
  }

  public void deleteAll() throws ServiceOperationException {
    try {
      log.debug("Removing all invoices.");
      invoiceDatabase.deleteAll();
    } catch (DatabaseOperationException e) {
      String message = "An error while removing all invoices.";
      log.error(message, e);
      throw new ServiceOperationException(message, e);
    }
  }

  public boolean invoiceExistsById(Long id) throws ServiceOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null.");
    }
    try {
      log.debug("Checking if invoice exists by id: {}", id);
      return invoiceDatabase.existsById(id);
    } catch (DatabaseOperationException e) {
      String message = String.format("An error while checking if invoice exists by id: %d", id);
      log.error(message, e);
      throw new ServiceOperationException(message, e);
    }
  }
}
