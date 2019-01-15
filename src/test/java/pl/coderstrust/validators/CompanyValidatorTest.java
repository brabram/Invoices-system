package pl.coderstrust.validators;

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
 void setup(){
   company = CompanyGenerator.getRandomCompany();
 }

  @ParameterizedTest
  @MethodSource("companyIdParameters")
  void shouldNotValidateCompanyId(String id, List<String> expected) {
    company.setId(id);
    List<String> resultOfValidation = CompanyValidator.validate(company);
    Assert.assertEquals(expected, resultOfValidation);
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
    Assert.assertEquals(expected, resultOfValidation);
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
    Assert.assertEquals(expected, resultOfValidation);
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
    List<String> resultOfValidation = CompanyValidator.validate(company);
    List expected = new ArrayList<String>();
    Assert.assertEquals(expected, resultOfValidation);
  }
}