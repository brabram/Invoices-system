package pl.coderstrust.database.nosql;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.coderstrust.database.nosql.model.NoSqlAccountNumber;
import pl.coderstrust.database.nosql.model.NoSqlAddress;
import pl.coderstrust.database.nosql.model.NoSqlCompany;
import pl.coderstrust.database.nosql.model.NoSqlContactDetails;
import pl.coderstrust.database.nosql.model.NoSqlInvoice;
import pl.coderstrust.database.nosql.model.NoSqlInvoiceEntry;
import pl.coderstrust.database.nosql.model.NoSqlVat;
import pl.coderstrust.model.AccountNumber;
import pl.coderstrust.model.Address;
import pl.coderstrust.model.Company;
import pl.coderstrust.model.ContactDetails;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.model.InvoiceEntry;
import pl.coderstrust.model.Vat;

@Mapper(componentModel = "spring")
public interface NoSqlModelMapper {

  @Mapping(target = "withId", source = "id")
  @Mapping(target = "withMongoId", ignore = true)
  @Mapping(target = "withNumber", source = "number")
  @Mapping(target = "withIssueDate", source = "issueDate")
  @Mapping(target = "withDueDate", source = "dueDate")
  @Mapping(target = "withTotalNetValue", source = "totalNetValue")
  @Mapping(target = "withTotalGrossValue", source = "totalGrossValue")
  @Mapping(target = "withSeller", source = "seller")
  @Mapping(target = "withBuyer", source = "buyer")
  @Mapping(target = "withEntries", source = "entries")
  NoSqlInvoice mapInvoice(pl.coderstrust.model.Invoice invoice);

  Invoice mapInvoice(NoSqlInvoice noSqlInvoice);

  List<Invoice> mapInvoices(List<NoSqlInvoice> noSqlInvoices);

  @Mapping(target = "withStreet", source = "street")
  @Mapping(target = "withNumber", source = "number")
  @Mapping(target = "withPostalCode", source = "postalCode")
  @Mapping(target = "withCity", source = "city")
  @Mapping(target = "withCountry", source = "country")
  NoSqlAddress mapAddress(pl.coderstrust.model.Address address);

  @Mapping(target = "id", constant = "0L")
  Address mapAddress(NoSqlAddress noSqlAddress);

  @Mapping(target = "withIbanNumber", source = "ibanNumber")
  @Mapping(target = "withLocalNumber", source = "localNumber")
  NoSqlAccountNumber mapAccountNumber(AccountNumber accountNumber);

  @Mapping(target = "id", constant = "0L")
  AccountNumber mapAccountNumber(NoSqlAccountNumber noSqlAccountNumber);

  @Mapping(target = "withName", source = "name")
  @Mapping(target = "withTaxIdentificationNumber", source = "taxIdentificationNumber")
  @Mapping(target = "withAccountNumber", source = "accountNumber")
  @Mapping(target = "withContactDetails", source = "contactDetails")
  NoSqlCompany mapCompany(Company company);

  @Mapping(target = "id", constant = "0L")
  Company mapCompany(NoSqlCompany noSqlCompany);

  @Mapping(target = "withEmail", source = "email")
  @Mapping(target = "withPhoneNumber", source = "phoneNumber")
  @Mapping(target = "withWebsite", source = "website")
  @Mapping(target = "withAddress", source = "address")
  NoSqlContactDetails mapContactDetails(ContactDetails contactDetails);

  @Mapping(target = "id", constant = "0L")
  ContactDetails mapContactDetails(NoSqlContactDetails noSqlContactDetails);

  @Mapping(target = "withItem", source = "item")
  @Mapping(target = "withQuantity", source = "quantity")
  @Mapping(target = "withPrice", source = "price")
  @Mapping(target = "withVatValue", source = "vatValue")
  @Mapping(target = "withGrossValue", source = "grossValue")
  @Mapping(target = "withVatRate", source = "vatRate")
  NoSqlInvoiceEntry mapInvoiceEntry(InvoiceEntry invoiceEntry);

  @Mapping(target = "id", constant = "0L")
  InvoiceEntry mapInvoiceEntry(NoSqlInvoiceEntry noSqlInvoiceEntry);

  NoSqlVat mapVat(Vat vat);

  Vat mapVat(NoSqlVat noSqlVat);
}
