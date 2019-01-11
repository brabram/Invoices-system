package pl.coderstrust.validators;

import junit.framework.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.coderstrust.generators.AccountNumberGenerator;
import pl.coderstrust.model.AccountNumber;
import pl.coderstrust.model.Address;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountNumberValidatorTest {

  @ParameterizedTest
  @MethodSource("parameters")
  void shouldValidateAccountNumber(String ibanNumber, List<String> expected) {
    AccountNumber accountNumber = AccountNumberGenerator.getRandomAccount();
    accountNumber.setIbanNumber(ibanNumber);

    List<String> resultOfValidation = AccountNumberValidator.validate(accountNumber);

    Assert.assertEquals(resultOfValidation, expected);
  }

  private static Stream<Arguments> parameters(){
    return Stream.of(
        Arguments.of(null, Collections.singletonList("asd")),
        Arguments.of("", Collections.singletonList("asd")),
        Arguments.of("sdf", Collections.singletonList("asd"))
        );
  }



  @Test
  void shouldValidateAddress() {
    Address address = new Address("Weewr", "13/33", "50-300", "Warsaw", "Poland");
    List<String> validate = AddressValidator.validate(address);
    Assert.assertEquals(address, validate);
  }

//  @Test
//  void shouldThrowExceptionForNulAccountNumber() {
//    assertThrows(Exception.class, () -> AccountNumberValidator.validate(null));
//  }
}