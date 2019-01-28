package pl.coderstrust.database;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
  void shouldSaveInvoice() throws DatabaseOperationException {
    //given
    Invoice invoiceToSave = InvoiceGenerator.getRandomInvoice();
    Optional<Invoice> savedInvoice = invoiceDatabase.save(invoiceToSave);

    //when
    assertTrue(savedInvoice.isPresent());
    Optional<Invoice> invoiceFromDatabase = invoiceDatabase.findById(savedInvoice.get().getId());

    //then
    assertNotNull(invoiceFromDatabase);
    assertEquals(invoiceFromDatabase, savedInvoice);
  }

  @Test
  void shouldUpdateInvoice() throws DatabaseOperationException {
    //given
    Invoice invoiceToSave = InvoiceGenerator.getRandomInvoice();
    Optional<Invoice> invoiceToUpdate = invoiceDatabase.save(invoiceToSave);
    assertTrue(invoiceToUpdate.isPresent());
    assertEquals(invoiceToUpdate, invoiceDatabase.findById(invoiceToUpdate.get().getId()));
    invoiceToUpdate.get().setNumber("11");
    invoiceToUpdate.get().setTotalNetValue(BigDecimal.valueOf(5555));

    //when
    Optional<Invoice> updatedInvoice = invoiceDatabase.save(invoiceToUpdate.get());
    assertTrue(updatedInvoice.isPresent());
    Optional<Invoice> invoiceFromDatabase = invoiceDatabase.findById(updatedInvoice.get().getId());

    //then
    assertNotNull(invoiceFromDatabase);
    assertEquals(invoiceToUpdate, updatedInvoice);
  }

  @Test
  void shouldFindOneInvoice() throws DatabaseOperationException {
    //given
    Invoice invoiceToSave = InvoiceGenerator.getRandomInvoice();
    Optional<Invoice> savedInvoice = invoiceDatabase.save(invoiceToSave);

    //when
    assertTrue(savedInvoice.isPresent());
    Optional<Invoice> invoiceFromDatabase = invoiceDatabase.findById(savedInvoice.get().getId());

    //then
    assertNotNull(invoiceFromDatabase);
    assertEquals(invoiceFromDatabase, savedInvoice);
  }

  @Test
  void shouldReturnTrueIfInvoiceExistsInDatabase() throws DatabaseOperationException {
    //given
    Invoice invoiceToSave = InvoiceGenerator.getRandomInvoice();
    Optional<Invoice> savedInvoice = invoiceDatabase.save(invoiceToSave);

    //when
    assertTrue(savedInvoice.isPresent());
    boolean isInvoiceExists = invoiceDatabase.existsById(savedInvoice.get().getId());

    //then
    assertTrue(isInvoiceExists);
  }

  @Test
  void shouldReturnFalseIfInvoiceNotExistsInDatabase() throws DatabaseOperationException {
    //when
    boolean isInvoiceExists = invoiceDatabase.existsById(1L);

    //then
    assertFalse(isInvoiceExists);
  }

  @Test
  void shouldFindAllInvoices() throws DatabaseOperationException {
    //given
    List<Invoice> expectedInvoices = new ArrayList<>();
    Invoice invoiceToSave1 = InvoiceGenerator.getRandomInvoice();
    Optional<Invoice> savedInvoice1 = invoiceDatabase.save(invoiceToSave1);
    assertTrue(savedInvoice1.isPresent());
    expectedInvoices.add(savedInvoice1.get());
    Invoice invoiceToSave2 = InvoiceGenerator.getRandomInvoice();
    Optional<Invoice> savedInvoice2 = invoiceDatabase.save(invoiceToSave2);
    assertTrue(savedInvoice2.isPresent());
    expectedInvoices.add(savedInvoice2.get());
    Invoice invoiceToSave3 = InvoiceGenerator.getRandomInvoice();
    Optional<Invoice> savedInvoice3 = invoiceDatabase.save(invoiceToSave3);
    assertTrue(savedInvoice3.isPresent());
    expectedInvoices.add(savedInvoice3.get());

    //when
    Optional<List<Invoice>> invoicesFromDatabase = invoiceDatabase.findAll();

    //then
    assertNotNull(invoicesFromDatabase);
    assertTrue(invoicesFromDatabase.isPresent());
    assertEquals(expectedInvoices, invoicesFromDatabase.get());
  }

  @Test
  void shouldDeleteInvoice() throws DatabaseOperationException {
    //given
    Invoice invoiceToSave = InvoiceGenerator.getRandomInvoice();
    Optional<Invoice> savedInvoice = invoiceDatabase.save(invoiceToSave);
    assertTrue(savedInvoice.isPresent());
    assertTrue(invoiceDatabase.existsById(savedInvoice.get().getId()));

    //when
    invoiceDatabase.deleteById(savedInvoice.get().getId());
    boolean isInvoiceExists = invoiceDatabase.existsById(savedInvoice.get().getId());

    //then
    assertFalse(isInvoiceExists);
  }

  @Test
  void shouldReturnNumberOfInvoices() throws DatabaseOperationException {
    //given
    List<Invoice> invoices = new ArrayList<>();
    Invoice invoiceToSave1 = InvoiceGenerator.getRandomInvoice();
    Optional<Invoice> savedInvoice1 = invoiceDatabase.save(invoiceToSave1);
    assertTrue(savedInvoice1.isPresent());
    invoices.add(savedInvoice1.get());
    Invoice invoiceToSave2 = InvoiceGenerator.getRandomInvoice();
    Optional<Invoice> savedInvoice2 = invoiceDatabase.save(invoiceToSave2);
    assertTrue(savedInvoice2.isPresent());
    invoices.add(savedInvoice2.get());
    Invoice invoiceToSave3 = InvoiceGenerator.getRandomInvoice();
    Optional<Invoice> savedInvoice3 = invoiceDatabase.save(invoiceToSave3);
    assertTrue(savedInvoice3.isPresent());
    invoices.add(savedInvoice3.get());

    //when
    long numberOfInvoices = invoiceDatabase.count();

    //then
    assertEquals(invoices.size(), numberOfInvoices);
  }

  @Test
  void shouldDeleteAllInvoices() throws DatabaseOperationException {
    //given
    Invoice invoiceToSave1 = InvoiceGenerator.getRandomInvoice();
    Optional<Invoice> savedInvoice1 = invoiceDatabase.save(invoiceToSave1);
    Invoice invoiceToSave2 = InvoiceGenerator.getRandomInvoice();
    Optional<Invoice> savedInvoice2 = invoiceDatabase.save(invoiceToSave2);
    List<Invoice> invoicesInDatabase = new ArrayList<>();
    assertTrue(savedInvoice1.isPresent());
    invoicesInDatabase.add(savedInvoice1.get());
    assertTrue(savedInvoice2.isPresent());
    invoicesInDatabase.add(savedInvoice2.get());
    assertEquals(invoicesInDatabase.size(), invoiceDatabase.count());

    //when
    invoiceDatabase.deleteAll();
    long numberOfInvoices = invoiceDatabase.count();

    //then
    assertEquals(0, numberOfInvoices);
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
  void findByIdMethodShouldThrowExceptionForNegativeNumberAsId() {
    assertThrows(IllegalArgumentException.class, () -> invoiceDatabase.findById(-1L));
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
