package pl.coderstrust.validators;

import pl.coderstrust.model.Company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static pl.coderstrust.validators.ResultOfValidation.addResultOfValidation;

public class CompanyValidator {

  public static List<String> validate(Company company) {
    if (company == null) {
      return Collections.singletonList("Company cannot be null");
    }
    List<String> result = new ArrayList<>();
    String idValidator = validateId(company.getId());
    String nameValidator = validateName(company.getName());
    String taxIdentificationNumberValidator = validateTaxIdentificationNumber(company.getTaxIdentificationNumber());
    AccountNumberValidator.validate(company.getAccountNumber());
    ContactDetailsValidator.validate(company.getContactDetails());
    addResultOfValidation(result, idValidator);
    addResultOfValidation(result, nameValidator);
    addResultOfValidation(result, taxIdentificationNumberValidator);
    return result;
  }

  private static String validateId(long id) {
    if (id < 0) {
      return "Id cannot be less than zero";
    }
    return "";
  }

  private static String validateName(String name) {
    if (name == null) {
      return "Name cannot be null";
    }
    if (name.trim().isEmpty()) {
      return "Name cannot be empty";
    }
    if (!name.matches("^([A-Za-z0-9][a-z0-9]*)+(?:[\\s-][A-Za-z0-9][a-z0-9]*)*$")) {
      return "Incorrect name";
    }
    return "";
  }

  private static String validateTaxIdentificationNumber(String taxIdentificationNumber) {
    if (taxIdentificationNumber == null) {
      return "Tax identification number cannot be null";
    }
    if (taxIdentificationNumber.trim().isEmpty()) {
      return "Tax identification number cannot be empty";
    }
    if (!taxIdentificationNumber.matches("\\d{10}")) {
      return "Incorrect tax identification number";
    }
    return "";
  }
}
