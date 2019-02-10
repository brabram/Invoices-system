package pl.coderstrust.soap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import pl.coderstrust.service.InvoiceService;

@Endpoint
public class InvoiceEndpoint {

  private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

  private InvoiceService invoiceService;

  @Autowired
  public InvoiceEndpoint(InvoiceService invoiceService) {
    this.invoiceService = invoiceService;
  }

//  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInvoiceRequest")
//  @ResponsePayload
//  public GetInvoicesResponse getCountry(@RequestPayload GetInvoicesRequest request) throws DatabaseOperationException {
//    GetInvoicesResponse response = new GetInvoicesResponse();
//    Long id = request.getId();
////    Invoice invoice = Optional.of(invoiceDatabase.findById(id));
////    response.setInvoice(invoice);
//
//    return response;
//  }
}