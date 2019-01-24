package pl.coderstrust.model.validators;

import junit.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.coderstrust.generators.CompanyGenerator;
import pl.coderstrust.model.Company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

class CompanyValidatorTest {
  private Company company;

  @BeforeEach
  void setup() {
    company = CompanyGenerator.getRandomCompany();
  }

  @ParameterizedTest
  @MethodSource("companyIdArguments")
  void shouldValidateCompanyId(Long id, List<String> expected) {
    company.setId(id);
    List<String> resultOfValidation = CompanyValidator.validate(company);
    Assert.assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> companyIdArguments() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Id cannot be null")),
        Arguments.of(Long.valueOf(-535), Collections.singletonList("Id cannot be less than or equal to 0")),
        Arguments.of(Long.valueOf(0), Collections.singletonList("Id cannot be less than or equal to 0")),
        Arguments.of(Long.valueOf(50), new ArrayList<String>())
    );
  }

  @ParameterizedTest
  @MethodSource("companyNameArguments")
  void shouldValidateCompanyName(String name, List<String> expected) {
    company.setName(name);
    List<String> resultOfValidation = CompanyValidator.validate(company);
    Assert.assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> companyNameArguments() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Name cannot be null")),
        Arguments.of("", Collections.singletonList("Name cannot be empty")),
        Arguments.of("fewf%^$", Collections.singletonList("Incorrect name")),
        Arguments.of("3M", new ArrayList<String>()),
        Arguments.of("Aferf", new ArrayList<String>())
    );
  }

  @ParameterizedTest
  @MethodSource("taxIdentificationNumberArguments")
  void shouldValidateTaxIdentificationNumber(String taxIdentificationNumber, List<String> expected) {
    company.setTaxIdentificationNumber(taxIdentificationNumber);
    List<String> resultOfValidation = CompanyValidator.validate(company);
    Assert.assertEquals(expected, resultOfValidation);
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
    Assert.assertEquals(expected, resultOfValidation);
  }
}
