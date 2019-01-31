package pl.coderstrust.generators;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import pl.coderstrust.model.AccountNumber;

public class AccountNumberGenerator {
  private static Random random = new Random();

  public static AccountNumber getRandomAccount() {
    long id = IdGenerator.getRandomId();
    String localNumber = String.format("%02d%04d%04d%04d%04d%04d%04d", random.nextInt(99), random.nextInt(9999), random.nextInt(9999),
        random.nextInt(9999), random.nextInt(9999), random.nextInt(9999), random.nextInt(9999));
    String ibanNumber = String.format("PL%s", localNumber);
    return new AccountNumber(id, ibanNumber, localNumber);
  }
}
