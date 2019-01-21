package pl.coderstrust.validators;

import pl.coderstrust.model.Address;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static pl.coderstrust.validators.ResultOfValidation.addResultOfValidation;

public class AddressValidator {
g
  public static List<String> validate(Address address) {
    if (address == null) {
      return Collections.singletonList("Address cannot be null");
    }
    List<String> result = new ArrayList<>();
    String streetNameValidator = validateStreet(address.getStreet());
    String addressNumberValidator = validateNumber(address.getNumber());
    String postalCodeValidator = validatePostalCode(address.getPostalCode());
    String cityNameValidator = validateCityName(address.getCity());
    String countryNameValidator = validateCountryName(address.getCountry());
    addResultOfValidation(result, streetNameValidator);
    addResultOfValidation(result, addressNumberValidator);
    addResultOfValidation(result, postalCodeValidator);
    addResultOfValidation(result, cityNameValidator);
    addResultOfValidation(result, countryNameValidator);
    return result;
  }

  private static String validateStreet(String street) {
    if (street == null) {
      return "Street cannot be null";
    }
    if (street.trim().isEmpty()) {
      return "Street cannot be empty";
    }
    if (!street.matches("^([A-Z][a-z]*)+(?:[\\s-][A-Z][a-z]*)*$")) {
      return "Incorrect street name";
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
    if (!number.matches("[0-9]{1,4}?[A-Za-z]?/[0-9]{1,4}?[A-Za-z]?")) {
      return "Incorrect address number";
    }
    return "";
  }

  private static String validatePostalCode(String postalCode) {
    if (postalCode == null) {
      return "Postal code cannot be null";
    }
    if (postalCode.trim().isEmpty()) {
      return "Postal code cannot be empty";
    }
    if (!postalCode.matches("[0-9]{2}-[0-9]{3}")) {
      return "Incorrect postal code";
    }
    return "";
  }

  private static String validateCityName(String city) {
    if (city == null) {
      return "City cannot be null";
    }
    if (city.trim().isEmpty()) {
      return "City cannot be empty";
    }
    if (!city.matches("^([A-Z][a-z]*)+(?:[\\s-][A-Z][a-z]*)*$")) {
      return "Incorrect city name";
    }
    return "";
  }

  private static String validateCountryName(String country) {
    if (country == null) {
      return "Country cannot be null";
    }
    if (country.trim().isEmpty()) {
      return "Country cannot be empty";
    }
    if (!country.matches("^([A-Z][a-z]*)+(?:[\\s-][A-Z][a-z]*)*$")) {
      return "Incorrect country name";
    }
    return "";
  }
}
