package pl.coderstrust.database;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.coderstrust.generators.InvoiceGenerator;
import pl.coderstrust.model.Invoice;

class InMemoryInvoiceDatabaseTest {

  private InvoiceDatabase invoiceDatabase;

  @BeforeEach
  void setup() {
    invoiceDatabase = new InMemoryInvoiceDatabase();
  }

  @Test
  void shouldSaveInvoice() throws InvoiceDatabaseOperationException {
    //given
    Invoice invoiceToSave = InvoiceGenerator.getRandomInvoice();
    Invoice savedInvoice = invoiceDatabase.save(invoiceToSave);

    //when
    Invoice invoiceFromRepository = invoiceDatabase.findById(savedInvoice.getId());

    //then
    Assert.assertNotNull(invoiceFromRepository);
    Assert.assertEquals(invoiceFromRepository, savedInvoice);
  }

  @Test
  void shouldUpdateInvoice() throws InvoiceDatabaseOperationException {
    //given
    Invoice invoiceToSave = InvoiceGenerator.getRandomInvoice();
    Invoice invoiceToUpdate = invoiceDatabase.save(invoiceToSave);
    Assert.assertEquals(invoiceToUpdate, invoiceDatabase.findById(invoiceToUpdate.getId()));
    invoiceToUpdate.setNumber(11);
    invoiceToUpdate.setTotalNetValue(BigDecimal.valueOf(5555));

    //when
    Invoice updatedInvoice = invoiceDatabase.save(invoiceToUpdate);
    Invoice invoiceFromRepository = invoiceDatabase.findById(updatedInvoice.getId());

    //then
    Assert.assertNotNull(invoiceFromRepository);
    Assert.assertEquals(invoiceToUpdate, updatedInvoice);
  }

  @Test
  void shouldFindOneInvoice() throws InvoiceDatabaseOperationException {
    //given
    Invoice invoiceToSave = InvoiceGenerator.getRandomInvoice();
    Invoice savedInvoice = invoiceDatabase.save(invoiceToSave);

    //when
    Invoice invoiceFromRepository = invoiceDatabase.findById(savedInvoice.getId());

    //then
    Assert.assertNotNull(invoiceFromRepository);
    Assert.assertEquals(invoiceFromRepository, savedInvoice);
  }

  @Test
  void shouldReturnTrueIfInvoiceExistsInDatabase() throws InvoiceDatabaseOperationException {
    //given
    Invoice invoiceToSave = InvoiceGenerator.getRandomInvoice();
    Invoice savedInvoice = invoiceDatabase.save(invoiceToSave);

    //when
    boolean isInvoiceExists = invoiceDatabase.existsById(savedInvoice.getId());

    //then
    Assert.assertTrue(isInvoiceExists);
  }

  @Test
  void shouldReturnFalseIfInvoiceNotExistsInDatabase() throws InvoiceDatabaseOperationException {
    //when
    boolean isInvoiceExists = invoiceDatabase.existsById("1");

    //then
    Assert.assertFalse(isInvoiceExists);
  }

  @Test
  void shouldFindAllInvoices() throws InvoiceDatabaseOperationException {
    //given
    Invoice invoiceToSave1 = InvoiceGenerator.getRandomInvoice();
    Invoice invoiceToSave2 = InvoiceGenerator.getRandomInvoice();
    Invoice invoiceToSave3 = InvoiceGenerator.getRandomInvoice();
    Invoice savedInvoice1 = invoiceDatabase.save(invoiceToSave1);
    Invoice savedInvoice2 = invoiceDatabase.save(invoiceToSave2);
    Invoice savedInvoice3 = invoiceDatabase.save(invoiceToSave3);
    List<Invoice> expectedInvoices = new ArrayList<>();
    expectedInvoices.add(savedInvoice1);
    expectedInvoices.add(savedInvoice2);
    expectedInvoices.add(savedInvoice3);

    //when
    List<Invoice> invoicesFromRepository = invoiceDatabase.findAll();

    //then
    Assert.assertNotNull(invoicesFromRepository);
    Assert.assertEquals(expectedInvoices, invoicesFromRepository);
  }

  @Test
  void shouldDeleteInvoice() throws InvoiceDatabaseOperationException {
    //given
    Invoice invoiceToSave = InvoiceGenerator.getRandomInvoice();
    Invoice savedInvoice = invoiceDatabase.save(invoiceToSave);
    Assert.assertTrue(invoiceDatabase.existsById(savedInvoice.getId()));

    //when
    invoiceDatabase.deleteById(savedInvoice.getId());
    boolean isInvoiceExists = invoiceDatabase.existsById(savedInvoice.getId());

    //then
    Assert.assertFalse(isInvoiceExists);
  }

  @Test
  void shouldReturnNumberOfInvoices() throws InvoiceDatabaseOperationException {
    //given
    List<Invoice> invoices = new ArrayList<>();
    Invoice invoiceToSave1 = InvoiceGenerator.getRandomInvoice();
    Invoice invoiceToSave2 = InvoiceGenerator.getRandomInvoice();
    Invoice invoiceToSave3 = InvoiceGenerator.getRandomInvoice();
    Invoice savedInvoice1 = invoiceDatabase.save(invoiceToSave1);
    Invoice savedInvoice2 = invoiceDatabase.save(invoiceToSave2);
    Invoice savedInvoice3 = invoiceDatabase.save(invoiceToSave3);
    invoices.add(savedInvoice1);
    invoices.add(savedInvoice2);
    invoices.add(savedInvoice3);

    //when
    long numberOfInvoices = invoiceDatabase.count();

    //then
    Assert.assertEquals(invoices.size(), numberOfInvoices);
  }

  @Test
  void shouldDeleteAllInvoices() throws InvoiceDatabaseOperationException {
    //given
    Invoice invoiceToSave1 = InvoiceGenerator.getRandomInvoice();
    Invoice savedInvoice1 = invoiceDatabase.save(invoiceToSave1);
    Invoice invoiceToSave2 = InvoiceGenerator.getRandomInvoice();
    Invoice savedInvoice2 = invoiceDatabase.save(invoiceToSave2);
    List<Invoice> invoicesInRepository = new ArrayList<>();
    invoicesInRepository.add(savedInvoice1);
    invoicesInRepository.add(savedInvoice2);
    Assert.assertEquals(invoicesInRepository.size(), invoiceDatabase.count());

    //when
    invoiceDatabase.deleteAll();
    long numberOfInvoices = invoiceDatabase.count();

    //then
    Assert.assertEquals(0, numberOfInvoices);
  }

  @Test
  void saveMethodShouldThrowExceptionForNullIAsInvoice() {
    assertThrows(IllegalArgumentException.class, () -> invoiceDatabase.save(null));
  }

  @Test
  void findByIdMethodShouldThrowExceptionForNullAsId() {
    assertThrows(IllegalArgumentException.class, () -> invoiceDatabase.findById(null));
  }

  @Test
  void deleteByIdMethodShouldThrowExceptionForNullAsId() {
    assertThrows(IllegalArgumentException.class, () -> invoiceDatabase.deleteById(null));
  }

  @Test
  void existsByIdMethodShouldThrowExceptionForNullAsId() {
    assertThrows(IllegalArgumentException.class, () -> invoiceDatabase.existsById(null));
  }
}
