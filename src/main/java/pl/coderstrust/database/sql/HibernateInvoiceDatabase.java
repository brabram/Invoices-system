package pl.coderstrust.database.sql;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.stereotype.Repository;
import pl.coderstrust.database.DatabaseOperationException;
import pl.coderstrust.database.InvoiceDatabase;
import pl.coderstrust.database.sql.model.SqlInvoice;
import pl.coderstrust.model.Invoice;

@ConditionalOnProperty(name = "pl.coderstrust.database", havingValue = "hibernate")
@Repository
public class HibernateInvoiceDatabase implements InvoiceDatabase {

  private static Logger log = LoggerFactory.getLogger(HibernateInvoiceDatabase.class);
  private HibernateInvoiceRepository hibernateInvoiceRepository;
  private final SqlModelMapper sqlModelMapper;

  @Autowired
  public HibernateInvoiceDatabase(HibernateInvoiceRepository hibernateInvoiceRepository, SqlModelMapper sqlModelMapper) {
    if (hibernateInvoiceRepository == null) {
      throw new IllegalArgumentException("hibernateInvoiceRepository cannot be null");
    }
    if (sqlModelMapper == null) {
      throw new IllegalArgumentException("sqlModelMapper cannot be null");
    }

    this.hibernateInvoiceRepository = hibernateInvoiceRepository;
    this.sqlModelMapper = sqlModelMapper;
  }

  @Override
  public Optional<Invoice> save(Invoice invoice) throws DatabaseOperationException {
    if (invoice == null) {
      throw new IllegalArgumentException("SqlInvoice cannot be null");
    }
    try {
      log.debug("Saving invoice: {}", invoice);
      SqlInvoice savedSqlInvoice = hibernateInvoiceRepository.save(sqlModelMapper.mapInvoice(invoice));
      return Optional.of(sqlModelMapper.mapInvoice(savedSqlInvoice));
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
      log.debug("Getting invoice by id: {} ", id);
      Optional<SqlInvoice> optionalInvoice = hibernateInvoiceRepository.findById(id);
      if (optionalInvoice.isPresent()) {
        return Optional.of(sqlModelMapper.mapInvoice(optionalInvoice.get()));
      }
      return Optional.empty();
    } catch (NoSuchElementException e) {
      String message = String.format("An error while getting invoice by id: %s", id);
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
      log.debug("Checking if invoice exists by id: {}", id);
      return hibernateInvoiceRepository.existsById(id);
    } catch (NonTransientDataAccessException e) {
      String message = String.format("An error while checking if invoice exist by id: %s", id);
      log.error(message, e);
      throw new DatabaseOperationException(message, e);
    }
  }

  @Override
  public Optional<List<Invoice>> findAll() throws DatabaseOperationException {
    try {
      log.debug("Getting all invoices.");
      List<Invoice> result = hibernateInvoiceRepository.findAll()
          .stream()
          .map(invoice -> sqlModelMapper.mapInvoice(invoice))
          .collect(Collectors.toList());
      return Optional.of(result);
    } catch (NonTransientDataAccessException e) {
      String message = "An error while getting all invoices.";
      log.error(message, e);
      throw new DatabaseOperationException(message, e);
    }
  }

  @Override
  public long count() throws DatabaseOperationException {
    try {
      log.debug("Getting withNumber of invoices.");
      return hibernateInvoiceRepository.count();
    } catch (NonTransientDataAccessException e) {
      String message = "An error while getting withNumber of invoices.";
      log.error(message, e);
      throw new DatabaseOperationException(message, e);
    }
  }

  @Override
  public void deleteById(Long id) throws DatabaseOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    try {
      log.debug("Deleting invoice by id: {}", id);
      if (!hibernateInvoiceRepository.existsById(id)) {
        throw new DatabaseOperationException(String.format("There was no invoice in database by id: %d", id));
      }
      hibernateInvoiceRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      String message = String.format("There was no invoice in database by id: %s", id);
      log.error(message, e);
      throw new DatabaseOperationException(message, e);
    }
  }

  @Override
  public void deleteAll() throws DatabaseOperationException {
    try {
      log.debug("Removing all invoices.");
      hibernateInvoiceRepository.deleteAll();
    } catch (NonTransientDataAccessException e) {
      String message = "An error while removing  all invoices.";
      log.error(message, e);
      throw new DatabaseOperationException(message, e);
    }
  }
}
