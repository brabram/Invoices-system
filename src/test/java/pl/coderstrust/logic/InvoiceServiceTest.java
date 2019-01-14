package pl.coderstrust.logic;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
  private InvoiceRepository<Invoice, String> invoiceRepository;

  @InjectMocks
  private InvoiceService invoiceService;

//  @BeforeEach
//  public void setup() {
//    invoiceService = new InvoiceService(invoiceRepository);
//  }

  @Test
  public void shouldGetAllInvoices() throws InvoiceRepositoryOperationException, InvoiceServiceOperationException {
    //Given
    List<Invoice> expectedInvoices = new ArrayList<>();
    Invoice randomInvoice = InvoiceGenerator.getRandomInvoice();
    expectedInvoices.add(randomInvoice);
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
    LocalDate toDate = LocalDate.parse("2019-01-10");
    List<Invoice> expectedInvoices = new ArrayList<>();
    Invoice randomInvoice = InvoiceGenerator.getRandomInvoice();
    LocalDate fromDate = randomInvoice.getIssueDate();
    expectedInvoices.add(randomInvoice);
    when(invoiceRepository.findAll()).thenReturn(expectedInvoices);

    //When
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
    List<Invoice> expectedInvoices = invoiceRepository.findAll();

    //When
    invoiceService.addInvoice(expectedInvoice);
    List<Invoice> actualInvoices = invoiceService.getAllInvoices();

    //Then
    Assert.assertEquals(expectedInvoices, actualInvoices);
    verify(invoiceRepository).save(expectedInvoice);
  }

  @Test
  public void shouldUpdateInvoice() throws InvoiceRepositoryOperationException, InvoiceServiceOperationException {
    //Given
    Invoice expectedInvoice = InvoiceGenerator.getRandomInvoice();
    when(invoiceRepository.save(expectedInvoice)).thenReturn(expectedInvoice);
    expectedInvoice.setNumber(1234);
    int expectedNumber = expectedInvoice.getNumber();

    //When
    invoiceService.updateInvoice(expectedInvoice);
    int actualNumber = expectedInvoice.getNumber();

    //Then
    Assert.assertEquals(expectedNumber, actualNumber);
    verify(invoiceRepository).save(expectedInvoice);
  }

  //@Ignore
  @Test
  public void shouldDeleteInvoiceById() throws InvoiceRepositoryOperationException, InvoiceServiceOperationException {
    //Given
    Invoice expectedInvoice = InvoiceGenerator.getRandomInvoice();
    String id = expectedInvoice.getId();
    List<Invoice> expectedInvoices = new ArrayList<>();
    when(invoiceRepository.save(expectedInvoice)).thenReturn(expectedInvoice);
    doNothing().when(invoiceRepository).deleteById(id);

    //When
    invoiceService.addInvoice(expectedInvoice);
    invoiceService.deleteInvoiceById(id);
    List<Invoice> actualInvoices = invoiceService.getAllInvoices();

    //Then
    Assert.assertEquals(expectedInvoices, actualInvoices);
    verify(invoiceRepository).deleteById(id);
  }

  @Test
  public void shouldDeleteInvoice() throws InvoiceRepositoryOperationException, InvoiceServiceOperationException {
    //Given
    Invoice expectedInvoice = InvoiceGenerator.getRandomInvoice();
    String id = expectedInvoice.getId();
    when(invoiceRepository.existsById(id)).thenReturn(true);
    doNothing().when(invoiceRepository).deleteById(id);
    List<Invoice> expectedInvoices = new ArrayList<>();

    //When
    invoiceService.addInvoice(expectedInvoice);
    invoiceService.deleteInvoice(expectedInvoice);
    List<Invoice> actualInvoices = invoiceService.getAllInvoices();

    //Then
    Assert.assertEquals(expectedInvoices, actualInvoices);
    verify(invoiceRepository).deleteById(id);
  }

  @Test
  public void shouldDeleteAll() throws InvoiceRepositoryOperationException, InvoiceServiceOperationException {
    //Given
    Invoice expectedInvoice = InvoiceGenerator.getRandomInvoice();
    List<Invoice> expectedInvoices = new ArrayList<>();
    doNothing().when(invoiceRepository).deleteAll();

    //When
    invoiceService.addInvoice(expectedInvoice);
    invoiceService.deleteAll();
    List<Invoice> actualInvoices = invoiceService.getAllInvoices();

    //Then
    Assert.assertEquals(expectedInvoices, actualInvoices);
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
  public void deleteInvoiceMethodShouldThrowExceptionForNullAsInvoice() {
    assertThrows(IllegalArgumentException.class, () -> invoiceService.deleteInvoice(null));
  }
}
