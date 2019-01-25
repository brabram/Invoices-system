package pl.coderstrust.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@ExtendWith(SpringExtension.class)
@WebMvcTest(InvoiceController.class)
class InvoiceControllerTest {

  private final String urlAddressTemplate = "/invoices/%s";
  private ObjectMapper mapper = new ObjectMapper();

  @Autowired
  private MockMvc mockMvc;

//  @Autowired
//  private WebApplicationContext context;

  @MockBean
  private InvoiceService invoiceService;

//  @Before
//  void setup() {
//    mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
//  }

//  @Test
//  void getAllInvoicesMethodShouldReturnIsOkStatusFromService() throws Exception {
//    //Given
//    List<Invoice> expectedInvoices = new ArrayList<>();
//    when(invoiceService.getAllInvoices()).thenReturn(Optional.of(expectedInvoices));
//
//    //Then
//    this.mockMvc.perform(get("")).andDo(print()).andExpect(status().isOk())
//        .andExpect(content().string(containsString("[]")));
//  }
//
//  @Test
//  void getAllInvoicesMethodShouldReturnIsNotFoundStatusFromService() throws Exception {
//    //Given
//    List<Invoice> expectedInvoices = new ArrayList<>();
//    when(invoiceService.getAllInvoices()).thenReturn(Optional.of(expectedInvoices));
//
//    //Then
//    this.mockMvc.perform(get("test")).andDo(print()).andExpect(status().isNotFound())
//        .andExpect(content().string(containsString("")));
//  }
//
//  @Test
//  void getInvoicesByIdMethodShouldReturnInvoiceFromService() throws Exception {
//    //Given
//    Invoice invoice = InvoiceGenerator.getRandomInvoice();
//    Long expectedId = invoice.getId();
//    when(invoiceService.getInvoiceById(expectedId)).thenReturn(Optional.of(invoice));
//
//    //Then
//    this.mockMvc.perform(get("/{id}", expectedId)).andDo(print()).andExpect(status().isOk())
//        .andExpect(content().string(containsString("" + invoice.getId())));
//  }
//
//  @Test
//  void getInvoicesByIdMethodShouldReturnEmptyBodyForIncorrectId() throws Exception {
//    //Given
//    Invoice invoice = InvoiceGenerator.getRandomInvoice();
//    Long expectedId = invoice.getId();
//    when(invoiceService.getInvoiceById(expectedId)).thenReturn(Optional.of(invoice));
//
//    //Then
//    this.mockMvc.perform(get("/{id}", expectedId + 1)).andDo(print()).andExpect(status().isOk())
//        .andExpect(content().string(containsString("")));
//  }

  @Test
  void addInvoiceMethodShouldReturnBadRequestWhenIdIsNull() throws Exception {
    //Given
    ErrorMessage expectedResponse = new ErrorMessage("Invalid id.");

    //When
    MvcResult result = mockMvc
        .perform(get(String.format(urlAddressTemplate, "")).accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int httpStatus = result.getResponse().getStatus();
    ErrorMessage actualResponse = mapper.readValue(result.getResponse().getContentAsString(), ErrorMessage.class);

    //Then
    assertEquals(HttpStatus.BAD_REQUEST.value(), httpStatus);
    assertEquals(expectedResponse, actualResponse);
    verify(invoiceService, never()).getInvoiceById(null);

  }
}
