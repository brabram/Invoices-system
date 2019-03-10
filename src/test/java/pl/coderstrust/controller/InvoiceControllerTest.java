package pl.coderstrust.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.coderstrust.configuration.ApplicationConfiguration;
import pl.coderstrust.generators.InvoiceGenerator;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.service.InvoiceEmailService;
import pl.coderstrust.service.InvoicePdfService;
import pl.coderstrust.service.InvoiceService;
import pl.coderstrust.service.ServiceOperationException;

@ExtendWith(SpringExtension.class)
@WebMvcTest(InvoiceController.class)
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = "ADMIN")
class InvoiceControllerTest {

  private final String urlAddressTemplate = "/invoices";
  private ObjectMapper mapper = new ApplicationConfiguration().getObjectMapper();

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private InvoiceService invoiceService;

  @MockBean
  private InvoicePdfService invoicePdfService;

  @MockBean
  private InvoiceEmailService invoiceEmailService;

  @Test
  void shouldReturnAllInvoices() throws Exception {
    //Given
    List<Invoice> expectedInvoices = Arrays.asList(InvoiceGenerator.getRandomInvoice(), InvoiceGenerator.getRandomInvoice());
    when(invoiceService.getAllInvoices()).thenReturn(Optional.of(expectedInvoices));

    //When
    MvcResult result = mockMvc
        .perform(get(urlAddressTemplate)
            .accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();
    List<Invoice> actualInvoices = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Invoice>>() {
    });

    //Then
    assertEquals(HttpStatus.OK.value(), actualHttpStatus);
    assertNotNull(actualInvoices);
    assertEquals(expectedInvoices, actualInvoices);
    verify(invoiceService).getAllInvoices();
  }

  @Test
  void shouldReturnEmptyListOfInvoicesWhenThereAreNoInvoicesInTheDatabase() throws Exception {
    //Given
    when(invoiceService.getAllInvoices()).thenReturn(Optional.empty());

    //When
    MvcResult result = mockMvc
        .perform(get(urlAddressTemplate)
            .accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();
    List<Invoice> actualInvoices = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Invoice>>() {
    });

    //Then
    assertEquals(HttpStatus.OK.value(), actualHttpStatus);
    assertNotNull(actualInvoices);
    assertEquals(new ArrayList<Invoice>(), actualInvoices);
    verify(invoiceService).getAllInvoices();
  }

  @Test
  void shouldReturnInternalServerErrorDuringGettingAllInvoicesWhenSomethingWentWrongOnServer() throws Exception {
    //Given
    doThrow(ServiceOperationException.class).when(invoiceService).getAllInvoices();
    ErrorMessage expectedResponse = new ErrorMessage("Internal server error while getting all invoices.");

    //When
    MvcResult result = mockMvc
        .perform(get(urlAddressTemplate)
            .accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();
    ErrorMessage actualResponse = mapper.readValue(result.getResponse().getContentAsString(), ErrorMessage.class);

    //Then
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), actualHttpStatus);
    assertNotNull(actualResponse);
    assertEquals(expectedResponse, actualResponse);
    verify(invoiceService).getAllInvoices();
  }

  @Test
  void shouldReturnInvoice() throws Exception {
    //Given
    Invoice expectedInvoice = InvoiceGenerator.getRandomInvoice();
    Long id = expectedInvoice.getId();
    when(invoiceService.getInvoiceById(id)).thenReturn(Optional.of(expectedInvoice));

    //When
    MvcResult result = mockMvc
        .perform(get(String.format("%s/%d", urlAddressTemplate, id))
            .accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();
    Invoice actualInvoice = mapper.readValue(result.getResponse().getContentAsString(), Invoice.class);

    //Then
    assertEquals(HttpStatus.OK.value(), actualHttpStatus);
    assertNotNull(actualInvoice);
    assertEquals(expectedInvoice, actualInvoice);
    verify(invoiceService).getInvoiceById(id);
  }

  @Test
  void shouldReturnNotFoundStatusDuringGettingInvoiceWhenInvoiceWithSpecificIdDoesNotExist() throws Exception {
    //Given
    long id = 1L;
    when(invoiceService.getInvoiceById(id)).thenReturn(Optional.empty());
    ErrorMessage expectedResponse = new ErrorMessage(String.format("Invoice not found for passed id: %d", id));

    //When
    MvcResult result = mockMvc
        .perform(get(String.format("%s/%d", urlAddressTemplate, 1))
            .accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();
    ErrorMessage actualResponse = mapper.readValue(result.getResponse().getContentAsString(), ErrorMessage.class);

    //Then
    assertEquals(HttpStatus.NOT_FOUND.value(), actualHttpStatus);
    assertNotNull(actualResponse);
    assertEquals(expectedResponse, actualResponse);
    verify(invoiceService).getInvoiceById(id);
  }

  @Test
  void shouldReturnInternalServerErrorDuringGettingInvoiceWhenSomethingWentWrongOnServer() throws Exception {
    //Given
    long id = 1L;
    doThrow(ServiceOperationException.class).when(invoiceService).getInvoiceById(id);
    ErrorMessage expectedResponse = new ErrorMessage(String.format("Internal server error while getting invoice by id: %d", id));

    //When
    MvcResult result = mockMvc
        .perform(get(String.format("%s/%d", urlAddressTemplate, 1))
            .accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();
    ErrorMessage actualResponse = mapper.readValue(result.getResponse().getContentAsString(), ErrorMessage.class);

    //Then
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), actualHttpStatus);
    assertNotNull(actualResponse);
    assertEquals(expectedResponse, actualResponse);
    verify(invoiceService).getInvoiceById(id);
  }

  @Test
  void shouldReturnInvoiceByNumber() throws Exception {
    //Given
    Invoice expectedInvoice = InvoiceGenerator.getRandomInvoice();
    List<Invoice> invoicesList = Collections.singletonList(expectedInvoice);
    String number = expectedInvoice.getNumber();
    when(invoiceService.getAllInvoices()).thenReturn(Optional.of(invoicesList));

    //When
    MvcResult result = mockMvc
        .perform(get(String.format("%s/byNumber", urlAddressTemplate))
            .param("number", number)
            .accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();
    Invoice actualInvoice = mapper.readValue(result.getResponse().getContentAsString(), Invoice.class);

    //Then
    assertEquals(HttpStatus.OK.value(), actualHttpStatus);
    assertNotNull(actualInvoice);
    assertEquals(expectedInvoice, actualInvoice);
    verify(invoiceService).getAllInvoices();
  }

  @Test
  void shouldReturnNotFoundStatusDuringGettingInvoiceWhenInvoiceWithSpecificNumberDoesNotExist() throws Exception {
    //Given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    List<Invoice> invoicesList = Collections.singletonList(invoice);
    String number = invoice.getNumber() + "xxx";
    when(invoiceService.getAllInvoices()).thenReturn(Optional.of(invoicesList));
    ErrorMessage expectedResponse = new ErrorMessage(String.format("Invoice not found for passed number: %s", number));

    //When
    MvcResult result = mockMvc
        .perform(get(String.format("%s/byNumber", urlAddressTemplate))
            .param("number", number)
            .accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();
    ErrorMessage actualResponse = mapper.readValue(result.getResponse().getContentAsString(), ErrorMessage.class);

    //Then
    assertEquals(HttpStatus.NOT_FOUND.value(), actualHttpStatus);
    assertNotNull(actualResponse);
    assertEquals(expectedResponse, actualResponse);
    verify(invoiceService).getAllInvoices();
  }

  @Test
  void shouldReturnNotFoundStatusDuringGettingInvoiceWhenThereAreNoInvoicesInTheDatabase() throws Exception {
    //Given
    String number = "1";
    when(invoiceService.getAllInvoices()).thenReturn(Optional.empty());
    ErrorMessage expectedResponse = new ErrorMessage(String.format("Invoice not found for passed number: %s", number));

    //When
    MvcResult result = mockMvc
        .perform(get(String.format("%s/byNumber", urlAddressTemplate))
            .param("number", number)
            .accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();
    ErrorMessage actualResponse = mapper.readValue(result.getResponse().getContentAsString(), ErrorMessage.class);

    //Then
    assertEquals(HttpStatus.NOT_FOUND.value(), actualHttpStatus);
    assertNotNull(actualResponse);
    assertEquals(expectedResponse, actualResponse);
    verify(invoiceService).getAllInvoices();
  }

  @Test
  void shouldReturnInternalServerErrorDuringGettingInvoiceByNumberWhenSomethingWentWrongOnServer() throws Exception {
    //Given
    String number = "1";
    doThrow(ServiceOperationException.class).when(invoiceService).getAllInvoices();
    ErrorMessage expectedResponse = new ErrorMessage(String.format("Internal server error while getting invoice by number: %s", number));

    //When
    MvcResult result = mockMvc
        .perform(get(String.format("%s/byNumber", urlAddressTemplate))
            .param("number", number)
            .accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();
    ErrorMessage actualResponse = mapper.readValue(result.getResponse().getContentAsString(), ErrorMessage.class);

    //Then
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), actualHttpStatus);
    assertNotNull(actualResponse);
    assertEquals(expectedResponse, actualResponse);
    verify(invoiceService).getAllInvoices();
  }

  @Test
  void shouldAddInvoice() throws Exception {
    //Given
    Invoice expectedInvoice = InvoiceGenerator.getRandomInvoice();
    Long id = expectedInvoice.getId();
    when(invoiceService.invoiceExistsById(id)).thenReturn(false);
    when(invoiceService.addInvoice(expectedInvoice)).thenReturn(Optional.of(expectedInvoice));

    //When
    MvcResult result = mockMvc
        .perform(post(urlAddressTemplate)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(mapper.writeValueAsString(expectedInvoice))
            .accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();
    Invoice actualInvoice = mapper.readValue(result.getResponse().getContentAsString(), Invoice.class);
    String actualLocationHeader = result.getResponse().getHeader("location");

    //Then
    assertEquals(HttpStatus.CREATED.value(), actualHttpStatus);
    assertEquals(String.format("/invoices/%s", id), actualLocationHeader);
    assertNotNull(actualInvoice);
    assertEquals(expectedInvoice, actualInvoice);
    verify(invoiceService).invoiceExistsById(id);
    verify(invoiceService).addInvoice(expectedInvoice);
  }

  @Test
  void shouldReturnConflictStatusDuringAddingInvoiceWhenInvoiceAlreadyExistInDatabase() throws Exception {
    //Given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    Long id = invoice.getId();
    when(invoiceService.invoiceExistsById(id)).thenReturn(true);
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
    assertNotNull(actualResponse);
    assertEquals(expectedResponse, actualResponse);
    verify(invoiceService).invoiceExistsById(id);
    verify(invoiceService, never()).addInvoice(invoice);
  }

  @Test
  void shouldReturnBadRequestStatusDuringAddingInvalidInvoice() throws Exception {
    //Given
    List<String> expectedDetails = Collections.singletonList("Invoice cannot be null");
    ErrorMessage expectedResponse = new ErrorMessage("Passed invoice is invalid.", expectedDetails);

    //When
    MvcResult result = mockMvc
        .perform(post(urlAddressTemplate)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(mapper.writeValueAsString(null))
            .accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();
    ErrorMessage actualResponse = mapper.readValue(result.getResponse().getContentAsString(), ErrorMessage.class);

    //Then
    assertEquals(HttpStatus.BAD_REQUEST.value(), actualHttpStatus);
    assertNotNull(actualResponse);
    assertEquals(expectedResponse, actualResponse);
    verify(invoiceService, never()).invoiceExistsById(anyLong());
    verify(invoiceService, never()).addInvoice(null);
  }

  @Test
  void shouldReturnInternalServerErrorDuringAddingInvoiceWhenSomethingWentWrongOnServer() throws Exception {
    //Given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    Long id = invoice.getId();
    doThrow(ServiceOperationException.class).when(invoiceService).invoiceExistsById(id);
    ErrorMessage expectedResponse = new ErrorMessage(String.format("Internal server error while adding invoice: %s", invoice));

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
    assertNotNull(actualResponse);
    assertEquals(expectedResponse, actualResponse);
    verify(invoiceService).invoiceExistsById(id);
    verify(invoiceService, never()).addInvoice(invoice);
  }

  @Test
  void shouldUpdateInvoice() throws Exception {
    //Given
    Invoice invoiceToUpdate = InvoiceGenerator.getRandomInvoice();
    Long id = invoiceToUpdate.getId();
    when(invoiceService.invoiceExistsById(id)).thenReturn(true);

    //When
    MvcResult result = mockMvc
        .perform(put(String.format("%s/%d", urlAddressTemplate, id))
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(mapper.writeValueAsString(invoiceToUpdate))
            .accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();
    Invoice actualInvoice = mapper.readValue(result.getResponse().getContentAsString(), Invoice.class);

    //Then
    assertEquals(HttpStatus.OK.value(), actualHttpStatus);
    assertNotNull(actualInvoice);
    assertEquals(invoiceToUpdate, actualInvoice);
    verify(invoiceService).invoiceExistsById(id);
    verify(invoiceService).updateInvoice(invoiceToUpdate);
  }

  @Test
  void shouldReturnBadRequestStatusDuringUpdatingInvoiceWithWrongId() throws Exception {
    //Given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    Long id = invoice.getId() + 1L;
    ErrorMessage expectedResponse = new ErrorMessage(String.format("Invoice to update has different id than %d.", id));

    //When
    MvcResult result = mockMvc
        .perform(put(String.format("%s/%d", urlAddressTemplate, id))
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(mapper.writeValueAsString(invoice))
            .accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();
    ErrorMessage actualResponse = mapper.readValue(result.getResponse().getContentAsString(), ErrorMessage.class);

    //Then
    assertEquals(HttpStatus.BAD_REQUEST.value(), actualHttpStatus);
    assertNotNull(actualResponse);
    assertEquals(expectedResponse, actualResponse);
    verify(invoiceService, never()).invoiceExistsById(id);
    verify(invoiceService, never()).updateInvoice(invoice);
  }

  @Test
  void shouldReturnNotFoundStatusDuringUpdatingNonExistingInvoice() throws Exception {
    //Given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    Long id = invoice.getId();
    when(invoiceService.invoiceExistsById(id)).thenReturn(false);
    ErrorMessage expectedResponse = new ErrorMessage(String.format("Invoice with %d id does not exist.", id));

    //When
    MvcResult result = mockMvc
        .perform(put(String.format("%s/%d", urlAddressTemplate, id))
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(mapper.writeValueAsString(invoice))
            .accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();
    ErrorMessage actualResponse = mapper.readValue(result.getResponse().getContentAsString(), ErrorMessage.class);

    //Then
    assertEquals(HttpStatus.NOT_FOUND.value(), actualHttpStatus);
    assertNotNull(actualResponse);
    assertEquals(expectedResponse, actualResponse);
    verify(invoiceService).invoiceExistsById(id);
    verify(invoiceService, never()).updateInvoice(invoice);
  }

  @Test
  void shouldReturnBadRequestStatusDuringUpdatingInvalidInvoice() throws Exception {
    //Given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    Long id = invoice.getId();
    List<String> expectedDetails = new ArrayList<>(Collections.singleton("Invoice cannot be null"));
    ErrorMessage expectedResponse = new ErrorMessage("Passed invoice is invalid.", expectedDetails);

    //When
    MvcResult result = mockMvc
        .perform(put(String.format("%s/%d", urlAddressTemplate, id))
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(mapper.writeValueAsString(null))
            .accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();
    ErrorMessage actualResponse = mapper.readValue(result.getResponse().getContentAsString(), ErrorMessage.class);

    //Then
    assertEquals(HttpStatus.BAD_REQUEST.value(), actualHttpStatus);
    assertNotNull(actualResponse);
    assertEquals(expectedResponse, actualResponse);
    verify(invoiceService, never()).deleteInvoiceById(anyLong());
    verify(invoiceService, never()).updateInvoice(invoice);
  }

  @Test
  void shouldReturnInternalServerErrorDuringUpdatingInvoiceWhenSomethingWentWrongOnServer() throws Exception {
    //Given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    Long id = invoice.getId();
    doThrow(ServiceOperationException.class).when(invoiceService).invoiceExistsById(id);
    ErrorMessage expectedResponse = new ErrorMessage(String.format("Internal server error while updating invoice %d id, %s invoice", id, invoice));

    //When
    MvcResult result = mockMvc
        .perform(put(String.format("%s/%d", urlAddressTemplate, id))
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(mapper.writeValueAsString(invoice))
            .accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();
    ErrorMessage actualResponse = mapper.readValue(result.getResponse().getContentAsString(), ErrorMessage.class);

    //Then
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), actualHttpStatus);
    assertNotNull(actualResponse);
    assertEquals(expectedResponse, actualResponse);
    verify(invoiceService).invoiceExistsById(id);
    verify(invoiceService, never()).updateInvoice(invoice);
  }

  @Test
  void shouldRemoveInvoice() throws Exception {
    //Given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    Long id = invoice.getId();
    when(invoiceService.getInvoiceById(id)).thenReturn(Optional.of(invoice));
    doNothing().when(invoiceService).deleteInvoiceById(id);

    //When
    MvcResult result = mockMvc
        .perform(delete(String.format("%s/%d", urlAddressTemplate, id))
            .accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();

    //Then
    assertEquals(HttpStatus.OK.value(), actualHttpStatus);
    verify(invoiceService).getInvoiceById(id);
    verify(invoiceService).deleteInvoiceById(id);
  }

  @Test
  void shouldReturnNotFoundStatusDuringRemovingNonExistingInvoice() throws Exception {
    //Given
    Long id = 1L;
    when(invoiceService.getInvoiceById(id)).thenReturn(Optional.empty());
    ErrorMessage expectedResponse = new ErrorMessage(String.format("Invoice with %d id does not exist.", id));

    //When
    MvcResult result = mockMvc
        .perform(delete(String.format("%s/%d", urlAddressTemplate, id))
            .accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();
    ErrorMessage actualResponse = mapper.readValue(result.getResponse().getContentAsString(), ErrorMessage.class);

    //Then
    assertEquals(HttpStatus.NOT_FOUND.value(), actualHttpStatus);
    assertNotNull(actualResponse);
    assertEquals(expectedResponse, actualResponse);
    verify(invoiceService).getInvoiceById(id);
    verify(invoiceService, never()).deleteInvoiceById(id);
  }

  @Test
  void shouldReturnInternalServerErrorStatusDuringRemovingInvoiceWhenSomethingWentWrongOnServer() throws Exception {
    //Given
    Long id = 1L;
    doThrow(ServiceOperationException.class).when(invoiceService).getInvoiceById(id);
    ErrorMessage expectedResponse = new ErrorMessage(String.format("Internal server error while removing invoice. id: %d", id));

    //When
    MvcResult result = mockMvc
        .perform(delete(String.format("%s/%d", urlAddressTemplate, id))
            .accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();
    ErrorMessage actualResponse = mapper.readValue(result.getResponse().getContentAsString(), ErrorMessage.class);

    //Then
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), actualHttpStatus);
    assertNotNull(actualResponse);
    assertEquals(expectedResponse, actualResponse);
    verify(invoiceService).getInvoiceById(id);
    verify(invoiceService, never()).deleteInvoiceById(id);
  }
}
