package pl.coderstrust.service.soap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.xml.datatype.DatatypeConfigurationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.coderstrust.generators.InvoiceGenerator;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.model.validators.InvoiceValidator;
import pl.coderstrust.service.rest.InvoiceService;
import pl.coderstrust.service.rest.ServiceOperationException;
import pl.coderstrust.soap.domainclasses.AddInvoiceRequest;
import pl.coderstrust.soap.domainclasses.AddInvoiceResponse;
import pl.coderstrust.soap.domainclasses.DeleteInvoiceRequest;
import pl.coderstrust.soap.domainclasses.DeleteInvoiceResponse;
import pl.coderstrust.soap.domainclasses.GetAllInvoicesRequest;
import pl.coderstrust.soap.domainclasses.GetAllInvoicesResponse;
import pl.coderstrust.soap.domainclasses.GetInvoiceByIdRequest;
import pl.coderstrust.soap.domainclasses.GetInvoiceByIdResponse;
import pl.coderstrust.soap.domainclasses.GetInvoiceByNumberRequest;
import pl.coderstrust.soap.domainclasses.GetInvoiceByNumberResponse;
import pl.coderstrust.soap.domainclasses.Status;
import pl.coderstrust.soap.domainclasses.UpdateInvoiceRequest;
import pl.coderstrust.soap.domainclasses.UpdateInvoiceResponse;

@ExtendWith(MockitoExtension.class)
public class InvoiceEndpointTest {

  @Mock
  InvoiceService invoiceService;

  @InjectMocks
  InvoiceEndpoint invoiceEndpoint;

  @Test
  void shouldGetAllInvoices() throws ServiceOperationException {
    //Given
    List<Invoice> expectedInvoices = Arrays.asList(InvoiceGenerator.getRandomInvoice(), InvoiceGenerator.getRandomInvoice());
    when(invoiceService.getAllInvoices()).thenReturn(Optional.of(expectedInvoices));
    GetAllInvoicesRequest request = new GetAllInvoicesRequest();
    String expectedStatusMessage = "All invoices were downloaded.";

    //When
    GetAllInvoicesResponse response = invoiceEndpoint.getAllInvoices(request);
    Status actualStatus = response.getStatus();
    String actualStatusMessage = response.getStatusMessage();

    //Then
    assertEquals(Status.SUCCESS, actualStatus);
    assertEquals(expectedStatusMessage, actualStatusMessage);
    verify(invoiceService).getAllInvoices();
  }

  @Test
  void getAllInvoicesMethodShouldReturnErrorOccurDuringExecutionGettingInvoicesFromDatabase() throws ServiceOperationException {
    //Given
    GetAllInvoicesRequest request = new GetAllInvoicesRequest();
    doThrow(ServiceOperationException.class).when(invoiceService).getAllInvoices();
    String expectedStatusMessage = "An error while getting invoices from database.";

    //When
    GetAllInvoicesResponse response = invoiceEndpoint.getAllInvoices(request);
    Status actualStatus = response.getStatus();
    String actualStatusMessage = response.getStatusMessage();

    //Then
    assertEquals(Status.ERROR, actualStatus);
    assertEquals(expectedStatusMessage, actualStatusMessage);
  }

  @Test
  void shouldGetInvoiceById() throws ServiceOperationException, DatatypeConfigurationException {
    //Given
    Invoice expectedInvoice = InvoiceGenerator.getRandomInvoice();
    Long id = expectedInvoice.getId();
    when(invoiceService.getInvoiceById(id)).thenReturn(Optional.of(expectedInvoice));
    GetInvoiceByIdRequest request = new GetInvoiceByIdRequest();
    request.setId(id);
    String expectedStatusMessage = String.format("Invoice with id %d was downloaded.", id);

    //When
    GetInvoiceByIdResponse response = invoiceEndpoint.getInvoiceById(request);
    Invoice actualInvoice = InvoiceFromXmlConverter.convertInvoiceFromXml(response.getInvoice());
    Status actualStatus = response.getStatus();
    String actualStatusMessage = response.getStatusMessage();

    //Then
    assertEquals(Status.SUCCESS, actualStatus);
    assertEquals(expectedStatusMessage, actualStatusMessage);
    assertEquals(expectedInvoice, actualInvoice);
    verify(invoiceService).getInvoiceById(id);
  }

  @Test
  void getInvoiceByIdMethodShouldReturnErrorWhenInvoiceWithSpecificIdDoesNotExist() throws ServiceOperationException {
    //Given
    Invoice expectedInvoice = InvoiceGenerator.getRandomInvoice();
    Long id = expectedInvoice.getId() + 1L;
    when(invoiceService.getInvoiceById(id)).thenReturn(Optional.empty());
    GetInvoiceByIdRequest request = new GetInvoiceByIdRequest();
    request.setId(id);
    String expectedStatusMessage = String.format("Not found invoice with id %d.", id);

    //When
    GetInvoiceByIdResponse response = invoiceEndpoint.getInvoiceById(request);
    Status actualStatus = response.getStatus();
    String actualStatusMessage = response.getStatusMessage();

    //Then
    assertEquals(Status.ERROR, actualStatus);
    assertEquals(expectedStatusMessage, actualStatusMessage);
    verify(invoiceService).getInvoiceById(id);
  }

  @Test
  void getInvoiceByIdMethodShouldReturnErrorOccurDuringExecutionGettingInvoiceByIdFromDatabase() throws ServiceOperationException {
    //Given
    GetInvoiceByIdRequest request = new GetInvoiceByIdRequest();
    long id = 1L;
    request.setId(id);
    doThrow(ServiceOperationException.class).when(invoiceService).getInvoiceById(id);
    String expectedStatusMessage = String.format("An error while getting invoice with id %d from database.", id);

    //When
    GetInvoiceByIdResponse response = invoiceEndpoint.getInvoiceById(request);
    Status actualStatus = response.getStatus();
    String actualStatusMessage = response.getStatusMessage();

    //Then
    assertEquals(Status.ERROR, actualStatus);
    assertEquals(expectedStatusMessage, actualStatusMessage);
  }

  @Test
  void shouldGetInvoiceByNumber() throws ServiceOperationException, DatatypeConfigurationException {
    //Given
    Invoice expectedInvoice = InvoiceGenerator.getRandomInvoice();
    Invoice expectedInvoice2 = InvoiceGenerator.getRandomInvoice();
    List<Invoice> expectedInvoices = new ArrayList<>();
    expectedInvoices.add(expectedInvoice);
    expectedInvoices.add(expectedInvoice2);
    when(invoiceService.getAllInvoices()).thenReturn(Optional.of(expectedInvoices));
    GetInvoiceByNumberRequest request = new GetInvoiceByNumberRequest();
    String number = expectedInvoice.getNumber();
    request.setNumber(number);
    String expectedStatusMessage = String.format("Invoice with number %s was downloaded.", number);

    //When
    GetInvoiceByNumberResponse response = invoiceEndpoint.getInvoiceByNumber(request);
    Invoice actualInvoice = InvoiceFromXmlConverter.convertInvoiceFromXml(response.getInvoice());
    Status actualStatus = response.getStatus();
    String actualStatusMessage = response.getStatusMessage();

    //Then
    assertEquals(Status.SUCCESS, actualStatus);
    assertEquals(expectedStatusMessage, actualStatusMessage);
    assertEquals(expectedInvoice, actualInvoice);
    verify(invoiceService).getAllInvoices();
  }

  @Test
  void getInvoiceByNumberMethodShouldReturnErrorWhenInvoiceWithSpecificIdDoesNotExist() throws ServiceOperationException {
    //Given
    Invoice expectedInvoice = InvoiceGenerator.getRandomInvoice();
    Invoice expectedInvoice2 = InvoiceGenerator.getRandomInvoice();
    List<Invoice> expectedInvoices = new ArrayList<>();
    expectedInvoices.add(expectedInvoice);
    expectedInvoices.add(expectedInvoice2);
    when(invoiceService.getAllInvoices()).thenReturn(Optional.of(expectedInvoices));
    GetInvoiceByNumberRequest request = new GetInvoiceByNumberRequest();
    String number = expectedInvoice.getNumber() + "test";
    request.setNumber(number);
    String expectedStatusMessage = String.format("Not found invoice with number %s.", number);

    //When
    GetInvoiceByNumberResponse response = invoiceEndpoint.getInvoiceByNumber(request);
    Status actualStatus = response.getStatus();
    String actualStatusMessage = response.getStatusMessage();

    //Then
    assertEquals(Status.ERROR, actualStatus);
    assertEquals(expectedStatusMessage, actualStatusMessage);
    verify(invoiceService).getAllInvoices();
  }

  @Test
  void getInvoiceByNumberMethodShouldReturnErrorOccurDuringExecutionGettingInvoicesFromDatabase() throws ServiceOperationException {
    //Given
    GetInvoiceByNumberRequest request = new GetInvoiceByNumberRequest();
    String number = "test";
    request.setNumber(number);
    doThrow(ServiceOperationException.class).when(invoiceService).getAllInvoices();
    String expectedStatusMessage = String.format("An error while getting invoice with number %s from database.", number);

    //When
    GetInvoiceByNumberResponse response = invoiceEndpoint.getInvoiceByNumber(request);
    Status actualStatus = response.getStatus();
    String actualStatusMessage = response.getStatusMessage();

    //Then
    assertEquals(Status.ERROR, actualStatus);
    assertEquals(expectedStatusMessage, actualStatusMessage);
  }

  @Test
  void shouldAddInvoice() throws ServiceOperationException, DatatypeConfigurationException {
    //Given
    Invoice expectedInvoice = InvoiceGenerator.getRandomInvoice();
    long id = expectedInvoice.getId();
    when(invoiceService.invoiceExistsById(id)).thenReturn(false);
    when(invoiceService.addInvoice(expectedInvoice)).thenReturn(Optional.of(expectedInvoice));
    AddInvoiceRequest request = new AddInvoiceRequest();
    request.setInvoice(InvoiceToXmlConverter.convertInvoiceToXml(expectedInvoice));
    String expectedMessage = String.format("Added new invoice with id %d.", id);

    //When
    AddInvoiceResponse response = invoiceEndpoint.addInvoice(request);
    Invoice actualInvoice = InvoiceFromXmlConverter.convertInvoiceFromXml(response.getInvoice());
    Status actualStatus = response.getStatus();
    String actualMessage = response.getStatusMessage();

    //Then
    assertEquals(expectedInvoice, actualInvoice);
    assertEquals(Status.SUCCESS, actualStatus);
    assertEquals(expectedMessage, actualMessage);
    verify(invoiceService).invoiceExistsById(id);
    verify(invoiceService).addInvoice(expectedInvoice);
  }

  @Test
  void addInvoiceMethodShouldReturnErrorWhenPassedInvoiceIsInvalid() throws DatatypeConfigurationException {
    //Given
    Invoice expectedInvoice = InvoiceGenerator.getRandomInvoice();
    expectedInvoice.setIssueDate(LocalDate.now());
    expectedInvoice.setDueDate(LocalDate.of(2019,01,01));
    List<String> resultOfValidation = InvoiceValidator.validate(expectedInvoice, false);
    AddInvoiceRequest request = new AddInvoiceRequest();
    request.setInvoice(InvoiceToXmlConverter.convertInvoiceToXml(expectedInvoice));
    String expectedMessage = String.format("Passed invoice is invalid. %s.", resultOfValidation.toString());

    //When
    AddInvoiceResponse response = invoiceEndpoint.addInvoice(request);
    Status actualStatus = response.getStatus();
    String actualMessage = response.getStatusMessage();

    //Then
    assertEquals(Status.ERROR, actualStatus);
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void addInvoiceMethodShouldReturnErrorWhenPassedInvoiceAlreadyExist() throws ServiceOperationException, DatatypeConfigurationException {
    //Given
    Invoice expectedInvoice = InvoiceGenerator.getRandomInvoice();
    long id = expectedInvoice.getId();
    when(invoiceService.invoiceExistsById(id)).thenReturn(true);
    AddInvoiceRequest request = new AddInvoiceRequest();
    request.setInvoice(InvoiceToXmlConverter.convertInvoiceToXml(expectedInvoice));
    String expectedMessage = "Invoice already exist.";

    //When
    AddInvoiceResponse response = invoiceEndpoint.addInvoice(request);
    Status actualStatus = response.getStatus();
    String actualMessage = response.getStatusMessage();

    //Then
    assertEquals(Status.ERROR, actualStatus);
    assertEquals(expectedMessage, actualMessage);
    verify(invoiceService).invoiceExistsById(id);
  }

  @Test
  void addInvoiceMethodShouldReturnErrorOccurDuringExecutionAddingInvoiceToDatabase() throws ServiceOperationException, DatatypeConfigurationException {
    //Given
    Invoice expectedInvoice = InvoiceGenerator.getRandomInvoice();
    long id = expectedInvoice.getId();
    when(invoiceService.invoiceExistsById(id)).thenReturn(false);
    doThrow(ServiceOperationException.class).when(invoiceService).addInvoice(expectedInvoice);
    AddInvoiceRequest request = new AddInvoiceRequest();
    request.setInvoice(InvoiceToXmlConverter.convertInvoiceToXml(expectedInvoice));
    String expectedMessage = "An error while adding invoice.";

    //When
    AddInvoiceResponse response = invoiceEndpoint.addInvoice(request);
    Status actualStatus = response.getStatus();
    String actualMessage = response.getStatusMessage();

    //Then
    assertEquals(Status.ERROR, actualStatus);
    assertEquals(expectedMessage, actualMessage);
    verify(invoiceService).invoiceExistsById(id);
  }

  @Test
  void shouldUpdateInvoice() throws ServiceOperationException, DatatypeConfigurationException {
    //Given
    Invoice expectedInvoice = InvoiceGenerator.getRandomInvoice();
    long id = expectedInvoice.getId();
    UpdateInvoiceRequest request = new UpdateInvoiceRequest();
    request.setInvoice(InvoiceToXmlConverter.convertInvoiceToXml(expectedInvoice));
    request.setId(id);
    when(invoiceService.invoiceExistsById(id)).thenReturn(true);
    doNothing().when(invoiceService).updateInvoice(expectedInvoice);
    String expectedMessage = String.format("Invoice with id %d was updated.", id);

    //When
    UpdateInvoiceResponse response = invoiceEndpoint.updateInvoice(request);
    Status actualStatus = response.getStatus();
    String actualMessage = response.getStatusMessage();
    Invoice actualInvoice = InvoiceFromXmlConverter.convertInvoiceFromXml(response.getInvoice());

    //Then
    assertEquals(Status.SUCCESS, actualStatus);
    assertEquals(expectedMessage, actualMessage);
    assertEquals(expectedInvoice, actualInvoice);
    verify(invoiceService).invoiceExistsById(id);
    verify(invoiceService).updateInvoice(expectedInvoice);
  }

  @Test
  void updateInvoiceMethodShouldReturnErrorWhenPassedInvoiceIsInvalid() throws DatatypeConfigurationException {
    //Given
    Invoice expectedInvoice = InvoiceGenerator.getRandomInvoice();
    expectedInvoice.setIssueDate(LocalDate.now());
    expectedInvoice.setDueDate(LocalDate.of(2019,01,01));
    List<String> resultOfValidation = InvoiceValidator.validate(expectedInvoice, false);
    UpdateInvoiceRequest request = new UpdateInvoiceRequest();
    request.setInvoice(InvoiceToXmlConverter.convertInvoiceToXml(expectedInvoice));
    String expectedMessage = String.format("Passed invoice is invalid. %s.", resultOfValidation.toString());

    //When
    UpdateInvoiceResponse response = invoiceEndpoint.updateInvoice(request);
    Status actualStatus = response.getStatus();
    String actualMessage = response.getStatusMessage();

    //Then
    assertEquals(Status.ERROR, actualStatus);
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void updateInvoiceMethodShouldReturnErrorWhenPassedInvoiceDoesNotExist() throws ServiceOperationException, DatatypeConfigurationException {
    //Given
    Invoice expectedInvoice = InvoiceGenerator.getRandomInvoice();
    long id = expectedInvoice.getId();
    when(invoiceService.invoiceExistsById(id)).thenReturn(false);
    UpdateInvoiceRequest request = new UpdateInvoiceRequest();
    request.setInvoice(InvoiceToXmlConverter.convertInvoiceToXml(expectedInvoice));
    request.setId(id);
    String expectedMessage = String.format("Not found invoice with id %d.", id);

    //When
    UpdateInvoiceResponse response = invoiceEndpoint.updateInvoice(request);
    Status actualStatus = response.getStatus();
    String actualMessage = response.getStatusMessage();

    //Then
    assertEquals(Status.ERROR, actualStatus);
    assertEquals(expectedMessage, actualMessage);
    verify(invoiceService).invoiceExistsById(id);
  }

  @Test
  void updateInvoiceMethodShouldReturnErrorWhenPassedInvoiceHasDifferentIdThanRequestId() throws DatatypeConfigurationException {
    //Given
    Invoice expectedInvoice = InvoiceGenerator.getRandomInvoice();
    long id = expectedInvoice.getId();
    UpdateInvoiceRequest request = new UpdateInvoiceRequest();
    request.setInvoice(InvoiceToXmlConverter.convertInvoiceToXml(expectedInvoice));
    request.setId(id + 1L);
    String expectedMessage = String.format("Invoice to update has different id than %d.", id + 1);

    //When
    UpdateInvoiceResponse response = invoiceEndpoint.updateInvoice(request);
    Status actualStatus = response.getStatus();
    String actualMessage = response.getStatusMessage();

    //Then
    assertEquals(Status.ERROR, actualStatus);
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void updateInvoiceMethodShouldReturnErrorOccurDuringExecutionUpdatingInvoiceInDatabase() throws ServiceOperationException, DatatypeConfigurationException {
    //Given
    Invoice expectedInvoice = InvoiceGenerator.getRandomInvoice();
    long id = expectedInvoice.getId();
    when(invoiceService.invoiceExistsById(id)).thenReturn(true);
    doThrow(ServiceOperationException.class).when(invoiceService).updateInvoice(expectedInvoice);
    UpdateInvoiceRequest request = new UpdateInvoiceRequest();
    request.setInvoice(InvoiceToXmlConverter.convertInvoiceToXml(expectedInvoice));
    request.setId(id);
    String expectedMessage = "An error while updating invoice.";

    //When
    UpdateInvoiceResponse response = invoiceEndpoint.updateInvoice(request);
    Status actualStatus = response.getStatus();
    String actualMessage = response.getStatusMessage();

    //Then
    assertEquals(Status.ERROR, actualStatus);
    assertEquals(expectedMessage, actualMessage);
    verify(invoiceService).invoiceExistsById(id);
  }

  @Test
  void shouldDeleteInvoice() throws ServiceOperationException {
    //Given
    Invoice expectedInvoice = InvoiceGenerator.getRandomInvoice();
    Long id = expectedInvoice.getId();
    when(invoiceService.getInvoiceById(id)).thenReturn(Optional.of(expectedInvoice));
    doNothing().when(invoiceService).deleteInvoiceById(id);
    DeleteInvoiceRequest request = new DeleteInvoiceRequest();
    request.setId(id);
    String expectedStatusMessage = String.format("Invoice with id %d was removed.", id);

    //When
    DeleteInvoiceResponse response = invoiceEndpoint.deleteInvoice(request);
    Status actualStatus = response.getStatus();
    String actualStatusMessage = response.getStatusMessage();

    //Then
    assertEquals(Status.SUCCESS, actualStatus);
    assertEquals(expectedStatusMessage, actualStatusMessage);
    verify(invoiceService).getInvoiceById(id);
  }

  @Test
  void deleteInvoiceMethodShoudReturnErrorWhenInvoiceDoesNotExist() throws ServiceOperationException {
    //Given
    Long id = 1L;
    when(invoiceService.getInvoiceById(id)).thenReturn(Optional.empty());
    DeleteInvoiceRequest request = new DeleteInvoiceRequest();
    request.setId(id);
    String expectedStatusMessage = String.format("Invoice with id %d does not exist.", id);

    //When
    DeleteInvoiceResponse response = invoiceEndpoint.deleteInvoice(request);
    Status actualStatus = response.getStatus();
    String actualStatusMessage = response.getStatusMessage();

    //Then
    assertEquals(Status.ERROR, actualStatus);
    assertEquals(expectedStatusMessage, actualStatusMessage);
    verify(invoiceService).getInvoiceById(id);
  }

  @Test
  void deleteInvoiceMethodShouldReturnErrorWhenInvoiceDoesNotExist() throws ServiceOperationException {
    //Given
    Invoice expectedInvoice = InvoiceGenerator.getRandomInvoice();
    Long id = expectedInvoice.getId();
    when(invoiceService.getInvoiceById(id)).thenReturn(Optional.of(expectedInvoice));
    doThrow(ServiceOperationException.class).when(invoiceService).deleteInvoiceById(id);
    DeleteInvoiceRequest request = new DeleteInvoiceRequest();
    request.setId(id);
    String expectedStatusMessage = "An error while deleting invoice.";

    //When
    DeleteInvoiceResponse response = invoiceEndpoint.deleteInvoice(request);
    Status actualStatus = response.getStatus();
    String actualStatusMessage = response.getStatusMessage();

    //Then
    assertEquals(Status.ERROR, actualStatus);
    assertEquals(expectedStatusMessage, actualStatusMessage);
    verify(invoiceService).getInvoiceById(id);
  }
}
