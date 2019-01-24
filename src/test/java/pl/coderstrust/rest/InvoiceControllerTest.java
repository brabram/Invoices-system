package pl.coderstrust.rest;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.coderstrust.generators.InvoiceGenerator;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.service.InvoiceService;

@RunWith(SpringRunner.class)
@WebMvcTest(InvoiceController.class)
public class InvoiceControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private InvoiceService invoiceService;

  @Test
  public void getAllInvoicesMethodShouldReturnIsOkStatusFromService() throws Exception {
    //Given
    List<Invoice> expectedInvoices = new ArrayList<>();
    when(invoiceService.getAllInvoices()).thenReturn(Optional.of(expectedInvoices));

    //Then
    this.mockMvc.perform(get("")).andDo(print()).andExpect(status().isOk())
        .andExpect(content().string(containsString("[]")));
  }

  @Test
  public void getAllInvoicesMethodShouldReturnIsNotFoundStatusFromService() throws Exception {
    //Given
    List<Invoice> expectedInvoices = new ArrayList<>();
    when(invoiceService.getAllInvoices()).thenReturn(Optional.of(expectedInvoices));

    //Then
    this.mockMvc.perform(get("test")).andDo(print()).andExpect(status().isNotFound())
        .andExpect(content().string(containsString("")));
  }

  @Test
  public void getInvoicesByIdMethodShouldReturnInvoiceFromService() throws Exception {
    //Given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    Long expectedId = invoice.getId();
    when(invoiceService.getInvoiceById(expectedId)).thenReturn(Optional.of(invoice));

    //Then
    this.mockMvc.perform(get("/{id}", expectedId)).andDo(print()).andExpect(status().isOk())
        .andExpect(content().string(containsString("" + invoice.getId())));
  }

  @Test
  public void getInvoicesByIdMethodShouldReturnEmptyBodyForIncorrectId() throws Exception {
    //Given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    Long expectedId = invoice.getId();
    when(invoiceService.getInvoiceById(expectedId)).thenReturn(Optional.of(invoice));

    //Then
    this.mockMvc.perform(get("/{id}", expectedId + 1)).andDo(print()).andExpect(status().isOk())
        .andExpect(content().string(containsString("")));
  }
}
