package pl.coderstrust.generators;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import pl.coderstrust.model.Company;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.model.InvoiceEntry;

public class InvoiceGenerator {
  private static Random random = new Random();

  public static Invoice getRandomInvoice() {
    long id = ThreadLocalRandom.current().nextLong(1,999);
    String number = String.valueOf(random.nextInt(5000));
    LocalDate issueDate = createRandomDate();
    LocalDate dueDate = issueDate.plusDays(7);
    Company seller = CompanyGenerator.getRandomCompany();
    Company buyer = CompanyGenerator.getRandomCompany();
    List<InvoiceEntry> list = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      list.add(InvoiceEntriesGenerator.getRandomInvoiceEntry());
    }
    BigDecimal totalNetValue = list.stream().map(item -> item.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal totalGrossValue = list.stream().map(item -> item.getGrossValue()).reduce(BigDecimal.ZERO, BigDecimal::add);
    return new Invoice(id, number, issueDate, dueDate, seller, buyer,
        list, totalNetValue, totalGrossValue);
  }

  public static Invoice getRandomInvoiceWithSpecificIssueDate(LocalDate issueDate) {
    Invoice invoice = getRandomInvoice();
    invoice.setIssueDate(issueDate);
    return invoice;
  }

  public static Invoice getRandomInvoiceWithSpecificDueDate(LocalDate dueDate) {
    Invoice invoice = getRandomInvoice();
    invoice.setDueDate(dueDate);
    return invoice;
  }

  public static Invoice getRandomInvoicesIssuedInSpecificDateRange(LocalDate startDate, LocalDate endDate) {
    Invoice invoice = getRandomInvoice();
    invoice.setIssueDate(createRandomDateInSpecificDateRange(startDate, endDate));
    return invoice;
  }

  private static LocalDate createRandomDate() {
    int day = createRandomIntBetween(1, 28);
    int month = createRandomIntBetween(1, 12);
    int year = createRandomIntBetween(1990, 2019);
    return LocalDate.of(year, month, day);
  }

  private static LocalDate createRandomDateInSpecificDateRange(LocalDate startDate, LocalDate endDate) {
    int day = createRandomIntBetween(startDate.getDayOfMonth(), endDate.getDayOfMonth());
    int month = createRandomIntBetween(startDate.getMonth().getValue(), endDate.getMonth().getValue());
    int year = createRandomIntBetween(startDate.getYear(), endDate.getYear());
    return LocalDate.of(year, month, day);
  }

  private static int createRandomIntBetween(int start, int end) {
    return start + (int) Math.round(Math.random() * (end - start));
  }
}
