package pl.coderstrust.validators;

import pl.coderstrust.model.AccountNumber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AccountNumberValidator {

  public static List<String> validate(AccountNumber accountNumber) {
    if (accountNumber == null) {
      return Collections.singletonList("Account number cannot be null");
    }
    List<String> result = new ArrayList();
    String ibanNumberValidator = validateIbanNumber(accountNumber.getIbanNumber());
    String localNumberValidator = validateLocalNumber(accountNumber.getLocalNumber());
    String compare = compareNumbers(accountNumber.getLocalNumber(), accountNumber.getIbanNumber());
    addResultOfValidation(result, ibanNumberValidator);
    addResultOfValidation(result, localNumberValidator);
    addResultOfValidation(result, compare);
    return result;
  }

  private static String validateLocalNumber(String localNumber) {
    if (localNumber == null) {
      return "Local number cannot be null.";
    }
    if (localNumber.trim().isEmpty()) {
      return "Local number cannot be empty.";
    }
    if (!localNumber.matches("[0-9]+")) {
      return "Local number cannot contain letters or another special chars.";
    }
    return "";
  }

  private static String validateIbanNumber(String ibanNumber) {
    if (ibanNumber == null) {
      return "Iban number cannot be null";
    }
    if (ibanNumber.trim().isEmpty()) {
      return "Iban number cannot be empty";
    }
    if (!ibanNumber.matches("[A-Z]{2}[0-9]+")) {
      return "Incorrect iban number";
    }
    return "";
  }

  private static String compareNumbers(String localNumber, String ibanNumber) {
    if (localNumber != ibanNumber.substring(3)) {
      return "Check if iban number and local number are same, iban number must contain 2 letters at the beginning";
    }
    return "";
  }

  private static void addResultOfValidation(List<String> resultList, String resultOfValidation) {
    if (resultOfValidation != null && !resultOfValidation.trim().isEmpty()) {
      resultList.add(resultOfValidation);
    }
  }
}
