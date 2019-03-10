package pl.coderstrust.generators;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import pl.coderstrust.model.InvoiceEntry;
import pl.coderstrust.model.Vat;

public class InvoiceEntriesGenerator {

  public static InvoiceEntry getRandomInvoiceEntry() {
    long id = IdGenerator.getNextId();
    String item = WordGenerator.getRandomWord();
    Long quantity = ThreadLocalRandom.current().nextLong(1, 999);
    BigDecimal price = BigDecimal.valueOf(ThreadLocalRandom.current().nextInt(1, 50) * quantity);
    BigDecimal vatValue = price.multiply(BigDecimal.valueOf(Vat.VAT_23.getValue())).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    BigDecimal grossValue = price.add(vatValue);
    Vat vatRate = Vat.VAT_23;
    return InvoiceEntry.builder()
            .withId(id)
            .withItem(item)
            .withQuantity(quantity)
            .withPrice(price)
            .withVatValue(vatValue)
            .withGrossValue(grossValue)
            .withVatRate(vatRate)
            .build();
  }

  public static InvoiceEntry getRandomInvoiceEntryWithoutId() {
    long id = 0L;
    String item = WordGenerator.getRandomWord();
    Long quantity = ThreadLocalRandom.current().nextLong(1, 999);
    BigDecimal price = BigDecimal.valueOf(ThreadLocalRandom.current().nextInt(1, 50) * quantity);
    BigDecimal vatValue = price.multiply(BigDecimal.valueOf(Vat.VAT_23.getValue())).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    BigDecimal grossValue = price.add(vatValue);
    Vat vatRate = Vat.VAT_23;
    return InvoiceEntry.builder()
            .withId(id)
            .withItem(item)
            .withQuantity(quantity)
            .withPrice(price)
            .withVatValue(vatValue)
            .withGrossValue(grossValue)
            .withVatRate(vatRate)
            .build();
  }
}
