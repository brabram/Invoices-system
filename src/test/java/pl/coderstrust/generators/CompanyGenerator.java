package pl.coderstrust.generators;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import pl.coderstrust.model.AccountNumber;
import pl.coderstrust.model.Company;
import pl.coderstrust.model.ContactDetails;

public class CompanyGenerator {
  private static Random random = new Random();
  private static AtomicLong atomicLong = new AtomicLong(1);

  public static Company getRandomCompany() {
    long id = atomicLong.incrementAndGet();
    String name = WordGenerator.getRandomWord();
    String taxIdentificationNumber = String.format("%05d%05d", random.nextInt(99999), random.nextInt(99999));
    AccountNumber accountNumber = AccountNumberGenerator.getRandomAccount();
    ContactDetails contactDetails = ContactDetailsGenerator.getRandomContactDetails();
    return new Company(id, name, taxIdentificationNumber, accountNumber, contactDetails);
  }
}
