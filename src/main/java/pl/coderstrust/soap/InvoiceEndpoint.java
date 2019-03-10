package pl.coderstrust.soap;

import static pl.coderstrust.soap.InvoiceFromXmlConverter.convertInvoiceFromXml;
import static pl.coderstrust.soap.InvoiceToXmlConverter.convertInvoiceToXml;

import java.util.List;
import java.util.Optional;
import javax.xml.datatype.DatatypeConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.model.validators.InvoiceValidator;
import pl.coderstrust.service.InvoiceService;
import pl.coderstrust.soap.domainclasses.AddInvoiceResponse;
import pl.coderstrust.soap.domainclasses.InvoicesList;
import pl.coderstrust.soap.domainclasses.Status;

@Endpoint
public class InvoiceEndpoint {

  private static Logger log = LoggerFactory.getLogger(InvoiceEndpoint.class);
  private static final String NAMESPACE_URI = "http://soap-invoice-service";
  private InvoiceService invoiceService;

  @Autowired
  public InvoiceEndpoint(InvoiceService invoiceService) {
    this.invoiceService = invoiceService;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllInvoicesRequest")
  @ResponsePayload
  public pl.coderstrust.soap.domainclasses.GetAllInvoicesResponse getAllInvoices(@RequestPayload pl.coderstrust.soap.domainclasses.GetAllInvoicesRequest request) {
    pl.coderstrust.soap.domainclasses.GetAllInvoicesResponse response = new pl.coderstrust.soap.domainclasses.GetAllInvoicesResponse();
    try {
      log.debug("Getting all invoices");
      Optional<List<Invoice>> invoicesOptionalList = invoiceService.getAllInvoices();
      if (invoicesOptionalList.isPresent()) {
        InvoicesList invoices = new InvoicesList();
        for (Invoice invoice : invoicesOptionalList.get()) {
          invoices.getInvoice().add(convertInvoiceToXml(invoice));
        }
        response.setStatus(Status.SUCCESS);
        response.setStatusMessage("All invoices were downloaded.");
        response.setInvoicesList(invoices);
        return response;
      }
    } catch (Exception e) {
      String message = "An error while getting invoices from database.";
      response.setStatus(Status.ERROR);
      response.setStatusMessage(message);
      log.error(message, e);
    }
    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInvoiceByIdRequest")
  @ResponsePayload
  public pl.coderstrust.soap.domainclasses.GetInvoiceByIdResponse getInvoiceById(@RequestPayload pl.coderstrust.soap.domainclasses.GetInvoiceByIdRequest request) {
    pl.coderstrust.soap.domainclasses.GetInvoiceByIdResponse response = new pl.coderstrust.soap.domainclasses.GetInvoiceByIdResponse();
    long id = request.getId();
    try {
      log.debug("Getting invoice by id: {}", id);
      Optional<Invoice> invoiceOptional = invoiceService.getInvoiceById(id);
      if (invoiceOptional.isPresent()) {
        response.setStatus(Status.SUCCESS);
        response.setStatusMessage(String.format("Invoice with id %d was downloaded.", id));
        response.setInvoice(convertInvoiceToXml(invoiceOptional.get()));
        return response;
      }
      response.setStatus(Status.ERROR);
      response.setStatusMessage(String.format("Not found invoice with id %d.", id));
      return response;
    } catch (Exception e) {
      String message = String.format("An error while getting invoice with id %d from database.", id);
      log.error(message, e);
      response.setStatus(Status.ERROR);
      response.setStatusMessage(message);
    }
    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInvoiceByNumberRequest")
  @ResponsePayload
  public pl.coderstrust.soap.domainclasses.GetInvoiceByNumberResponse getInvoiceByNumber(@RequestPayload pl.coderstrust.soap.domainclasses.GetInvoiceByNumberRequest request) {
    pl.coderstrust.soap.domainclasses.GetInvoiceByNumberResponse response = new pl.coderstrust.soap.domainclasses.GetInvoiceByNumberResponse();
    String number = request.getNumber();
    try {
      log.debug("Getting invoice by withNumber: {}", number);
      Optional<List<Invoice>> optionalInvoicesList = invoiceService.getAllInvoices();
      if (optionalInvoicesList.isPresent()) {
        Optional<Invoice> optionalInvoice = optionalInvoicesList.get()
            .stream()
            .filter(invoiceToGet -> invoiceToGet.getNumber().equals(number))
            .findFirst();
        if (optionalInvoice.isPresent()) {
          response.setStatus(Status.SUCCESS);
          response.setStatusMessage(String.format("Invoice with number %s was downloaded.", number));
          response.setInvoice(convertInvoiceToXml(optionalInvoice.get()));
          return response;
        }
        response.setStatus(Status.ERROR);
        response.setStatusMessage(String.format("Not found invoice with number %s.", number));
        return response;
      }
    } catch (Exception e) {
      String message = String.format("An error while getting invoice with number %s from database.", number);
      log.error(message, e);
      response.setStatus(Status.ERROR);
      response.setStatusMessage(message);
    }
    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addInvoiceRequest")
  @ResponsePayload
  public pl.coderstrust.soap.domainclasses.AddInvoiceResponse addInvoice(@RequestPayload pl.coderstrust.soap.domainclasses.AddInvoiceRequest request) throws DatatypeConfigurationException {
    pl.coderstrust.soap.domainclasses.AddInvoiceResponse response = new AddInvoiceResponse();
    Invoice invoice = convertInvoiceFromXml(request.getInvoice());
    try {
      log.debug("Adding invoice: {}", invoice);
      List<String> resultOfValidation = InvoiceValidator.validate(invoice, false);
      if (resultOfValidation.size() > 0) {
        response.setStatus(Status.ERROR);
        response.setStatusMessage(String.format("Passed invoice is invalid. %s.", resultOfValidation.toString()));
        return response;
      }
      if (invoice.getId() == null || !invoiceService.invoiceExistsById(invoice.getId())) {
        Optional<Invoice> optionalInvoice = invoiceService.addInvoice(invoice);
        if (optionalInvoice.isPresent()) {
          response.setStatus(Status.SUCCESS);
          response.setStatusMessage(String.format("Added new invoice with id %d.", optionalInvoice.get().getId()));
          response.setInvoice(convertInvoiceToXml(optionalInvoice.get()));
          return response;
        }
        response.setStatus(Status.ERROR);
        response.setStatusMessage("An error while adding invoice. ");
        return response;
      }
      response.setStatus(Status.ERROR);
      response.setStatusMessage("Invoice already exist.");
      return response;
    } catch (Exception e) {
      String message = String.format("An error while adding invoice: %s", invoice);
      log.error(message, e);
      response.setStatus(Status.ERROR);
      response.setStatusMessage(message);
    }
    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateInvoiceRequest")
  @ResponsePayload
  public pl.coderstrust.soap.domainclasses.UpdateInvoiceResponse updateInvoice(@RequestPayload pl.coderstrust.soap.domainclasses.UpdateInvoiceRequest request) throws DatatypeConfigurationException {
    pl.coderstrust.soap.domainclasses.UpdateInvoiceResponse response = new pl.coderstrust.soap.domainclasses.UpdateInvoiceResponse();
    Invoice invoice = convertInvoiceFromXml(request.getInvoice());
    long id = request.getId();
    try {
      log.debug("Updating invoice. id: {}, invoice: {}", id, invoice);
      List<String> resultOfValidation = InvoiceValidator.validate(invoice, true);
      if (resultOfValidation.size() > 0) {
        response.setStatus(Status.ERROR);
        response.setStatusMessage(String.format("Passed invoice is invalid. %s.", resultOfValidation.toString()));
        return response;
      }
      if (id != invoice.getId()) {
        response.setStatus(Status.ERROR);
        response.setStatusMessage(String.format("Invoice to update has different id than %d.", id));
        return response;
      }
      if (!invoiceService.invoiceExistsById(id)) {
        response.setStatus(Status.ERROR);
        response.setStatusMessage(String.format("Not found invoice with id %d.", id));
        return response;
      }
      invoiceService.updateInvoice(invoice);
      response.setStatus(Status.SUCCESS);
      response.setStatusMessage(String.format("Invoice with id %d was updated.", id));
      response.setInvoice(convertInvoiceToXml(invoice));
      return response;
    } catch (Exception e) {
      String message = String.format("An error while updating invoice with  %d id, %s invoice", id, invoice);
      log.error(message, e);
      response.setStatus(Status.ERROR);
      response.setStatusMessage(message);
    }
    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteInvoiceRequest")
  @ResponsePayload
  public pl.coderstrust.soap.domainclasses.DeleteInvoiceResponse deleteInvoice(@RequestPayload pl.coderstrust.soap.domainclasses.DeleteInvoiceRequest request) {
    pl.coderstrust.soap.domainclasses.DeleteInvoiceResponse response = new pl.coderstrust.soap.domainclasses.DeleteInvoiceResponse();
    long id = request.getId();
    try {
      log.debug("Removing invoice. id: {}", id);
      Optional<Invoice> optionalInvoice = invoiceService.getInvoiceById(id);
      if (optionalInvoice.isPresent()) {
        invoiceService.deleteInvoiceById(id);
        response.setStatus(Status.SUCCESS);
        response.setStatusMessage(String.format("Invoice with id %d was removed.", id));
        return response;
      }
      response.setStatus(Status.ERROR);
      response.setStatusMessage(String.format("Invoice with id %d does not exist.", id));
      return response;
    } catch (Exception e) {
      String message = String.format("An error while deleting invoice. id: %d", id);
      log.error(message, e);
      response.setStatus(Status.ERROR);
      response.setStatusMessage(message);
    }
    return response;
  }
}
