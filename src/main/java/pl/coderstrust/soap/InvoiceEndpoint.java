package pl.coderstrust.soap;

import io.spring.guides.gs_producing_web_service.GetInvoiceByIdRequest;
import io.spring.guides.gs_producing_web_service.GetInvoiceByIdResponse;
import io.spring.guides.gs_producing_web_service.ObjectFactory;
import java.util.Optional;
import javax.xml.datatype.DatatypeConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.service.InvoiceService;
import pl.coderstrust.service.ServiceOperationException;

@Endpoint
public class InvoiceEndpoint {

  private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";
  private InvoiceService invoiceService;
  private ObjectFactory objectFactory;
  private InvoiceToXmlConverter invoiceToXmlConverter;

  @Autowired
  public InvoiceEndpoint(InvoiceService invoiceService) {
    this.invoiceService = invoiceService;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInvoiceByIdRequest")
  @ResponsePayload
  public GetInvoiceByIdResponse getInvoiceById(@RequestPayload GetInvoiceByIdRequest request) throws ServiceOperationException, DatatypeConfigurationException {
    GetInvoiceByIdResponse response = new GetInvoiceByIdResponse();
    Optional<Invoice> invoiceOptional = invoiceService.getInvoiceById(request.getId());
    if (invoiceOptional.isPresent()) {
      response.setInvoice(invoiceToXmlConverter.convertInvoiceToXml(invoiceOptional.get()));
    }
    return response;
  }
}