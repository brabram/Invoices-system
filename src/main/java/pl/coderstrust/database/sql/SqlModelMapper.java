package pl.coderstrust.database.sql;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.coderstrust.database.sql.model.SqlAccountNumber;
import pl.coderstrust.database.sql.model.SqlAddress;
import pl.coderstrust.database.sql.model.SqlCompany;
import pl.coderstrust.database.sql.model.SqlContactDetails;
import pl.coderstrust.database.sql.model.SqlInvoice;
import pl.coderstrust.database.sql.model.SqlInvoiceEntry;
import pl.coderstrust.database.sql.model.SqlVat;

@Mapper(componentModel = "spring")
public interface SqlModelMapper {

  @Mapping(target = "withId", source = "id")
  @Mapping(target = "withNumber", source = "number")
  @Mapping(target = "withIssueDate", source = "issueDate")
  @Mapping(target = "withDueDate", source = "dueDate")
  @Mapping(target = "withTotalNetValue", source = "totalNetValue")
  @Mapping(target = "withTotalGrossValue", source = "totalGrossValue")
  @Mapping(target = "withSeller", source = "seller")
  @Mapping(target = "withBuyer", source = "buyer")
  @Mapping(target = "withEntries", source = "entries")
  SqlInvoice mapInvoice(pl.coderstrust.model.Invoice invoice);

  @Mapping(target = "withId", source = "id")
  @Mapping(target = "withNumber", source = "number")
  @Mapping(target = "withIssueDate", source = "issueDate")
  @Mapping(target = "withDueDate", source = "dueDate")
  @Mapping(target = "withTotalNetValue", source = "totalNetValue")
  @Mapping(target = "withTotalGrossValue", source = "totalGrossValue")
  @Mapping(target = "withSeller", source = "seller")
  @Mapping(target = "withBuyer", source = "buyer")
  @Mapping(target = "withEntries", source = "entries")
  pl.coderstrust.model.Invoice mapInvoice(SqlInvoice sqlInvoice);

  @Mapping(target = "withId", source = "id")
  @Mapping(target = "withStreet", source = "street")
  @Mapping(target = "withNumber", source = "number")
  @Mapping(target = "withPostalCode", source = "postalCode")
  @Mapping(target = "withCity", source = "city")
  @Mapping(target = "withCountry", source = "country")
  SqlAddress mapAddress(pl.coderstrust.model.Address address);

  @Mapping(target = "withId", source = "id")
  @Mapping(target = "withStreet", source = "street")
  @Mapping(target = "withNumber", source = "number")
  @Mapping(target = "withPostalCode", source = "postalCode")
  @Mapping(target = "withCity", source = "city")
  @Mapping(target = "withCountry", source = "country")
  pl.coderstrust.model.Address mapAddress(SqlAddress sqlAddress);

  @Mapping(target = "withId", source = "id")
  @Mapping(target = "withIbanNumber", source = "ibanNumber")
  @Mapping(target = "withLocalNumber", source = "localNumber")
  SqlAccountNumber mapAccountNumber(pl.coderstrust.model.AccountNumber accountNumber);

  @Mapping(target = "withId", source = "id")
  @Mapping(target = "withIbanNumber", source = "ibanNumber")
  @Mapping(target = "withLocalNumber", source = "localNumber")
  pl.coderstrust.model.AccountNumber mapAccountNumber(SqlAccountNumber sqlAccountNumber);

  @Mapping(target = "withId", source = "id")
  @Mapping(target = "withName", source = "name")
  @Mapping(target = "withTaxIdentificationNumber", source = "taxIdentificationNumber")
  @Mapping(target = "withAccountNumber", source = "accountNumber")
  @Mapping(target = "withContactDetails", source = "contactDetails")
  SqlCompany mapCompany(pl.coderstrust.model.Company company);

  @Mapping(target = "withId", source = "id")
  @Mapping(target = "withName", source = "name")
  @Mapping(target = "withTaxIdentificationNumber", source = "taxIdentificationNumber")
  @Mapping(target = "withAccountNumber", source = "accountNumber")
  @Mapping(target = "withContactDetails", source = "contactDetails")
  pl.coderstrust.model.Company mapCompany(SqlCompany sqlCompany);

  @Mapping(target = "withId", source = "id")
  @Mapping(target = "withEmail", source = "email")
  @Mapping(target = "withPhoneNumber", source = "phoneNumber")
  @Mapping(target = "withWebsite", source = "website")
  @Mapping(target = "withAddress", source = "address")
  SqlContactDetails mapContactDetails(pl.coderstrust.model.ContactDetails contactDetails);

  @Mapping(target = "withId", source = "id")
  @Mapping(target = "withEmail", source = "email")
  @Mapping(target = "withPhoneNumber", source = "phoneNumber")
  @Mapping(target = "withWebsite", source = "website")
  @Mapping(target = "withAddress", source = "address")
  pl.coderstrust.model.ContactDetails mapContactDetails(SqlContactDetails sqlContactDetails);

  @Mapping(target = "withId", source = "id")
  @Mapping(target = "withItem", source = "item")
  @Mapping(target = "withQuantity", source = "quantity")
  @Mapping(target = "withPrice", source = "price")
  @Mapping(target = "withVatValue", source = "vatValue")
  @Mapping(target = "withGrossValue", source = "grossValue")
  @Mapping(target = "withVatRate", source = "vatRate")
  SqlInvoiceEntry mapInvoiceEntry(pl.coderstrust.model.InvoiceEntry invoiceEntry);

  @Mapping(target = "withId", source = "id")
  @Mapping(target = "withItem", source = "item")
  @Mapping(target = "withQuantity", source = "quantity")
  @Mapping(target = "withPrice", source = "price")
  @Mapping(target = "withVatValue", source = "vatValue")
  @Mapping(target = "withGrossValue", source = "grossValue")
  @Mapping(target = "withVatRate", source = "vatRate")
  pl.coderstrust.model.InvoiceEntry mapInvoiceEntry(SqlInvoiceEntry sqlInvoiceEntry);

  SqlVat mapVat(pl.coderstrust.model.Vat vat);

  pl.coderstrust.model.Vat mapVat(SqlVat sqlVat);
}
