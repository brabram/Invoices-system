package pl.coderstrust.validators;

import junit.framework.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.coderstrust.generators.AccountNumberGenerator;
import pl.coderstrust.generators.CompanyGenerator;
import pl.coderstrust.generators.ContactDetailsGenerator;
import pl.coderstrust.model.AccountNumber;
import pl.coderstrust.model.Company;
import pl.coderstrust.model.ContactDetails;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

class CompanyValidatorTest {

 private Company company = CompanyGenerator.getRandomCompany();

  @ParameterizedTest
  @MethodSource("companyIdParameters")
  void shouldNotValidateCompanyId(String id, List<String> expected) {
    company.setId(id);
    List<String> resultOfValidation = CompanyValidator.validate(company);
    Assert.assertEquals(resultOfValidation, expected);
  }

  private static Stream<Arguments> companyIdParameters() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Id cannot be null")),
        Arguments.of("", Collections.singletonList("Id cannot be empty")),
        Arguments.of("sdf35", Collections.singletonList("Incorrect id")),
        Arguments.of("35fewf", Collections.singletonList("Incorrect id")),
        Arguments.of("-535", Collections.singletonList("Incorrect id"))
    );
  }

  @ParameterizedTest
  @MethodSource("companyNameParameters")
  void shouldNotValidateCompanyName(String name, List<String> expected) {
    company.setName(name);
    List<String> resultOfValidation = CompanyValidator.validate(company);
    Assert.assertEquals(resultOfValidation, expected);
  }

  private static Stream<Arguments> companyNameParameters() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Name cannot be null")),
        Arguments.of("", Collections.singletonList("Name cannot be empty")),
        Arguments.of("fewf%^$", Collections.singletonList("Incorrect name"))
    );
  }

  @ParameterizedTest
  @MethodSource("taxIdentificationNumberParameters")
  void shouldNotValidateTaxIdentificationNumber(String taxIdentificationNumber, List<String> expected) {
    company.setTaxIdentificationNumber(taxIdentificationNumber);
    List<String> resultOfValidation = CompanyValidator.validate(company);
    Assert.assertEquals(resultOfValidation, expected);
  }

  private static Stream<Arguments> taxIdentificationNumberParameters() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Tax identification number cannot be null")),
        Arguments.of("", Collections.singletonList("Tax identification number cannot be empty")),
        Arguments.of("fewf%^$", Collections.singletonList("Incorrect tax identification number")),
        Arguments.of("856-42-8943", Collections.singletonList("Incorrect tax identification number")),
        Arguments.of("956428943", Collections.singletonList("Incorrect tax identification number")),
        Arguments.of("fwfw", Collections.singletonList("Incorrect tax identification number"))
    );
  }

  @Test
  void shouldApproveContactDetails() {
    company.setContactDetails(ContactDetailsGenerator.getRandomContactDetails());
    company.setAccountNumber(AccountNumberGenerator.getRandomAccount());
    ContactDetails contactDetails = company.getContactDetails();
    AccountNumber accountNumber = company.getAccountNumber();
    List<String> actual = CompanyValidator.validate(company);
    List<String> expected = Collections.singletonList("");
    Assert.assertNotNull(contactDetails);
    Assert.assertNotNull(accountNumber);
    Assert.assertEquals(expected.toString(), actual.toString());
  }
}