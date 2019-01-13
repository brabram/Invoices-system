package pl.coderstrust.validators;

import pl.coderstrust.model.Company;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.model.InvoiceEntry;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static pl.coderstrust.validators.ResultOfValidation.addResultOfValidation;

public class InvoiceValidator {

  public static List<String> validate(Invoice invoice) {
    if (invoice == null) {
      return Collections.singletonList("Invoice cannot be null");
    }
    List<String> result = new ArrayList<>();
    String idValidator = validateId(String.valueOf(invoice.getId()));
    String numberValidator = validateNumber(String.valueOf(invoice.getNumber()));
    String issueDateValidator = validateDate(invoice.getIssueDate());
    String dueDateValidator = validateDate(invoice.getDueDate());
    String companySeller = String.valueOf(CompanyValidator.validate(invoice.getSeller()));
    String companyBuyer = String.valueOf(CompanyValidator.validate(invoice.getBuyer()));
    List<String> invoiceEntriesValidator = InvoiceEntryValidator.validate((InvoiceEntry) invoice.getEntries());
    String totalNetValueValidator = validateTotalNetValue(String.valueOf(invoice.getTotalNetValue()));
    String totalGrossValueValidator = validateTotalGrossValue(String.valueOf(invoice.getTotalGrossValue()));
    addResultOfValidation(result, idValidator);
    addResultOfValidation(result, numberValidator);
    addResultOfValidation(result, issueDateValidator);
    addResultOfValidation(result, dueDateValidator);
    addResultOfValidation(result, companySeller);
    addResultOfValidation(result, companyBuyer);
    addResultOfValidation(result, String.valueOf(invoiceEntriesValidator));
    addResultOfValidation(result, totalNetValueValidator);
    addResultOfValidation(result, totalGrossValueValidator);
    return result;
  }

  private static String validateId(String id) {
    if (id == null) {
      return "Id cannot be null";
    }
    if (id.trim().isEmpty()) {
      return "Id cannot be empty";
    }
    if (!id.matches("[0-9]+")) {
      return "Incorrect id";
    }
    return "";
  }

  private static String validateNumber(String number) {
    if (number == null) {
      return "Number cannot be null";
    }
    if (number.trim().isEmpty()) {
      return "Number cannot be empty";
    }
    if (!number.matches("[A-Z][0-9]+")) {
      return "Incorrect number";
    }
    return "";
  }

  private static String validateDate(LocalDate date) {
    if (date == null) {
      return "Number cannot be null";
    }
    if (date.toString().trim().isEmpty()) {
      return "Number cannot be empty";
    }
    if (!date.toString().matches("^(3[01]|[12][0-9]|0[1-9]).(1[0-2]|0[1-9]).[0-9]{4}$")) {
      return "Incorrect number";
    }
    return "";
  }

  private static String validateTotalNetValue(String totalNetValue) {
    if (totalNetValue == null) {
      return "Total net value cannot be null";
    }
    if (totalNetValue.trim().isEmpty()) {
      return "Total net value cannot be empty";
    }
    if (!totalNetValue.matches("[0-9]+([,.][0-9]{1,2})?")) {
      return "Incorrect total net value";
    }
    return "";
  }

  private static String validateTotalGrossValue(String totalGrossValue) {
    if (totalGrossValue == null) {
      return "Total gross value cannot be null";
    }
    if (totalGrossValue.trim().isEmpty()) {
      return "Total gross value cannot be empty";
    }
    if (!totalGrossValue.matches("[0-9]+([,.][0-9]{1,2})?")) {
      return "Incorrect total gross value";
    }
    return "";
  }
}
