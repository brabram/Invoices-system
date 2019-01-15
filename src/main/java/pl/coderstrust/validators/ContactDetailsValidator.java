package pl.coderstrust.validators;

import pl.coderstrust.model.ContactDetails;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static pl.coderstrust.validators.ResultOfValidation.addResultOfValidation;

public class ContactDetailsValidator {

  public static List<String> validate(ContactDetails contactDetails) {
    if (contactDetails == null) {
      return Collections.singletonList("Contact details cannot be null");
    }
    List<String> result = new ArrayList<>();
    String emailAddressValidator = validateEmail(contactDetails.getEmail());
    String phoneNumberValidator = validatePhoneNumber(contactDetails.getPhoneNumber());
    String webSiteValidator = validateWebSite(contactDetails.getWebsite());
    AddressValidator.validate(contactDetails.getAddress());
    addResultOfValidation(result, emailAddressValidator);
    addResultOfValidation(result, phoneNumberValidator);
    addResultOfValidation(result, webSiteValidator);
    return result;
  }

  private static String validateEmail(String email) {
    if (email == null) {
      return "Email cannot be null";
    }
    if (email.trim().isEmpty()) {
      return "Email cannot be empty";
    }
    if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
      return "Incorrect email address";
    }
    return "";
  }

  private static String validatePhoneNumber(String phoneNumber) {
    if (phoneNumber == null) {
      return "Phone number cannot be null";
    }
    if (phoneNumber.trim().isEmpty()) {
      return "Phone number cannot be empty";
    }
    if (!phoneNumber.matches("[+0-9]+")) {
      return "Incorrect phone number";
    }
    return "";
  }

  private static String validateWebSite(String webSite) {
    if (webSite == null) {
      return "Web site cannot be null";
    }
    if (webSite.trim().isEmpty()) {
      return "Web site cannot be empty";
    }
    if (!webSite.matches("(www.)([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?")) {
      return "Incorrect web site type";
    }
    return "";
  }
}
