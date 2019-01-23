package pl.coderstrust.database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import pl.coderstrust.model.Invoice;

public class InMemoryInvoiceDatabase implements InvoiceDatabase {

  private Map<Long, Invoice> invoices = Collections.synchronizedMap(new HashMap<>());
  private AtomicLong counter = new AtomicLong();
  private final Object lock = new Object();

  @Override
  public Optional<Invoice> save(Invoice invoice) {
    synchronized (lock) {
      if (invoice == null) {
        throw new IllegalArgumentException("Invoice cannot be null");
      }
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
      if (id < 0) {
        throw new IllegalArgumentException("Id cannot be lower than zero");
      }
      return Optional.ofNullable(invoices.get(id));
    }
  }

  @Override
  public boolean existsById(Long id) {
    synchronized (lock) {
      if (id == null) {
        throw new IllegalArgumentException("Id cannot be null");
      }
      if (id < 0) {
        throw new IllegalArgumentException("Id cannot be lower than zero");
      }
      return isInvoiceExist(id);
    }
  }

  @Override
  public Optional<List<Invoice>> findAll() {
    synchronized (lock) {
      return Optional.of(new ArrayList<>(invoices.values()));
    }
  }

  @Override
  public synchronized long count() {
    synchronized (lock) {
      return invoices.size();
    }
  }

  @Override
  public void deleteById(Long id) throws InvoiceDatabaseOperationException {
    synchronized (lock) {
      if (id == null) {
        throw new IllegalArgumentException("Id cannot be null");
      }
      if (id < 0) {
        throw new IllegalArgumentException("Id cannot be lower than zero");
      }
      if (!isInvoiceExist(id)) {
        throw new InvoiceDatabaseOperationException("Invoice does not exist");
      }
      invoices.remove(id);
    }
  }

  @Override
  public void deleteAll() {
    synchronized (lock) {
      invoices.clear();
    }
  }

  private boolean isInvoiceExist(long id) {
    return invoices.containsKey(id);
  }
}
