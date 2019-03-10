package pl.coderstrust.generators;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import pl.coderstrust.model.Company;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.model.InvoiceEntry;

public class InvoiceGenerator {
  private static Random random = new Random();

  public static Invoice getRandomInvoice() {
    long id = IdGenerator.getNextId();
    String number = String.valueOf(random.nextInt(5000));
    LocalDate issueDate = createRandomDate();
    LocalDate dueDate = issueDate.plusDays(7);
    Company seller = CompanyGenerator.getRandomCompany();
    Company buyer = CompanyGenerator.getRandomCompany();
    List<InvoiceEntry> entries = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      entries.add(InvoiceEntriesGenerator.getRandomInvoiceEntry());
    }
    BigDecimal totalNetValue = entries.stream().map(item -> item.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal totalGrossValue = entries.stream().map(item -> item.getGrossValue()).reduce(BigDecimal.ZERO, BigDecimal::add);
    return Invoice.builder()
            .withId(id)
            .withNumber(number)
            .withIssueDate(issueDate)
            .withDueDate(dueDate)
            .withSeller(seller)
            .withBuyer(buyer)
            .withEntries(entries)
            .withTotalNetValue(totalNetValue)
            .withTotalGrossValue(totalGrossValue)
            .build();
  }

  public static Invoice getRandomInvoiceWithoutIdInOtherEntities() {
    long id = IdGenerator.getNextId();
    String number = String.valueOf(random.nextInt(5000));
    LocalDate issueDate = createRandomDate();
    LocalDate dueDate = issueDate.plusDays(7);
    Company seller = CompanyGenerator.getRandomCompanyWithoutId();
    Company buyer = CompanyGenerator.getRandomCompanyWithoutId();
    List<InvoiceEntry> entries = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      entries.add(InvoiceEntriesGenerator.getRandomInvoiceEntryWithoutId());
    }
    BigDecimal totalNetValue = entries.stream().map(item -> item.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal totalGrossValue = entries.stream().map(item -> item.getGrossValue()).reduce(BigDecimal.ZERO, BigDecimal::add);
    return Invoice.builder()
            .withId(id)
            .withNumber(number)
            .withIssueDate(issueDate)
            .withDueDate(dueDate)
            .withSeller(seller)
            .withBuyer(buyer)
            .withEntries(entries)
            .withTotalNetValue(totalNetValue)
            .withTotalGrossValue(totalGrossValue)
            .build();
  }

  public static Invoice getRandomInvoicesIssuedInSpecificDateRange(LocalDate startDate, LocalDate endDate) {
    Invoice invoice = getRandomInvoice();
    return Invoice.builder()
            .withId(invoice.getId())
            .withNumber(invoice.getNumber())
            .withIssueDate(startDate)
            .withDueDate(endDate)
            .withSeller(invoice.getSeller())
            .withBuyer(invoice.getBuyer())
            .withEntries(invoice.getEntries())
            .withTotalNetValue(invoice.getTotalNetValue())
            .withTotalGrossValue(invoice.getTotalGrossValue())
            .build();
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
