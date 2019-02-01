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
      log.info("Getting all invoices from database.");
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
    return Optional.of(invoicesInDataRange);
  }

  public Optional<Invoice> getInvoiceById(Long id) throws ServiceOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null.");
    }
    try {
      log.info("Getting invoice by id from database.");
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

  public boolean invoiceExistsById(Long id) throws ServiceOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null.");
    }
    try {
      return invoiceDatabase.existsById(id);
    } catch (DatabaseOperationException e) {
      throw new ServiceOperationException("An error while checking if invoice exist.", e);
    }
  }
}
