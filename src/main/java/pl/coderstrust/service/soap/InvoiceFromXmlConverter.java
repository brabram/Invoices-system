package pl.coderstrust.service.soap;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import pl.coderstrust.model.AccountNumber;
import pl.coderstrust.model.Address;
import pl.coderstrust.model.Company;
import pl.coderstrust.model.ContactDetails;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.model.InvoiceEntry;
import pl.coderstrust.model.Vat;
import pl.coderstrust.soap.domainclasses.InvoiceEntries;

public class InvoiceFromXmlConverter {

  public static Invoice convertInvoiceFromXml(pl.coderstrust.soap.domainclasses.Invoice invoiceXml) throws DatatypeConfigurationException {
    return new Invoice(
        invoiceXml.getId(),
        invoiceXml.getNumber(),
        convertXmlToLocalDate(invoiceXml.getDueDate()),
        convertXmlToLocalDate(invoiceXml.getIssueDate()),
        convertCompanyFromXml(invoiceXml.getSeller()),
        convertCompanyFromXml(invoiceXml.getBuyer()),
        convertEntriesFromXml(invoiceXml.getInvoiceEntries()),
        invoiceXml.getTotalNetValue(),
        invoiceXml.getTotalGrossValue());
  }

  private static LocalDate convertXmlToLocalDate(XMLGregorianCalendar xmlGregorianCalendar) {
    return xmlGregorianCalendar.toGregorianCalendar().toZonedDateTime().toLocalDate();
  }

  private static List<InvoiceEntry> convertEntriesFromXml(InvoiceEntries entriesXml) {
    List<InvoiceEntry> invoiceEntries = new ArrayList<>();
    List<pl.coderstrust.soap.domainclasses.InvoiceEntry> invoiceEntriesXml = entriesXml.getInvoiceEntry();
    for (pl.coderstrust.soap.domainclasses.InvoiceEntry invoiceEntryXml: invoiceEntriesXml){
      invoiceEntries.add(convertInvoiceEntryFromXml(invoiceEntryXml));
    }
    return invoiceEntries;
  }

  private static InvoiceEntry convertInvoiceEntryFromXml(pl.coderstrust.soap.domainclasses.InvoiceEntry invoiceEntryXml) {
    return new InvoiceEntry(
        invoiceEntryXml.getId(),
        invoiceEntryXml.getItem(),
        invoiceEntryXml.getQuantity(),
        invoiceEntryXml.getPrice(),
        invoiceEntryXml.getVatValue(),
        invoiceEntryXml.getGrossValue(),
        convertVatRateFromXml(invoiceEntryXml.getVatRate()));
  }

  private static Vat convertVatRateFromXml(pl.coderstrust.soap.domainclasses.Vat vatRateXml) {
    return Vat.valueOf(vatRateXml.value());
  }

  private static Company convertCompanyFromXml(pl.coderstrust.soap.domainclasses.Company companyXml) {
    return new Company(
        companyXml.getId(),
        companyXml.getName(),
        companyXml.getTaxIdentificationNumber(),
        convertAccountNumberFromXml(companyXml.getAccountNumber()),
        convertContactDetailsFromXml(companyXml.getContactDetails()));
  }

  private static AccountNumber convertAccountNumberFromXml(pl.coderstrust.soap.domainclasses.AccountNumber accountNumberXml) {
    return new AccountNumber(
        accountNumberXml.getId(),
        accountNumberXml.getIbanNumber(),
        accountNumberXml.getLocalNumber());
  }

  private static ContactDetails convertContactDetailsFromXml(pl.coderstrust.soap.domainclasses.ContactDetails contactDetailsXml) {
    return new ContactDetails(
        contactDetailsXml.getId(),
        contactDetailsXml.getEmail(),
        contactDetailsXml.getPhoneNumber(),
        contactDetailsXml.getWebsite(),
        convertAddressFromXml(contactDetailsXml.getAddress()));
  }

  private static Address convertAddressFromXml(pl.coderstrust.soap.domainclasses.Address addressXml) {
    return new Address(
        addressXml.getId(),
        addressXml.getStreet(),
        addressXml.getNumber(),
        addressXml.getPostalCode(),
        addressXml.getCity(),
        addressXml.getCountry());
  }
}
