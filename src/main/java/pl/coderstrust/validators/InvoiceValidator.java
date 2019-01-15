package pl.coderstrust.validators;

import pl.coderstrust.model.Invoice;
import pl.coderstrust.model.InvoiceEntry;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static pl.coderstrust.validators.ResultOfValidation.addResultOfValidation;

public class InvoiceValidator {

  public static List<String> validate(Invoice invoice) {
    if (invoice == null) {
      return Collections.singletonList("Invoice cannot be null");
    }
    List<String> result = new ArrayList<>();
    String idValidator = validateId(invoice.getId());
    String numberValidator = validateNumber(invoice.getNumber());
    String issueDateValidator = validateDate(invoice.getIssueDate(), invoice.getDueDate());
    String totalNetValueValidator = validateTotalNetValue(invoice.getTotalNetValue());
    String totalGrossValueValidator = validateTotalGrossValue(invoice.getTotalGrossValue());
    CompanyValidator.validate(invoice.getSeller());
    CompanyValidator.validate(invoice.getBuyer());
    addResultOfValidation(result, idValidator);
    addResultOfValidation(result, numberValidator);
    addResultOfValidation(result, issueDateValidator);
    addResultOfValidation(result, totalNetValueValidator);
    addResultOfValidation(result, totalGrossValueValidator);
    return result;
  }

  private static String validateId(int id) {
    if (id < 0) {
      return "Id cannot be less than 0";
    }
    return "";
  }

  private static String validateNumber(int number) {
    if (number < 0) {
      return "Number cannot be less than 0";
    }
    return "";
  }

  private static String validateDate(LocalDate issueDate, LocalDate dueDate) {
    if (issueDate == null || dueDate == null) {
      return "Date cannot be null";
    }
    if(issueDate.isAfter(dueDate)) {
      return "Due date cannot be after local date";
    }
    return "";
  }

  private static String validateTotalNetValue(BigDecimal totalNetValue) {
    if (totalNetValue == null) {
      return "Total net value cannot be null";
    }
    if (totalNetValue.intValue() < 0) {
      return "Total net value cannot be less than 0";
    }
    return "";
  }

  private static String validateTotalGrossValue(BigDecimal totalGrossValue) {
    if (totalGrossValue == null) {
      return "Total gross value cannot be null";
    }
    if (totalGrossValue.intValue() < 0) {
      return "Total gross value cannot be less than 0";
    }
    return "";
  }
}
