package pl.coderstrust.database.sql;

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
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.NonTransientDataAccessException;
import pl.coderstrust.database.DatabaseOperationException;
import pl.coderstrust.database.InvoiceDatabase;
import pl.coderstrust.database.sql.model.SqlInvoice;
import pl.coderstrust.generators.InvoiceGenerator;
import pl.coderstrust.model.Invoice;


@ExtendWith(MockitoExtension.class)
class HibernateInvoiceDatabaseTest {

  @Mock
  private HibernateInvoiceRepository hibernateInvoiceRepository;
  private SqlModelMapper sqlModelMapper;


  private InvoiceDatabase invoiceDatabase;

  @BeforeEach
  void setup() {
    sqlModelMapper = new SqlModelMapperImpl();
    invoiceDatabase = new HibernateInvoiceDatabase(hibernateInvoiceRepository, sqlModelMapper);
  }

  @Test
  void shouldSaveInvoice() throws DatabaseOperationException {
    //given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    SqlInvoice sqlInvoice = sqlModelMapper.mapInvoice(invoice);
    when(hibernateInvoiceRepository.save(sqlInvoice)).thenReturn(sqlInvoice);

    //when
    Optional<Invoice> savedInvoice = invoiceDatabase.save(invoice);

    //then
    assertTrue(savedInvoice.isPresent());
    assertEquals(invoice, savedInvoice.get());
    verify(hibernateInvoiceRepository).save(sqlInvoice);
  }

  @Test
  void shouldFindOneInvoice() throws DatabaseOperationException {
    //given
    Invoice invoice = (InvoiceGenerator.getRandomInvoice());
    SqlInvoice sqlInvoice = sqlModelMapper.mapInvoice(invoice);
    Long id = invoice.getId();
    when(hibernateInvoiceRepository.findById(id)).thenReturn(Optional.of(sqlInvoice));

    //When
    Optional<Invoice> actualInvoice = invoiceDatabase.findById(id);

    //Then
    assertTrue(actualInvoice.isPresent());
    assertEquals(invoice, actualInvoice.get());
    verify(hibernateInvoiceRepository).findById(id);
  }

  @Test
  void shouldReturnTrueIfInvoiceExistsInDatabase() throws DatabaseOperationException {
    //given
    Long id = 1L;
    when(hibernateInvoiceRepository.existsById(id)).thenReturn(true);

    //when
    boolean isInvoiceExists = invoiceDatabase.existsById(id);

    //then
    Assert.assertTrue(isInvoiceExists);
    verify(hibernateInvoiceRepository).existsById(id);
  }

  @Test
  void shouldReturnFalseIfInvoiceExistsInDatabase() throws DatabaseOperationException {
    //given
    Long id = 1L;
    when(hibernateInvoiceRepository.existsById(id)).thenReturn(false);

    //when
    boolean isInvoiceExists = invoiceDatabase.existsById(id);

    //then
    Assert.assertFalse(isInvoiceExists);
    verify(hibernateInvoiceRepository).existsById(id);
  }

  @Test
  void shouldFindAllInvoices() throws DatabaseOperationException {
    //given
    List<SqlInvoice> sqlInvoices = new ArrayList<>();
    sqlInvoices.add(sqlModelMapper.mapInvoice(InvoiceGenerator.getRandomInvoice()));
    sqlInvoices.add(sqlModelMapper.mapInvoice(InvoiceGenerator.getRandomInvoice()));
    when(hibernateInvoiceRepository.findAll()).thenReturn(sqlInvoices);
    List<Invoice> expectedInvoices = sqlInvoices
        .stream()
        .map(invoice -> sqlModelMapper.mapInvoice(invoice))
        .collect(Collectors.toList());

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
    long numberOfInvoices = 3L;
    when(hibernateInvoiceRepository.count()).thenReturn(numberOfInvoices);

    //when
    long actualNumberOfInvoices = invoiceDatabase.count();

    //then
    Assert.assertEquals(numberOfInvoices, actualNumberOfInvoices);
    verify(hibernateInvoiceRepository).count();
  }

  @Test
  void shouldDeleteById() throws DatabaseOperationException {
    //given
    Long id = 1L;
    when(hibernateInvoiceRepository.existsById(id)).thenReturn(true);
    doNothing().when(hibernateInvoiceRepository).deleteById(id);

    //when
    invoiceDatabase.deleteById(id);

    //then
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
  void existByIdMethodShouldThrowExceptionWhenIdIsNull() {
    assertThrows(IllegalArgumentException.class, () -> invoiceDatabase.existsById(null));
  }

  @Test
  void deleteByIdMethodShouldThrowExceptionWhenIdIsNull() {
    assertThrows(IllegalArgumentException.class, () -> invoiceDatabase.deleteById(null));
  }

  @Test
  void saveInvoiceMethodShouldThrowExceptionWhenAnErrorOccurDuringExecution() {
    //Given
    NonTransientDataAccessException mockedException = Mockito.mock(NonTransientDataAccessException.class);
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    SqlInvoice sqlInvoice = sqlModelMapper.mapInvoice(invoice);
    doThrow(mockedException).when(hibernateInvoiceRepository).save(sqlInvoice);

    //Then
    assertThrows(DatabaseOperationException.class, () -> invoiceDatabase.save(invoice));
    verify(hibernateInvoiceRepository).save(sqlInvoice);
  }

  @Test
  void deleteInvoiceByIdMethodShouldThrowExceptionWhenAnErrorOccurDuringExecution() {
    //Given
    Long id = 1L;
    when(hibernateInvoiceRepository.existsById(id)).thenReturn(true);
    EmptyResultDataAccessException mockedException = Mockito.mock(EmptyResultDataAccessException.class);
    doThrow(mockedException).when(hibernateInvoiceRepository).deleteById(id);

    //Then
    assertThrows(DatabaseOperationException.class, () -> invoiceDatabase.deleteById(id));
    verify(hibernateInvoiceRepository).deleteById(id);
  }

  @Test
  void deleteAllMethodShouldThrowExceptionWhenAnErrorOccurDuringExecution() {
    //Given
    NonTransientDataAccessException mockedException = Mockito.mock(NonTransientDataAccessException.class);
    doThrow(mockedException).when(hibernateInvoiceRepository).deleteAll();

    //Then
    assertThrows(DatabaseOperationException.class, () -> invoiceDatabase.deleteAll());
    verify(hibernateInvoiceRepository).deleteAll();
  }

  @Test
  void countMethodShouldThrowExceptionWhenAnErrorOccurDuringExecution() {
    //Given
    NonTransientDataAccessException mockedException = Mockito.mock(NonTransientDataAccessException.class);
    doThrow(mockedException).when(hibernateInvoiceRepository).count();

    //Then
    assertThrows(DatabaseOperationException.class, () -> invoiceDatabase.count());
    verify(hibernateInvoiceRepository).count();
  }

  @Test
  void findInvoiceByIdMethodShouldThrowExceptionWhenAnErrorOccurDuringExecution() {
    //Given
    Long id = 1L;
    NoSuchElementException mockedException = Mockito.mock(NoSuchElementException.class);
    doThrow(mockedException).when(hibernateInvoiceRepository).findById(id);

    //Then
    assertThrows(DatabaseOperationException.class, () -> invoiceDatabase.findById(id));
    verify(hibernateInvoiceRepository).findById(id);
  }

  @Test
  void existInvoiceByIdMethodShouldThrowExceptionWhenAnErrorOccurDuringExecution() {
    //Given
    Long id = 1L;
    NonTransientDataAccessException mockedException = Mockito.mock(NonTransientDataAccessException.class);
    doThrow(mockedException).when(hibernateInvoiceRepository).existsById(id);

    //Then
    assertThrows(DatabaseOperationException.class, () -> invoiceDatabase.existsById(id));
    verify(hibernateInvoiceRepository).existsById(id);
  }

  @Test
  void findAllInvoiceByIdMethodShouldThrowExceptionWhenAnErrorOccurDuringExecution() {
    //Given
    NonTransientDataAccessException mockedException = Mockito.mock(NonTransientDataAccessException.class);
    doThrow(mockedException).when(hibernateInvoiceRepository).findAll();

    //Then
    assertThrows(DatabaseOperationException.class, () -> invoiceDatabase.findAll());
    verify(hibernateInvoiceRepository).findAll();
  }
}

