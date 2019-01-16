package pl.coderstrust.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import edu.emory.mathcs.backport.java.util.Collections;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.coderstrust.generators.InvoiceGenerator;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.repository.InvoiceRepository;
import pl.coderstrust.repository.InvoiceRepositoryOperationException;

@RunWith(MockitoJUnitRunner.class)
public class InvoiceServiceTest {

  @Mock
  private InvoiceRepository invoiceRepository;

  @InjectMocks
  private InvoiceService invoiceService;

  @Test
  public void shouldGetAllInvoices() throws InvoiceRepositoryOperationException, InvoiceServiceOperationException {
    //Given
    List<Invoice> expectedInvoices = Collections.singletonList(InvoiceGenerator.getRandomInvoice());
    when(invoiceRepository.findAll()).thenReturn(expectedInvoices);

    //When
    List<Invoice> actualInvoices = invoiceService.getAllInvoices();

    //Then
    Assert.assertEquals(expectedInvoices, actualInvoices);
    verify(invoiceRepository).findAll();
  }

  @Test
  public void shouldGetAllInvoicesInGivenDateRange() throws InvoiceRepositoryOperationException, InvoiceServiceOperationException {
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
    when(invoiceRepository.findAll()).thenReturn(expectedInvoices);

    //When
    invoiceService.addInvoice(randomInvoice1);
    invoiceService.addInvoice(randomInvoice2);
    invoiceService.addInvoice(randomInvoice3);
    invoiceService.addInvoice(randomInvoice4);
    invoiceService.addInvoice(randomInvoice5);
    List<Invoice> actualInvoices = invoiceService.getAllInvoicesInGivenDateRange(fromDate, toDate);

    //Then
    Assert.assertEquals(expectedInvoices, actualInvoices);
    verify(invoiceRepository).findAll();
  }

  @Test
  public void shouldGetInvoiceById() throws InvoiceRepositoryOperationException, InvoiceServiceOperationException {
    //Given
    Invoice expectedInvoice = InvoiceGenerator.getRandomInvoice();
    String id = expectedInvoice.getId();
    when(invoiceRepository.findById(id)).thenReturn(expectedInvoice);

    //When
    Invoice actualInvoice = invoiceService.getInvoiceById(id);

    //Then
    Assert.assertEquals(expectedInvoice, actualInvoice);
    verify(invoiceRepository).findById(id);
  }

  @Test
  public void shouldAddInvoice() throws InvoiceRepositoryOperationException, InvoiceServiceOperationException {
    //Given
    Invoice expectedInvoice = InvoiceGenerator.getRandomInvoice();
    when(invoiceRepository.save(expectedInvoice)).thenReturn(expectedInvoice);

    //When
    Invoice actualInvoice = invoiceService.addInvoice(expectedInvoice);

    //Then
    Assert.assertEquals(expectedInvoice, actualInvoice);
    verify(invoiceRepository).save(expectedInvoice);
  }

  @Test
  public void shouldUpdateInvoice() throws InvoiceRepositoryOperationException, InvoiceServiceOperationException {
    //Given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    when(invoiceRepository.save(invoice)).thenReturn(invoice);
    invoiceService.addInvoice(invoice);

    //When
    invoiceService.updateInvoice(invoice);

    //Then
    verify(invoiceRepository).save(invoice);
  }

  @Test
  public void shouldDeleteInvoiceById() throws InvoiceRepositoryOperationException, InvoiceServiceOperationException {
    //Given
    String id = "3448";
    doNothing().when(invoiceRepository).deleteById(id);

    //When
    invoiceService.deleteInvoiceById(id);

    //Then
    verify(invoiceRepository).deleteById(id);
  }

  @Test
  public void shouldDeleteAll() throws InvoiceRepositoryOperationException, InvoiceServiceOperationException {
    //Given
    doNothing().when(invoiceRepository).deleteAll();

    //When
    invoiceService.deleteAll();

    //Then
    verify(invoiceRepository).deleteAll();
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
  public void getAllInvoicesMethodShouldThrowInvoiceServiceOperationExceptionWhenIsSomeErrorWhileGettingInvoicesFromRepository() throws InvoiceRepositoryOperationException {
    //Given
    doThrow(InvoiceRepositoryOperationException.class).when(invoiceRepository).findAll();

    //Then
    assertThrows(InvoiceServiceOperationException.class, () -> invoiceService.getAllInvoices());
  }

  @Test
  public void getInvoiceByIdMethodShouldThrowInvoiceServiceOperationExceptionWhenIsSomeErrorWhileGettingInvoicesFromRepository() throws InvoiceRepositoryOperationException {
    //Given
    doThrow(InvoiceRepositoryOperationException.class).when(invoiceRepository).findById("1");

    //Then
    assertThrows(InvoiceServiceOperationException.class, () -> invoiceService.getInvoiceById("1"));
  }

  @Test
  public void addInvoiceMethodShouldThrowInvoiceServiceOperationExceptionWhenIsSomeErrorWhileGettingInvoicesFromRepository() throws InvoiceRepositoryOperationException {
    //Given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    doThrow(InvoiceRepositoryOperationException.class).when(invoiceRepository).save(invoice);

    //Then
    assertThrows(InvoiceServiceOperationException.class, () -> invoiceService.addInvoice(invoice));
  }

  @Test
  public void updateInvoiceMethodShouldThrowInvoiceServiceOperationExceptionWhenIsSomeErrorWhileGettingInvoicesFromRepository() throws InvoiceRepositoryOperationException {
    //Given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    doThrow(InvoiceRepositoryOperationException.class).when(invoiceRepository).save(invoice);

    //Then
    assertThrows(InvoiceServiceOperationException.class, () -> invoiceService.updateInvoice(invoice));
  }

  @Test
  public void deleteInvoiceByIdMethodShouldThrowInvoiceServiceOperationExceptionWhenIsSomeErrorWhileGettingInvoicesFromRepository() throws InvoiceRepositoryOperationException {
    //Given
    when(invoiceRepository.existsById("1")).thenReturn(true);
    doThrow(InvoiceRepositoryOperationException.class).when(invoiceRepository).deleteById("1");

    //Then
    assertThrows(InvoiceServiceOperationException.class, () -> invoiceService.deleteInvoiceById("1"));
  }

  @Test
  public void deleteAllMethodShouldThrowInvoiceServiceOperationExceptionWhenIsSomeErrorWhileGettingInvoicesFromRepository() throws InvoiceRepositoryOperationException {
    //Given
    doThrow(InvoiceRepositoryOperationException.class).when(invoiceRepository).deleteAll();

    //Then
    assertThrows(InvoiceServiceOperationException.class, () -> invoiceService.deleteAll());
  }
}
