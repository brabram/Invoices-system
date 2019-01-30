package pl.coderstrust.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.NonTransientDataAccessException;
import pl.coderstrust.generators.InvoiceGenerator;
import pl.coderstrust.model.Invoice;

@ExtendWith(MockitoExtension.class)
class HibernateInvoiceDatabaseTest {

  @Mock
  private HibernateInvoiceRepository hibernateInvoiceRepository;

  private InvoiceDatabase invoiceDatabase;

  @BeforeEach
  void setup() {
    invoiceDatabase = new HibernateInvoiceDatabase(hibernateInvoiceRepository);
  }

  @Test
  void shouldSaveInvoice() throws DatabaseOperationException {
    //given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    when(hibernateInvoiceRepository.save(invoice)).thenReturn(invoice);

    //when
    Optional<Invoice> actualInvoice = invoiceDatabase.save(invoice);

    //then
    assertEquals(Optional.of(invoice), actualInvoice);
    verify(hibernateInvoiceRepository).save(invoice);
  }

  @Test
  void shouldFindOneInvoice() throws DatabaseOperationException {
    //given
    Optional<Invoice> invoice = Optional.of(InvoiceGenerator.getRandomInvoice());
    Long id = invoice.get().getId();
    when(hibernateInvoiceRepository.findById(id)).thenReturn(invoice);

    //When
    Optional<Invoice> actualInvoice = invoiceDatabase.findById(id);

    //Then
    assertEquals(invoice, actualInvoice);
    verify(hibernateInvoiceRepository).findById(id);
  }

  @Test
  void shouldReturnTrueIfInvoiceExistsInDatabase() throws DatabaseOperationException {
    //given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    when(hibernateInvoiceRepository.save(invoice)).thenReturn(invoice);
    when(hibernateInvoiceRepository.existsById(invoice.getId())).thenReturn(true);

    //when
    invoiceDatabase.save(invoice);
    boolean isInvoiceExists = invoiceDatabase.existsById(invoice.getId());

    //then
    Assert.assertTrue(isInvoiceExists);
    verify(hibernateInvoiceRepository).save(invoice);
    verify(hibernateInvoiceRepository).existsById(invoice.getId());
  }

  @Test
  void shouldFindAllInvoices() throws DatabaseOperationException {
    //given
    List<Invoice> expectedInvoices = new ArrayList<>();
    Invoice invoiceToSave1 = InvoiceGenerator.getRandomInvoice();
    Invoice invoiceToSave2 = InvoiceGenerator.getRandomInvoice();
    expectedInvoices.add(invoiceToSave1);
    expectedInvoices.add(invoiceToSave2);
    when(hibernateInvoiceRepository.findAll()).thenReturn(expectedInvoices);

    //When
    Optional<List<Invoice>> actualInvoices = invoiceDatabase.findAll();

    //Then
    assertTrue(actualInvoices.isPresent());
    assertEquals(expectedInvoices, actualInvoices.get());
    verify(hibernateInvoiceRepository).findAll();
  }

  @Test
  void shouldReturnNumberOfInvoices() throws DatabaseOperationException {
    //given
    Invoice invoiceToSave1 = InvoiceGenerator.getRandomInvoice();
    Invoice invoiceToSave2 = InvoiceGenerator.getRandomInvoice();
    Invoice savedInvoice1 = hibernateInvoiceRepository.save(invoiceToSave1);
    Invoice savedInvoice2 = hibernateInvoiceRepository.save(invoiceToSave2);
    List<Invoice> tableOfInvoices = new ArrayList<>();
    tableOfInvoices.add(savedInvoice1);
    tableOfInvoices.add(savedInvoice2);
    when(hibernateInvoiceRepository.findAll()).thenReturn(tableOfInvoices);
    long expectedInvoicesCount = (long) tableOfInvoices.size();
    when(hibernateInvoiceRepository.count()).thenReturn(expectedInvoicesCount);

    //when
    invoiceDatabase.findAll();
    long actualInvoicesCount = invoiceDatabase.count();

    //then
    Assert.assertEquals(expectedInvoicesCount, actualInvoicesCount);
    verify(hibernateInvoiceRepository).findAll();
    verify(hibernateInvoiceRepository).count();
  }

  @Test
  void shouldDeleteById() throws DatabaseOperationException {
    //given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    Long id = invoice.getId();
    when(hibernateInvoiceRepository.existsById(id)).thenReturn(true);
    assertTrue(hibernateInvoiceRepository.existsById(id));
    doNothing().when(hibernateInvoiceRepository).deleteById(id);

    //when
    invoiceDatabase.deleteById(id);

    //then
    verify(hibernateInvoiceRepository).existsById(id);
    verify(hibernateInvoiceRepository).deleteById(id);
  }

  @Test
  void shouldDeleteAllInvoices() throws DatabaseOperationException {
    //given
    doNothing().when(hibernateInvoiceRepository).deleteAll();

    //when
    invoiceDatabase.deleteAll();

    //then
    verify(hibernateInvoiceRepository).deleteAll();
  }

  @Test
  void saveInvoiceMethodShouldThrowExceptionWhenInvoiceIsNull() {
    assertThrows(IllegalArgumentException.class, () -> invoiceDatabase.save(null));
  }

  @Test
  void findByIdMethodShouldThrowExceptionWhenIdIsNull() {
    assertThrows(IllegalArgumentException.class, () -> invoiceDatabase.findById(null));
  }

  @Test
  void findByIdMethodShouldThrowExceptionWhenIdIsLessThanZero() {
    assertThrows(IllegalArgumentException.class, () -> invoiceDatabase.findById(-1L));
  }

  @Test
  void existByIdMethodShouldThrowExceptionWhenIdIsNull() {
    assertThrows(IllegalArgumentException.class, () -> invoiceDatabase.existsById(null));
  }

  @Test
  void existByIdMethodShouldThrowExceptionWhenIdIsLessThanZero() {
    assertThrows(IllegalArgumentException.class, () -> invoiceDatabase.existsById(-1L));
  }

  @Test
  void deleteByIdMethodShouldThrowExceptionWhenIdIsNull() {
    assertThrows(IllegalArgumentException.class, () -> invoiceDatabase.deleteById(null));
  }

  @Test
  void deleteByIdMethodShouldThrowExceptionWhenIdIsLessThanZero() {
    assertThrows(IllegalArgumentException.class, () -> invoiceDatabase.deleteById(-1L));
  }

  @Test
  void saveInvoiceMethodShouldThrowExceptionIfGetSomeError() {
    //Given
    NonTransientDataAccessException mockedException = Mockito.mock(NonTransientDataAccessException.class);
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    doThrow(mockedException).when(hibernateInvoiceRepository).save(invoice);

    //Then
    assertThrows(DatabaseOperationException.class, () -> invoiceDatabase.save(invoice));
  }

  @Test
  void deleteInvoiceByIdMethodShouldThrowExceptionIfGetSomeError() {
    //Given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    EmptyResultDataAccessException mockedException = Mockito.mock(EmptyResultDataAccessException.class);
    doThrow(mockedException).when(hibernateInvoiceRepository).deleteById(invoice.getId());

    //Then
    assertThrows(DatabaseOperationException.class, () -> invoiceDatabase.deleteById(invoice.getId()));
  }

  @Test
  void deleteAllMethodShouldThrowExceptionIfGetSomeError() {
    //Given
    NonTransientDataAccessException mockedException = Mockito.mock(NonTransientDataAccessException.class);
    doThrow(mockedException).when(hibernateInvoiceRepository).deleteAll();

    //Then
    assertThrows(DatabaseOperationException.class, () -> invoiceDatabase.deleteAll());
  }

  @Test
  void countMethodShouldThrowExceptionIfGetSomeError() {
    //Given
    NonTransientDataAccessException mockedException = Mockito.mock(NonTransientDataAccessException.class);
    doThrow(mockedException).when(hibernateInvoiceRepository).count();

    //Then
    assertThrows(DatabaseOperationException.class, () -> invoiceDatabase.count());
  }

  @Test
  void findInvoiceByIdMethodShouldThrowExceptionIfGetSomeError() {
    //Given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    NoSuchElementException mockedException = Mockito.mock(NoSuchElementException.class);
    doThrow(mockedException).when(hibernateInvoiceRepository).findById(invoice.getId());

    //Then
    assertThrows(DatabaseOperationException.class, () -> invoiceDatabase.findById(invoice.getId()));
  }

  @Test
  void existInvoiceByIdMethodShouldThrowExceptionIfGetSomeError() {
    //Given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    NonTransientDataAccessException mockedException = Mockito.mock(NonTransientDataAccessException.class);
    doThrow(mockedException).when(hibernateInvoiceRepository).existsById(invoice.getId());

    //Then
    assertThrows(DatabaseOperationException.class, () -> invoiceDatabase.existsById(invoice.getId()));
  }

  @Test
  void findAllInvoiceByIdMethodShouldThrowExceptionIfGetSomeError() {
    //Given
    NonTransientDataAccessException mockedException = Mockito.mock(NonTransientDataAccessException.class);
    doThrow(mockedException).when(hibernateInvoiceRepository).findAll();

    //Then
    assertThrows(DatabaseOperationException.class, () -> invoiceDatabase.findAll());
  }
}
