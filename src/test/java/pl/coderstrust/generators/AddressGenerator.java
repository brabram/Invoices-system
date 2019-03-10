package pl.coderstrust.generators;

import java.util.Random;

import pl.coderstrust.model.Address;

public class AddressGenerator {
  private static Random random = new Random();

  public static Address getRandomAddress() {
    long id = IdGenerator.getNextId();
    String street = "Krakowska";
    String number = String.format("%d/%d", random.nextInt(150), random.nextInt(180));
    String postalCode = String.format("%05d", random.nextInt(99999));
    String city = "Warsaw";
    String country = "Poland";
    return Address.builder()
            .withId(id)
            .withStreet(street)
            .withNumber(number)
            .withPostalCode(postalCode)
            .withCity(city)
            .withCountry(country)
            .build();
  }

  public static Address getRandomAddressWithoutId() {
    long id = 0L;
    String street = "Krakowska";
    String number = String.format("%d/%d", random.nextInt(150), random.nextInt(180));
    String postalCode = String.format("%05d", random.nextInt(99999));
    String city = "Warsaw";
    String country = "Poland";
    return Address.builder()
            .withId(id)
            .withStreet(street)
            .withNumber(number)
            .withPostalCode(postalCode)
            .withCity(city)
            .withCountry(country)
            .build();
  }
}
