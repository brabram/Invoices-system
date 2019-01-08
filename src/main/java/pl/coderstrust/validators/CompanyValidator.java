package pl.coderstrust.validators;

public class CompanyValidator {

  public static void main(String[] args) {
    String regrex = "(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?";
    String data = "www.fewfw.few";
    System.out.println(data.matches(regrex));
  }

}
