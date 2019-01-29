package pl.coderstrust.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.coderstrust.generators.InvoiceGenerator;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.service.InvoiceService;
import pl.coderstrust.service.ServiceOperationException;

@ExtendWith(SpringExtension.class)
@WebMvcTest(InvoiceController.class)
@AutoConfigureMockMvc
class InvoiceControllerTest {

  private final String urlAddressTemplate = "/invoices/";
  private ObjectMapper mapper = new ObjectMapper();

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private InvoiceService invoiceService;


  @Test
  void getAllInvoicesMethodShouldReturnOkStatusWhenAnyInvoiceExist() throws Exception {
    //Given
    List<Invoice> expectedInvoices = new ArrayList<>();
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    expectedInvoices.add(invoice);
    when(invoiceService.getAllInvoices()).thenReturn(Optional.of(expectedInvoices));

    //When
    MvcResult result = mockMvc
        .perform(get(urlAddressTemplate).accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();

    //Then
    assertEquals(HttpStatus.OK.value(), actualHttpStatus);
    verify(invoiceService).getAllInvoices();
  }

  @Test
  void getAllInvoicesMethodShouldReturnNotFoundStatusWhenThereAreNoInvoicesInTheDatabase() throws Exception {
    //Given
    ErrorMessage expectedResponse = new ErrorMessage("Not found any invoices.");
    when(invoiceService.getAllInvoices()).thenReturn(Optional.empty());

    //When
    MvcResult result = mockMvc
        .perform(get(urlAddressTemplate).accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();
    ErrorMessage actualResponse = mapper.readValue(result.getResponse().getContentAsString(), ErrorMessage.class);

    //Then
    assertEquals(HttpStatus.NOT_FOUND.value(), actualHttpStatus);
    assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
    verify(invoiceService).getAllInvoices();
  }

  @Test
  void getAllInvoicesMethodShouldReturnInternalServerErrorStatusWhenIsSomeError() throws Exception {
    //Given
    ErrorMessage expectedResponse = new ErrorMessage("Internal Server Error.");
    doThrow(ServiceOperationException.class).when(invoiceService).getAllInvoices();

    //When
    MvcResult result = mockMvc
        .perform(get(urlAddressTemplate).accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();
    ErrorMessage actualResponse = mapper.readValue(result.getResponse().getContentAsString(), ErrorMessage.class);

    //Then
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), actualHttpStatus);
    assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
  }

  @Test
  void getInvoiceByIdMethodShouldReturnOkStatusWhenInvoiceExist() throws Exception {
    //Given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    Long id = invoice.getId();
    when(invoiceService.getInvoiceById(id)).thenReturn(Optional.of(invoice));

    //When
    MvcResult result = mockMvc
        .perform(get(String.format("%s%d", urlAddressTemplate, id)).accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();

    //Then
    assertEquals(HttpStatus.OK.value(), actualHttpStatus);
    verify(invoiceService).getInvoiceById(id);
  }

  @Test
  void getInvoiceByIdMethodShouldReturnNotFoundStatusWhenInvoiceWithSpecificIdDoesNotExist() throws Exception {
    //Given
    ErrorMessage expectedResponse = new ErrorMessage("Invoice not found.");
    when(invoiceService.getInvoiceById(1L)).thenReturn(Optional.empty());

    //When
    MvcResult result = mockMvc
        .perform(get(String.format("%s%d", urlAddressTemplate, 1)).accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();
    ErrorMessage actualResponse = mapper.readValue(result.getResponse().getContentAsString(), ErrorMessage.class);

    //Then
    assertEquals(HttpStatus.NOT_FOUND.value(), actualHttpStatus);
    assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
    verify(invoiceService).getInvoiceById(1L);
  }

  @Test
  void getInvoiceByIdMethodShouldReturnInternalServerErrorStatusWhenIsSomeError() throws Exception {
    //Given
    ErrorMessage expectedResponse = new ErrorMessage("Internal Server Error.");
    doThrow(ServiceOperationException.class).when(invoiceService).getInvoiceById(1L);

    //When
    MvcResult result = mockMvc
        .perform(get(String.format("%s%d", urlAddressTemplate, 1)).accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();
    ErrorMessage actualResponse = mapper.readValue(result.getResponse().getContentAsString(), ErrorMessage.class);

    //Then
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), actualHttpStatus);
    assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
    verify(invoiceService).getInvoiceById(1L);
  }

  @Test
  void getInvoiceByNumberMethodShouldReturnOkStatusWhenInvoiceExist() throws Exception {
    //Given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    List<Invoice> invoicesList = new ArrayList<>();
    invoicesList.add(invoice);
    String number = invoice.getNumber();
    when(invoiceService.getAllInvoices()).thenReturn(Optional.of(invoicesList));

    //When
    MvcResult result = mockMvc
        .perform(get(String.format("%s/number/%s", urlAddressTemplate, number)).accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();

    //Then
    assertEquals(HttpStatus.OK.value(), actualHttpStatus);
    verify(invoiceService).getAllInvoices();
  }

  @Test
  void getInvoiceByNumberMethodShouldReturnNotFoundStatusWhenInvoiceWithSpecificNumberDoesNotExist() throws Exception {
    //Given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    List<Invoice> invoicesList = new ArrayList<>();
    invoicesList.add(invoice);
    when(invoiceService.getAllInvoices()).thenReturn(Optional.of(invoicesList));
    ErrorMessage expectedResponse = new ErrorMessage("Invoice not found.");

    //When
    MvcResult result = mockMvc
        .perform(get(String.format("%snumber/%s", urlAddressTemplate, "1")).accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();
    ErrorMessage actualResponse = mapper.readValue(result.getResponse().getContentAsString(), ErrorMessage.class);

    //Then
    assertEquals(HttpStatus.NOT_FOUND.value(), actualHttpStatus);
    assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
    verify(invoiceService).getAllInvoices();
  }

  @Test
  void getInvoiceByNumberMethodShouldReturnNotFoundStatusWhenThereAreNoInvoicesInTheDatabase() throws Exception {
    //Given
    ErrorMessage expectedResponse = new ErrorMessage("Invoice not found.");
    when(invoiceService.getAllInvoices()).thenReturn(Optional.empty());

    //When
    MvcResult result = mockMvc
        .perform(get(String.format("%snumber/%s", urlAddressTemplate, "1")).accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();
    ErrorMessage actualResponse = mapper.readValue(result.getResponse().getContentAsString(), ErrorMessage.class);

    //Then
    assertEquals(HttpStatus.NOT_FOUND.value(), actualHttpStatus);
    assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
    verify(invoiceService).getAllInvoices();
  }

  @Test
  void getInvoiceByNumberMethodShouldReturnInternalServerErrorStatusWhenIsSomeError() throws Exception {
    //Given
    ErrorMessage expectedResponse = new ErrorMessage("Internal Server Error.");
    doThrow(ServiceOperationException.class).when(invoiceService).getAllInvoices();

    //When
    MvcResult result = mockMvc
        .perform(get(String.format("%snumber/%s", urlAddressTemplate, 1)).accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();
    ErrorMessage actualResponse = mapper.readValue(result.getResponse().getContentAsString(), ErrorMessage.class);

    //Then
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), actualHttpStatus);
    assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
    verify(invoiceService).getAllInvoices();
  }

  @Test
  void addInvoiceMethodShouldReturnCreatedStatusWhenInvoiceDoesNotExist() throws Exception {
    //Given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    mapper.registerModule(new JavaTimeModule());
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    when(invoiceService.getInvoiceById(invoice.getId())).thenReturn(Optional.empty());

    //When
    MvcResult result = mockMvc
        .perform(post(urlAddressTemplate)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(mapper.writeValueAsString(invoice))
            .accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();

    //Then
    assertEquals(HttpStatus.CREATED.value(), actualHttpStatus);
    verify(invoiceService).getInvoiceById(invoice.getId());
  }

  @Test
  void addInvoiceMethodShouldReturnConflictStatusWhenInvoiceExist() throws Exception {
    //Given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    when(invoiceService.getInvoiceById(invoice.getId())).thenReturn(Optional.of(invoice));
    mapper.registerModule(new JavaTimeModule());
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    ErrorMessage expectedResponse = new ErrorMessage("Invoice already exist.");

    //When
    MvcResult result = mockMvc
        .perform(post(urlAddressTemplate)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(mapper.writeValueAsString(invoice))
            .accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();
    ErrorMessage actualResponse = mapper.readValue(result.getResponse().getContentAsString(), ErrorMessage.class);

    //Then
    assertEquals(HttpStatus.CONFLICT.value(), actualHttpStatus);
    assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
    verify(invoiceService).getInvoiceById(invoice.getId());
  }

  @Test
  void addInvoiceMethodShouldReturnBadRequestStatusWhenInvoiceIsNull() throws Exception {
    //Given
    ErrorMessage expectedResponse = new ErrorMessage("Invoice cannot be null.");

    //When
    MvcResult result = mockMvc
        .perform(post(urlAddressTemplate)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(mapper.writeValueAsString(null))
            .accept(MediaType.APPLICATION_JSON_UTF8))
        .andDo(print())
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();
    ErrorMessage actualResponse = mapper.readValue(result.getResponse().getContentAsString(), ErrorMessage.class);

    //Then
    assertEquals(HttpStatus.BAD_REQUEST.value(), actualHttpStatus);
    assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
    verify(invoiceService, never()).addInvoice(null);
  }

  @Test
  void addInvoiceMethodShouldReturnInternalServerErrorStatusWhenIsSomeError() throws Exception {
    //Given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    ErrorMessage expectedResponse = new ErrorMessage("Internal Server Error.");
    doThrow(ServiceOperationException.class).when(invoiceService).getInvoiceById(invoice.getId());

    //When
    MvcResult result = mockMvc
        .perform(post(urlAddressTemplate)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(mapper.writeValueAsString(invoice))
            .accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();
    ErrorMessage actualResponse = mapper.readValue(result.getResponse().getContentAsString(), ErrorMessage.class);

    //Then
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), actualHttpStatus);
    assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
    verify(invoiceService).getInvoiceById(invoice.getId());
    verify(invoiceService, never()).addInvoice(invoice);
  }

  @Test
  void updateInvoiceMethod_ShouldReturnOkStatus_WhenInvoiceExist() throws Exception {
    //Given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    when(invoiceService.addInvoice(invoice)).thenReturn(Optional.of(invoice));
    when(invoiceService.getInvoiceById(invoice.getId())).thenReturn(Optional.of(invoice));
    invoice.setNumber("999");
    mapper.registerModule(new JavaTimeModule());
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    Long id = invoice.getId();

    //When
    MvcResult result = mockMvc
        .perform(put(String.format("%s/updateId=%d", urlAddressTemplate, id))
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(mapper.writeValueAsString(invoice))
            .accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();

    //Then
    assertEquals(HttpStatus.OK.value(), actualHttpStatus);
    verify(invoiceService).getInvoiceById(id);
  }

  @Test
  void updateInvoiceMethod_ShouldReturnNotFoundStatus_WhenInvoiceDoesNotExist() throws Exception {
    //Given
    Long id = 1L;
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    mapper.registerModule(new JavaTimeModule());
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    ErrorMessage expectedResponse = new ErrorMessage("Invoice does not exist.");

    //When
    MvcResult result = mockMvc
        .perform(put(String.format("%s/updateId=%d", urlAddressTemplate, id))
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(mapper.writeValueAsString(invoice))
            .accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();
    ErrorMessage actualResponse = mapper.readValue(result.getResponse().getContentAsString(), ErrorMessage.class);

    //Then
    assertEquals(HttpStatus.NOT_FOUND.value(), actualHttpStatus);
    assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
  }

  @Test
  void updateInvoiceMethod_ShouldReturnBadRequestStatus_WhenIdIsNull() throws Exception {
    //Given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
//    ErrorMessage expectedResponse = new ErrorMessage("Invalid id.");

    //When
    MvcResult result = mockMvc
        .perform(put(String.format("%s/updateId=%s", urlAddressTemplate, ""))
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(mapper.writeValueAsString(invoice))
            .accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();
//    ErrorMessage actualResponse = mapper.readValue(result.getResponse().getContentAsString(), ErrorMessage.class);

    //Then
    assertEquals(HttpStatus.BAD_REQUEST.value(), actualHttpStatus);
//    assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
    verify(invoiceService, never()).getInvoiceById(null);
  }

  @Test
  void updateInvoiceMethod_ShouldReturnBadRequestStatus_WhenInvoiceIsNull() throws Exception {
    //Given
    Long id = 1L;
//    ErrorMessage expectedResponse = new ErrorMessage("Invoice cannot be empty.");

    //When
    MvcResult result = mockMvc
        .perform(put(String.format("%s/updateId=%d", urlAddressTemplate, id))
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(mapper.writeValueAsString(""))
            .accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();
//    ErrorMessage actualResponse = mapper.readValue(result.getResponse().getContentAsString(), ErrorMessage.class);

    //Then
    assertEquals(HttpStatus.BAD_REQUEST.value(), actualHttpStatus);
//    assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
    verify(invoiceService, never()).getInvoiceById(null);
  }

  @Test
  void removeInvoiceByIdMethod_ShouldReturnOkStatus_WhenInvoiceExist() throws Exception {
    //Given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    Long id = invoice.getId();
    when(invoiceService.addInvoice(invoice)).thenReturn(Optional.of(invoice));
    when(invoiceService.getInvoiceById(invoice.getId())).thenReturn(Optional.of(invoice));

    //When
    MvcResult result = mockMvc
        .perform(delete(String.format("%s/deleteId=%d", urlAddressTemplate, id))
            .accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();

    //Then
    assertEquals(HttpStatus.OK.value(), actualHttpStatus);
    verify(invoiceService).getInvoiceById(id);
  }

  @Test
  void removeInvoiceByIdMethod_ShouldReturnNotFoundStatus_WhenInvoiceDoesNotExist() throws Exception {
    //Given
    ErrorMessage expectedResponse = new ErrorMessage("Invoice does not exist.");

    //When
    MvcResult result = mockMvc
        .perform(delete(String.format("%s/deleteId=%d", urlAddressTemplate, 1L))
            .accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();
    ErrorMessage actualResponse = mapper.readValue(result.getResponse().getContentAsString(), ErrorMessage.class);

    //Then
    assertEquals(HttpStatus.NOT_FOUND.value(), actualHttpStatus);
    assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
  }

  @Test
  void deleteInvoiceByIdMethod_ShouldReturnBadRequestStatus_WhenIdIsNull() throws Exception {
    //Given
    ErrorMessage expectedResponse = new ErrorMessage("Invalid id.");

    //When
    MvcResult result = mockMvc
        .perform(delete(String.format("%s/deleteId=%s", urlAddressTemplate, ""))
            .accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();
    ErrorMessage actualResponse = mapper.readValue(result.getResponse().getContentAsString(), ErrorMessage.class);

    //Then
    assertEquals(HttpStatus.BAD_REQUEST.value(), actualHttpStatus);
    assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
    verify(invoiceService, never()).getInvoiceById(null);
  }
}
