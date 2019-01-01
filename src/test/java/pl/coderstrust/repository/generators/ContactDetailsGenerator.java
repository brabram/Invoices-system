package pl.coderstrust.repository.generators;

import pl.coderstrust.model.ContactDetails;

import java.util.Random;

import static pl.coderstrust.repository.generators.randomWordGenerator.randomWordGenerator;

public class ContactDetailsGenerator {
    static Random random = new Random();

    public static ContactDetails getRandomContactDetails() {
        String email = String.format("%s@gmail.com", randomWordGenerator());
        String phoneNumber = String.format("+48%09d", random.nextInt(999999999));
        String webSite = String.format("%s.com", randomWordGenerator());
        return new ContactDetails(email, phoneNumber, webSite, AddressGenerator.getRandomAddress());
    }
}
