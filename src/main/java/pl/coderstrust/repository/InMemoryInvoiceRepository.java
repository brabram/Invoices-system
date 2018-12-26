package pl.coderstrust.repository;

import org.springframework.stereotype.Repository;
import pl.coderstrust.model.Invoice;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryInvoiceRepository implements InvoiceRepository {

    private Map<Long, Invoice> invoicesMap = new HashMap<>();
    private AtomicInteger counter = new AtomicInteger();

    @Override
    public Object save(Object o) throws InvoiceRepositoryOperationException {
        return null;
    }

    @Override
    public Object findById(Object id) throws InvoiceRepositoryOperationException {
        if(id == null){
           throw new InvoiceRepositoryOperationException();
        }
        return invoicesMap.get(id).getId();
    }

    @Override
    public boolean existsById(Object id) throws InvoiceRepositoryOperationException {
        if(!invoicesMap.containsKey(id)){
            throw new InvoiceRepositoryOperationException();
        }
        return invoicesMap.containsKey(id);
    }

    @Override
    public Iterable findAll() throws InvoiceRepositoryOperationException {
        if(invoicesMap == null){
            throw new InvoiceRepositoryOperationException();
        }
        return invoicesMap.keySet();
    }

    @Override
    public long count() throws InvoiceRepositoryOperationException {
        if(invoicesMap.size() == 0){
            throw new InvoiceRepositoryOperationException();
        }
        return invoicesMap.size();
    }

    @Override
    public void deleteById(Object id) throws InvoiceRepositoryOperationException {
        if(!invoicesMap.containsKey(id)){
            throw new InvoiceRepositoryOperationException();
        }
        invoicesMap.remove(id).getId();
    }

    @Override
    public void deleteAll() throws InvoiceRepositoryOperationException {
        if(invoicesMap == null){
            throw new InvoiceRepositoryOperationException();
        }
        invoicesMap.clear();
    }
}
