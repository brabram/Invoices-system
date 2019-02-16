package pl.coderstrust.service.soap;

import static pl.coderstrust.service.soap.InvoiceFromXmlConverter.convertInvoiceFromXml;
import static pl.coderstrust.service.soap.InvoiceToXmlConverter.convertInvoiceToXml;

import java.util.ArrayList;
import java.util.List;
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
import pl.coderstrust.service.soap.domainclasses.AddInvoiceResponse;

@Endpoint
public class InvoiceEndpoint {

  private static final String NAMESPACE_URI = "http://soap-invoice-service";
  private InvoiceService invoiceService;

  @Autowired
  public InvoiceEndpoint(InvoiceService invoiceService) {
    this.invoiceService = invoiceService;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllInvoicesRequest")
  @ResponsePayload
  public pl.coderstrust.service.soap.domainclasses.GetAllInvoicesResponse getAllInvoices(@RequestPayload pl.coderstrust.service.soap.domainclasses.GetAllInvoicesRequest request) throws ServiceOperationException, DatatypeConfigurationException {
    pl.coderstrust.service.soap.domainclasses.GetAllInvoicesResponse response = new pl.coderstrust.service.soap.domainclasses.GetAllInvoicesResponse();
    Optional<List<Invoice>> invoicesOptionalList = invoiceService.getAllInvoices();
    if (invoicesOptionalList.isPresent()) {
      List<pl.coderstrust.service.soap.domainclasses.Invoice> invoices = new ArrayList<>();
      for (Invoice invoice : invoicesOptionalList.get()) {
        invoices.add(convertInvoiceToXml(invoice));
      }
      response.setInvoicesList(invoices);
    }
    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInvoiceByIdRequest")
  @ResponsePayload
  public pl.coderstrust.service.soap.domainclasses.GetInvoiceByIdResponse getInvoiceById(@RequestPayload pl.coderstrust.service.soap.domainclasses.GetInvoiceByIdRequest request) throws ServiceOperationException, DatatypeConfigurationException {
    pl.coderstrust.service.soap.domainclasses.GetInvoiceByIdResponse response = new pl.coderstrust.service.soap.domainclasses.GetInvoiceByIdResponse();
    Optional<Invoice> invoiceOptional = invoiceService.getInvoiceById(request.getId());
    if (invoiceOptional.isPresent()) {
      response.setInvoice(convertInvoiceToXml(invoiceOptional.get()));
    }
    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addInvoiceRequest")
  @ResponsePayload
  public pl.coderstrust.service.soap.domainclasses.AddInvoiceResponse addInvoice(@RequestPayload pl.coderstrust.service.soap.domainclasses.AddInvoiceRequest request) throws ServiceOperationException, DatatypeConfigurationException {
    pl.coderstrust.service.soap.domainclasses.AddInvoiceResponse response = new AddInvoiceResponse();
    Invoice invoice = convertInvoiceFromXml(request.getInvoice());
    Optional<Invoice> optionalInvoice = invoiceService.addInvoice(invoice);
    if (optionalInvoice.isPresent()) {
      response.setInvoice(convertInvoiceToXml(optionalInvoice.get()));
    }
    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateInvoiceRequest")
  @ResponsePayload
  public pl.coderstrust.service.soap.domainclasses.UpdateInvoiceResponse updateInvoice(@RequestPayload pl.coderstrust.service.soap.domainclasses.UpdateInvoiceRequest request) throws ServiceOperationException, DatatypeConfigurationException {
    pl.coderstrust.service.soap.domainclasses.UpdateInvoiceResponse response = new pl.coderstrust.service.soap.domainclasses.UpdateInvoiceResponse();
    invoiceService.updateInvoice(convertInvoiceFromXml(request.getInvoice()));
    Optional<Invoice> optionalInvoice = invoiceService.getInvoiceById(request.getId());
    if (optionalInvoice.isPresent()) {
      response.setInvoice(convertInvoiceToXml(optionalInvoice.get()));
    }
    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteInvoiceRequest")
  @ResponsePayload
  public pl.coderstrust.service.soap.domainclasses.DeleteInvoiceResponse deleteInvoice(@RequestPayload pl.coderstrust.service.soap.domainclasses.DeleteInvoiceRequest request) throws ServiceOperationException, DatatypeConfigurationException {
    pl.coderstrust.service.soap.domainclasses.DeleteInvoiceResponse response = new pl.coderstrust.service.soap.domainclasses.DeleteInvoiceResponse();
    invoiceService.deleteInvoiceById(request.getId());
    Optional<Invoice> optionalInvoice = invoiceService.getInvoiceById(request.getId());
    if (!optionalInvoice.isPresent()) {
      response.setStatusMessage(String.format("Invoice with %d id removed", request.getId()));
    }
    return response;
  }

}