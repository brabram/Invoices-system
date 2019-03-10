package pl.coderstrust.soap;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.GregorianCalendar;
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

  public static pl.coderstrust.soap.domainclasses.Invoice convertInvoiceToXml(Invoice invoice) throws DatatypeConfigurationException {
    pl.coderstrust.soap.domainclasses.Invoice invoiceXml = new pl.coderstrust.soap.domainclasses.Invoice();
    invoiceXml.setId(invoice.getId());
    invoiceXml.setNumber(invoice.getNumber());
    invoiceXml.setDueDate(convertLocalDateToXml(invoice.getDueDate()));
    invoiceXml.setIssueDate(convertLocalDateToXml(invoice.getIssueDate()));
    invoiceXml.setTotalNetValue(invoice.getTotalNetValue());
    invoiceXml.setTotalGrossValue(invoice.getTotalGrossValue());
    invoiceXml.setSeller(convertCompanyToXml(invoice.getSeller()));
    invoiceXml.setBuyer(convertCompanyToXml(invoice.getBuyer()));
    for (InvoiceEntry invoiceEntry : invoice.getEntries()) {
      invoiceXml.getInvoiceEntries().add(convertInvoiceEntryToXml(invoiceEntry));
    }
    return invoiceXml;
  }

  private static XMLGregorianCalendar convertLocalDateToXml(LocalDate localDate) throws DatatypeConfigurationException {
    GregorianCalendar dateXml = GregorianCalendar.from(localDate.atStartOfDay(ZoneId.systemDefault()));
    return DatatypeFactory.newInstance().newXMLGregorianCalendar(dateXml);
  }

  private static pl.coderstrust.soap.domainclasses.InvoiceEntry convertInvoiceEntryToXml(InvoiceEntry invoiceEntry) {
    pl.coderstrust.soap.domainclasses.InvoiceEntry invoiceEntryXml = new pl.coderstrust.soap.domainclasses.InvoiceEntry();
    invoiceEntryXml.setId(invoiceEntry.getId());
    invoiceEntryXml.setItem(invoiceEntry.getItem());
    invoiceEntryXml.setPrice(invoiceEntry.getPrice());
    invoiceEntryXml.setQuantity(invoiceEntry.getQuantity());
    invoiceEntryXml.setVatRate(convertVatRateToXml(invoiceEntry.getVatRate()));
    invoiceEntryXml.setVatValue(invoiceEntry.getVatValue());
    invoiceEntryXml.setGrossValue(invoiceEntry.getGrossValue());
    return invoiceEntryXml;
  }

  private static pl.coderstrust.soap.domainclasses.Vat convertVatRateToXml(Vat vatRate) {
    return pl.coderstrust.soap.domainclasses.Vat.fromValue(String.valueOf(vatRate.name()));
  }

  private static pl.coderstrust.soap.domainclasses.Company convertCompanyToXml(Company company) {
    pl.coderstrust.soap.domainclasses.Company companyXml = new pl.coderstrust.soap.domainclasses.Company();
    companyXml.setId(company.getId());
    companyXml.setName(company.getName());
    companyXml.setTaxIdentificationNumber(company.getTaxIdentificationNumber());
    companyXml.setAccountNumber(convertAccountNumberToXml(company.getAccountNumber()));
    companyXml.setContactDetails(convertContactDetailsToXml(company.getContactDetails()));
    return companyXml;
  }

  private static pl.coderstrust.soap.domainclasses.AccountNumber convertAccountNumberToXml(AccountNumber accountNumber) {
    pl.coderstrust.soap.domainclasses.AccountNumber accountNumberXml = new pl.coderstrust.soap.domainclasses.AccountNumber();
    accountNumberXml.setId(accountNumber.getId());
    accountNumberXml.setIbanNumber(accountNumber.getIbanNumber());
    accountNumberXml.setLocalNumber(accountNumber.getLocalNumber());
    return accountNumberXml;
  }

  private static pl.coderstrust.soap.domainclasses.ContactDetails convertContactDetailsToXml(ContactDetails contactDetails) {
    pl.coderstrust.soap.domainclasses.ContactDetails contactDetailsXml = new pl.coderstrust.soap.domainclasses.ContactDetails();
    contactDetailsXml.setId(contactDetails.getId());
    contactDetailsXml.setEmail(contactDetails.getEmail());
    contactDetailsXml.setPhoneNumber(contactDetails.getPhoneNumber());
    contactDetailsXml.setWebsite(contactDetails.getWebsite());
    contactDetailsXml.setAddress(convertAddressToXml(contactDetails.getAddress()));
    return contactDetailsXml;
  }

  private static pl.coderstrust.soap.domainclasses.Address convertAddressToXml(Address address) {
    pl.coderstrust.soap.domainclasses.Address addressXml = new pl.coderstrust.soap.domainclasses.Address();
    addressXml.setId(address.getId());
    addressXml.setStreet(address.getStreet());
    addressXml.setNumber(address.getNumber());
    addressXml.setPostalCode(address.getPostalCode());
    addressXml.setCity(address.getCity());
    addressXml.setCountry(address.getCountry());
    return addressXml;
  }
}
