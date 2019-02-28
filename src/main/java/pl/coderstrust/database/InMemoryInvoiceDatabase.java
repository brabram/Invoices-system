package pl.coderstrust.database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import pl.coderstrust.model.Invoice;

@ConditionalOnProperty(name = "pl.coderstrust.database", havingValue = "in-memory")
@Repository
public class InMemoryInvoiceDatabase implements InvoiceDatabase {
  private static Logger log = LoggerFactory.getLogger(InMemoryInvoiceDatabase.class);
  private Map<Long, Invoice> invoices = Collections.synchronizedMap(new HashMap<>());
  private AtomicLong counter = new AtomicLong();
  private final Object lock = new Object();

  @Override
  public Optional<Invoice> save(Invoice invoice) {
    synchronized (lock) {
      if (invoice == null) {
        throw new IllegalArgumentException("Invoice cannot be null");
      }
      log.debug("Saving invoice: {}", invoice);
      if (isInvoiceExist(invoice.getId())) {
        invoices.put(invoice.getId(), invoice);
        return Optional.of(invoice);
      }
      long id = counter.incrementAndGet();
      invoice.setId(id);
      invoices.put(id, invoice);
      return Optional.of(invoice);
    }
  }

  @Override
  public Optional<Invoice> findById(Long id) {
    synchronized (lock) {
      if (id == null) {
        throw new IllegalArgumentException("Id cannot be null");
      }
      log.debug("Searching invoice by id: {}", id);
      return Optional.ofNullable(invoices.get(id));
    }
  }

  @Override
  public boolean existsById(Long id) {
    synchronized (lock) {
      if (id == null) {
        throw new IllegalArgumentException("Id cannot be null");
      }
      log.debug("Checking if invoice exists by id: {}", id);
      return isInvoiceExist(id);
    }
  }

  @Override
  public Optional<List<Invoice>> findAll() {
    synchronized (lock) {
      log.debug("Getting all invoices.");
      return Optional.of(new ArrayList<>(invoices.values()));
    }
  }

  @Override
  public synchronized long count() {
    synchronized (lock) {
      log.debug("Getting number of invoices.");
      return invoices.size();
    }
  }

  @Override
  public void deleteById(Long id) throws DatabaseOperationException {
    synchronized (lock) {
      if (id == null) {
        throw new IllegalArgumentException("Id cannot be null");
      }
      if (!isInvoiceExist(id)) {
        throw new DatabaseOperationException("Invoice does not exist");
      }
      log.debug("Removing invoice by id: {}", id);
      invoices.remove(id);
    }
  }

  @Override
  public void deleteAll() {
    synchronized (lock) {
      log.debug("Removing all invoices.");
      invoices.clear();
    }
  }

  private boolean isInvoiceExist(long id) {
    log.info("Checking if invoice exists by id: {}", id);
    return invoices.containsKey(id);
  }
}
