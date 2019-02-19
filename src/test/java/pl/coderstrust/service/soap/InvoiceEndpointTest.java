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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
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
    String filePathRequest = "src/test/resources/soap/addInvoiceRequest.xml";
    String filePathResponse = "src/test/resources/soap/addInvoiceResponse.xml";
    String request = Files.lines(Paths.get(filePathRequest)).collect(Collectors.joining("\n"));
    String response = Files.lines(Paths.get(filePathResponse)).collect(Collectors.joining("\n"));
    Source requestPayload = new StringSource(request);
    Source responsePayload = new StringSource(response);

    //When
    mockWebServiceClient
        .sendRequest(withPayload(requestPayload))
        //Then
        .andExpect(noFault())
        .andExpect(payload(responsePayload))
        .andExpect(validPayload(xsdSchema));

  }

  @Test
  void shouldGetAllInvoices() throws IOException {
    //Given
    String filePathRequest = "src/test/resources/soap/getAllInvoicesRequest.xml";
    String filePathResponse = "src/test/resources/soap/getAllInvoicesResponse.xml";
    String request = Files.lines(Paths.get(filePathRequest)).collect(Collectors.joining("\n"));
    String response = Files.lines(Paths.get(filePathResponse)).collect(Collectors.joining("\n"));
    Source requestPayload = new StringSource(request);
    Source responsePayload = new StringSource(response);

    //When
    mockWebServiceClient
        .sendRequest(withPayload(requestPayload))
        //Then
        .andExpect(noFault())
        .andExpect(payload(responsePayload))
        .andExpect(validPayload(xsdSchema));

  }

  @Test
  void shouldGetInvoiceById() throws IOException {
    //Given
    String filePathRequest = "src/test/resources/soap/getInvoiceByIdRequest.xml";
    String filePathResponse = "src/test/resources/soap/getInvoiceByIdResponse.xml";
    String request = Files.lines(Paths.get(filePathRequest)).collect(Collectors.joining("\n"));
    String response = Files.lines(Paths.get(filePathResponse)).collect(Collectors.joining("\n"));
    Source requestPayload = new StringSource(request);
    Source responsePayload = new StringSource(response);

    //When
    mockWebServiceClient
        .sendRequest(withPayload(requestPayload))
        //Then
        .andExpect(noFault())
        .andExpect(payload(responsePayload))
        .andExpect(validPayload(xsdSchema));

  }

  @Test
  void shouldGetInvoiceByNumber() throws IOException {
    //Given
    String filePathRequest = "src/test/resources/soap/getInvoiceByNumberRequest.xml";
    String filePathResponse = "src/test/resources/soap/getInvoiceByNumberResponse.xml";
    String request = Files.lines(Paths.get(filePathRequest)).collect(Collectors.joining("\n"));
    String response = Files.lines(Paths.get(filePathResponse)).collect(Collectors.joining("\n"));
    Source requestPayload = new StringSource(request);
    Source responsePayload = new StringSource(response);

    //When
    mockWebServiceClient
        .sendRequest(withPayload(requestPayload))
        //Then
        .andExpect(noFault())
        .andExpect(payload(responsePayload))
        .andExpect(validPayload(xsdSchema));

  }

  @Test
  void shouldUpdateInvoice() throws IOException {
    //Given
    String filePathRequest = "src/test/resources/soap/updateInvoiceRequest.xml";
    String filePathResponse = "src/test/resources/soap/updateInvoiceResponse.xml";
    String request = Files.lines(Paths.get(filePathRequest)).collect(Collectors.joining("\n"));
    String response = Files.lines(Paths.get(filePathResponse)).collect(Collectors.joining("\n"));
    Source requestPayload = new StringSource(request);
    Source responsePayload = new StringSource(response);

    //When
    mockWebServiceClient
        .sendRequest(withPayload(requestPayload))
        //Then
        .andExpect(noFault())
        .andExpect(payload(responsePayload))
        .andExpect(validPayload(xsdSchema));

  }

  @Test
  void shouldDeleteInvoice() throws IOException {
    //Given
    String filePathRequest = "src/test/resources/soap/deleteInvoiceRequest.xml";
    String filePathResponse = "src/test/resources/soap/deleteInvoiceResponse.xml";
    String request = Files.lines(Paths.get(filePathRequest)).collect(Collectors.joining("\n"));
    String response = Files.lines(Paths.get(filePathResponse)).collect(Collectors.joining("\n"));
    Source requestPayload = new StringSource(request);
    Source responsePayload = new StringSource(response);

    //When
    mockWebServiceClient
        .sendRequest(withPayload(requestPayload))
        //Then
        .andExpect(noFault())
        .andExpect(payload(responsePayload))
        .andExpect(validPayload(xsdSchema));

  }
}