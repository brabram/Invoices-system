package pl.coderstrust.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.coderstrust.generators.InvoiceGenerator;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.service.InvoiceService;

@ExtendWith(MockitoExtension.class)
class InvoiceControllerTest {


  @Mock
  private InvoiceService invoiceService;

  @InjectMocks
  private InvoiceController invoiceController;

  @Test
  void shouldGetAllInvoices() {
    //Given
    List<Invoice> expectedInvoices = new ArrayList<>();
    Invoice randomInvoice1 = InvoiceGenerator.getRandomInvoice();
    Invoice randomInvoice2 = InvoiceGenerator.getRandomInvoice();
    expectedInvoices.add(randomInvoice1);
    expectedInvoices.add(randomInvoice2);
    when(invoiceService.getAllInvoices()).thenReturn(Optional.of(expectedInvoices));

    //When
    Optional<List<Invoice>> actualInvoices = invoiceController.getAllInvoices();

    //Then
    assertTrue(actualInvoices.isPresent());
    assertEquals(expectedInvoices, actualInvoices.get());
    verify(invoiceService).getAllInvoices();
  }


  @Test
  void shouldGetInvoiceById() {
  }

  @Test
  void shouldGetInvoiceByNumber() {
  }

  @Test
  void shouldAddInvoice() {
  }

  @Test
  void shouldUpdateInvoice() {
  }

  @Test
  void shouldRemoveInvoice() {
  }
}