package pl.coderstrust.soap;

import io.spring.guides.gs_producing_web_service.AccountNumberXml;
import io.spring.guides.gs_producing_web_service.AddressXml;
import io.spring.guides.gs_producing_web_service.CompanyXml;
import io.spring.guides.gs_producing_web_service.ContactDetailsXml;
import io.spring.guides.gs_producing_web_service.InvoiceEntryXml;
import io.spring.guides.gs_producing_web_service.InvoiceXml;
import io.spring.guides.gs_producing_web_service.ObjectFactory;
import io.spring.guides.gs_producing_web_service.VatXml;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import pl.coderstrust.model.AccountNumber;
import pl.coderstrust.model.Address;
import pl.coderstrust.model.Company;
import pl.coderstrust.model.ContactDetails;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.model.InvoiceEntry;
import pl.coderstrust.model.Vat;

public class InvoiceToXmlConverter {

  private ObjectFactory objectFactory;

  public InvoiceToXmlConverter(ObjectFactory objectFactory) {
    this.objectFactory = objectFactory;
  }

  public InvoiceXml convertInvoiceToXml(Invoice invoice) throws DatatypeConfigurationException {
    InvoiceXml invoiceXml = objectFactory.createInvoiceXml();
    invoiceXml.setId(invoice.getId());
    invoiceXml.setNumber(invoice.getNumber());
    invoiceXml.setDueDate(convertLocalDateToXml(invoice.getDueDate()));
    invoiceXml.setIssueDate(convertLocalDateToXml(invoice.getIssueDate()));
    invoiceXml.setTotalNetValue(invoice.getTotalNetValue());
    invoiceXml.setTotalGrossValue(invoiceXml.getTotalGrossValue());
    invoiceXml.setSeller(convertCompanyToXml(invoice.getSeller()));
    invoiceXml.setBuyer(convertCompanyToXml(invoice.getBuyer()));
    invoiceXml.setEntries(convertEntriesToXml(invoice.getEntries()));
    return invoiceXml;
  }

  private XMLGregorianCalendar convertLocalDateToXml(LocalDate localDate) throws DatatypeConfigurationException {
    GregorianCalendar dateXml = GregorianCalendar.from(localDate.atStartOfDay(ZoneId.systemDefault()));
    return DatatypeFactory.newInstance().newXMLGregorianCalendar(dateXml);
  }

  private List<InvoiceEntryXml> convertEntriesToXml(List<InvoiceEntry> entries) {
    InvoiceEntryXml invoiceEntryXml = objectFactory.createInvoiceEntryXml();
    List<InvoiceEntryXml> entriesXml = new ArrayList<>();
    for (InvoiceEntry invoiceEntry : entries) {
      invoiceEntryXml.setId(invoiceEntry.getId());
      invoiceEntryXml.setItem(invoiceEntry.getItem());
      invoiceEntryXml.setPrice(invoiceEntry.getPrice());
      invoiceEntryXml.setQuantity(invoiceEntry.getQuantity());
      invoiceEntryXml.setVatRate(convertVatRateToXml(invoiceEntry.getVatRate()));
      invoiceEntryXml.setVatValue(invoiceEntry.getVatValue());
      invoiceEntryXml.setGrossValue(invoiceEntry.getGrossValue());
      entriesXml.add(invoiceEntryXml);
    }
    return entriesXml;
  }

  private VatXml convertVatRateToXml(Vat vatRate) {
    return VatXml.fromValue(String.valueOf(vatRate.getValue()));

  }

  public CompanyXml convertCompanyToXml(Company company) {
    CompanyXml companyXml = objectFactory.createCompanyXml();
    companyXml.setId(company.getId());
    companyXml.setName(company.getName());
    companyXml.setTaxIdentificationNumber(company.getTaxIdentificationNumber());
    companyXml.setAccountNumber(convertAccountNumberToXml(company.getAccountNumber()));
    companyXml.setContactDetails(convertContactDetailsToXml(company.getContactDetails()));
    return companyXml;
  }

  public AccountNumberXml convertAccountNumberToXml(AccountNumber accountNumber) {
    AccountNumberXml accountNumberXml = objectFactory.createAccountNumberXml();
    accountNumberXml.setId(accountNumber.getId());
    accountNumberXml.setIbanNumber(accountNumber.getIbanNumber());
    accountNumberXml.setLocalNumber(accountNumber.getLocalNumber());
    return accountNumberXml;
  }

  public ContactDetailsXml convertContactDetailsToXml(ContactDetails contactDetails) {
    ContactDetailsXml contactDetailsXml = objectFactory.createContactDetailsXml();
    contactDetailsXml.setId(contactDetails.getId());
    contactDetailsXml.setAddress(convertAddressToXml(contactDetails.getAddress()));
    contactDetailsXml.setEmail(contactDetails.getEmail());
    contactDetailsXml.setPhoneNumber(contactDetails.getPhoneNumber());
    contactDetailsXml.setWebsite(contactDetails.getWebsite());
    return contactDetailsXml;
  }

  private AddressXml convertAddressToXml(Address address) {
    AddressXml addressXml = objectFactory.createAddressXml();
    addressXml.setId(address.getId());
    addressXml.setStreet(address.getStreet());
    addressXml.setNumber(address.getNumber());
    addressXml.setCity(address.getCity());
    addressXml.setPostalCode(address.getPostalCode());
    addressXml.setCountry(address.getCountry());
    return addressXml;
  }
}
