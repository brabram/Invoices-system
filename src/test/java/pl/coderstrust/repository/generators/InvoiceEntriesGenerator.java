package pl.coderstrust.repository.generators;

import pl.coderstrust.model.InvoiceEntry;
import pl.coderstrust.model.Vat;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class InvoiceEntriesGenerator {

  private static Random random = new Random();

  public static InvoiceEntry getRandomInvoiceEntry() {
    AtomicInteger atomicInteger = new AtomicInteger(random.nextInt(999));
    int id = atomicInteger.incrementAndGet();
    String item = WordGenerator.getRandomWord();
    Long quantity = (long) random.nextInt(10000);
    BigDecimal price = BigDecimal.valueOf(random.nextInt(2000) * quantity);
    BigDecimal vatValue = price.multiply(BigDecimal.valueOf(Vat.VAT_23.getValue())).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    BigDecimal grossValue = price.add(vatValue);
    Vat vatRate = Vat.VAT_23;
    return new InvoiceEntry(id, item, quantity, price, vatValue, grossValue, vatRate);
  }
}
