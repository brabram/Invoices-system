package pl.coderstrust.generators;

import java.util.Random;

import pl.coderstrust.model.AccountNumber;

public class AccountNumberGenerator {
  private static Random random = new Random();

  public static AccountNumber getRandomAccount() {
    long id = IdGenerator.getNextId();
    String localNumber = String.format("%02d%04d%04d%04d%04d%04d%04d", random.nextInt(99), random.nextInt(9999), random.nextInt(9999),
        random.nextInt(9999), random.nextInt(9999), random.nextInt(9999), random.nextInt(9999));
    String ibanNumber = String.format("PL%s", localNumber);
    return  AccountNumber.builder()
            .withId(id)
            .withIbanNumber(ibanNumber)
            .withLocalNumber(localNumber)
            .build();
  }

  public static AccountNumber getRandomAccountWithoutId() {
    long id = 0L;
    String localNumber = String.format("%02d%04d%04d%04d%04d%04d%04d", random.nextInt(99), random.nextInt(9999), random.nextInt(9999),
        random.nextInt(9999), random.nextInt(9999), random.nextInt(9999), random.nextInt(9999));
    String ibanNumber = String.format("PL%s", localNumber);
    return  AccountNumber.builder()
            .withId(id)
            .withIbanNumber(ibanNumber)
            .withLocalNumber(localNumber)
            .build();
  }
}
