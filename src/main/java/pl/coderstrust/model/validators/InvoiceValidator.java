package pl.coderstrust.model.validators;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;

import pl.coderstrust.model.Invoice;

public class InvoiceValidator extends Validator {

  public static List<String> validate(Invoice invoice, boolean isIdRequired) {
    if (invoice == null) {
      return Collections.singletonList("Invoice cannot be null");
    }
    List<String> result = new ArrayList<>();
    if (isIdRequired) {
      String resultOfIdValidation = validateId(invoice.getId());
      addResultOfValidation(result, resultOfIdValidation);
    }
    String resultOfNumberValidation = validateNumber(invoice.getNumber());
    addResultOfValidation(result, resultOfNumberValidation);
    String resultOfIssueDateValidation = validateDate(invoice.getIssueDate(), invoice.getDueDate());
    addResultOfValidation(result, resultOfIssueDateValidation);
    String resultOfTotalNetValueValidation = validateTotalNetValue(invoice.getTotalNetValue());
    addResultOfValidation(result, resultOfTotalNetValueValidation);
    String resultOfTotalGrossValueValidation = validateTotalGrossValue(invoice.getTotalGrossValue());
    addResultOfValidation(result, resultOfTotalGrossValueValidation);
    List<String> resultOfCompanySellerValidation = CompanyValidator.validate(invoice.getSeller());
    addResultOfValidation(result, resultOfCompanySellerValidation);
    List<String> resultOfCompanyBuyerValidation = CompanyValidator.validate(invoice.getBuyer());
    addResultOfValidation(result, resultOfCompanyBuyerValidation);
    List<String> resultOfInvoiceEntriesValidation = InvoiceEntryValidator.validate(invoice.getEntries());
    addResultOfValidation(result, resultOfInvoiceEntriesValidation);
    return result;
  }

  private static String validateId(Long id) {
    if (id == null) {
      return "Id cannot be null";
    }
    if (id <= 0) {
      return "Id cannot be less than or equal to 0";
    }
    return null;
  }

  private static String validateNumber(String number) {
    if (number == null) {
      return "Number cannot be null";
    }
    if (number.trim().isEmpty()) {
      return "Number cannot be empty";
    }
    Matcher matcher = RegExpPatterns.invoiceNumberPattern.matcher(number);
    if (!matcher.matches()) {
      return "Incorrect number";
    }
    return null;
  }

  private static String validateDate(LocalDate issueDate, LocalDate dueDate) {
    if (issueDate == null) {
      return "Issue date cannot be null";
    }
    if (dueDate == null) {
      return "Due date cannot be null";
    }
    if (issueDate.isAfter(dueDate)) {
      return "Issue date cannot be after due date";
    }
    return null;
  }

  private static String validateTotalNetValue(BigDecimal totalNetValue) {
    if (totalNetValue == null) {
      return "Total net value cannot be null";
    }
    if (totalNetValue.intValue() <= 0) {
      return "Total net value cannot be less than or equal to 0";
    }
    return null;
  }

  private static String validateTotalGrossValue(BigDecimal totalGrossValue) {
    if (totalGrossValue == null) {
      return "Total gross value cannot be null";
    }
    if (totalGrossValue.intValue() <= 0) {
      return "Total gross value cannot be less than or equal to 0";
    }
    return null;
  }
}
