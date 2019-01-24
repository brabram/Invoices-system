package pl.coderstrust.model.validators;

import pl.coderstrust.model.ContactDetails;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;

public class ContactDetailsValidator extends Validator {

  public static List<String> validate(ContactDetails contactDetails) {
    if (contactDetails == null) {
      return Collections.singletonList("Contact details cannot be null");
    }
    List<String> result = new ArrayList<>();
    String resultOfEmailAddressValidation = validateEmail(contactDetails.getEmail());
    String resultOfPhoneNumberValidation = validatePhoneNumber(contactDetails.getPhoneNumber());
    String resultOfWebSiteValidation = validateWebSite(contactDetails.getWebsite());
    List<String> resultOfAddressValidation = AddressValidator.validate(contactDetails.getAddress());
    addResultOfValidation(result, resultOfEmailAddressValidation);
    addResultOfValidation(result, resultOfPhoneNumberValidation);
    addResultOfValidation(result, resultOfWebSiteValidation);
    addResultOfValidation(result, resultOfAddressValidation);
    return result;
  }

  private static String validateEmail(String email) {
    if (email == null) {
      return "Email cannot be null";
    }
    if (email.trim().isEmpty()) {
      return "Email cannot be empty";
    }
    Matcher matcher = RegrexValidators.emailPattern.matcher(email);
    if (!matcher.matches()) {
      return "Incorrect email address";
    }
    return null;
  }

  private static String validatePhoneNumber(String phoneNumber) {
    if (phoneNumber == null) {
      return "Phone number cannot be null";
    }
    if (phoneNumber.trim().isEmpty()) {
      return "Phone number cannot be empty";
    }
    Matcher matcher = RegrexValidators.phoneNumberPattern.matcher(phoneNumber);
    if (!matcher.matches()) {
      return "Incorrect phone number";
    }
    return null;
  }

  private static String validateWebSite(String webSite) {
    if (webSite == null) {
      return "Web site cannot be null";
    }
    if (webSite.trim().isEmpty()) {
      return "Web site cannot be empty";
    }
    Matcher matcher = RegrexValidators.webSitePattern.matcher(webSite);
    if (!matcher.matches()) {
      return "Incorrect web site type";
    }
    return null;
  }
}
