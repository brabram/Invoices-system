package pl.coderstrust.validators;

import junit.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.coderstrust.generators.AddressGenerator;
import pl.coderstrust.model.Address;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

class AddressValidatorTest {
  private Address address;

  @BeforeEach
  void setup(){
    address = AddressGenerator.getRandomAddress();
  }

  @ParameterizedTest
  @MethodSource("streetParameters")
  void shouldNotValidateStreet(String street, List<String> expected) {
    address.setStreet(street);
    List<String> resultOfValidation = AddressValidator.validate(address);
    Assert.assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> streetParameters() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Street cannot be null")),
        Arguments.of("", Collections.singletonList("Street cannot be empty")),
        Arguments.of("sdf34535", Collections.singletonList("Incorrect street name")),
        Arguments.of("53533242", Collections.singletonList("Incorrect street name")),
        Arguments.of("-53533242", Collections.singletonList("Incorrect street name")),
        Arguments.of("krakowska", Collections.singletonList("Incorrect street name")),
        Arguments.of("kRAkoWska", Collections.singletonList("Incorrect street name"))
    );
  }

  @ParameterizedTest
  @MethodSource("cityParameters")
  void shouldNotValidateCity(String city, List<String> expected) {
    address.setCity(city);
    List<String> resultOfValidation = AddressValidator.validate(address);
    Assert.assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> cityParameters() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("City cannot be null")),
        Arguments.of("", Collections.singletonList("City cannot be empty")),
        Arguments.of("sdf34535", Collections.singletonList("Incorrect city name")),
        Arguments.of("53533242", Collections.singletonList("Incorrect city name")),
        Arguments.of("-53533242", Collections.singletonList("Incorrect city name")),
        Arguments.of("warsaw", Collections.singletonList("Incorrect city name")),
        Arguments.of("wARsaw", Collections.singletonList("Incorrect city name"))
    );
  }

  @ParameterizedTest
  @MethodSource("countryParameters")
  void shouldNotValidateCountry(String country, List<String> expected) {
    address.setCountry(country);
    List<String> resultOfValidation = AddressValidator.validate(address);
    Assert.assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> countryParameters() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Country cannot be null")),
        Arguments.of("", Collections.singletonList("Country cannot be empty")),
        Arguments.of("sdf34535", Collections.singletonList("Incorrect country name")),
        Arguments.of("53533242", Collections.singletonList("Incorrect country name")),
        Arguments.of("pOlAND", Collections.singletonList("Incorrect country name")),
        Arguments.of("poland", Collections.singletonList("Incorrect country name"))
    );
  }

  @ParameterizedTest
  @MethodSource("addressNumberParameters")
  void shouldNotValidateAddressNumber(String addressNumber, List<String> expected) {
    address.setNumber(addressNumber);
    List<String> resultOfValidation = AddressValidator.validate(address);
    Assert.assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> addressNumberParameters() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Number cannot be null")),
        Arguments.of("", Collections.singletonList("Number cannot be empty")),
        Arguments.of("sdf34535", Collections.singletonList("Incorrect address number")),
        Arguments.of("5352", Collections.singletonList("Incorrect address number")),
        Arguments.of("4363/35654", Collections.singletonList("Incorrect address number"))
    );
  }

  @ParameterizedTest
  @MethodSource("postalCodeParameters")
  void shouldNotValidatePostalCode(String postalCode, List<String> expected) {
    address.setPostalCode(postalCode);
    List<String> resultOfValidation = AddressValidator.validate(address);
    Assert.assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> postalCodeParameters() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Postal code cannot be null")),
        Arguments.of("", Collections.singletonList("Postal code cannot be empty")),
        Arguments.of("sdf34535", Collections.singletonList("Incorrect postal code")),
        Arguments.of("32555", Collections.singletonList("Incorrect postal code")),
        Arguments.of("53543-453", Collections.singletonList("Incorrect postal code"))
    );
  }

  @Test
  void shouldApproveAddressValidation() {
    List<String> actual = AddressValidator.validate(address);
    List<String> expected = new ArrayList<>();
    Assert.assertEquals(expected, actual);
  }
}
