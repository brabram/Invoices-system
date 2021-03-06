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
import pl.coderstrust.generators.AddressGenerator;
import pl.coderstrust.model.ContactDetails;

class ContactDetailsValidatorTest {

  @ParameterizedTest
  @MethodSource("emailArguments")
  void shouldValidateEmail(String email, List<String> expected) {
    ContactDetails contactDetails = ContactDetails.builder()
            .withId(1L)
            .withEmail(email)
            .withPhoneNumber("+48786345298")
            .withWebsite("www.company.net.eu")
            .withAddress(AddressGenerator.getRandomAddress())
            .build();
    List<String> resultOfValidation = ContactDetailsValidator.validate(contactDetails);
    assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> emailArguments() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Email cannot be null")),
        Arguments.of("", Collections.singletonList("Email cannot be empty")),
        Arguments.of("weefwfw", Collections.singletonList("Incorrect withEmail withAddress")),
        Arguments.of("weefwfw@fer", Collections.singletonList("Incorrect withEmail withAddress")),
        Arguments.of("weefwfw@gmail.com", new ArrayList<String>()),
        Arguments.of("Weefwfw@gmail.com.pl", new ArrayList<String>())
    );
  }

  @ParameterizedTest
  @MethodSource("webSiteArguments")
  void shouldValidateWebSite(String webSite, List<String> expected) {
    ContactDetails contactDetails = ContactDetails.builder()
            .withId(1L)
            .withEmail("poczta@onet.pl")
            .withPhoneNumber("+48786345298")
            .withWebsite(webSite)
            .withAddress(AddressGenerator.getRandomAddress())
            .build();
    List<String> resultOfValidation = ContactDetailsValidator.validate(contactDetails);
    assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> webSiteArguments() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Web site cannot be null")),
        Arguments.of("", Collections.singletonList("Web site cannot be empty")),
        Arguments.of("5366544", Collections.singletonList("Incorrect web site type")),
        Arguments.of("vergtre", Collections.singletonList("Incorrect web site type")),
        Arguments.of("rgtrtr.com", Collections.singletonList("Incorrect web site type")),
        Arguments.of("www.rgtr@#$%tr.com", Collections.singletonList("Incorrect web site type")),
        Arguments.of("www.rgtrtr.com", new ArrayList<String>()),
        Arguments.of("www.rgtrtr.com.pl", new ArrayList<String>())
    );
  }

  @ParameterizedTest
  @MethodSource("phoneNumberArguments")
  void shouldValidatePhoneNumber(String phoneNumber, List<String> expected) {
    ContactDetails contactDetails = ContactDetails.builder()
            .withId(1L)
            .withEmail("poczta@onet.pl")
            .withPhoneNumber(phoneNumber)
            .withWebsite("www.company.net.eu")
            .withAddress(AddressGenerator.getRandomAddress())
            .build();
    List<String> resultOfValidation = ContactDetailsValidator.validate(contactDetails);
    assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> phoneNumberArguments() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Phone withNumber cannot be null")),
        Arguments.of("", Collections.singletonList("Phone withNumber cannot be empty")),
        Arguments.of("gtr", Collections.singletonList("Incorrect phone withNumber")),
        Arguments.of("23243grrg", Collections.singletonList("Incorrect phone withNumber")),
        Arguments.of("+48111111111", new ArrayList<String>()),
        Arguments.of("48111111111", new ArrayList<String>())
    );
  }

  @Test
  void shouldThrowExceptionWhenContactDetailsIsNull() {
    List<String> resultOfValidation = ContactDetailsValidator.validate(null);
    List<String> expected = Collections.singletonList("Contact details cannot be null");
    assertEquals(expected, resultOfValidation);
  }

  @Test
  void shouldValidateAddress() {
    ContactDetails contactDetails = ContactDetails.builder()
            .withId(1L)
            .withEmail("poczta@onet.pl")
            .withPhoneNumber("48786345298")
            .withWebsite("www.company.net.eu")
            .withAddress(null)
            .build();
    List<String> expected = Collections.singletonList("Address cannot be null");
    List<String> resultOfValidation = ContactDetailsValidator.validate(contactDetails);
    assertEquals(expected, resultOfValidation);
  }
}
