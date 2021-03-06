package pl.coderstrust.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.coderstrust.database.DatabaseOperationException;
import pl.coderstrust.database.InvoiceDatabase;
import pl.coderstrust.generators.InvoiceGenerator;
import pl.coderstrust.model.Invoice;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceTest {

  @Mock
  private InvoiceDatabase invoiceDatabase;

  @InjectMocks
  private InvoiceService invoiceService;

  @Test
  void shouldGetAllInvoices() throws DatabaseOperationException, ServiceOperationException {
    //Given
    List<Invoice> expectedInvoices = new ArrayList<>();
    Invoice randomInvoice1 = InvoiceGenerator.getRandomInvoice();
    Invoice randomInvoice2 = InvoiceGenerator.getRandomInvoice();
    expectedInvoices.add(randomInvoice1);
    expectedInvoices.add(randomInvoice2);
    when(invoiceDatabase.findAll()).thenReturn(Optional.of(expectedInvoices));

    //When
    Optional<List<Invoice>> actualInvoices = invoiceService.getAllInvoices();

    //Then
    assertTrue(actualInvoices.isPresent());
    assertEquals(expectedInvoices, actualInvoices.get());
    verify(invoiceDatabase).findAll();
  }

  @Test
  void shouldGetAllInvoicesInGivenDateRange() throws DatabaseOperationException, ServiceOperationException {
    //Given
    LocalDate fromDate = LocalDate.parse("2019-01-01");
    LocalDate toDate = LocalDate.parse("2019-01-10");
    List<Invoice> expectedInvoices = new ArrayList<>();
    Invoice randomInvoice1 = InvoiceGenerator.getRandomInvoicesIssuedInSpecificDateRange(fromDate, toDate);
    expectedInvoices.add(randomInvoice1);
    invoiceService.addInvoice(randomInvoice1);
    Invoice randomInvoice2 = InvoiceGenerator.getRandomInvoicesIssuedInSpecificDateRange(LocalDate.parse("2018-01-01"), LocalDate.parse("2018-01-10"));
    invoiceService.addInvoice(randomInvoice2);
    Invoice randomInvoice3 = InvoiceGenerator.getRandomInvoicesIssuedInSpecificDateRange(fromDate, toDate);
    expectedInvoices.add(randomInvoice3);
    invoiceService.addInvoice(randomInvoice3);
    Invoice randomInvoice4 = InvoiceGenerator.getRandomInvoicesIssuedInSpecificDateRange(fromDate, toDate);
    expectedInvoices.add(randomInvoice4);
    invoiceService.addInvoice(randomInvoice4);
    Invoice randomInvoice5 = InvoiceGenerator.getRandomInvoicesIssuedInSpecificDateRange(LocalDate.parse("2018-01-01"), LocalDate.parse("2018-01-10"));
    invoiceService.addInvoice(randomInvoice5);
    Invoice randomInvoice6 = InvoiceGenerator.getRandomInvoicesIssuedInSpecificDateRange(fromDate, fromDate);
    expectedInvoices.add(randomInvoice6);
    invoiceService.addInvoice(randomInvoice6);
    Invoice randomInvoice7 = InvoiceGenerator.getRandomInvoicesIssuedInSpecificDateRange(toDate, toDate);
    expectedInvoices.add(randomInvoice7);
    invoiceService.addInvoice(randomInvoice7);
    when(invoiceDatabase.findAll()).thenReturn(Optional.of(expectedInvoices));

    //When
    Optional<List<Invoice>> actualInvoices = invoiceService.getAllInvoicesInGivenDateRange(fromDate, toDate);

    //Then
    assertTrue(actualInvoices.isPresent());
    assertEquals(expectedInvoices, actualInvoices.get());
    verify(invoiceDatabase).findAll();
  }

  @Test
  void shouldGetInvoiceById() throws DatabaseOperationException, ServiceOperationException {
    //Given
    Optional<Invoice> expectedInvoice = Optional.of(InvoiceGenerator.getRandomInvoice());
    Long id = expectedInvoice.get().getId();
    when(invoiceDatabase.findById(id)).thenReturn(expectedInvoice);

    //When
    Optional<Invoice> actualInvoice = invoiceService.getInvoiceById(id);

    //Then
    assertEquals(expectedInvoice, actualInvoice);
    verify(invoiceDatabase).findById(id);
  }

  @Test
  void shouldAddInvoice() throws DatabaseOperationException, ServiceOperationException {
    //Given
    Invoice invoiceToAdd = InvoiceGenerator.getRandomInvoice();
    Invoice expectedInvoice = InvoiceGenerator.getRandomInvoice();
    when(invoiceDatabase.save(invoiceToAdd)).thenReturn(Optional.of(expectedInvoice));
    when(invoiceDatabase.existsById(invoiceToAdd.getId())).thenReturn(false);

    //When
    Optional<Invoice> actualInvoice = invoiceService.addInvoice(invoiceToAdd);

    //Then
    assertTrue(actualInvoice.isPresent());
    assertEquals(expectedInvoice, actualInvoice.get());
    verify(invoiceDatabase).save(invoiceToAdd);
    verify(invoiceDatabase).existsById(invoiceToAdd.getId());
  }

  @Test
  void shouldUpdateInvoice() throws DatabaseOperationException, ServiceOperationException {
    //Given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    when(invoiceDatabase.save(invoice)).thenReturn(Optional.of(invoice));
    when(invoiceDatabase.existsById(invoice.getId())).thenReturn(true);

    //When
    invoiceService.updateInvoice(invoice);

    //Then
    verify(invoiceDatabase).save(invoice);
    verify(invoiceDatabase).existsById(invoice.getId());
  }

  @Test
  void shouldDeleteInvoiceById() throws DatabaseOperationException, ServiceOperationException {
    //Given
    Long id = 3448L;
    when(invoiceDatabase.existsById(id)).thenReturn(true);
    doNothing().when(invoiceDatabase).deleteById(id);

    //When
    invoiceService.deleteInvoiceById(id);

    //Then
    verify(invoiceDatabase).existsById(id);
    verify(invoiceDatabase).deleteById(id);
  }

  @Test
  void shouldDeleteAll() throws DatabaseOperationException, ServiceOperationException {
    //Given
    doNothing().when(invoiceDatabase).deleteAll();

    //When
    invoiceService.deleteAll();

    //Then
    verify(invoiceDatabase).deleteAll();
  }

  @Test
  void shouldReturnTrueWhenExistById() throws DatabaseOperationException, ServiceOperationException {
    //Given
    Long id = 3448L;
    when(invoiceDatabase.existsById(id)).thenReturn(true);

    //When
    invoiceService.invoiceExistsById(id);

    //Then
    verify(invoiceDatabase).existsById(id);
  }

  @Test
  void shouldReturnFalseWhenNotExistById() throws DatabaseOperationException, ServiceOperationException {
    //Given
    Long id = 3448L;
    when(invoiceDatabase.existsById(id)).thenReturn(false);

    //When
    invoiceService.invoiceExistsById(id);

    //Then
    verify(invoiceDatabase).existsById(id);
  }

  @Test
  void getAllInvoicesInGivenDateRangeMethodShouldThrowIllegalArgumentExceptionForNullAsFromDate() {
    assertThrows(IllegalArgumentException.class, () -> invoiceService.getAllInvoicesInGivenDateRange(null, LocalDate.now()));
  }

  @Test
  void getAllInvoicesInGivenDateRangeMethodShouldThrowIllegalArgumentExceptionForNullAsToDate() {
    assertThrows(IllegalArgumentException.class, () -> invoiceService.getAllInvoicesInGivenDateRange(LocalDate.now(), null));
  }

  @Test
  void getAllInvoicesInGivenDateRangeMethodShouldThrowIllegalArgumentExceptionForToDateBeforeFromDate() {
    assertThrows(IllegalArgumentException.class, () -> invoiceService.getAllInvoicesInGivenDateRange(LocalDate.now(), LocalDate.of(2018, 10, 10)));
  }

  @Test
  void getInvoiceByIdMethodShouldThrowIllegalArgumentExceptionForNullAsId() {
    assertThrows(IllegalArgumentException.class, () -> invoiceService.getInvoiceById(null));
  }

  @Test
  void addInvoiceMethodShouldThrowIllegalArgumentExceptionForNullAsInvoice() {
    assertThrows(IllegalArgumentException.class, () -> invoiceService.addInvoice(null));
  }

  @Test
  void updateInvoiceMethodShouldThrowIllegalArgumentExceptionForNullAsInvoice() {
    assertThrows(IllegalArgumentException.class, () -> invoiceService.updateInvoice(null));
  }

  @Test
  void deleteInvoiceByIdMethodShouldThrowIllegalArgumentExceptionForNullAsId() {
    assertThrows(IllegalArgumentException.class, () -> invoiceService.deleteInvoiceById(null));
  }

  @Test
  void invoiceExistsByIdMethodShouldThrowIllegalArgumentExceptionForNullAsId() {
    assertThrows(IllegalArgumentException.class, () -> invoiceService.invoiceExistsById(null));
  }

  @Test
  void getAllInvoicesMethodShouldThrowInvoiceServiceOperationExceptionWhenWhenAnErrorOccurDuringExecutionGettingInvoicesFromDatabase() throws DatabaseOperationException {
    //Given
    doThrow(DatabaseOperationException.class).when(invoiceDatabase).findAll();

    //Then
    assertThrows(ServiceOperationException.class, () -> invoiceService.getAllInvoices());
  }

  @Test
  void getInvoiceByIdMethodShouldThrowInvoiceServiceOperationExceptionWhenWhenAnErrorOccurDuringExecutionGettingInvoicesFromDatabase() throws DatabaseOperationException {
    //Given
    doThrow(DatabaseOperationException.class).when(invoiceDatabase).findById(1L);

    //Then
    assertThrows(ServiceOperationException.class, () -> invoiceService.getInvoiceById(1L));
  }

  @Test
  void addInvoiceMethodShouldThrowInvoiceServiceOperationExceptionWhenWhenAnErrorOccurDuringExecutionGettingInvoicesFromDatabase() throws DatabaseOperationException {
    //Given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    doThrow(DatabaseOperationException.class).when(invoiceDatabase).save(invoice);

    //Then
    assertThrows(ServiceOperationException.class, () -> invoiceService.addInvoice(invoice));
  }

  @Test
  void addInvoiceMethodShouldThrowInvoiceServiceOperationExceptionWhenInvoiceAlreadyExistsInDatabase() throws DatabaseOperationException {
    //Given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    when(invoiceDatabase.existsById(invoice.getId())).thenReturn(true);

    //Then
    assertThrows(ServiceOperationException.class, () -> invoiceService.addInvoice(invoice));
    verify(invoiceDatabase, never()).save(invoice);
  }

  @Test
  void updateInvoiceMethodShouldThrowInvoiceServiceOperationExceptionWhenWhenAnErrorOccurDuringExecutionGettingInvoicesFromDatabase() throws DatabaseOperationException {
    //Given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    when(invoiceDatabase.existsById(invoice.getId())).thenReturn(true);
    doThrow(DatabaseOperationException.class).when(invoiceDatabase).save(invoice);

    //Then
    assertThrows(ServiceOperationException.class, () -> invoiceService.updateInvoice(invoice));
  }

  @Test
  void updateInvoiceMethodShouldThrowInvoiceServiceOperationExceptionWhenInvoiceDoesNotExist() throws DatabaseOperationException {
    //Given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    when(invoiceDatabase.existsById(invoice.getId())).thenReturn(false);

    //Then
    assertThrows(ServiceOperationException.class, () -> invoiceService.updateInvoice(invoice));
  }

  @Test
  void deleteInvoiceByIdMethodShouldThrowInvoiceServiceOperationExceptionWhenWhenAnErrorOccurDuringExecutionGettingInvoicesFromDatabase() throws DatabaseOperationException {
    //Given
    when(invoiceDatabase.existsById(1L)).thenReturn(true);
    doThrow(DatabaseOperationException.class).when(invoiceDatabase).deleteById(1L);

    //Then
    assertThrows(ServiceOperationException.class, () -> invoiceService.deleteInvoiceById(1L));
  }

  @Test
  void deleteInvoiceByIdMethodShouldThrowInvoiceServiceOperationExceptionWhenInvoiceDosesNotExist() throws DatabaseOperationException {
    //Given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    when(invoiceDatabase.existsById(invoice.getId())).thenReturn(false);

    //Then
    assertThrows(ServiceOperationException.class, () -> invoiceService.deleteInvoiceById(invoice.getId()));
  }

  @Test
  void deleteAllMethodShouldThrowInvoiceServiceOperationExceptionWhenWhenAnErrorOccurDuringExecutionGettingInvoicesFromDatabase() throws DatabaseOperationException {
    //Given
    doThrow(DatabaseOperationException.class).when(invoiceDatabase).deleteAll();

    //Then
    assertThrows(ServiceOperationException.class, () -> invoiceService.deleteAll());
  }

  @Test
  void invoiceExistsByIdMethodShouldThrowInvoiceServiceOperationExceptionWhenWhenAnErrorOccurDuringExecutionGettingInvoicesFromDatabase() throws DatabaseOperationException {
    //Given
    long id = 1L;
    doThrow(DatabaseOperationException.class).when(invoiceDatabase).existsById(id);

    //Then
    assertThrows(ServiceOperationException.class, () -> invoiceService.invoiceExistsById(id));
  }
}
