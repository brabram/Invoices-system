package pl.coderstrust.service.soap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
public class InvoiceEndopointTest {

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
  void shouldUpdateInvoice() throws ServiceOperationException, DatatypeConfigurationException {
    //Given
    Invoice expectedInvoice = InvoiceGenerator.getRandomInvoice();
    long id = expectedInvoice.getId();
    UpdateInvoiceRequest request = new UpdateInvoiceRequest();
    request.setInvoice(InvoiceToXmlConverter.convertInvoiceToXml(expectedInvoice));
    request.setId(id);
    String expectedMessage = String.format("Invoice with id %d was updated.", id);
    when(invoiceService.invoiceExistsById(id)).thenReturn(true);
    doNothing().when(invoiceService).updateInvoice(expectedInvoice);

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
  void shouldDeleteInvoice() throws ServiceOperationException, DatatypeConfigurationException {
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
}
