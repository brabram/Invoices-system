package pl.coderstrust.generators;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import pl.coderstrust.model.InvoiceEntry;
import pl.coderstrust.model.Vat;

public class InvoiceEntriesGenerator {
  private static Random random = new Random();

  public static InvoiceEntry getRandomInvoiceEntry() {
    long id = IdGenerator.getNextId();
    String item = WordGenerator.getRandomWord();
    Long quantity = ThreadLocalRandom.current().nextLong(1, 999);
    BigDecimal price = BigDecimal.valueOf(random.nextInt(2000) * quantity);
    BigDecimal vatValue = price.multiply(BigDecimal.valueOf(Vat.VAT_23.getValue())).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    BigDecimal grossValue = price.add(vatValue);
    Vat vatRate = Vat.VAT_23;
    return new InvoiceEntry(id, item, quantity, price, vatValue, grossValue, vatRate);
  }

  public static InvoiceEntry getRandomInvoiceEntryWithoutId() {
    long id = 0L;
    String item = WordGenerator.getRandomWord();
    Long quantity = ThreadLocalRandom.current().nextLong(1, 999);
    BigDecimal price = BigDecimal.valueOf(random.nextInt(2000) * quantity);
    BigDecimal vatValue = price.multiply(BigDecimal.valueOf(Vat.VAT_23.getValue())).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    BigDecimal grossValue = price.add(vatValue);
    Vat vatRate = Vat.VAT_23;
    return new InvoiceEntry(id, item, quantity, price, vatValue, grossValue, vatRate);
  }
}
