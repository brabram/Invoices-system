package pl.coderstrust.validators;

import junit.framework.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.coderstrust.generators.AccountNumberGenerator;
import pl.coderstrust.model.AccountNumber;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

class AccountNumberValidatorTest {
  private AccountNumber accountNumber = AccountNumberGenerator.getRandomAccount();

  @ParameterizedTest
  @MethodSource("ibanNumberParameters")
  void shouldNotValidateAccountIbanNumber(String ibanNumber, List<String> expected) {
    accountNumber.setIbanNumber(ibanNumber);
    List<String> resultOfValidation = AccountNumberValidator.validate(accountNumber);
    Assert.assertEquals(resultOfValidation, expected);
  }

  private static Stream<Arguments> ibanNumberParameters() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Iban number cannot be null")),
        Arguments.of("", Collections.singletonList("Iban number cannot be empty")),
        Arguments.of("sdf", Collections.singletonList("Incorrect iban number")),
        Arguments.of("53533242", Collections.singletonList("Incorrect iban number"))
    );
  }

  @ParameterizedTest
  @MethodSource("localNumberParameters")
  void shouldNotValidateAccountLocalNumber(String localNumber, List<String> expected) {
    accountNumber.setLocalNumber(localNumber);
    List<String> resultOfValidation = AccountNumberValidator.validate(accountNumber);
    Assert.assertEquals(resultOfValidation, expected);
  }

  private static Stream<Arguments> localNumberParameters() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Local number cannot be null")),
        Arguments.of("", Collections.singletonList("Local number cannot be empty")),
        Arguments.of("sdf", Collections.singletonList("Local number cannot contain letters or another special chars")),
        Arguments.of("sdf4353", Collections.singletonList("Local number cannot contain letters or another special chars")),
        Arguments.of("PL435346546", Collections.singletonList("Local number cannot contain letters or another special chars"))
    );
  }

  @Test
  void shouldApproveAccountNumberValidation() {
    List<String> actual = AccountNumberValidator.validate(accountNumber);
    List<String> expected = Collections.singletonList("");
    Assert.assertEquals(expected.toString(), actual.toString());
  }

  @Test
  void shouldCompareIbanNumberAndLocalNumber() {
    AccountNumber accountNumber = new AccountNumber("PL53434", "5434353");
    List<String> actual = AccountNumberValidator.validate(accountNumber);
    List<String> expected = Collections.singletonList("Check if iban number and local number are same, iban number must contain 2 letters at the beginning");
    Assert.assertEquals(expected.toString(), actual.toString());
  }
}
