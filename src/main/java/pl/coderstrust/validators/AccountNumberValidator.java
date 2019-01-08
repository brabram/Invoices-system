package pl.coderstrust.validators;

import pl.coderstrust.model.AccountNumber;

import java.util.ArrayList;
import java.util.List;

public class AccountNumberValidator {
  public static final int LOCAL_ACCOUNT_NUMBER_SIZE = 24;

  public static List<String> validate(AccountNumber accountNumber) {
    List<String> result = new ArrayList();
    if (accountNumber.getLocalNumber().length() > LOCAL_ACCOUNT_NUMBER_SIZE || accountNumber.getLocalNumber().length() < LOCAL_ACCOUNT_NUMBER_SIZE) {
      throw new IllegalArgumentException("Incorrect local number size");
    }
   String localNumber = accountNumber.getLocalNumber();
   localNumber.format("%d", "[0-9]{24}");
   String ibanNumber = String.format("PL%d" , localNumber);
   result.add(localNumber);
   result.add(ibanNumber);
   return result;
  }
}
