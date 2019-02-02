package pl.coderstrust.model.validators;

import java.util.regex.Pattern;

class RegExpPatterns {
  static Pattern numberPattern = Pattern.compile("[0-9]+");
  static Pattern ibanNumberPattern = Pattern.compile("[A-Z]{2}[0-9]+");
  static Pattern geographicalNamePattern = Pattern.compile("^([A-Z][a-z]*)+(?:[\\s-][A-Z][a-z]*)*$");
  static Pattern postalCodePattern = Pattern.compile("[0-9]{5}");
  static Pattern addressNumberPattern = Pattern.compile("[0-9]+([A-Za-z]?[/]?[0-9]{1,4}?[A-Za-z]?)?$");
  static Pattern companyNamePattern = Pattern.compile("^([A-Za-z0-9][a-z0-9]*)+(?:[\\s-][A-Za-z0-9][a-z0-9]*)*$");
  static Pattern taxIdentificationNumberPattern = Pattern.compile("\\d{10}");
  static Pattern emailPattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
  static Pattern phoneNumberPattern = Pattern.compile("[+]?[0-9]+");
  static Pattern webSitePattern = Pattern.compile("(www.)([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?");
  static Pattern itemNamePattern = Pattern.compile("^([A-Za-z][a-z]*)+(?:[\\s-][A-Za-z][a-z]*)*$");
  static Pattern invoiceNumberPattern = Pattern.compile("^([A-Za-z0-9][a-z0-9]*)+(?:[\\s-][A-Za-z0-9][a-z0-9]*)*$");
}
