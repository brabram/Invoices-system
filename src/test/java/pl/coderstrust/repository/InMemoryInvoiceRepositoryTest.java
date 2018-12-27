package pl.coderstrust.repository;

import junit.framework.Assert;
import org.junit.jupiter.api.Test;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.repository.generators.InvoiceGenerator;

import java.util.HashMap;
import java.util.Map;

class InMemoryInvoiceRepositoryTest {
    InvoiceRepository<Invoice, Integer> inMemoryInvoiceRepository = new InMemoryInvoiceRepository();

    @Test
    void save() {
    }

    @Test
    void findById() throws InvoiceRepositoryOperationException {
       // boolean actual =  inMemoryInvoiceRepository.findById(321);
       // Assert.assertEquals(false, actual);
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
        Invoice invoice = inMemoryInvoiceRepository.save(InvoiceGenerator);
        boolean exists = inMemoryInvoiceRepository.existsById(invoice.getId());
        Assert.assertTrue(exists);
        inMemoryInvoiceRepository.deleteById(invoice.getId());
        exists = inMemoryInvoiceRepository.existsById(invoice.getId());
        Assert.assertFalse(exists);

    }

    @Test
    void deleteAll() {
    }
}