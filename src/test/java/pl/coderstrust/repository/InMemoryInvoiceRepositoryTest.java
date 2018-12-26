package pl.coderstrust.repository;

import junit.framework.Assert;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class InMemoryInvoiceRepositoryTest {
    private Map<Integer, Integer> invoiceMap = new HashMap<>();
    InMemoryInvoiceRepository inMemoryInvoiceRepository = new InMemoryInvoiceRepository();

    @Test
    void save() {
    }

    @Test
    void findById() throws InvoiceRepositoryOperationException {
        boolean actual = (boolean) inMemoryInvoiceRepository.findById(321);
        Assert.assertEquals(false, actual);
    }

    @Test
    void existsById() throws InvoiceRepositoryOperationException { ;
        boolean actual = inMemoryInvoiceRepository.existsById(123);
        Assert.assertEquals(false, actual);
    }


    @Test
    void findAll() {
    }

    @Test
    void deleteById() throws InvoiceRepositoryOperationException {
        invoiceMap.put(321, 123);
        invoiceMap.remove(321);
        Integer actual = (Integer) inMemoryInvoiceRepository.findById(321);
        Assert.assertNull(actual);

    }

    @Test
    void deleteAll() {
    }
}