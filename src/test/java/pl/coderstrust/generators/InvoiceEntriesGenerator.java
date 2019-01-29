package pl.coderstrust.generators;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import pl.coderstrust.model.InvoiceEntry;
import pl.coderstrust.model.Vat;

public class InvoiceEntriesGenerator {
  private static Random random = new Random();

  public static InvoiceEntry getRandomInvoiceEntry() {
    long id = ThreadLocalRandom.current().nextLong(1, 999);
    String item = WordGenerator.getRandomWord();
    Long quantity = (long) random.nextInt(10000);
    BigDecimal price = BigDecimal.valueOf(random.nextInt(2000) * quantity);
    BigDecimal vatValue = price.multiply(BigDecimal.valueOf(Vat.VAT_23.getValue())).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    BigDecimal grossValue = price.add(vatValue);
    Vat vatRate = Vat.VAT_23;
    return new InvoiceEntry(id, item, quantity, price, vatValue, grossValue, vatRate);
  }
}
