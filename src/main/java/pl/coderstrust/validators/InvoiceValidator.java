package pl.coderstrust.validators;

import pl.coderstrust.model.Invoice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InvoiceValidator {

  public static void main(String[] args) {
    String regrex = "^(3[01]|[12][0-9]|0[1-9]).(1[0-2]|0[1-9]).[0-9]{4}$";
    String data = "31.12.2011";
    System.out.println(data.matches(regrex));
  }
  public static List<String> validate(Invoice invoice) {
    if (invoice == null) {
      return Collections.singletonList("Invoice cannot be null");
    }
    List<String> result = new ArrayList();
    String idValidator = validateId(String.valueOf(invoice.getId()));
    String numberValidator = validateNumber(String.valueOf(invoice.getNumber()));
    String issueDateValidator = validateDate(String.valueOf(invoice.getIssueDate()));
    String dueDateValidator = validateDate(String.valueOf(invoice.getDueDate()));
    List invoceEntriesValidator = InvoiceEntryValidator.validate(invoice.getEntries()))
    addResultOfValidation(result, idValidator);
    addResultOfValidation(result, numberValidator);
    addResultOfValidation(result, issueDateValidator);
    addResultOfValidation(result, dueDateValidator);
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
    if (!number.matches("[0-9]+")) {
      return "Incorrect number";
    }
    return "";
  }

  private static String validateDate(String date) {
    if (date == null) {
      return "Number cannot be null";
    }
    if (date.trim().isEmpty()) {
      return "Number cannot be empty";
    }
    if (!date.matches("^(3[01]|[12][0-9]|0[1-9]).(1[0-2]|0[1-9]).[0-9]{4}$")) {
      return "Incorrect number";
    }
    return "";
  }

  private static void addResultOfValidation(List<String> resultList, String resultOfValidation) {
    if (resultOfValidation != null && !resultOfValidation.trim().isEmpty()) {
      resultList.add(resultOfValidation);
    }
  }
}
