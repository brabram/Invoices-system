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
import pl.coderstrust.model.Invoice;
import pl.coderstrust.repository.InvoiceRepository;
import pl.coderstrust.repository.InvoiceRepositoryOperationException;

@RunWith(MockitoJUnitRunner.class)
public class InvoiceServiceTest {

  @Mock
  private InvoiceRepository<Invoice, Integer> invoiceRepository;

  @Mock
  private Invoice invoice;

  @InjectMocks
  private InvoiceService invoiceService;

//  @BeforeEach
//  public void setup() {
//    invoiceService = new InvoiceService(invoiceRepository);
//  }

  @Test
  public void shouldGetAllInvoices() throws InvoiceRepositoryOperationException {
    //Given
    List<Invoice> expectedInvoices = new ArrayList<>();
    //add some invoice
    when(invoiceRepository.findAll()).thenReturn(expectedInvoices);

    //When
    List<Invoice> actualInvoices = invoiceService.getAllInvoices();

    //Then
    Assert.assertEquals(expectedInvoices, actualInvoices);
    verify(invoiceRepository).findAll();
  }

  @Test
  public void shouldGetAllInvoicesInGivenDateRange() throws InvoiceRepositoryOperationException {
    //Given
    LocalDate fromDate = LocalDate.parse("2019-01-01");
    LocalDate toDate = LocalDate.parse("2019-01-10");
    List<Invoice> invoices = new ArrayList<>();
    when(invoiceRepository.findAll()).thenReturn(invoices);

    //When
    invoiceService.getAllInvoicesInGivenDateRange(fromDate, toDate);

    //Then
    verify(invoiceRepository).findAll();
  }

  @Test
  public void shouldGetInvoiceById() throws InvoiceRepositoryOperationException {
    //Given
    Integer id = 0;
    when(invoiceRepository.findById(id)).thenReturn(invoice);

    //When
    invoiceService.getInvoiceById(id);

    //Then
    verify(invoiceRepository).findById(id);
  }

  @Test
  public void shouldAddInvoice() throws InvoiceRepositoryOperationException {
    //Given
    when(invoiceRepository.save(invoice)).thenReturn(invoice);

    //When
    invoiceService.addInvoice(invoice);

    //Then
    verify(invoiceRepository).save(invoice);
  }

  @Test
  public void shouldUpdateInvoice() throws InvoiceRepositoryOperationException {
    //Given
    when(invoiceRepository.save(invoice)).thenReturn(invoice);

    //When
    invoiceService.updateInvoice(invoice);

    //Then
    verify(invoiceRepository).save(invoice);
  }

  @Test
  public void shouldDeleteInvoiceById() throws InvoiceRepositoryOperationException {
    //Given
    Integer id = 0;
    doNothing().when(invoiceRepository).deleteById(id);

    //When
    invoiceService.deleteInvoiceById(id);

    //Then
    verify(invoiceRepository).deleteById(id);
  }

  @Test
  public void shouldDeleteInvoice() throws InvoiceRepositoryOperationException {
    //Given
    Integer id = 0;
    when(invoiceRepository.existsById(id)).thenReturn(true);
    doNothing().when(invoiceRepository).deleteById(id);

    //When
    invoiceService.deleteInvoice(invoice);

    //Then
    verify(invoiceRepository).deleteById(id);
  }

  @Test
  public void shouldDeleteAll() throws InvoiceRepositoryOperationException {
    //Given
    doNothing().when(invoiceRepository).deleteAll();

    //When
    invoiceService.deleteAll();

    //Then
    verify(invoiceRepository).deleteAll();
  }

  InvoiceService invoiceServiceThrowException = new InvoiceService(invoiceRepository);

  @Test
  public void getAllInvoicesInGivenDateRangeMethodShouldThrowExceptionForNullAsFromDate() {
    assertThrows(IllegalArgumentException.class, () -> invoiceServiceThrowException.getAllInvoicesInGivenDateRange(null, LocalDate.now()));
  }

  @Test
  public void getAllInvoicesInGivenDateRangeMethodShouldThrowExceptionForNullAsToDate() {
    assertThrows(IllegalArgumentException.class, () -> invoiceServiceThrowException.getAllInvoicesInGivenDateRange(LocalDate.now(), null));
  }

  @Test
  public void getInvoiceByIdMethodShouldThrowExceptionForIdLowerThanZero() {
    assertThrows(IllegalArgumentException.class, () -> invoiceServiceThrowException.getInvoiceById(-1));
  }

  @Test
  public void addInvoiceMethodShouldThrowExceptionForNullAsInvoice() {
    assertThrows(IllegalArgumentException.class, () -> invoiceServiceThrowException.addInvoice(null));
  }

  @Test
  public void updateInvoiceMethodShouldThrowExceptionForNullAsInvoice() {
    assertThrows(IllegalArgumentException.class, () -> invoiceServiceThrowException.updateInvoice(null));
  }

  @Test
  public void deleteInvoiceByIdMethodShouldThrowExceptionForIdLowerThanZero() {
    assertThrows(IllegalArgumentException.class, () -> invoiceServiceThrowException.deleteInvoiceById(-1));
  }

  @Test
  public void deleteInvoiceMethodShouldThrowExceptionForNullAsInvoice() {
    assertThrows(IllegalArgumentException.class, () -> invoiceServiceThrowException.deleteInvoice(null));
  }
}
