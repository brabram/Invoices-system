package pl.coderstrust.service.soap;

import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.noFault;
import static org.springframework.ws.test.server.ResponseMatchers.payload;
import static org.springframework.ws.test.server.ResponseMatchers.validPayload;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import javax.xml.transform.Source;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class InvoiceEndpointTest {

  @Autowired
  private WebApplicationContext webApplicationContext;
  private MockWebServiceClient mockWebServiceClient;
  private Resource xsdSchema = new ClassPathResource("invoicesSchema.xsd");

  @BeforeEach
  public void setup() {
    mockWebServiceClient = MockWebServiceClient.createClient(webApplicationContext);
  }
  @Test
  void shouldAddInvoice() throws IOException {
    //Given
    String addFilePathRequest = "src/test/resources/soap/addInvoiceRequest.xml";
    String addFilePathResponse = "src/test/resources/soap/addInvoiceResponse.xml";
    String addRequest = Files.lines(Paths.get(addFilePathRequest)).collect(Collectors.joining("\n"));
    String addResponse = Files.lines(Paths.get(addFilePathResponse)).collect(Collectors.joining("\n"));
    Source addRequestPayload = new StringSource(addRequest);
    Source addResponsePayload = new StringSource(addResponse);

    //When
    mockWebServiceClient
        .sendRequest(withPayload(addRequestPayload))
        //Then
        .andExpect(noFault())
        .andExpect(payload(addResponsePayload))
        .andExpect(validPayload(xsdSchema));
  }

  @Test
  void shouldGetAllInvoices() throws IOException {
    //Given
    String addFilePathRequest = "src/test/resources/soap/addInvoiceRequest.xml";
    String addFilePathResponse = "src/test/resources/soap/addInvoiceResponse.xml";
    String addRequest = Files.lines(Paths.get(addFilePathRequest)).collect(Collectors.joining("\n"));
    String addResponse = Files.lines(Paths.get(addFilePathResponse)).collect(Collectors.joining("\n"));
    Source addRequestPayload = new StringSource(addRequest);
    Source addResponsePayload = new StringSource(addResponse);
    String getAllFilePathRequest = "src/test/resources/soap/getAllInvoicesRequest.xml";
    String getAllFilePathResponse = "src/test/resources/soap/getAllInvoicesResponse.xml";
    String getAllRequest = Files.lines(Paths.get(getAllFilePathRequest)).collect(Collectors.joining("\n"));
    String getAllResponse = Files.lines(Paths.get(getAllFilePathResponse)).collect(Collectors.joining("\n"));
    Source getAllRequestPayload = new StringSource(getAllRequest);
    Source getAllResponsePayload = new StringSource(getAllResponse);

    //When
    mockWebServiceClient
        .sendRequest(withPayload(addRequestPayload))
        .andExpect(noFault())
        .andExpect(payload(addResponsePayload))
        .andExpect(validPayload(xsdSchema));
    mockWebServiceClient
        .sendRequest(withPayload(getAllRequestPayload))
        //Then
        .andExpect(noFault())
        .andExpect(payload(getAllResponsePayload))
        .andExpect(validPayload(xsdSchema));
  }

  @Test
  void shouldGetInvoiceById() throws IOException {
    //Given
    String addFilePathRequest = "src/test/resources/soap/addInvoiceRequest.xml";
    String addFilePathResponse = "src/test/resources/soap/addInvoiceResponse.xml";
    String addRequest = Files.lines(Paths.get(addFilePathRequest)).collect(Collectors.joining("\n"));
    String addResponse = Files.lines(Paths.get(addFilePathResponse)).collect(Collectors.joining("\n"));
    Source addRequestPayload = new StringSource(addRequest);
    Source addResponsePayload = new StringSource(addResponse);
    String getByIdFilePathRequest = "src/test/resources/soap/getInvoiceByIdRequest.xml";
    String getByIdFilePathResponse = "src/test/resources/soap/getInvoiceByIdResponse.xml";
    String getByIdRequest = Files.lines(Paths.get(getByIdFilePathRequest)).collect(Collectors.joining("\n"));
    String getByIdResponse = Files.lines(Paths.get(getByIdFilePathResponse)).collect(Collectors.joining("\n"));
    Source getByIdRequestPayload = new StringSource(getByIdRequest);
    Source getByIdResponsePayload = new StringSource(getByIdResponse);

    //When
    mockWebServiceClient
        .sendRequest(withPayload(addRequestPayload))
        .andExpect(noFault())
        .andExpect(payload(addResponsePayload))
        .andExpect(validPayload(xsdSchema));
    mockWebServiceClient
        .sendRequest(withPayload(getByIdRequestPayload))
        //Then
        .andExpect(noFault())
        .andExpect(payload(getByIdResponsePayload))
        .andExpect(validPayload(xsdSchema));
  }

  @Test
  void shouldGetInvoiceByNumber() throws IOException {
    //Given
    String addFilePathRequest = "src/test/resources/soap/addInvoiceRequest.xml";
    String addFilePathResponse = "src/test/resources/soap/addInvoiceResponse.xml";
    String addRequest = Files.lines(Paths.get(addFilePathRequest)).collect(Collectors.joining("\n"));
    String addResponse = Files.lines(Paths.get(addFilePathResponse)).collect(Collectors.joining("\n"));
    Source addRequestPayload = new StringSource(addRequest);
    Source addResponsePayload = new StringSource(addResponse);
    String getByNumberFilePathRequest = "src/test/resources/soap/getInvoiceByNumberRequest.xml";
    String getByNumberFilePathResponse = "src/test/resources/soap/getInvoiceByNumberResponse.xml";
    String getByNumberRequest = Files.lines(Paths.get(getByNumberFilePathRequest)).collect(Collectors.joining("\n"));
    String getByNumberResponse = Files.lines(Paths.get(getByNumberFilePathResponse)).collect(Collectors.joining("\n"));
    Source getByNumberRequestPayload = new StringSource(getByNumberRequest);
    Source getByNumberResponsePayload = new StringSource(getByNumberResponse);

    //When
    mockWebServiceClient
        .sendRequest(withPayload(addRequestPayload))
        .andExpect(noFault())
        .andExpect(payload(addResponsePayload))
        .andExpect(validPayload(xsdSchema));
    mockWebServiceClient
        .sendRequest(withPayload(getByNumberRequestPayload))
        //Then
        .andExpect(noFault())
        .andExpect(payload(getByNumberResponsePayload))
        .andExpect(validPayload(xsdSchema));
  }

  @Test
  void shouldUpdateInvoice() throws IOException {
    //Given
    String addFilePathRequest = "src/test/resources/soap/addInvoiceRequest.xml";
    String addFilePathResponse = "src/test/resources/soap/addInvoiceResponse.xml";
    String addRequest = Files.lines(Paths.get(addFilePathRequest)).collect(Collectors.joining("\n"));
    String addResponse = Files.lines(Paths.get(addFilePathResponse)).collect(Collectors.joining("\n"));
    Source addRequestPayload = new StringSource(addRequest);
    Source addResponsePayload = new StringSource(addResponse);
    String updateFilePathRequest = "src/test/resources/soap/updateInvoiceRequest.xml";
    String updateFilePathResponse = "src/test/resources/soap/updateInvoiceResponse.xml";
    String updateRequest = Files.lines(Paths.get(updateFilePathRequest)).collect(Collectors.joining("\n"));
    String updateResponse = Files.lines(Paths.get(updateFilePathResponse)).collect(Collectors.joining("\n"));
    Source updateRequestPayload = new StringSource(updateRequest);
    Source updateResponsePayload = new StringSource(updateResponse);

    //When
    mockWebServiceClient
        .sendRequest(withPayload(addRequestPayload))
        .andExpect(noFault())
        .andExpect(payload(addResponsePayload))
        .andExpect(validPayload(xsdSchema));
    mockWebServiceClient
        .sendRequest(withPayload(updateRequestPayload))
        //Then
        .andExpect(noFault())
        .andExpect(payload(updateResponsePayload))
        .andExpect(validPayload(xsdSchema));
  }

  @Test
  void shouldDeleteInvoice() throws IOException {
    //Given
    String addFilePathRequest = "src/test/resources/soap/addInvoiceRequest.xml";
    String addFilePathResponse = "src/test/resources/soap/addInvoiceResponse.xml";
    String addRequest = Files.lines(Paths.get(addFilePathRequest)).collect(Collectors.joining("\n"));
    String addResponse = Files.lines(Paths.get(addFilePathResponse)).collect(Collectors.joining("\n"));
    Source addRequestPayload = new StringSource(addRequest);
    Source addResponsePayload = new StringSource(addResponse);
    String deleteFilePathRequest = "src/test/resources/soap/deleteInvoiceRequest.xml";
    String deleteFilePathResponse = "src/test/resources/soap/deleteInvoiceResponse.xml";
    String deleteRequest = Files.lines(Paths.get(deleteFilePathRequest)).collect(Collectors.joining("\n"));
    String deleteResponse = Files.lines(Paths.get(deleteFilePathResponse)).collect(Collectors.joining("\n"));
    Source deleteRequestPayload = new StringSource(deleteRequest);
    Source deleteResponsePayload = new StringSource(deleteResponse);

    //When
    mockWebServiceClient
        .sendRequest(withPayload(addRequestPayload))
        .andExpect(noFault())
        .andExpect(payload(addResponsePayload))
        .andExpect(validPayload(xsdSchema));
    mockWebServiceClient
        .sendRequest(withPayload(deleteRequestPayload))
        //Then
        .andExpect(noFault())
        .andExpect(payload(deleteResponsePayload))
        .andExpect(validPayload(xsdSchema));
  }
}
