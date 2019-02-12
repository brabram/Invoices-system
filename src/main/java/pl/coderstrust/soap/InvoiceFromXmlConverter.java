package pl.coderstrust.soap;

import io.spring.guides.gs_producing_web_service.AccountNumberXml;
import io.spring.guides.gs_producing_web_service.AddressXml;
import io.spring.guides.gs_producing_web_service.CompanyXml;
import io.spring.guides.gs_producing_web_service.ContactDetailsXml;
import io.spring.guides.gs_producing_web_service.InvoiceEntryXml;
import io.spring.guides.gs_producing_web_service.InvoiceXml;
import io.spring.guides.gs_producing_web_service.VatXml;
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

  public InvoiceFromXmlConverter() {
  }

  public Invoice convertInvoiceFromXml(InvoiceXml invoiceXml) throws DatatypeConfigurationException {
    Invoice invoice = null;
    invoice.setId(invoiceXml.getId());
    invoice.setNumber(invoice.getNumber());
    invoice.setDueDate(convertXmlToLocalDate(invoiceXml.getDueDate()));
    invoice.setIssueDate(convertXmlToLocalDate(invoiceXml.getIssueDate()));
    invoice.setTotalNetValue(invoiceXml.getTotalNetValue());
    invoice.setTotalGrossValue(invoiceXml.getTotalGrossValue());
    invoice.setSeller(convertCompanyFromXml(invoiceXml.getSeller()));
    invoice.setBuyer(convertCompanyFromXml(invoiceXml.getBuyer()));
    invoice.setEntries(convertEntriesFromXml(invoiceXml.getEntries()));
    return invoice;
  }

  private LocalDate convertXmlToLocalDate(XMLGregorianCalendar xmlGregorianCalendar) {
    return xmlGregorianCalendar.toGregorianCalendar().toZonedDateTime().toLocalDate();
  }


  private List<InvoiceEntry> convertEntriesFromXml(List<InvoiceEntryXml> entriesXml) {
    InvoiceEntry invoiceEntry = null;
    List<InvoiceEntry> entries = new ArrayList<>();
    for (InvoiceEntryXml invoiceEntryXml : entriesXml) {
      invoiceEntry.setId(invoiceEntryXml.getId());
      invoiceEntry.setItem(invoiceEntryXml.getItem());
      invoiceEntry.setPrice(invoiceEntryXml.getPrice());
      invoiceEntry.setQuantity(invoiceEntryXml.getQuantity());
      invoiceEntry.setVatRate(convertVatRateFromXml(invoiceEntryXml.getVatRate()));
      invoiceEntry.setVatValue(invoiceEntryXml.getVatValue());
      invoiceEntry.setGrossValue(invoiceEntryXml.getGrossValue());
      entries.add(invoiceEntry);
    }
    return entries;
  }

  private Vat convertVatRateFromXml(VatXml vatRateXml) {
    return Vat.valueOf(String.valueOf(vatRateXml.value()));

  }

  private Company convertCompanyFromXml(CompanyXml companyXml) {
    Company company = null;
    company.setId(companyXml.getId());
    company.setName(companyXml.getName());
    company.setTaxIdentificationNumber(companyXml.getTaxIdentificationNumber());
    company.setAccountNumber(convertAccountNumberFromXml(companyXml.getAccountNumber()));
    company.setContactDetails(convertContactDetailsFromXml(companyXml.getContactDetails()));
    return company;
  }

  private AccountNumber convertAccountNumberFromXml(AccountNumberXml accountNumberXml) {
    AccountNumber accountNumber = null;
    accountNumber.setId(accountNumberXml.getId());
    accountNumber.setIbanNumber(accountNumberXml.getIbanNumber());
    accountNumber.setLocalNumber(accountNumberXml.getLocalNumber());
    return accountNumber;
  }

  private ContactDetails convertContactDetailsFromXml(ContactDetailsXml contactDetailsXml) {
    ContactDetails contactDetails = null;
    contactDetails.setId(contactDetails.getId());
    contactDetails.setAddress(convertAddressFromXml(contactDetailsXml.getAddress()));
    contactDetails.setEmail(contactDetailsXml.getEmail());
    contactDetails.setPhoneNumber(contactDetailsXml.getPhoneNumber());
    contactDetails.setWebsite(contactDetailsXml.getWebsite());
    return contactDetails;
  }

  private Address convertAddressFromXml(AddressXml addressXml) {
    Address address = null;
    address.setId(addressXml.getId());
    address.setStreet(addressXml.getStreet());
    address.setNumber(addressXml.getNumber());
    address.setCity(addressXml.getCity());
    address.setPostalCode(addressXml.getPostalCode());
    address.setCountry(addressXml.getCountry());
    return address;
  }

}
