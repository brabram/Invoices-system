package pl.coderstrust.repository;

import pl.coderstrust.model.Invoice;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryInvoiceRepository implements InvoiceRepository<Invoice, Integer> {

    private Map<Integer, Invoice> invoicesMap = Collections.synchronizedMap(new HashMap<>());
    private AtomicInteger counter = new AtomicInteger();
    Object lock = new Object();

    @Override
    public Invoice save(Invoice invoice) throws InvoiceRepositoryOperationException {
        synchronized (lock) {
            if (invoice == null) {
                throw new IllegalArgumentException("Invoice cannot be empty");
            }
            if (invoicesMap.containsKey(invoice.getId())) {
                invoicesMap.put(invoice.getId(), invoice);
            }
            int id = counter.incrementAndGet();
            invoice.setId(id);
            invoicesMap.put(id, invoice);
            return invoice;
        }
    }

    @Override
    public Invoice findById(Integer id) throws InvoiceRepositoryOperationException {
        synchronized (lock) {
            if (id == null) {
                throw new IllegalArgumentException("Id cannot be empty");
            }
            if (existsById(id)) {
                return invoicesMap.get(id);
            }
        }
        return null;
    }

    @Override
    public boolean existsById(Integer id) throws InvoiceRepositoryOperationException {
        synchronized (lock) {
            if (id == null) {
                throw new IllegalArgumentException("Id cannot be empty");
            }
            return invoicesMap.containsKey(id);
        }
    }

    @Override
    public Iterable<Invoice> findAll() throws InvoiceRepositoryOperationException {
        synchronized (lock) {
            return invoicesMap.values();
        }
    }

    @Override
    public synchronized long count() throws InvoiceRepositoryOperationException {
        synchronized (lock) {
            return invoicesMap.size();
        }
    }

    @Override
    public void deleteById(Integer id) throws InvoiceRepositoryOperationException {
        synchronized (lock) {
            if (existsById(id)) {
                invoicesMap.remove(id);
            }
        }
    }

    @Override
    public void deleteAll() throws InvoiceRepositoryOperationException {
        synchronized (lock) {
            invoicesMap.clear();
        }
    }
}
