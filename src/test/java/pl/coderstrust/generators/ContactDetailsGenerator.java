package pl.coderstrust.generators;

import static pl.coderstrust.generators.WordGenerator.getRandomWord;

import java.util.Random;

import pl.coderstrust.model.Address;
import pl.coderstrust.model.ContactDetails;

public class ContactDetailsGenerator {
  private static Random random = new Random();

  public static ContactDetails getRandomContactDetails() {
    long id = IdGenerator.getRandomId();
    String email = String.format("%s@gmail.com", getRandomWord());
    String phoneNumber = String.format("+48%09d", random.nextInt(999999999));
    String webSite = String.format("www.%s.com", getRandomWord());
    Address address = AddressGenerator.getRandomAddress();
    return new ContactDetails(id, email, phoneNumber, webSite, address);
  }
}
