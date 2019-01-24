package pl.coderstrust.model.validators;

import java.util.regex.Pattern;

public class RegrexPatterns {
  protected static Pattern numberPattern = Pattern.compile("[0-9]+");
  protected static Pattern ibanNumberPattern = Pattern.compile("[A-Z]{2}[0-9]+");
  protected static Pattern geographicalNamePattern = Pattern.compile("^([A-Z][a-z]*)+(?:[\\s-][A-Z][a-z]*)*$");
  protected static Pattern postalCodePattern = Pattern.compile("[0-9]{5}");
  protected static Pattern addressNumberPattern = Pattern.compile("[0-9]{1,4}?[A-Za-z]?/[0-9]{1,4}?[A-Za-z]?");
  protected static Pattern companyNamePattern = Pattern.compile("^([A-Za-z0-9][a-z0-9]*)+(?:[\\s-][A-Za-z0-9][a-z0-9]*)*$");
  protected static Pattern taxIdentificationNumberPattern = Pattern.compile("\\d{10}");
  protected static Pattern emailPattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
  protected static Pattern phoneNumberPattern = Pattern.compile("[+0-9]+");
  protected static Pattern webSitePattern = Pattern.compile("(www.)([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?");
  protected static Pattern itemNamePattern = Pattern.compile("^([A-Za-z][a-z]*)+(?:[\\s-][A-Z][a-z]*)*$");
}
