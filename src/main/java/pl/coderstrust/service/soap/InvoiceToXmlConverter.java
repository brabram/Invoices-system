package pl.coderstrust.service.soap;

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

  private static pl.coderstrust.service.soap.domainclasses.ObjectFactory objectFactory;

  public InvoiceToXmlConverter(pl.coderstrust.service.soap.domainclasses.ObjectFactory objectFactory) {
    InvoiceToXmlConverter.objectFactory = objectFactory;
  }

  public static pl.coderstrust.service.soap.domainclasses.Invoice convertInvoiceToXml(Invoice invoice) throws DatatypeConfigurationException {
    pl.coderstrust.service.soap.domainclasses.Invoice invoiceXml = new pl.coderstrust.service.soap.domainclasses.Invoice();
    invoiceXml.setId(invoice.getId());
    invoiceXml.setNumber(invoice.getNumber());
    invoiceXml.setDueDate(convertLocalDateToXml(invoice.getDueDate()));
    invoiceXml.setIssueDate(convertLocalDateToXml(invoice.getIssueDate()));
    invoiceXml.setTotalNetValue(invoice.getTotalNetValue());
    invoiceXml.setTotalGrossValue(invoice.getTotalGrossValue());
    invoiceXml.setSeller(convertCompanyToXml(invoice.getSeller()));
    invoiceXml.setBuyer(convertCompanyToXml(invoice.getBuyer()));
    invoiceXml.setEntries(convertEntriesToXml(invoice.getEntries()));
    return invoiceXml;
  }

  private static XMLGregorianCalendar convertLocalDateToXml(LocalDate localDate) throws DatatypeConfigurationException {
    GregorianCalendar dateXml = GregorianCalendar.from(localDate.atStartOfDay(ZoneId.systemDefault()));
    return DatatypeFactory.newInstance().newXMLGregorianCalendar(dateXml);
  }

  private static List<pl.coderstrust.service.soap.domainclasses.InvoiceEntry> convertEntriesToXml(List<InvoiceEntry> entries) {
    pl.coderstrust.service.soap.domainclasses.InvoiceEntry invoiceEntryXml = new pl.coderstrust.service.soap.domainclasses.InvoiceEntry();
    List<pl.coderstrust.service.soap.domainclasses.InvoiceEntry> entriesXml = new ArrayList<>();
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

  private static pl.coderstrust.service.soap.domainclasses.Vat convertVatRateToXml(Vat vatRate) {
    return pl.coderstrust.service.soap.domainclasses.Vat.fromValue(vatRate.toString());

  }

  private static pl.coderstrust.service.soap.domainclasses.Company convertCompanyToXml(Company company) {
    pl.coderstrust.service.soap.domainclasses.Company companyXml = new pl.coderstrust.service.soap.domainclasses.Company();
    companyXml.setId(company.getId());
    companyXml.setName(company.getName());
    companyXml.setTaxIdentificationNumber(company.getTaxIdentificationNumber());
    companyXml.setAccountNumber(convertAccountNumberToXml(company.getAccountNumber()));
    companyXml.setContactDetails(convertContactDetailsToXml(company.getContactDetails()));
    return companyXml;
  }

  private static pl.coderstrust.service.soap.domainclasses.AccountNumber convertAccountNumberToXml(AccountNumber accountNumber) {
    pl.coderstrust.service.soap.domainclasses.AccountNumber accountNumberXml = new pl.coderstrust.service.soap.domainclasses.AccountNumber();
    accountNumberXml.setId(accountNumber.getId());
    accountNumberXml.setIbanNumber(accountNumber.getIbanNumber());
    accountNumberXml.setLocalNumber(accountNumber.getLocalNumber());
    return accountNumberXml;
  }

  private static pl.coderstrust.service.soap.domainclasses.ContactDetails convertContactDetailsToXml(ContactDetails contactDetails) {
    pl.coderstrust.service.soap.domainclasses.ContactDetails contactDetailsXml = new pl.coderstrust.service.soap.domainclasses.ContactDetails();
    contactDetailsXml.setId(contactDetails.getId());
    contactDetailsXml.setAddress(convertAddressToXml(contactDetails.getAddress()));
    contactDetailsXml.setEmail(contactDetails.getEmail());
    contactDetailsXml.setPhoneNumber(contactDetails.getPhoneNumber());
    contactDetailsXml.setWebsite(contactDetails.getWebsite());
    return contactDetailsXml;
  }

  private static pl.coderstrust.service.soap.domainclasses.Address convertAddressToXml(Address address) {
    pl.coderstrust.service.soap.domainclasses.Address addressXml = new pl.coderstrust.service.soap.domainclasses.Address();
    addressXml.setId(address.getId());
    addressXml.setStreet(address.getStreet());
    addressXml.setNumber(address.getNumber());
    addressXml.setCity(address.getCity());
    addressXml.setPostalCode(address.getPostalCode());
    addressXml.setCountry(address.getCountry());
    return addressXml;
  }
}
