package pl.coderstrust.database;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.stereotype.Repository;
import pl.coderstrust.model.Invoice;

@ConditionalOnProperty(name = "pl.coderstrust.database", havingValue = "hibernate")
@Repository
public class HibernateInvoiceDatabase implements InvoiceDatabase {
  private HibernateInvoiceRepository hibernateInvoiceRepository;
  private static Logger log = LoggerFactory.getLogger(HibernateInvoiceDatabase.class);

  @Autowired
  public HibernateInvoiceDatabase(HibernateInvoiceRepository hibernateInvoiceRepository) {
    this.hibernateInvoiceRepository = hibernateInvoiceRepository;
  }

  @Override
  public Optional<Invoice> save(Invoice invoice) throws DatabaseOperationException {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice cannot be null");
    }
    try {
      log.debug("Saving invoice in hibernate database: {}", invoice);
      return Optional.of(hibernateInvoiceRepository.save(invoice));
    } catch (NonTransientDataAccessException e) {
      String message = String.format("An error while saving invoice: %s", invoice);
      log.error(message, e);
      throw new DatabaseOperationException(message, e);
    }
  }

  @Override
  public Optional<Invoice> findById(Long id) throws DatabaseOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    try {
      log.debug("Searching invoice from hibernate database by id: {} ", id);
      return hibernateInvoiceRepository.findById(id);
    } catch (NoSuchElementException e) {
      String message = String.format("An error while searching for invoice by id: %d", id);
      log.error(message, e);
      throw new DatabaseOperationException(message, e);
    }
  }

  @Override
  public boolean existsById(Long id) throws DatabaseOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    try {
      log.debug("Checking if invoice exist in hibernate database by id: {}", id);
      return hibernateInvoiceRepository.existsById(id);
    } catch (NonTransientDataAccessException e) {
      String message = "An error while looking for invoice.";
      log.error(message, e);
      throw new DatabaseOperationException(message, e);
    }
  }

  @Override
  public Optional<List<Invoice>> findAll() throws DatabaseOperationException {
    try {
      log.debug("Searching all invoices in hibernate database");
      return Optional.of(hibernateInvoiceRepository.findAll());
    } catch (NonTransientDataAccessException e) {
      String message = "An error while searching for all invoices.";
      log.error(message, e);
      throw new DatabaseOperationException(message, e);
    }
  }

  @Override
  public long count() throws DatabaseOperationException {
    try {
      log.debug("Checking number of invoices in hibernate database");
      return hibernateInvoiceRepository.count();
    } catch (NonTransientDataAccessException e) {
      String message = "An error while counting invoices.";
      log.error(message, e);
      throw new DatabaseOperationException(message, e);
    }
  }

  @Override
  public void deleteById(Long id) throws DatabaseOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    if (id < 0) {
      throw new IllegalArgumentException("Id cannot be less than 0");
    }
    try {
      log.debug("Deleting invoice by id from hibernate database: {}", id);
      hibernateInvoiceRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      String message = String.format("There was no invoice in database by id: %d", id);
      log.error(message, e);
      throw new DatabaseOperationException(message, e);
    }
  }

  @Override
  public void deleteAll() throws DatabaseOperationException {
    try {
      log.debug("Deleting all invoices from hibernate database");
      hibernateInvoiceRepository.deleteAll();
    } catch (NonTransientDataAccessException e) {
      String message = "An error while deleting all invoices.";
      log.error(message, e);
      throw new DatabaseOperationException(message, e);
    }
  }
}
