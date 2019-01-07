package pl.coderstrust.repository;

import junit.framework.Assert;
import org.junit.jupiter.api.Test;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.repository.generators.InvoiceGenerator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class InMemoryInvoiceRepositoryTest {

  @Test
  void shouldSaveInvoice() throws InvoiceRepositoryOperationException {
    InvoiceRepository<Invoice, Integer> inMemoryInvoiceRepository = new InMemoryInvoiceRepository();
    Invoice invoice = inMemoryInvoiceRepository.save(InvoiceGenerator.getRandomInvoice());
    boolean existsById = inMemoryInvoiceRepository.existsById(invoice.getId());
    Assert.assertTrue(existsById);
  }

  @Test
  void shouldUpdateInvoice() throws InvoiceRepositoryOperationException {
    InvoiceRepository<Invoice, Integer> inMemoryInvoiceRepository = new InMemoryInvoiceRepository();
    int id = 1;
    Invoice invoice = inMemoryInvoiceRepository.save(InvoiceGenerator.getRandomInvoice());
    invoice.setId(id);
    boolean exists = inMemoryInvoiceRepository.existsById(invoice.getId());
    if (exists) {
      inMemoryInvoiceRepository.deleteById(1);
      Invoice invoice1 = inMemoryInvoiceRepository.save(InvoiceGenerator.getRandomInvoice());
      inMemoryInvoiceRepository.save(inMemoryInvoiceRepository.save(invoice1));
      invoice.setId(id);
      Assert.assertNotNull(inMemoryInvoiceRepository);
    }
  }

  @Test
  void shouldFindOneInvoice() throws InvoiceRepositoryOperationException {
    InvoiceRepository<Invoice, Integer> inMemoryInvoiceRepository = new InMemoryInvoiceRepository();
    Invoice invoice = inMemoryInvoiceRepository.save(InvoiceGenerator.getRandomInvoice());
    Invoice invoice2 = inMemoryInvoiceRepository.save(InvoiceGenerator.getRandomInvoice());
    Invoice invoice3 = inMemoryInvoiceRepository.save(InvoiceGenerator.getRandomInvoice());
    Invoice invoiceFromDatabase = inMemoryInvoiceRepository.findById(invoice.getId());
    Invoice invoiceFromDatabase2 = inMemoryInvoiceRepository.findById(invoice2.getId());
    Invoice invoiceFromDatabase3 = inMemoryInvoiceRepository.findById(invoice3.getId());
    Assert.assertEquals(invoiceFromDatabase, invoice);
    Assert.assertEquals(invoiceFromDatabase2, invoice2);
    Assert.assertEquals(invoiceFromDatabase3, invoice3);
  }

  @Test
  void shouldReturnTrueIfInvoiceExistsInDatabase() throws InvoiceRepositoryOperationException {
    InvoiceRepository<Invoice, Integer> inMemoryInvoiceRepository = new InMemoryInvoiceRepository();
    Invoice invoice = inMemoryInvoiceRepository.save(InvoiceGenerator.getRandomInvoice());
    boolean exists = inMemoryInvoiceRepository.existsById(invoice.getId());
    Assert.assertTrue(exists);
  }

  @Test
  void shouldReturnFalseIfInvoiceNotExistsInDatabase() throws InvoiceRepositoryOperationException {
    InvoiceRepository<Invoice, Integer> inMemoryInvoiceRepository = new InMemoryInvoiceRepository();
    boolean exists = inMemoryInvoiceRepository.existsById(1);
    Assert.assertFalse(exists);
  }

  @Test
  void shouldFindAllInvoices() throws InvoiceRepositoryOperationException {
    InvoiceRepository<Invoice, Integer> inMemoryInvoiceRepository = new InMemoryInvoiceRepository();
    List<Invoice> list = new ArrayList<>();
    list.add(inMemoryInvoiceRepository.save(InvoiceGenerator.getRandomInvoice()));
    list.add(inMemoryInvoiceRepository.save(InvoiceGenerator.getRandomInvoice()));
    list.add(inMemoryInvoiceRepository.save(InvoiceGenerator.getRandomInvoice()));
    Iterable<Invoice> actualInvoices = inMemoryInvoiceRepository.findAll();
    Assert.assertEquals(actualInvoices.toString(), list.toString());
  }

  @Test
  void shouldDeleteInvoice() throws InvoiceRepositoryOperationException {
    InvoiceRepository<Invoice, Integer> inMemoryInvoiceRepository = new InMemoryInvoiceRepository();
    Invoice invoice = inMemoryInvoiceRepository.save(InvoiceGenerator.getRandomInvoice());
    boolean exists = inMemoryInvoiceRepository.existsById(invoice.getId());
    Assert.assertTrue(exists);
    inMemoryInvoiceRepository.deleteById(invoice.getId());
    exists = inMemoryInvoiceRepository.existsById(invoice.getId());
    Assert.assertFalse(exists);
  }

  @Test
  void shouldReturnCount() throws InvoiceRepositoryOperationException {
    InvoiceRepository<Invoice, Integer> inMemoryInvoiceRepository = new InMemoryInvoiceRepository();
    List<Invoice> list = new ArrayList<>();
    list.add(inMemoryInvoiceRepository.save(InvoiceGenerator.getRandomInvoice()));
    list.add(inMemoryInvoiceRepository.save(InvoiceGenerator.getRandomInvoice()));
    list.add(inMemoryInvoiceRepository.save(InvoiceGenerator.getRandomInvoice()));
    int count = Math.toIntExact(inMemoryInvoiceRepository.count());
    Assert.assertEquals(list.size(), count);
  }

  @Test
  void shouldDeleteAllInvoices() throws InvoiceRepositoryOperationException {
    List<Invoice> list = new ArrayList<>();
    InvoiceRepository<Invoice, Integer> inMemoryInvoiceRepository = new InMemoryInvoiceRepository();
    list.add(inMemoryInvoiceRepository.save(InvoiceGenerator.getRandomInvoice()));
    list.add(inMemoryInvoiceRepository.save(InvoiceGenerator.getRandomInvoice()));
    list.add(inMemoryInvoiceRepository.save(InvoiceGenerator.getRandomInvoice()));
    inMemoryInvoiceRepository.deleteAll();
    Assert.assertNotNull(inMemoryInvoiceRepository);
  }

  @Test
  void shouldThrowExceptionForNullIdInSaveId() {
    assertThrows(IllegalArgumentException.class,
        () -> {
          InMemoryInvoiceRepository inMemoryInvoiceRepository = new InMemoryInvoiceRepository();
          inMemoryInvoiceRepository.save(null);
        });
  }

  @Test
  void shouldThrowExceptionForNullIdInFindById() {
    assertThrows(IllegalArgumentException.class,
        () -> {
          InMemoryInvoiceRepository inMemoryInvoiceRepository = new InMemoryInvoiceRepository();
          inMemoryInvoiceRepository.findById(null);
        });
  }

  @Test
  void shouldThrowExceptionForNullIdInDeleteById() {
    assertThrows(IllegalArgumentException.class,
        () -> {
          InMemoryInvoiceRepository inMemoryInvoiceRepository = new InMemoryInvoiceRepository();
          inMemoryInvoiceRepository.deleteById(null);
        });
  }

  @Test
  void shouldThrowExceptionForNullIdInExistById() {
    assertThrows(IllegalArgumentException.class,
        () -> {
          InMemoryInvoiceRepository inMemoryInvoiceRepository = new InMemoryInvoiceRepository();
          inMemoryInvoiceRepository.existsById(null);
        });
  }
}
