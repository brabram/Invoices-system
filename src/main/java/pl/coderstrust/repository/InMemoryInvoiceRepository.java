package pl.coderstrust.repository;

import org.springframework.stereotype.Repository;
import pl.coderstrust.model.Invoice;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryInvoiceRepository implements InvoiceRepository<Invoice, Integer> {

    private Map<Integer, Invoice> invoicesMap = new HashMap<>();
    private AtomicInteger counter = new AtomicInteger();

    @Override
    public Invoice save(Invoice o) throws InvoiceRepositoryOperationException {
        return null;
    }

    @Override
    public Invoice findById(Integer id) throws InvoiceRepositoryOperationException {
        if(id == null){
           throw new InvoiceRepositoryOperationException();
        }

        if (existsById(id)){
            return invoicesMap.get(id);
        }
        return null;
    }

    @Override
    public boolean existsById(Integer id) throws InvoiceRepositoryOperationException {
        if(id == null){
            throw new InvoiceRepositoryOperationException();
        }
        return invoicesMap.containsKey(id);
    }

    @Override
    public Iterable<Invoice> findAll() throws InvoiceRepositoryOperationException {
        return invoicesMap.values();
    }

    @Override
    public long count() throws InvoiceRepositoryOperationException {
        return invoicesMap.size();
    }

    @Override
    public void deleteById(Integer id) throws InvoiceRepositoryOperationException {
        if (existsById(id)){
            invoicesMap.remove(id);
        }
    }

    @Override
    public void deleteAll() throws InvoiceRepositoryOperationException {
        invoicesMap.clear();
    }
}
