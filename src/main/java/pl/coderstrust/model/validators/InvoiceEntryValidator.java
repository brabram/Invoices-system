package pl.coderstrust.model.validators;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;

import pl.coderstrust.model.InvoiceEntry;
import pl.coderstrust.model.Vat;

public class InvoiceEntryValidator extends Validator {

  public static List<String> validate(InvoiceEntry invoiceEntry) {
    if (invoiceEntry == null) {
      return Collections.singletonList("Invoice entry cannot be null");
    }
    List<String> result = new ArrayList<>();
    String resultOfItemValidation = validateItem(invoiceEntry.getItem());
    addResultOfValidation(result, resultOfItemValidation);
    String resultOfQuantityValidation = validateQuantity(invoiceEntry.getQuantity());
    addResultOfValidation(result, resultOfQuantityValidation);
    String resultOfPriceValidation = validatePrice(invoiceEntry.getPrice());
    addResultOfValidation(result, resultOfPriceValidation);
    String resultOfVatValueValidation = validateVatValue(invoiceEntry.getVatValue());
    addResultOfValidation(result, resultOfVatValueValidation);
    String resultOfGrossValueValidation = validateGrossValue(invoiceEntry.getGrossValue());
    addResultOfValidation(result, resultOfGrossValueValidation);
    String resultOfVatRateValidation = validateVatRate(invoiceEntry.getVatRate());
    addResultOfValidation(result, resultOfVatRateValidation);
    return result;
  }

  public static List<String> validate(List<InvoiceEntry> invoiceEntries) {
    if (invoiceEntries == null) {
      return Collections.singletonList("Invoice entries cannot be null");
    }
    List<String> result = new ArrayList<>();
    invoiceEntries.forEach(invoiceEntry -> addResultOfValidation(result, validate(invoiceEntry)));
    return result;
  }


  private static String validateItem(String item) {
    if (item == null) {
      return "Item cannot be null";
    }
    if (item.trim().isEmpty()) {
      return "Item cannot be empty";
    }
    Matcher matcher = RegExpPatterns.itemNamePattern.matcher(item);
    if (!matcher.matches()) {
      return "Incorrect item";
    }
    return null;
  }

  private static String validateQuantity(Long quantity) {
    if (quantity == null) {
      return "Quantity cannot be null";
    }
    if (quantity <= 0) {
      return "Quantity cannot be less than or equal to 0";
    }
    return null;
  }

  private static String validatePrice(BigDecimal price) {
    if (price == null) {
      return "Price cannot be null";
    }
    if (price.intValue() <= 0) {
      return "Price cannot be less than or equal to 0";
    }
    return null;
  }

  private static String validateVatValue(BigDecimal vatValue) {
    if (vatValue == null) {
      return "Vat value cannot be null";
    }
    if (vatValue.intValue() <= 0) {
      return "Vat value cannot be less than or equal to 0";
    }
    return null;
  }

  private static String validateGrossValue(BigDecimal grossValue) {
    if (grossValue == null) {
      return "Gross value cannot be null";
    }
    if (grossValue.intValue() <= 0) {
      return "Gross value cannot be less than or equal to 0";
    }
    return null;
  }

  private static String validateVatRate(Vat vatRate) {
    if (vatRate == null) {
      return "Vat rate cannot be null";
    }
    return null;
  }
}
