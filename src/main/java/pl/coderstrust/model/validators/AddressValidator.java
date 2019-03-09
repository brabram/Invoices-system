package pl.coderstrust.model.validators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;

import pl.coderstrust.model.Address;

public class AddressValidator extends Validator {

  public static List<String> validate(Address address) {
    if (address == null) {
      return Collections.singletonList("Address cannot be null");
    }
    List<String> result = new ArrayList<>();
    String resultOfStreetNameValidation = validateStreet(address.getStreet());
    addResultOfValidation(result, resultOfStreetNameValidation);
    String resultOfAddressNumberValidation = validateNumber(address.getNumber());
    addResultOfValidation(result, resultOfAddressNumberValidation);
    String resultOPostalCodeValidation = validatePostalCode(address.getPostalCode());
    addResultOfValidation(result, resultOPostalCodeValidation);
    String resultOfCityNameValidation = validateCityName(address.getCity());
    addResultOfValidation(result, resultOfCityNameValidation);
    String resultOfCountryNameValidation = validateCountryName(address.getCountry());
    addResultOfValidation(result, resultOfCountryNameValidation);
    return result;
  }

  private static String validateStreet(String street) {
    if (street == null) {
      return "Street cannot be null";
    }
    if (street.trim().isEmpty()) {
      return "Street cannot be empty";
    }
    Matcher matcher = RegExpPatterns.streetNamePattern.matcher(street);
    if (!matcher.matches()) {
      return "Incorrect withStreet withName";
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
    Matcher matcher = RegExpPatterns.addressNumberPattern.matcher(number);
    if (!matcher.matches()) {
      return "Incorrect withAddress withNumber";
    }
    return null;
  }

  private static String validatePostalCode(String postalCode) {
    if (postalCode == null) {
      return "Postal code cannot be null";
    }
    if (postalCode.trim().isEmpty()) {
      return "Postal code cannot be empty";
    }
    Matcher matcher = RegExpPatterns.postalCodePattern.matcher(postalCode);
    if (!matcher.matches()) {
      return "Incorrect postal code";
    }
    return null;
  }

  private static String validateCityName(String city) {
    if (city == null) {
      return "City cannot be null";
    }
    if (city.trim().isEmpty()) {
      return "City cannot be empty";
    }
    Matcher matcher = RegExpPatterns.geographicalNamePattern.matcher(city);
    if (!matcher.matches()) {
      return "Incorrect withCity withName";
    }
    return null;
  }

  private static String validateCountryName(String country) {
    if (country == null) {
      return "Country cannot be null";
    }
    if (country.trim().isEmpty()) {
      return "Country cannot be empty";
    }
    Matcher matcher = RegExpPatterns.geographicalNamePattern.matcher(country);
    if (!matcher.matches()) {
      return "Incorrect withCountry withName";
    }
    return null;
  }
}
