package pl.coderstrust.model.validators;

import junit.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.coderstrust.generators.AccountNumberGenerator;
import pl.coderstrust.model.AccountNumber;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

class AccountNumberValidatorTest {
  private AccountNumber accountNumber;

  @BeforeEach
  void setup() {
    accountNumber = AccountNumberGenerator.getRandomAccount();
  }

  @ParameterizedTest
  @MethodSource("ibanNumberArguments")
  void shouldValidateIbanNumber(String ibanNumber, List<String> expected) {
    accountNumber.setIbanNumber(ibanNumber);
    List<String> resultOfValidation = AccountNumberValidator.validate(accountNumber);
    Assert.assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> ibanNumberArguments() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Iban number cannot be null")),
        Arguments.of("", Collections.singletonList("Iban number cannot be empty")),
        Arguments.of("sdf", Collections.singletonList("Incorrect iban number")),
        Arguments.of("53533242", Collections.singletonList("Incorrect iban number")),
        Arguments.of("PL53533242", Collections.singletonList("Check if iban number and local number are same, iban number must contain 2 letters at the beginning"))
    );
  }

  @ParameterizedTest
  @MethodSource("localNumberArguments")
  void shouldValidateLocalNumber(String localNumber, List<String> expected) {
    accountNumber.setLocalNumber(localNumber);
    List<String> resultOfValidation = AccountNumberValidator.validate(accountNumber);
    Assert.assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> localNumberArguments() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Local number cannot be null")),
        Arguments.of("", Collections.singletonList("Local number cannot be empty")),
        Arguments.of("sdf", Collections.singletonList("Local number cannot contain letters or another special chars")),
        Arguments.of("sdf4353", Collections.singletonList("Local number cannot contain letters or another special chars")),
        Arguments.of("PL435346546", Collections.singletonList("Local number cannot contain letters or another special chars")),
        Arguments.of("435346546", Collections.singletonList("Check if iban number and local number are same, iban number must contain 2 letters at the beginning"))
    );
  }

  @ParameterizedTest
  @MethodSource("accountNumberParameters")
  void shouldCompareIbanNumberAndLocalNumber(String localNumber, String ibanNumber, List<String> expected) {
    accountNumber.setLocalNumber(localNumber);
    accountNumber.setIbanNumber(ibanNumber);
    List<String> resultOfValidation = AccountNumberValidator.validate(accountNumber);
    Assert.assertEquals(expected.toString(), resultOfValidation.toString());
  }

  private static Stream<Arguments> accountNumberParameters() {
    return Stream.of(
        Arguments.of("34342", "PL54646", Collections.singletonList("Check if iban number and local number are same, iban number must contain 2 letters at the beginning")),
        Arguments.of("34222", "PL34222", new ArrayList<String>())
    );
  }

  @Test
  void shouldThrowExceptionWhenAccountNumberIsNull() {
    List<String> resultOfValidation = AccountNumberValidator.validate(null);
    List<String> expected = Collections.singletonList("Account number cannot be null");
    Assert.assertEquals(expected, resultOfValidation);
  }
}
