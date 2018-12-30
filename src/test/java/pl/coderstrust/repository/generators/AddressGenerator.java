package pl.coderstrust.repository.generators;

import pl.coderstrust.model.Address;

import java.util.Random;

public class AddressGenerator {
    static Random random = new Random();

    public static Address getRandomAddress() {
        String street = "Krakowska";
        String number = String.format("%d/%d", random.nextInt(150), random.nextInt(180));
        String postalCode = String.format("25-%03d", random.nextInt(999));
        String city = "Warsaw";
        String country = "Poland";
        return new Address(street, number, postalCode, city, country);
    }
}
