package pl.coderstrust.generators;

import static pl.coderstrust.generators.WordGenerator.getRandomWord;

import java.util.Random;

import pl.coderstrust.model.Address;
import pl.coderstrust.model.ContactDetails;

class ContactDetailsGenerator {
  private static Random random = new Random();

  static ContactDetails getRandomContactDetails() {
    String email = String.format("%s@gmail.com", getRandomWord());
    String phoneNumber = String.format("+48%09d", random.nextInt(999999999));
    String webSite = String.format("www.%s.com", getRandomWord());
    Address address = AddressGenerator.getRandomAddress();
    return new ContactDetails(email, phoneNumber, webSite, address);
  }
}
