package pl.coderstrust.repository;

import pl.coderstrust.model.Invoice;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryInvoiceRepository implements InvoiceRepository<Invoice, Integer> {

  private Map<Integer, Invoice> invoices = Collections.synchronizedMap(new HashMap<>());
  private AtomicInteger counter = new AtomicInteger();
  final Object lock = new Object();

  @Override
  public Invoice save(Invoice invoice) {
    synchronized (lock) {
      if (invoice == null) {
        throw new IllegalArgumentException("Invoice cannot be null");
      }
      if (isInvoiceExist(invoice.getId())) {
        invoices.put(invoice.getId(), invoice);
        return invoice;
      }
      int id = counter.incrementAndGet();
      invoice.setId(id);
      invoices.put(id, invoice);
      return invoice;
    }
  }

  @Override
  public Invoice findById(Integer id) {
    synchronized (lock) {
      if (id == null) {
        throw new IllegalArgumentException("Id cannot be null");
      }
      if (isInvoiceExist(id)) {
        return invoices.get(id);
      }
      return null;
    }
  }

  @Override
  public boolean existsById(Integer id) {
    synchronized (lock) {
      if (id == null) {
        throw new IllegalArgumentException("Id cannot be null");
      }
      return isInvoiceExist(id);
    }
  }

  @Override
  public List<Invoice> findAll() {
    synchronized (lock) {
      return new ArrayList(invoices.values());
    }
  }

  @Override
  public synchronized long count() {
    synchronized (lock) {
      return invoices.size();
    }
  }

  @Override
  public void deleteById(Integer id) throws InvoiceRepositoryOperationException {
    synchronized (lock) {
      if (id == null) {
        throw new IllegalArgumentException("Id cannot be null");
      }
      if (!isInvoiceExist(id)) {
        throw new InvoiceRepositoryOperationException("Invoice does not exist");
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

  private boolean isInvoiceExist(Integer id) {
    return invoices.containsKey(id);
  }
}
