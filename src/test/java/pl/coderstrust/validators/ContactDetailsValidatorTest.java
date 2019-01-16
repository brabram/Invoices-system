package pl.coderstrust.validators;

import junit.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.coderstrust.generators.ContactDetailsGenerator;
import pl.coderstrust.model.ContactDetails;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;


class ContactDetailsValidatorTest {
  private ContactDetails contactDetails;

  @BeforeEach
  void setup() {
    contactDetails = ContactDetailsGenerator.getRandomContactDetails();
  }

  @ParameterizedTest
  @MethodSource("emailParameters")
  void shouldNotValidateEmail(String email, List<String> expected) {
    contactDetails.setEmail(email);
    List<String> resultOfValidation = ContactDetailsValidator.validate(contactDetails);
    Assert.assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> emailParameters() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Email cannot be null")),
        Arguments.of("", Collections.singletonList("Email cannot be empty")),
        Arguments.of("weefwfw", Collections.singletonList("Incorrect email address")),
        Arguments.of("weefwfw@fer", Collections.singletonList("Incorrect email address"))
    );
  }

  @ParameterizedTest
  @MethodSource("webSiteParameters")
  void shouldNotValidateWebSite(String webSite, List<String> expected) {
    contactDetails.setWebsite(webSite);
    List<String> resultOfValidation = ContactDetailsValidator.validate(contactDetails);
    Assert.assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> webSiteParameters() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Web site cannot be null")),
        Arguments.of("", Collections.singletonList("Web site cannot be empty")),
        Arguments.of("5366544", Collections.singletonList("Incorrect web site type")),
        Arguments.of("vergtre", Collections.singletonList("Incorrect web site type")),
        Arguments.of("rgtrtr.com", Collections.singletonList("Incorrect web site type")),
        Arguments.of("www.rgtr@#$%tr.com", Collections.singletonList("Incorrect web site type"))
    );
  }

  @ParameterizedTest
  @MethodSource("phoneNumberParameters")
  void shouldNotValidatePhoneNumber(String phoneNumber, List<String> expected) {
    contactDetails.setPhoneNumber(phoneNumber);
    List<String> resultOfValidation = ContactDetailsValidator.validate(contactDetails);
    Assert.assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> phoneNumberParameters() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Phone number cannot be null")),
        Arguments.of("", Collections.singletonList("Phone number cannot be empty")),
        Arguments.of("gtr", Collections.singletonList("Incorrect phone number")),
        Arguments.of("23243grrg", Collections.singletonList("Incorrect phone number"))
    );
  }

  @Test
  void shouldApproveContactDetails() {
    List<String> actual = ContactDetailsValidator.validate(contactDetails);
    List<String> expected = new ArrayList<>();
    Assert.assertEquals(expected, actual);
  }
}
