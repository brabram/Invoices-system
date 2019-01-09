package pl.coderstrust.validators;

import pl.coderstrust.model.Company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CompanyValidator {

  public static List<String> validate(Company company) {
    if (company == null) {
      return Collections.singletonList("Company cannot be null");
    }
    List<String> result = new ArrayList();
    String idValidator = validateId(String.valueOf(company.getId()));
    String nameValidator = validateName(company.getName());
    String taxIdentificationNumberValidator = validateTaxIdentificationNumber(company.getTaxIdentificationNumber());
    String accountNumberValidator = String.valueOf(AccountNumberValidator.validate(company.getAccountNumber()));
    String contactDetailsOperator = String.valueOf(ContactDetailsValidator.validate(company.getContactDetails()));
    addResultOfValidation(result, idValidator);
    addResultOfValidation(result, nameValidator);
    addResultOfValidation(result, taxIdentificationNumberValidator);
    addResultOfValidation(result, accountNumberValidator);
    addResultOfValidation(result, contactDetailsOperator);
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

  private static String validateName(String name) {
    if (name == null) {
      return "Name cannot be null";
    }
    if (name.trim().isEmpty()) {
      return "Name cannot be empty";
    }
    if (!name.matches("^([A-Z][a-z]*)+(?:[\\s-][A-Z][a-z]*)*$")) {
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
    if (!taxIdentificationNumber.matches("[9-9][0-9]{2}\\-[0-9]{2}\\-[0-9]{4}$")) {
      return "Incorrect tax identification number";
    }
    return "";
  }

  private static void addResultOfValidation(List<String> resultList, String resultOfValidation) {
    if (resultOfValidation != null && !resultOfValidation.trim().isEmpty()) {
      resultList.add(resultOfValidation);
    }
  }
}
