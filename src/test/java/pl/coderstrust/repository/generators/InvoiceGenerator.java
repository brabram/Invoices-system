package pl.coderstrust.repository.generators;

import pl.coderstrust.model.Company;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.model.InvoiceEntry;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class InvoiceGenerator {
  private static Random random = new Random();

  public static Invoice getRandomInvoice() {
    AtomicInteger atomicInteger = new AtomicInteger(random.nextInt(9999));
    int id = atomicInteger.incrementAndGet();
    int number = random.nextInt(5000);
    LocalDate issueDate = createRandomDate();
    LocalDate dueDate = issueDate.plusDays(7);
    Company seller = CompanyGenerator.getRandomCompany();
    Company buyer = CompanyGenerator.getRandomCompany();
    List<InvoiceEntry> list = Collections.singletonList(InvoiceEntriesGenerator.getRandomInvoiceEntry());
    BigDecimal totalNetValue =InvoiceEntriesGenerator.getRandomInvoiceEntry().getPrice();
    BigDecimal totalGrossValue = InvoiceEntriesGenerator.getRandomInvoiceEntry().getGrossValue();
    return new Invoice(id, number, issueDate, dueDate, seller, buyer,
        list, totalNetValue, totalGrossValue);
  }

  private static LocalDate createRandomDate() {
    int day = createRandomIntBetween(1, 28);
    int month = createRandomIntBetween(1, 12);
    int year = createRandomIntBetween(1990, 2019);
    return LocalDate.of(year, month, day);
  }

  private static int createRandomIntBetween(int start, int end) {
    return start + (int) Math.round(Math.random() * (end - start));
  }
}
