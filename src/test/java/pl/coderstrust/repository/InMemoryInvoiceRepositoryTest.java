package pl.coderstrust.repository;

import junit.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.generators.InvoiceGenerator;

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
    Invoice invoiceToSave = InvoiceGenerator.getRandomInvoice();
    Invoice savedInvoice = invoiceRepository.save(invoiceToSave);

    //when
    Invoice invoiceFromRepository = invoiceRepository.findById(savedInvoice.getId());

    //then
    Assert.assertNotNull(invoiceFromRepository);
    Assert.assertEquals(invoiceFromRepository, savedInvoice);
  }

  @Test
  void shouldUpdateInvoice() throws InvoiceRepositoryOperationException {
    //given
    Invoice invoiceToSave = InvoiceGenerator.getRandomInvoice();
    Invoice invoiceToUpdate = invoiceRepository.save(invoiceToSave);
    Assert.assertEquals(invoiceToUpdate, invoiceRepository.findById(invoiceToUpdate.getId()));
    invoiceToUpdate.setNumber(11);
    invoiceToUpdate.setTotalNetValue(BigDecimal.valueOf(5555));

    //when
    Invoice updatedInvoice = invoiceRepository.save(invoiceToUpdate);
    Invoice invoiceFromRepository = invoiceRepository.findById(updatedInvoice.getId());

    //then
    Assert.assertNotNull(invoiceFromRepository);
    Assert.assertEquals(invoiceToUpdate, updatedInvoice);
  }

  @Test
  void shouldFindOneInvoice() throws InvoiceRepositoryOperationException {
    //given
    Invoice invoiceToSave = InvoiceGenerator.getRandomInvoice();
    Invoice savedInvoice = invoiceRepository.save(invoiceToSave);

    //when
    Invoice invoiceFromRepository = invoiceRepository.findById(savedInvoice.getId());

    //then
    Assert.assertNotNull(invoiceFromRepository);
    Assert.assertEquals(invoiceFromRepository, savedInvoice);
  }

  @Test
  void shouldReturnTrueIfInvoiceExistsInDatabase() throws InvoiceRepositoryOperationException {
    //given
    Invoice invoiceToSave = InvoiceGenerator.getRandomInvoice();
    Invoice savedInvoice = invoiceRepository.save(invoiceToSave);

    //when
    boolean isInvoiceExists = invoiceRepository.existsById(savedInvoice.getId());

    //then
    Assert.assertTrue(isInvoiceExists);
  }

  @Test
  void shouldReturnFalseIfInvoiceNotExistsInDatabase() throws InvoiceRepositoryOperationException {
    //when
    boolean isInvoiceExists = invoiceRepository.existsById(1);

    //then
    Assert.assertFalse(isInvoiceExists);
  }

  @Test
  void shouldFindAllInvoices() throws InvoiceRepositoryOperationException {
    //given
    Invoice invoiceToSave1 = InvoiceGenerator.getRandomInvoice();
    Invoice invoiceToSave2 = InvoiceGenerator.getRandomInvoice();
    Invoice invoiceToSave3 = InvoiceGenerator.getRandomInvoice();
    Invoice savedInvoice1 = invoiceRepository.save(invoiceToSave1);
    Invoice savedInvoice2 = invoiceRepository.save(invoiceToSave2);
    Invoice savedInvoice3 = invoiceRepository.save(invoiceToSave3);
    List<Invoice> expectedInvoices = new ArrayList<>();
    expectedInvoices.add(savedInvoice1);
    expectedInvoices.add(savedInvoice2);
    expectedInvoices.add(savedInvoice3);

    //when
    List<Invoice> invoicesFromRepository = invoiceRepository.findAll();

    //then
    Assert.assertNotNull(invoicesFromRepository);
    Assert.assertEquals(expectedInvoices, invoicesFromRepository);
  }

  @Test
  void shouldDeleteInvoice() throws InvoiceRepositoryOperationException {
    //given
    Invoice invoiceToSave = InvoiceGenerator.getRandomInvoice();
    Invoice savedInvoice = invoiceRepository.save(invoiceToSave);
    Assert.assertTrue(invoiceRepository.existsById(savedInvoice.getId()));

    //when
    invoiceRepository.deleteById(savedInvoice.getId());
    boolean isInvoiceExists = invoiceRepository.existsById(savedInvoice.getId());

    //then
    Assert.assertFalse(isInvoiceExists);
  }

  @Test
  void shouldReturnNumberOfInvoices() throws InvoiceRepositoryOperationException {
    //given
    List<Invoice> invoices = new ArrayList<>();
    Invoice invoiceToSave1 = InvoiceGenerator.getRandomInvoice();
    Invoice invoiceToSave2 = InvoiceGenerator.getRandomInvoice();
    Invoice invoiceToSave3 = InvoiceGenerator.getRandomInvoice();
    Invoice savedInvoice1 = invoiceRepository.save(invoiceToSave1);
    Invoice savedInvoice2 = invoiceRepository.save(invoiceToSave2);
    Invoice savedInvoice3 = invoiceRepository.save(invoiceToSave3);
    invoices.add(savedInvoice1);
    invoices.add(savedInvoice2);
    invoices.add(savedInvoice3);

    //when
    long numberOfInvoices = invoiceRepository.count();

    //then
    Assert.assertEquals(invoices.size(), numberOfInvoices);
  }

  @Test
  void shouldDeleteAllInvoices() throws InvoiceRepositoryOperationException {
    //given
    Invoice invoiceToSave1 = InvoiceGenerator.getRandomInvoice();
    Invoice savedInvoice1 = invoiceRepository.save(invoiceToSave1);
    Invoice invoiceToSave2 = InvoiceGenerator.getRandomInvoice();
    Invoice savedInvoice2 = invoiceRepository.save(invoiceToSave2);
    List<Invoice> invoicesInRepository = new ArrayList<>();
    invoicesInRepository.add(savedInvoice1);
    invoicesInRepository.add(savedInvoice2);
    Assert.assertEquals(invoicesInRepository.size(), invoiceRepository.count());

    //when
    invoiceRepository.deleteAll();
    long numberOfInvoices = invoiceRepository.count();

    //then
    Assert.assertEquals(0, numberOfInvoices);
  }

  @Test
  void saveMethodShouldThrowExceptionForNullIAsInvoice() {
    assertThrows(IllegalArgumentException.class, () -> invoiceRepository.save(null));
  }

  @Test
  void findByIdMethodShouldThrowExceptionForNullAsId() {
    assertThrows(IllegalArgumentException.class, () -> invoiceRepository.findById(null));
  }

  @Test
  void deleteByIdMethodShouldThrowExceptionForNullAsId() {
    assertThrows(IllegalArgumentException.class, () -> invoiceRepository.deleteById(null));
  }

  @Test
  void existsByIdMethodShouldThrowExceptionForNullAsId() {
    assertThrows(IllegalArgumentException.class, () -> invoiceRepository.existsById(null));
  }

  @Test
  void findByIdMethodShouldThrowExceptionForNegativeNumberAsId() { assertThrows(IllegalArgumentException.class, () -> invoiceRepository.findById(-1)); }

  @Test
  void deleteByIdMethodShouldThrowExceptionForNegativeNumberAsId() { assertThrows(IllegalArgumentException.class, () -> invoiceRepository.deleteById(-1)); }

  @Test
  void existsByIdMethodShouldThrowExceptionForNegativeNumberAsId() { assertThrows(IllegalArgumentException.class, () -> invoiceRepository.existsById(-1)); }
}
