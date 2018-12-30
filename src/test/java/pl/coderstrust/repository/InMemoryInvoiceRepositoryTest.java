package pl.coderstrust.repository;

import junit.framework.Assert;
import org.junit.jupiter.api.Test;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.repository.generators.InvoiceGenerator;

import java.util.ArrayList;
import java.util.List;

class InMemoryInvoiceRepositoryTest {
    InvoiceRepository<Invoice, Integer> inMemoryInvoiceRepository = new InMemoryInvoiceRepository();

    @Test
    void save() throws InvoiceRepositoryOperationException {
        Invoice invoice = inMemoryInvoiceRepository.save(InvoiceGenerator.invoice());
        Assert.assertNotNull(invoice);
    }

    @Test
    void findById() throws InvoiceRepositoryOperationException {
        Invoice invoice = inMemoryInvoiceRepository.save(InvoiceGenerator.invoice());
        Invoice getId = inMemoryInvoiceRepository.findById(invoice.getId());
        Assert.assertNotNull(getId);
    }

    @Test
    void existsById() throws InvoiceRepositoryOperationException {
        Invoice invoice = inMemoryInvoiceRepository.save(InvoiceGenerator.invoice());
        boolean exists = inMemoryInvoiceRepository.existsById(invoice.getId());
        Assert.assertTrue(exists);
    }

    @Test
    void findAll() throws InvoiceRepositoryOperationException {
        Invoice invoice = inMemoryInvoiceRepository.save(InvoiceGenerator.invoice());
        Invoice invoice1 = inMemoryInvoiceRepository.save(InvoiceGenerator.invoice());
        inMemoryInvoiceRepository.findById(invoice.getNumber());
        inMemoryInvoiceRepository.findById(invoice1.getNumber());
        Iterable<Invoice> findAll = inMemoryInvoiceRepository.findAll();
        Assert.assertNotNull(findAll);
    }

    @Test
    void deleteById() throws InvoiceRepositoryOperationException {
        Invoice invoice = inMemoryInvoiceRepository.save(InvoiceGenerator.invoice());
        boolean exists = inMemoryInvoiceRepository.existsById(invoice.getId());
        Assert.assertTrue(exists);
        inMemoryInvoiceRepository.deleteById(invoice.getId());
        exists = inMemoryInvoiceRepository.existsById(invoice.getId());
        Assert.assertFalse(exists);
    }
}
