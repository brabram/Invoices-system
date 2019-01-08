package pl.coderstrust.repository;

import junit.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.repository.generators.InvoiceGenerator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class InMemoryInvoiceRepositoryTest {
  private InvoiceRepository<Invoice, Integer> invoiceRepository;

  @BeforeEach
  void setup() {
    invoiceRepository = new InMemoryInvoiceRepository();
  }

  @Test
  void shouldSaveInvoice() throws InvoiceRepositoryOperationException {
    //given
    Invoice invoice = invoiceRepository.save(InvoiceGenerator.getRandomInvoice());

    //when
    boolean existsById = invoiceRepository.existsById(invoice.getId());

    //then
    Assert.assertTrue(existsById);
  }

  @Test
  void shouldUpdateInvoice() throws InvoiceRepositoryOperationException {
    //given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    Invoice invoiceToUpdate = invoiceRepository.save(invoice);
    Assert.assertEquals(invoiceToUpdate, invoiceRepository.findById(invoiceToUpdate.getId()));
    invoiceToUpdate.setNumber(11);
    invoiceToUpdate.setTotalNetValue(BigDecimal.valueOf(5555));

    //when
    Invoice updatedInvoice = invoiceRepository.save(invoiceToUpdate);

    //then
    Assert.assertEquals(invoiceToUpdate, updatedInvoice);
    Assert.assertEquals(invoiceToUpdate, invoiceRepository.findById(updatedInvoice.getId()));
  }

  @Test
  void shouldFindOneInvoice() throws InvoiceRepositoryOperationException {
    //given
    Invoice invoice = invoiceRepository.save(InvoiceGenerator.getRandomInvoice());
    Invoice invoice2 = invoiceRepository.save(InvoiceGenerator.getRandomInvoice());
    Invoice invoice3 = invoiceRepository.save(InvoiceGenerator.getRandomInvoice());

    //when
    Invoice invoiceFromDatabase = invoiceRepository.findById(invoice.getId());
    Invoice invoiceFromDatabase2 = invoiceRepository.findById(invoice2.getId());
    Invoice invoiceFromDatabase3 = invoiceRepository.findById(invoice3.getId());

    //then
    Assert.assertEquals(invoiceFromDatabase, invoice);
    Assert.assertEquals(invoiceFromDatabase2, invoice2);
    Assert.assertEquals(invoiceFromDatabase3, invoice3);
  }

  @Test
  void shouldReturnTrueIfInvoiceExistsInDatabase() throws InvoiceRepositoryOperationException {
    //given
    Invoice invoice = invoiceRepository.save(InvoiceGenerator.getRandomInvoice());

    //when
    boolean exists = invoiceRepository.existsById(invoice.getId());

    //then
    Assert.assertTrue(exists);
  }

  @Test
  void shouldReturnFalseIfInvoiceNotExistsInDatabase() throws InvoiceRepositoryOperationException {
    //when
    boolean exists = invoiceRepository.existsById(1);

    //then
    Assert.assertFalse(exists);
  }

  @Test
  void shouldFindAllInvoices() throws InvoiceRepositoryOperationException {
    //given
    List<Invoice> list = new ArrayList<>();
    list.add(invoiceRepository.save(InvoiceGenerator.getRandomInvoice()));
    list.add(invoiceRepository.save(InvoiceGenerator.getRandomInvoice()));
    list.add(invoiceRepository.save(InvoiceGenerator.getRandomInvoice()));

    //when
    List<Invoice> actualInvoices = invoiceRepository.findAll();

    //then
    Assert.assertEquals(actualInvoices.toString(), list.toString());
  }

  @Test
  void shouldDeleteInvoice() throws InvoiceRepositoryOperationException {
    //given
    Invoice invoice = invoiceRepository.save(InvoiceGenerator.getRandomInvoice());
    boolean exists = invoiceRepository.existsById(invoice.getId());
    Assert.assertTrue(exists);

    //when
    invoiceRepository.deleteById(invoice.getId());
    exists = invoiceRepository.existsById(invoice.getId());

    //then
    Assert.assertFalse(exists);
  }

  @Test
  void shouldReturnCount() throws InvoiceRepositoryOperationException {
    //given
    List<Invoice> list = new ArrayList<>();
    list.add(invoiceRepository.save(InvoiceGenerator.getRandomInvoice()));
    list.add(invoiceRepository.save(InvoiceGenerator.getRandomInvoice()));
    list.add(invoiceRepository.save(InvoiceGenerator.getRandomInvoice()));

    //when
    int count = Math.toIntExact(invoiceRepository.count());

    //then
    Assert.assertEquals(list.size(), count);
  }

  @Test
  void shouldDeleteAllInvoices() throws InvoiceRepositoryOperationException {
    //given
    List<Invoice> expected = new ArrayList<>();
    List<Invoice> list = new ArrayList<>();
    list.add(invoiceRepository.save(InvoiceGenerator.getRandomInvoice()));
    list.add(invoiceRepository.save(InvoiceGenerator.getRandomInvoice()));
    list.add(invoiceRepository.save(InvoiceGenerator.getRandomInvoice()));

    //when
    invoiceRepository.deleteAll();
    List<Invoice> result = invoiceRepository.findAll();

    //then
    Assert.assertEquals(expected, result);
  }

  @Test
  void shouldThrowExceptionForNullIdInSaveId() {
    assertThrows(IllegalArgumentException.class, () -> invoiceRepository.save(null));
  }

  @Test
  void shouldThrowExceptionForNullIdInFindById() {

    assertThrows(IllegalArgumentException.class, () -> invoiceRepository.findById(null));
  }

  @Test
  void shouldThrowExceptionForNullIdInDeleteById() {
    assertThrows(IllegalArgumentException.class, () -> invoiceRepository.deleteById(null));
  }

  @Test
  void shouldThrowExceptionForNullIdInExistById() {
    assertThrows(IllegalArgumentException.class, () -> invoiceRepository.existsById(null));
  }
}
