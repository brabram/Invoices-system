package pl.coderstrust.repository.generators;

import pl.coderstrust.model.Company;

import java.util.Random;

public class CompanyGenerator {
    static Random random = new Random();

    public static Company randomCompanyGenerator() {
        int min = 0;
        int max = 999;
        int id = random.nextInt((max - min) + 1) + min;
        String name = String.format("%s", randomWordGenerator.randomWordGenerator());
        String taxIdentificationNumber = String.format("9%02d-%02d-%04d", random.nextInt(99), random.nextInt(99), random.nextInt(9999));
        return new Company(id, name, taxIdentificationNumber, AccountNumberGenerator.randomAccountGeneator(), ContactDetailsGenerator.getRandomContactDetails());
    }
}
