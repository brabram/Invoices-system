package pl.coderstrust.generators;

import pl.coderstrust.model.Company;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.model.InvoiceEntry;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class InvoiceGenerator {
  private static Random random = new Random();
  private static AtomicInteger atomicInteger = new AtomicInteger(random.nextInt(9999));

  public static Invoice getRandomInvoice() {
    int id = atomicInteger.incrementAndGet();
    int number = random.nextInt(5000);
    LocalDate issueDate = createRandomDate();
    LocalDate dueDate = issueDate.plusDays(7);
    Company seller = CompanyGenerator.getRandomCompany();
    Company buyer = CompanyGenerator.getRandomCompany();
    List<InvoiceEntry> list = new ArrayList<>();
    for (int i = 0; i < 5 ; i++) {
      list.add(InvoiceEntriesGenerator.getRandomInvoiceEntry());
    }
    BigDecimal totalNetValue = list.stream().map(item -> item.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal totalGrossValue = list.stream().map(item -> item.getGrossValue()).reduce(BigDecimal.ZERO, BigDecimal::add);
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
