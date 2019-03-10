package pl.coderstrust.model.validators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.coderstrust.generators.AccountNumberGenerator;
import pl.coderstrust.generators.ContactDetailsGenerator;
import pl.coderstrust.model.Company;

class CompanyValidatorTest {

  @ParameterizedTest
  @MethodSource("companyNameArguments")
  void shouldValidateCompanyName(String name, List<String> expected) {
    Company company = Company.builder()
        .withId(1L)
        .withName(name)
        .withTaxIdentificationNumber("2541278654")
        .withAccountNumber(AccountNumberGenerator.getRandomAccount())
        .withContactDetails(ContactDetailsGenerator.getRandomContactDetails())
        .build();
    List<String> resultOfValidation = CompanyValidator.validate(company);
    assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> companyNameArguments() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Name cannot be null")),
        Arguments.of("", Collections.singletonList("Name cannot be empty")),
        Arguments.of("fewf%^$", Collections.singletonList("Incorrect name")),
        Arguments.of("3M", new ArrayList<String>()),
        Arguments.of("Afer - fer  & bny, uyt", new ArrayList<String>())
    );
  }

  @ParameterizedTest
  @MethodSource("taxIdentificationNumberArguments")
  void shouldValidateTaxIdentificationNumber(String taxIdentificationNumber, List<String> expected) {
    Company company = Company.builder()
        .withId(1L)
        .withName("name")
        .withTaxIdentificationNumber(taxIdentificationNumber)
        .withAccountNumber(AccountNumberGenerator.getRandomAccount())
        .withContactDetails(ContactDetailsGenerator.getRandomContactDetails())
        .build();
    List<String> resultOfValidation = CompanyValidator.validate(company);
    assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> taxIdentificationNumberArguments() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Tax identification number cannot be null")),
        Arguments.of("", Collections.singletonList("Tax identification number cannot be empty")),
        Arguments.of("fewf%^$", Collections.singletonList("Incorrect tax identification number")),
        Arguments.of("856-42-8943", Collections.singletonList("Incorrect tax identification number")),
        Arguments.of("956428943", Collections.singletonList("Incorrect tax identification number")),
        Arguments.of("fwfw", Collections.singletonList("Incorrect tax identification number")),
        Arguments.of("1111111111", new ArrayList<String>())
    );
  }

  @Test
  void shouldThrowExceptionWhenCompanyIsNull() {
    List<String> resultOfValidation = CompanyValidator.validate(null);
    List<String> expected = Collections.singletonList("Company cannot be null");
    assertEquals(expected, resultOfValidation);
  }

  @Test
  void shouldValidateAccountNumber() {
    Company company = Company.builder()
        .withId(1L)
        .withName("name")
        .withTaxIdentificationNumber("2541278654")
        .withAccountNumber(null)
        .withContactDetails(ContactDetailsGenerator.getRandomContactDetails())
        .build();
    List<String> expected = Collections.singletonList("Account number cannot be null");
    List<String> resultOfValidation = CompanyValidator.validate(company);
    assertEquals(expected, resultOfValidation);
  }

  @Test
  void shouldValidateContactDetails() {
    Company company = Company.builder()
        .withId(1L)
        .withName("name")
        .withTaxIdentificationNumber("2541278654")
        .withAccountNumber(AccountNumberGenerator.getRandomAccount())
        .withContactDetails(null)
        .build();
    List<String> expected = Collections.singletonList("Contact details cannot be null");
    List<String> resultOfValidation = CompanyValidator.validate(company);
    assertEquals(expected, resultOfValidation);
  }
}
