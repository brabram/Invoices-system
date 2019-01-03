package pl.coderstrust.repository.generators;

import pl.coderstrust.model.Company;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.model.InvoiceEntry;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class InvoiceGenerator {
    static Random random = new Random();

    public static Invoice invoice() {
        int min = 0;
        int max = 10000;
        int id = random.nextInt((max - min) + 1) + min;
        int number = Integer.parseInt(String.format("%d", random.nextInt(5000)));
        LocalDate issueDate = LocalDate.parse(String.format("%s", dateGenerator()));
        LocalDate dueDate = LocalDate.parse(String.format("%s", dateGenerator()));
        Company seller = CompanyGenerator.randomCompanyGenerator();
        Company buyer = CompanyGenerator.randomCompanyGenerator();
        List<InvoiceEntry> list = Collections.singletonList(InvoiceEntriesGenerator.invoiceEntriesGenerator());
        BigDecimal totalNetValue = BigDecimal.valueOf(5333);
        BigDecimal totalGrossValue = BigDecimal.valueOf(5333);
        return new Invoice(id, number, issueDate, dueDate, seller, buyer,
                list, totalNetValue, totalGrossValue);
    }

    private static LocalDate dateGenerator() {
        long minDay = LocalDate.of(2000, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2019, 1, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay++);
        return randomDate;
    }
}
