package pl.coderstrust.soap;

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

  public static Invoice convertInvoiceFromXml(pl.coderstrust.soap.domainclasses.Invoice invoiceXml) throws DatatypeConfigurationException {
    return Invoice.builder()
        .withId(invoiceXml.getId())
        .withNumber(invoiceXml.getNumber())
        .withIssueDate(convertXmlToLocalDate(invoiceXml.getIssueDate()))
        .withDueDate(convertXmlToLocalDate(invoiceXml.getDueDate()))
        .withSeller(convertCompanyFromXml(invoiceXml.getSeller()))
        .withBuyer(convertCompanyFromXml(invoiceXml.getBuyer()))
        .withEntries(convertEntriesFromXml(invoiceXml.getInvoiceEntries()))
        .withTotalNetValue(invoiceXml.getTotalNetValue())
        .withTotalGrossValue(invoiceXml.getTotalGrossValue()).build();
  }

  private static LocalDate convertXmlToLocalDate(XMLGregorianCalendar xmlGregorianCalendar) {
    return xmlGregorianCalendar.toGregorianCalendar().toZonedDateTime().toLocalDate();
  }

  private static List<InvoiceEntry> convertEntriesFromXml(List<pl.coderstrust.soap.domainclasses.InvoiceEntry> entriesXml) {
    List<InvoiceEntry> entries = new ArrayList<>();
    for (pl.coderstrust.soap.domainclasses.InvoiceEntry invoiceEntryXml : entriesXml) {
      entries.add(convertInvoiceEntryFromXml(invoiceEntryXml));
    }
    return entries;
  }

  private static InvoiceEntry convertInvoiceEntryFromXml(pl.coderstrust.soap.domainclasses.InvoiceEntry invoiceEntryXml) {
    return InvoiceEntry.builder()
        .withId(invoiceEntryXml.getId())
        .withItem(invoiceEntryXml.getItem())
        .withQuantity(invoiceEntryXml.getQuantity())
        .withPrice(invoiceEntryXml.getPrice())
        .withVatValue(invoiceEntryXml.getVatValue())
        .withGrossValue(invoiceEntryXml.getGrossValue())
        .withVatRate(convertVatRateFromXml(invoiceEntryXml.getVatRate())).build();
  }

  private static Vat convertVatRateFromXml(pl.coderstrust.soap.domainclasses.Vat vatRateXml) {
    return Vat.valueOf(vatRateXml.value());
  }

  private static Company convertCompanyFromXml(pl.coderstrust.soap.domainclasses.Company companyXml) {
    return Company.builder()
        .withId(companyXml.getId())
        .withName(companyXml.getName())
        .withTaxIdentificationNumber(companyXml.getTaxIdentificationNumber())
        .withAccountNumber(convertAccountNumberFromXml(companyXml.getAccountNumber()))
        .withContactDetails(convertContactDetailsFromXml(companyXml.getContactDetails())).build();
  }

  private static AccountNumber convertAccountNumberFromXml(pl.coderstrust.soap.domainclasses.AccountNumber accountNumberXml) {
    return AccountNumber.builder()
        .withId(accountNumberXml.getId())
        .withIbanNumber(accountNumberXml.getIbanNumber())
        .withLocalNumber(accountNumberXml.getLocalNumber()).build();
  }

  private static ContactDetails convertContactDetailsFromXml(pl.coderstrust.soap.domainclasses.ContactDetails contactDetailsXml) {
    return ContactDetails.builder()
        .withId(contactDetailsXml.getId())
        .withEmail(contactDetailsXml.getEmail())
        .withPhoneNumber(contactDetailsXml.getPhoneNumber())
        .withWebsite(contactDetailsXml.getWebsite())
        .withAddress(convertAddressFromXml(contactDetailsXml.getAddress())).build();
  }

  private static Address convertAddressFromXml(pl.coderstrust.soap.domainclasses.Address addressXml) {
    return Address.builder()
        .withId(addressXml.getId())
        .withStreet(addressXml.getStreet())
        .withNumber(addressXml.getNumber())
        .withPostalCode(addressXml.getPostalCode())
        .withCity(addressXml.getCity())
        .withCountry(addressXml.getCountry()).build();
  }
}
