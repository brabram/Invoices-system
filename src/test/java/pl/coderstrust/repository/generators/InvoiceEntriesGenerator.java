package pl.coderstrust.repository.generators;

import pl.coderstrust.model.InvoiceEntry;
import pl.coderstrust.model.Vat;

import java.math.BigDecimal;
import java.util.Random;

public class InvoiceEntriesGenerator {

    static Random random = new Random();

    public static InvoiceEntry invoiceEntriesGenerator() {
        int min = 0;
        int max = 10000;
        int id = random.nextInt((max - min) + 1) + min;
        String item = String.format("%s", randomWordGenerator.randomWordGenerator());
        Long quantity = Long.valueOf(String.format("%d", random.nextInt(10000)));
        BigDecimal price = BigDecimal.valueOf(Long.parseLong(String.format("%d", random.nextInt(2000) * quantity)));
        BigDecimal vatValue = price.multiply(BigDecimal.valueOf(0.23));
        BigDecimal grossValue = price.add(vatValue);
        Vat vatRate = Vat.VAT_23;
        return new InvoiceEntry(id, item, quantity, price, vatValue, grossValue, vatRate);
    }
}
