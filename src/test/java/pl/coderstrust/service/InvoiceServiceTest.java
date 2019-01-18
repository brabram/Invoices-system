package pl.coderstrust.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import edu.emory.mathcs.backport.java.util.Collections;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.coderstrust.database.InvoiceDatabase;
import pl.coderstrust.database.InvoiceDatabaseOperationException;
import pl.coderstrust.generators.InvoiceGenerator;
import pl.coderstrust.model.Invoice;

@RunWith(MockitoJUnitRunner.class)
public class InvoiceServiceTest {

  @Mock
  private InvoiceDatabase invoiceDatabase;

  @InjectMocks
  private InvoiceService invoiceService;

  @Test
  public void shouldGetAllInvoices() throws InvoiceDatabaseOperationException, InvoiceServiceOperationException {
    //Given
    List<Invoice> expectedInvoices = Collections.singletonList(InvoiceGenerator.getRandomInvoice());
    when(invoiceDatabase.findAll()).thenReturn(Optional.of(expectedInvoices));

    //When
    Optional<List<Invoice>> actualInvoices = invoiceService.getAllInvoices();

    //Then
    assertTrue(actualInvoices.isPresent());
    Assert.assertEquals(expectedInvoices, actualInvoices.get());
    verify(invoiceDatabase).findAll();
  }

  @Test
  public void shouldGetAllInvoicesInGivenDateRange() throws InvoiceDatabaseOperationException, InvoiceServiceOperationException {
    //Given
    LocalDate fromDate = LocalDate.parse("2019-01-01");
    LocalDate toDate = LocalDate.parse("2019-01-10");
    List<Invoice> expectedInvoices = new ArrayList<>();
    Invoice randomInvoice1 = InvoiceGenerator.getRandomInvoicesIssuedInSpecificDateRange(fromDate, toDate);
    Invoice randomInvoice2 = InvoiceGenerator.getRandomInvoicesIssuedInSpecificDateRange(LocalDate.parse("2018-01-01"), LocalDate.parse("2018-01-10"));
    Invoice randomInvoice3 = InvoiceGenerator.getRandomInvoicesIssuedInSpecificDateRange(fromDate, toDate);
    Invoice randomInvoice4 = InvoiceGenerator.getRandomInvoicesIssuedInSpecificDateRange(fromDate, toDate);
    Invoice randomInvoice5 = InvoiceGenerator.getRandomInvoicesIssuedInSpecificDateRange(LocalDate.parse("2018-01-01"), LocalDate.parse("2018-01-10"));
    expectedInvoices.add(randomInvoice1);
    expectedInvoices.add(randomInvoice3);
    expectedInvoices.add(randomInvoice4);
    when(invoiceDatabase.findAll()).thenReturn(Optional.of(expectedInvoices));

    //When
    invoiceService.addInvoice(randomInvoice1);
    invoiceService.addInvoice(randomInvoice2);
    invoiceService.addInvoice(randomInvoice3);
    invoiceService.addInvoice(randomInvoice4);
    invoiceService.addInvoice(randomInvoice5);
    Optional<List<Invoice>> actualInvoices = invoiceService.getAllInvoicesInGivenDateRange(fromDate, toDate);

    //Then
    assertTrue(actualInvoices.isPresent());
    Assert.assertEquals(expectedInvoices, actualInvoices.get());
    verify(invoiceDatabase).findAll();
  }

  @Test
  public void shouldGetInvoiceById() throws InvoiceDatabaseOperationException, InvoiceServiceOperationException {
    //Given
    Optional<Invoice> expectedInvoice = Optional.of(InvoiceGenerator.getRandomInvoice());
    Long id = expectedInvoice.get().getId();
    when(invoiceDatabase.findById(id)).thenReturn(expectedInvoice);

    //When
    Optional<Invoice> actualInvoice = invoiceService.getInvoiceById(id);

    //Then
    Assert.assertEquals(expectedInvoice, actualInvoice);
    verify(invoiceDatabase).findById(id);
  }

  @Test
  public void shouldAddInvoice() throws InvoiceDatabaseOperationException, InvoiceServiceOperationException {
    //Given
    Invoice expectedInvoice = InvoiceGenerator.getRandomInvoice();
    when(invoiceDatabase.save(expectedInvoice)).thenReturn(Optional.of(expectedInvoice));

    //When
    Optional<Invoice> actualInvoice = invoiceService.addInvoice(expectedInvoice);

    //Then
    assertTrue(actualInvoice.isPresent());
    Assert.assertEquals(expectedInvoice, actualInvoice.get());
    verify(invoiceDatabase).save(expectedInvoice);
  }

  @Test
  public void shouldUpdateInvoice() throws InvoiceDatabaseOperationException, InvoiceServiceOperationException {
    //Given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    when(invoiceDatabase.save(invoice)).thenReturn(Optional.of(invoice));
    invoiceService.addInvoice(invoice);

    //When
    invoiceService.updateInvoice(invoice);

    //Then
    verify(invoiceDatabase).save(invoice);
  }

  @Test
  public void shouldDeleteInvoiceById() throws InvoiceDatabaseOperationException, InvoiceServiceOperationException {
    //Given
    Long id = 3448L;
    when(invoiceDatabase.existsById(id)).thenReturn(true);
    doNothing().when(invoiceDatabase).deleteById(id);

    //When
    invoiceService.deleteInvoiceById(id);

    //Then
    verify(invoiceDatabase).deleteById(id);
  }

  @Test
  public void shouldDeleteAll() throws InvoiceDatabaseOperationException, InvoiceServiceOperationException {
    //Given
    doNothing().when(invoiceDatabase).deleteAll();

    //When
    invoiceService.deleteAll();

    //Then
    verify(invoiceDatabase).deleteAll();
  }

  @Test
  public void getAllInvoicesInGivenDateRangeMethodShouldThrowExceptionForNullAsFromDate() {
    assertThrows(IllegalArgumentException.class, () -> invoiceService.getAllInvoicesInGivenDateRange(null, LocalDate.now()));
  }

  @Test
  public void getAllInvoicesInGivenDateRangeMethodShouldThrowExceptionForNullAsToDate() {
    assertThrows(IllegalArgumentException.class, () -> invoiceService.getAllInvoicesInGivenDateRange(LocalDate.now(), null));
  }

  @Test
  public void getAllInvoicesInGivenDateRangeMethodShouldThrowExceptionForToDateBeforeFromDate() {
    assertThrows(IllegalArgumentException.class, () -> invoiceService.getAllInvoicesInGivenDateRange(LocalDate.now(), LocalDate.of(2018, 10, 10)));
  }

  @Test
  public void getInvoiceByIdMethodShouldThrowExceptionForNullAsId() {
    assertThrows(IllegalArgumentException.class, () -> invoiceService.getInvoiceById(null));
  }

  @Test
  public void addInvoiceMethodShouldThrowExceptionForNullAsInvoice() {
    assertThrows(IllegalArgumentException.class, () -> invoiceService.addInvoice(null));
  }

  @Test
  public void updateInvoiceMethodShouldThrowExceptionForNullAsInvoice() {
    assertThrows(IllegalArgumentException.class, () -> invoiceService.updateInvoice(null));
  }

  @Test
  public void deleteInvoiceByIdMethodShouldThrowExceptionForNullAsId() {
    assertThrows(IllegalArgumentException.class, () -> invoiceService.deleteInvoiceById(null));
  }

  @Test
  public void getAllInvoicesMethodShouldThrowInvoiceServiceOperationExceptionWhenIsSomeErrorWhileGettingInvoicesFromRepository() throws InvoiceDatabaseOperationException {
    //Given
    doThrow(InvoiceDatabaseOperationException.class).when(invoiceDatabase).findAll();

    //Then
    assertThrows(InvoiceServiceOperationException.class, () -> invoiceService.getAllInvoices());
  }

  @Test
  public void getInvoiceByIdMethodShouldThrowInvoiceServiceOperationExceptionWhenIsSomeErrorWhileGettingInvoicesFromRepository() throws InvoiceDatabaseOperationException {
    //Given
    doThrow(InvoiceDatabaseOperationException.class).when(invoiceDatabase).findById(1L);

    //Then
    assertThrows(InvoiceServiceOperationException.class, () -> invoiceService.getInvoiceById(1L));
  }

  @Test
  public void addInvoiceMethodShouldThrowInvoiceServiceOperationExceptionWhenIsSomeErrorWhileGettingInvoicesFromRepository() throws InvoiceDatabaseOperationException {
    //Given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    doThrow(InvoiceDatabaseOperationException.class).when(invoiceDatabase).save(invoice);

    //Then
    assertThrows(InvoiceServiceOperationException.class, () -> invoiceService.addInvoice(invoice));
  }

  @Test
  public void updateInvoiceMethodShouldThrowInvoiceServiceOperationExceptionWhenIsSomeErrorWhileGettingInvoicesFromRepository() throws InvoiceDatabaseOperationException {
    //Given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    when(invoiceDatabase.existsById(invoice.getId())).thenReturn(true);
    doThrow(InvoiceDatabaseOperationException.class).when(invoiceDatabase).save(invoice);

    //Then
    assertThrows(InvoiceServiceOperationException.class, () -> invoiceService.updateInvoice(invoice));
  }

  @Test
  public void deleteInvoiceByIdMethodShouldThrowInvoiceServiceOperationExceptionWhenIsSomeErrorWhileGettingInvoicesFromRepository() throws InvoiceDatabaseOperationException {
    //Given
    when(invoiceDatabase.existsById(1L)).thenReturn(true);
    doThrow(InvoiceDatabaseOperationException.class).when(invoiceDatabase).deleteById(1L);

    //Then
    assertThrows(InvoiceServiceOperationException.class, () -> invoiceService.deleteInvoiceById(1L));
  }

  @Test
  public void deleteAllMethodShouldThrowInvoiceServiceOperationExceptionWhenIsSomeErrorWhileGettingInvoicesFromRepository() throws InvoiceDatabaseOperationException {
    //Given
    doThrow(InvoiceDatabaseOperationException.class).when(invoiceDatabase).deleteAll();

    //Then
    assertThrows(InvoiceServiceOperationException.class, () -> invoiceService.deleteAll());
  }
}
