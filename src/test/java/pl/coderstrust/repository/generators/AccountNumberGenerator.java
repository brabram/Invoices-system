package pl.coderstrust.repository.generators;


import pl.coderstrust.model.AccountNumber;

public class AccountNumberGenerator {

    public static AccountNumber randomAccountGeneator() {
        long min = 0000000000000000L;
        long max = 10000000000000000L;
        long ibanNumberfirst14 = (long) (Math.random() * max);
        long ibanNumberGenerator = min + ibanNumberfirst14;
        long localNumberFirst14 = (long) (Math.random() * max);
        long localNumberGenerator = min + localNumberFirst14;
        String ibanNumber = String.format("%016d", ibanNumberGenerator);
        String localNumber = String.format("%016d", localNumberGenerator);
        return new AccountNumber(ibanNumber, localNumber);
    }
}
