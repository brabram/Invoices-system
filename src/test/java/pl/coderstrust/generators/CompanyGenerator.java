package pl.coderstrust.generators;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import pl.coderstrust.model.AccountNumber;
import pl.coderstrust.model.Company;
import pl.coderstrust.model.ContactDetails;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class CompanyGenerator {

  private static Random random = new Random();
  private static AtomicLong atomicLong = new AtomicLong(random.nextInt(Math.toIntExact(9999L)));

  public static Company getRandomCompany() {
    Long id = atomicLong.incrementAndGet();
    String name = WordGenerator.getRandomWord();
    String taxIdentificationNumber = String.format("%05d%05d", random.nextInt(99999), random.nextInt(99999));
    AccountNumber accountNumber = AccountNumberGenerator.getRandomAccount();
    ContactDetails contactDetails = ContactDetailsGenerator.getRandomContactDetails();
    return new Company(id, name, taxIdentificationNumber, accountNumber, contactDetails);
  }
}
