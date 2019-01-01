package pl.coderstrust.repository.generators;


import pl.coderstrust.model.AccountNumber;

import java.util.Random;

public class AccountNumberGenerator {

    public static AccountNumber randomAccountGeneator() {
        Random random = new Random();
        String ibanNumber = String.format("%02d %04d %04d %04d %04d %04d %04d", random.nextInt(99), random.nextInt(9999), random.nextInt(9999),
                random.nextInt(9999), random.nextInt(9999), random.nextInt(9999), random.nextInt(9999));
        String localNumber = ibanNumber;
        return new AccountNumber("PL" + ibanNumber, localNumber);
    }
}
