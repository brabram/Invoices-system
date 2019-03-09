package pl.coderstrust.model.validators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;

import pl.coderstrust.model.ContactDetails;

public class ContactDetailsValidator extends Validator {

  public static List<String> validate(ContactDetails contactDetails) {
    if (contactDetails == null) {
      return Collections.singletonList("Contact details cannot be null");
    }
    List<String> result = new ArrayList<>();
    String resultOfEmailAddressValidation = validateEmail(contactDetails.getEmail());
    addResultOfValidation(result, resultOfEmailAddressValidation);
    String resultOfPhoneNumberValidation = validatePhoneNumber(contactDetails.getPhoneNumber());
    addResultOfValidation(result, resultOfPhoneNumberValidation);
    String resultOfWebSiteValidation = validateWebSite(contactDetails.getWebsite());
    addResultOfValidation(result, resultOfWebSiteValidation);
    List<String> resultOfAddressValidation = AddressValidator.validate(contactDetails.getAddress());
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
    Matcher matcher = RegExpPatterns.emailPattern.matcher(email);
    if (!matcher.matches()) {
      return "Incorrect withEmail withAddress";
    }
    return null;
  }

  private static String validatePhoneNumber(String phoneNumber) {
    if (phoneNumber == null) {
      return "Phone withNumber cannot be null";
    }
    if (phoneNumber.trim().isEmpty()) {
      return "Phone withNumber cannot be empty";
    }
    Matcher matcher = RegExpPatterns.phoneNumberPattern.matcher(phoneNumber);
    if (!matcher.matches()) {
      return "Incorrect phone withNumber";
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
    Matcher matcher = RegExpPatterns.webSitePattern.matcher(webSite);
    if (!matcher.matches()) {
      return "Incorrect web site type";
    }
    return null;
  }
}
