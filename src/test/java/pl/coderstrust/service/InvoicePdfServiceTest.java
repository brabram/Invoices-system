package pl.coderstrust.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.coderstrust.generators.InvoiceGenerator;
import pl.coderstrust.model.Invoice;

@ExtendWith(MockitoExtension.class)
class InvoicePdfServiceTest {
  private InvoicePdfService invoicePdfService;

  @BeforeEach
  private void setup() {
    invoicePdfService = new InvoicePdfService();
  }

  @Test
  void shouldThrowExceptionIfMethodCreatePdfIsInvokedWithNull() {
    assertThrows(IllegalArgumentException.class, () -> invoicePdfService.createPdf(null));
  }

  @Test
  void shouldReturnPdfForPassedInvoice() throws Exception {
    //given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();

    //when
    byte[] pdfAsArrayOgBytes = invoicePdfService.createPdf(invoice);

    //then
    assertNotNull(pdfAsArrayOgBytes);
    assertTrue(pdfAsArrayOgBytes.length > 0);
  }
}
