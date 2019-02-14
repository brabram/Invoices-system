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

public class InvoiceFromXmlConverter {

  public static Invoice convertInvoiceFromXml(pl.coderstrust.service.soap.domainclasses.Invoice invoiceXml) throws DatatypeConfigurationException {
    return new Invoice(
        invoiceXml.getId(),
        invoiceXml.getNumber(),
        convertXmlToLocalDate(invoiceXml.getDueDate()),
        convertXmlToLocalDate(invoiceXml.getIssueDate()),
        convertCompanyFromXml(invoiceXml.getSeller()),
        convertCompanyFromXml(invoiceXml.getBuyer()),
        convertEntriesFromXml(invoiceXml.getEntries()),
        invoiceXml.getTotalNetValue(),
        invoiceXml.getTotalGrossValue());
  }

  private static LocalDate convertXmlToLocalDate(XMLGregorianCalendar xmlGregorianCalendar) {
    return xmlGregorianCalendar.toGregorianCalendar().toZonedDateTime().toLocalDate();
  }


  private static List<InvoiceEntry> convertEntriesFromXml(List<pl.coderstrust.service.soap.domainclasses.InvoiceEntry> entriesXml) {
    List<InvoiceEntry> entries = new ArrayList<>();
    for (pl.coderstrust.service.soap.domainclasses.InvoiceEntry invoiceEntryXml : entriesXml) {
      entries.add(convertInvoiceEntryFromXml(invoiceEntryXml));
    }
    return entries;
  }

  private static InvoiceEntry convertInvoiceEntryFromXml(pl.coderstrust.service.soap.domainclasses.InvoiceEntry invoiceEntryXml) {
    return new InvoiceEntry(
        invoiceEntryXml.getId(),
        invoiceEntryXml.getItem(),
        invoiceEntryXml.getQuantity(),
        invoiceEntryXml.getPrice(),
        invoiceEntryXml.getVatValue(),
        invoiceEntryXml.getGrossValue(),
        convertVatRateFromXml(invoiceEntryXml.getVatRate()));
  }

  private static Vat convertVatRateFromXml(pl.coderstrust.service.soap.domainclasses.Vat vatRateXml) {
    return Vat.valueOf(vatRateXml.value());
  }

  private static Company convertCompanyFromXml(pl.coderstrust.service.soap.domainclasses.Company companyXml) {
    return new Company(
        companyXml.getId(),
        companyXml.getName(),
        companyXml.getTaxIdentificationNumber(),
        convertAccountNumberFromXml(companyXml.getAccountNumber()),
        convertContactDetailsFromXml(companyXml.getContactDetails()));
  }

  private static AccountNumber convertAccountNumberFromXml(pl.coderstrust.service.soap.domainclasses.AccountNumber accountNumberXml) {
    return new AccountNumber(
        accountNumberXml.getId(),
        accountNumberXml.getIbanNumber(),
        accountNumberXml.getLocalNumber());
  }

  private static ContactDetails convertContactDetailsFromXml(pl.coderstrust.service.soap.domainclasses.ContactDetails contactDetailsXml) {
    return new ContactDetails(
        contactDetailsXml.getId(),
        contactDetailsXml.getEmail(),
        contactDetailsXml.getPhoneNumber(),
        contactDetailsXml.getWebsite(),
        convertAddressFromXml(contactDetailsXml.getAddress()));
  }

  private static Address convertAddressFromXml(pl.coderstrust.service.soap.domainclasses.Address addressXml) {
    return new Address(
        addressXml.getId(),
        addressXml.getStreet(),
        addressXml.getNumber(),
        addressXml.getCity(),
        addressXml.getPostalCode(),
        addressXml.getCountry());
  }
}
