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
    Invoice invoiceFromDatabase = invoiceDatabase.findById(savedInvoice.getId());

    //then
    Assert.assertNotNull(invoiceFromDatabase);
    Assert.assertEquals(invoiceFromDatabase, savedInvoice);
  }

  @Test
  void shouldUpdateInvoice() throws InvoiceDatabaseOperationException {
    //given
    Invoice invoiceToSave = InvoiceGenerator.getRandomInvoice();
    Invoice invoiceToUpdate = invoiceDatabase.save(invoiceToSave);
    Assert.assertEquals(invoiceToUpdate, invoiceDatabase.findById(invoiceToUpdate.getId()));
    invoiceToUpdate.setNumber("11");
    invoiceToUpdate.setTotalNetValue(BigDecimal.valueOf(5555));

    //when
    Invoice updatedInvoice = invoiceDatabase.save(invoiceToUpdate);
    Invoice invoiceFromDatabase = invoiceDatabase.findById(updatedInvoice.getId());

    //then
    Assert.assertNotNull(invoiceFromDatabase);
    Assert.assertEquals(invoiceToUpdate, updatedInvoice);
  }

  @Test
  void shouldFindOneInvoice() throws InvoiceDatabaseOperationException {
    //given
    Invoice invoiceToSave = InvoiceGenerator.getRandomInvoice();
    Invoice savedInvoice = invoiceDatabase.save(invoiceToSave);

    //when
    Invoice invoiceFromDatabase = invoiceDatabase.findById(savedInvoice.getId());

    //then
    Assert.assertNotNull(invoiceFromDatabase);
    Assert.assertEquals(invoiceFromDatabase, savedInvoice);
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
    boolean isInvoiceExists = invoiceDatabase.existsById(1L);

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
    List<Invoice> invoicesFromDatabase = invoiceDatabase.findAll();

    //then
    Assert.assertNotNull(invoicesFromDatabase);
    Assert.assertEquals(expectedInvoices, invoicesFromDatabase);
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
    List<Invoice> invoicesInDatabase = new ArrayList<>();
    invoicesInDatabase.add(savedInvoice1);
    invoicesInDatabase.add(savedInvoice2);
    Assert.assertEquals(invoicesInDatabase.size(), invoiceDatabase.count());

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

  @Test
  void findByIdMethodShouldThrowExceptionForNegativeNumberAsId() { assertThrows(IllegalArgumentException.class, () -> invoiceDatabase.findById(-1L));
  }

  @Test
  void deleteByIdMethodShouldThrowExceptionForNegativeNumberAsId() {
    assertThrows(IllegalArgumentException.class, () -> invoiceDatabase.deleteById(-1L));
  }

  @Test
  void existsByIdMethodShouldThrowExceptionForNegativeNumberAsId() {
    assertThrows(IllegalArgumentException.class, () -> invoiceDatabase.existsById(-1L));
  }
}
