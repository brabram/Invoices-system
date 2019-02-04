package pl.coderstrust.database;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.NonTransientDataAccessException;
import pl.coderstrust.model.Invoice;

//@Repository
public class HibernateInvoiceDatabase implements InvoiceDatabase {

  private HibernateInvoiceRepository hibernateInvoiceRepository;

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
      return Optional.of(hibernateInvoiceRepository.save(invoice));
    } catch (NonTransientDataAccessException e) {
      throw new DatabaseOperationException("An error while saving invoice.", e);
    }
  }

  @Override
  public Optional<Invoice> findById(Long id) throws DatabaseOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    if (id < 0) {
      throw new IllegalArgumentException("Id cannot be less than 0");
    }
    try {
      return hibernateInvoiceRepository.findById(id);
    } catch (NoSuchElementException e) {
      throw new DatabaseOperationException("An error while searching for invoice.", e);
    }
  }

  @Override
  public boolean existsById(Long id) throws DatabaseOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    if (id < 0) {
      throw new IllegalArgumentException("Id cannot be less than 0");
    }
    try {
      return hibernateInvoiceRepository.existsById(id);
    } catch (NonTransientDataAccessException e) {
      throw new DatabaseOperationException("An error while looking for invoice.", e);
    }
  }

  @Override
  public Optional<List<Invoice>> findAll() throws DatabaseOperationException {
    try {
      return Optional.of(hibernateInvoiceRepository.findAll());
    } catch (NonTransientDataAccessException e) {
      throw new DatabaseOperationException("An error while searching for invoices.", e);
    }
  }

  @Override
  public long count() throws DatabaseOperationException {
    try {
      return hibernateInvoiceRepository.count();
    } catch (NonTransientDataAccessException e) {
      throw new DatabaseOperationException("An error while counting invoices.", e);
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
      hibernateInvoiceRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new DatabaseOperationException("There was no invoice in database.", e);
    }
  }

  @Override
  public void deleteAll() throws DatabaseOperationException {
    try {
      hibernateInvoiceRepository.deleteAll();
    } catch (NonTransientDataAccessException e) {
      throw new DatabaseOperationException("An error while deleting all invoices.", e);
    }
  }
}
