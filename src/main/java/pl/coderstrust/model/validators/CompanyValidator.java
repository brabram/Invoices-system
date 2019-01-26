package pl.coderstrust.model.validators;

import pl.coderstrust.model.Company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;

public class CompanyValidator extends Validator {

  public static List<String> validate(Company company) {
    if (company == null) {
      return Collections.singletonList("Company cannot be null");
    }
    List<String> result = new ArrayList<>();
    String resultOfNameValidation = validateName(company.getName());
    String resultOfTaxIdentificationNumberValidation = validateTaxIdentificationNumber(company.getTaxIdentificationNumber());
    List<String> resultOfAccountNumberValidation = AccountNumberValidator.validate(company.getAccountNumber());
    List<String> resultOfContactDetailsValidation = ContactDetailsValidator.validate(company.getContactDetails());
    addResultOfValidation(result, resultOfNameValidation);
    addResultOfValidation(result, resultOfTaxIdentificationNumberValidation);
    addResultOfValidation(result, resultOfAccountNumberValidation);
    addResultOfValidation(result, resultOfContactDetailsValidation);
    return result;
  }

  private static String validateName(String name) {
    if (name == null) {
      return "Name cannot be null";
    }
    if (name.trim().isEmpty()) {
      return "Name cannot be empty";
    }
    Matcher matcher = RegExpPatterns.companyNamePattern.matcher(name);
    if (!matcher.matches()) {
      return "Incorrect name";
    }
    return null;
  }

  private static String validateTaxIdentificationNumber(String taxIdentificationNumber) {
    if (taxIdentificationNumber == null) {
      return "Tax identification number cannot be null";
    }
    if (taxIdentificationNumber.trim().isEmpty()) {
      return "Tax identification number cannot be empty";
    }
    Matcher matcher = RegExpPatterns.taxIdentificationNumberPattern.matcher(taxIdentificationNumber);
    if (!matcher.matches()) {
      return "Incorrect tax identification number";
    }
    return null;
  }
}