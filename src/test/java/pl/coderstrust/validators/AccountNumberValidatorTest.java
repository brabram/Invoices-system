package pl.coderstrust.validators;

import junit.framework.Assert;
import org.junit.jupiter.api.Test;
import pl.coderstrust.model.AccountNumber;
import pl.coderstrust.model.Address;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountNumberValidatorTest {

  @Test
  void shouldValidateAccountNumber() {
    AccountNumber accountNumber = new AccountNumber("PL3453535322423", "3453535322423");
    List<String> validate = AccountNumberValidator.validate(accountNumber);
    Assert.assertNotNull(validate);
  }

  @Test
  void shouldValidateAddress(){
    Address address = new Address("Weewr", "13/33", "50-300", "Warsaw", "Poland");
    List<String> validate = AddressValidator.validate(address);
    Assert.assertEquals(address, validate);
  }

  @Test
  void shouldThrowExceptionForNulAccountNumber(){
    assertThrows(Exception.class, () -> AccountNumberValidator.validate(null));
  }
}