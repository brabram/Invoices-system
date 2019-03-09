package pl.coderstrust.model.validators;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.coderstrust.model.Address;

class AddressValidatorTest {

  @ParameterizedTest
  @MethodSource("streetArguments")
  void shouldValidateStreet(String street, List<String> expected) {
    Address address = new Address(1L, street, "15a/1", "00810", "Warsaw", "Poland");
    List<String> resultOfValidation = AddressValidator.validate(address);
    assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> streetArguments() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Street cannot be null")),
        Arguments.of("", Collections.singletonList("Street cannot be empty")),
        Arguments.of("sdf34535", Collections.singletonList("Incorrect street name")),
        Arguments.of("53533242", Collections.singletonList("Incorrect street name")),
        Arguments.of("-53533242", Collections.singletonList("Incorrect street name")),
        Arguments.of("aleja Krakowska", new ArrayList<String>()),
        Arguments.of("Krakowska", new ArrayList<String>()),
        Arguments.of("Krakowska Eefefe Rege", new ArrayList<String>())
    );
  }

  @ParameterizedTest
  @MethodSource("cityArguments")
  void shouldValidateCity(String city, List<String> expected) {
    Address address = new Address(1L, "Warszawska", "15a/1", "00810", city, "Poland");
    List<String> resultOfValidation = AddressValidator.validate(address);
    assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> cityArguments() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("City cannot be null")),
        Arguments.of("", Collections.singletonList("City cannot be empty")),
        Arguments.of("sdf34535", Collections.singletonList("Incorrect city name")),
        Arguments.of("53533242", Collections.singletonList("Incorrect city name")),
        Arguments.of("-53533242", Collections.singletonList("Incorrect city name")),
        Arguments.of("warsaw", Collections.singletonList("Incorrect city name")),
        Arguments.of("wARsaw", Collections.singletonList("Incorrect city name")),
        Arguments.of("Warsaw", new ArrayList<String>()),
        Arguments.of("Wfef Ffefr", new ArrayList<String>())
    );
  }

  @ParameterizedTest
  @MethodSource("countryArguments")
  void shouldValidateCountry(String country, List<String> expected) {
    Address address = new Address(1L, "Warszawska", "15a/1", "00810", "Warsaw", country);
    List<String> resultOfValidation = AddressValidator.validate(address);
    assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> countryArguments() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Country cannot be null")),
        Arguments.of("", Collections.singletonList("Country cannot be empty")),
        Arguments.of("sdf34535", Collections.singletonList("Incorrect country name")),
        Arguments.of("53533242", Collections.singletonList("Incorrect country name")),
        Arguments.of("pOlAND", Collections.singletonList("Incorrect country name")),
        Arguments.of("poland", Collections.singletonList("Incorrect country name")),
        Arguments.of("United arab Emirates", Collections.singletonList("Incorrect country name")),
        Arguments.of("Poland", new ArrayList<String>()),
        Arguments.of("United Arab Emirates", new ArrayList<String>())
    );
  }

  @ParameterizedTest
  @MethodSource("addressNumberArguments")
  void shouldValidateAddressNumber(String addressNumber, List<String> expected) {
    Address address = new Address(1L, "Warszawska", addressNumber, "00810", "Warsaw", "Poland");
    List<String> resultOfValidation = AddressValidator.validate(address);
    assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> addressNumberArguments() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Number cannot be null")),
        Arguments.of("", Collections.singletonList("Number cannot be empty")),
        Arguments.of("sdf34535", Collections.singletonList("Incorrect address number")),
        Arguments.of("5352", new ArrayList<String>()),
        Arguments.of("5", new ArrayList<String>()),
        Arguments.of("4363/35654", Collections.singletonList("Incorrect address number")),
        Arguments.of("63/55", new ArrayList<String>())
    );
  }

  @ParameterizedTest
  @MethodSource("postalCodeArguments")
  void shouldValidatePostalCode(String postalCode, List<String> expected) {
    Address address = new Address(1L, "Warszawska", "15a/1", postalCode, "Warsaw", "Poland");
    List<String> resultOfValidation = AddressValidator.validate(address);
    assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> postalCodeArguments() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Postal code cannot be null")),
        Arguments.of("", Collections.singletonList("Postal code cannot be empty")),
        Arguments.of("sdf34535", Collections.singletonList("Incorrect postal code")),
        Arguments.of("53543-453", Collections.singletonList("Incorrect postal code")),
        Arguments.of("53543", new ArrayList<String>())
    );
  }

  @Test
  void shouldThrowExceptionWhenAddressIsNull() {
    List<String> resultOfValidation = AddressValidator.validate(null);
    List<String> expected = Collections.singletonList("Address cannot be null");
    assertEquals(expected, resultOfValidation);
  }
}
