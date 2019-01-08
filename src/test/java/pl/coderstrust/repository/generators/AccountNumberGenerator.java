package pl.coderstrust.repository.generators;

import pl.coderstrust.model.AccountNumber;

import java.util.Random;

public class AccountNumberGenerator {
  private static Random random = new Random();

  public static AccountNumber getRandomAccount() {
    String localNumber = String.format("%02d %04d %04d %04d %04d %04d %04d", random.nextInt(99), random.nextInt(9999), random.nextInt(9999),
        random.nextInt(9999), random.nextInt(9999), random.nextInt(9999), random.nextInt(9999));
    String ibanNumber = String.format("PL %s", localNumber);
    return new AccountNumber(ibanNumber, localNumber);
  }
}
