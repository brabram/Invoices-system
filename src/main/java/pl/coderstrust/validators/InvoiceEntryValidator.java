package pl.coderstrust.validators;

import pl.coderstrust.model.InvoiceEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InvoiceEntryValidator {

  public static List<String> validate(InvoiceEntry invoiceEntry) {
    if (invoiceEntry == null) {
      return Collections.singletonList("Invoice entry cannot be null");
    }
    List<String> result = new ArrayList();
    String idValidator = validateId(String.valueOf(invoiceEntry.getId()));
    String itemValidator = validateItem(invoiceEntry.getItem());
    String quantityValidator = validateQuantity(String.valueOf(invoiceEntry.getQuantity()));
    String priceValidator = validatePrice(String.valueOf(invoiceEntry.getPrice()));
    String vatValueValidator = validateVatValue(String.valueOf(invoiceEntry.getVatValue()));
    String grossValueValidator = validateGrossValue(String.valueOf(invoiceEntry.getGrossValue()));
    String vatRateValidator = validateVatRate(String.valueOf(invoiceEntry.getVatRate()));
    addResultOfValidation(result, idValidator);
    addResultOfValidation(result, itemValidator);
    addResultOfValidation(result, quantityValidator);
    addResultOfValidation(result, priceValidator);
    addResultOfValidation(result, vatValueValidator);
    addResultOfValidation(result, grossValueValidator);
    addResultOfValidation(result, vatRateValidator);
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

  private static String validateItem(String item) {
    if (item == null) {
      return "Item cannot be null";
    }
    if (item.trim().isEmpty()) {
      return "Item cannot be empty";
    }
    if (!item.matches("^([A-Z][a-z]*)+(?:[\\s-][A-Z][a-z]*)*$")) {
      return "Incorrect item";
    }
    return "";
  }

  private static String validateQuantity(String quantity) {
    if (quantity == null) {
      return "Quantity cannot be null";
    }
    if (quantity.trim().isEmpty()) {
      return "Quantity cannot be empty";
    }
    if (!quantity.matches("[0-9]+")) {
      return "Incorrect quantity";
    }
    return "";
  }

  private static String validatePrice(String price) {
    if (price == null) {
      return "Price cannot be null";
    }
    if (price.trim().isEmpty()) {
      return "Price cannot be empty";
    }
    if (!price.matches("[0-9]+([,.][0-9]{1,2})?")) {
      return "Incorrect price";
    }
    return "";
  }

  private static String validateVatValue(String vatValue) {
    if (vatValue == null) {
      return "Vat value cannot be null";
    }
    if (vatValue.trim().isEmpty()) {
      return "Vat value cannot be empty";
    }
    if (!vatValue.matches("[0-9]+([,.][0-9]{1,2})?")) {
      return "Incorrect Vat value";
    }
    return "";
  }

  private static String validateGrossValue(String grossValue) {
    if (grossValue == null) {
      return "Gross value cannot be null";
    }
    if (grossValue.trim().isEmpty()) {
      return "Gross value cannot be empty";
    }
    if (!grossValue.matches("[0-9]+([,.][0-9]{1,2})?")) {
      return "Incorrect Gross value";
    }
    return "";
  }

  private static String validateVatRate(String vatRate) {
    if (vatRate == null) {
      return "Vat rate cannot be null";
    }
    if (vatRate.trim().isEmpty()) {
      return "Vat rate cannot be empty";
    }
    if (!vatRate.matches("[0-9]{2}%")) {
      return "Incorrect vat rate";
    }
    return "";
  }

  private static void addResultOfValidation(List<String> resultList, String resultOfValidation) {
    if (resultOfValidation != null && !resultOfValidation.trim().isEmpty()) {
      resultList.add(resultOfValidation);
    }
  }
}