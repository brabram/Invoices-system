package pl.coderstrust.service.soap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.coderstrust.service.rest.InvoiceService;

@ExtendWith(MockitoExtension.class)
public class InvoiceEndopointTest {

  @Mock
  InvoiceService invoiceService;

  @InjectMocks
  InvoiceEndpoint invoiceEndpoint;

  @Test
  void shoulAddInvoice(){

  }
}
