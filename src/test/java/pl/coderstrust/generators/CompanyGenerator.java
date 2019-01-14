package pl.coderstrust.generators;

import pl.coderstrust.model.AccountNumber;
import pl.coderstrust.model.Company;
import pl.coderstrust.model.ContactDetails;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class CompanyGenerator {

  private static Random random = new Random();
  private static AtomicInteger atomicInteger = new AtomicInteger(random.nextInt(999));

  public static Company getRandomCompany() {
    String id = String.valueOf(atomicInteger.incrementAndGet());
    String name = WordGenerator.getRandomWord();
    String taxIdentificationNumber = String.format("9%02d-%02d-%04d", random.nextInt(99), random.nextInt(99), random.nextInt(9999));
    AccountNumber accountNumber = AccountNumberGenerator.getRandomAccount();
    ContactDetails contactDetails = ContactDetailsGenerator.getRandomContactDetails();
    return new Company(id, name, taxIdentificationNumber, accountNumber, contactDetails);
  }
}
