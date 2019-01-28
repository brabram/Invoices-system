package pl.coderstrust.model.validators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;

import pl.coderstrust.model.AccountNumber;

public class AccountNumberValidator extends Validator {

  public static List<String> validate(AccountNumber accountNumber) {
    if (accountNumber == null) {
      return Collections.singletonList("Account number cannot be null");
    }
    List<String> result = new ArrayList<>();
    String resultOfIbanNumberValidation = validateIbanNumber(accountNumber.getIbanNumber());
    addResultOfValidation(result, resultOfIbanNumberValidation);
    String resultOfLocalNumberValidation = validateLocalNumber(accountNumber.getLocalNumber());
    addResultOfValidation(result, resultOfLocalNumberValidation);
    if ((resultOfIbanNumberValidation == null) && (resultOfLocalNumberValidation == null)) {
      String resultOfComparison = compareNumbers(accountNumber.getLocalNumber(), accountNumber.getIbanNumber());
      addResultOfValidation(result, resultOfComparison);
    }
    return result;
  }

  private static String validateLocalNumber(String localNumber) {
    if (localNumber == null) {
      return "Local number cannot be null";
    }
    if (localNumber.trim().isEmpty()) {
      return "Local number cannot be empty";
    }
    Matcher matcher = RegExpPatterns.numberPattern.matcher(localNumber);
    if (!matcher.matches()) {
      return "Local number cannot contain letters or another special chars";
    }
    return null;
  }

  private static String validateIbanNumber(String ibanNumber) {
    if (ibanNumber == null) {
      return "Iban number cannot be null";
    }
    if (ibanNumber.trim().isEmpty()) {
      return "Iban number cannot be empty";
    }
    Matcher matcher = RegExpPatterns.ibanNumberPattern.matcher(ibanNumber);
    if (!matcher.matches()) {
      return "Incorrect iban number, iban number must contain two letters at the beginning and then numbers";
    }
    return null;
  }

  private static String compareNumbers(String localNumber, String ibanNumber) {
    if (!localNumber.equals(ibanNumber.substring(2))) {
      return "Check if iban number and local number are same, iban number must contain 2 letters at the beginning";
    }
    return null;
  }
}
