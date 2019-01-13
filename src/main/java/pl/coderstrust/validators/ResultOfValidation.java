package pl.coderstrust.validators;

import java.util.List;

public class ResultOfValidation {

  public static void addResultOfValidation(List<String> resultList, String resultOfValidation) {
    if (resultOfValidation != null && !resultOfValidation.trim().isEmpty()) {
      resultList.add(resultOfValidation);
    }
  }
}
