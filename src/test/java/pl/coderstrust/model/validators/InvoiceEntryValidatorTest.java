package pl.coderstrust.model.validators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.coderstrust.generators.InvoiceEntriesGenerator;
import pl.coderstrust.model.InvoiceEntry;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

class InvoiceEntryValidatorTest {
  private InvoiceEntry invoiceEntry;

  private static Stream<Arguments> itemNameArguments() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Item cannot be null")),
        Arguments.of("", Collections.singletonList("Item cannot be empty")),
        Arguments.of("sdf35", Collections.singletonList("Incorrect item")),
        Arguments.of("35fewf", Collections.singletonList("Incorrect item")),
        Arguments.of("-535", Collections.singletonList("Incorrect item")),
        Arguments.of("Something", new ArrayList<String>()),
        Arguments.of("something", new ArrayList<String>()),
        Arguments.of("free wwd fre", new ArrayList<String>()),
        Arguments.of("Free Wwd Fre", new ArrayList<String>()),
        Arguments.of("Free-Wwd-Fre", new ArrayList<String>())
    );
  }

  private static Stream<Arguments> quantityArguments() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Quantity cannot be null")),
        Arguments.of(-535L, Collections.singletonList("Quantity cannot be less than or equal to 0")),
        Arguments.of(0L, Collections.singletonList("Quantity cannot be less than or equal to 0")),
        Arguments.of(30L, new ArrayList<String>())
    );
  }

  private static Stream<Arguments> priceArguments() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Price cannot be null")),
        Arguments.of(BigDecimal.valueOf(-55), Collections.singletonList("Price cannot be less than or equal to 0")),
        Arguments.of(BigDecimal.valueOf(0), Collections.singletonList("Price cannot be less than or equal to 0")),
        Arguments.of(BigDecimal.valueOf(30), new ArrayList<String>())
    );
  }

  private static Stream<Arguments> grossValueArguments() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Gross value cannot be null")),
        Arguments.of(BigDecimal.valueOf(-55), Collections.singletonList("Gross value cannot be less than or equal to 0")),
        Arguments.of(BigDecimal.valueOf(0), Collections.singletonList("Gross value cannot be less than or equal to 0")),
        Arguments.of(BigDecimal.valueOf(30), new ArrayList<String>())
    );
  }

  private static Stream<Arguments> vatValueArguments() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Vat value cannot be null")),
        Arguments.of(BigDecimal.valueOf(-55), Collections.singletonList("Vat value cannot be less than or equal to 0")),
        Arguments.of(BigDecimal.valueOf(0), Collections.singletonList("Vat value cannot be less than or equal to 0")),
        Arguments.of(BigDecimal.valueOf(30), new ArrayList<String>())
    );
  }

  @BeforeEach
  void setup() {
    invoiceEntry = InvoiceEntriesGenerator.getRandomInvoiceEntry();
  }

  @ParameterizedTest
  @MethodSource("itemNameArguments")
  void shouldValidateItemName(String item, List<String> expected) {
    invoiceEntry.setItem(item);
    List<String> resultOfValidation = InvoiceEntryValidator.validate(invoiceEntry);
    assertEquals(expected, resultOfValidation);
  }

  @ParameterizedTest
  @MethodSource("quantityArguments")
  void shouldValidateQuantity(Long quantity, List<String> expected) {
    invoiceEntry.setQuantity(quantity);
    List<String> resultOfValidation = InvoiceEntryValidator.validate(invoiceEntry);
    assertEquals(expected, resultOfValidation);
  }

  @ParameterizedTest
  @MethodSource("priceArguments")
  void shouldValidatePrice(BigDecimal price, List<String> expected) {
    invoiceEntry.setPrice(price);
    List<String> resultOfValidation = InvoiceEntryValidator.validate(invoiceEntry);
    assertEquals(expected, resultOfValidation);
  }

  @ParameterizedTest
  @MethodSource("grossValueArguments")
  void shouldValidateGrossValue(BigDecimal grossValue, List<String> expected) {
    invoiceEntry.setGrossValue(grossValue);
    List<String> resultOfValidation = InvoiceEntryValidator.validate(invoiceEntry);
    assertEquals(expected, resultOfValidation);
  }

  @ParameterizedTest
  @MethodSource("vatValueArguments")
  void shouldValidateVatValue(BigDecimal vatValue, List<String> expected) {
    invoiceEntry.setVatValue(vatValue);
    List<String> resultOfValidation = InvoiceEntryValidator.validate(invoiceEntry);
    assertEquals(expected, resultOfValidation);
  }

  @Test
  void shouldValidateVatRate() {
    invoiceEntry.setVatRate(null);
    List<String> resultOfValidation = InvoiceEntryValidator.validate(invoiceEntry);
    List<String> expected = Collections.singletonList("Vat rate cannot be null");
    assertEquals(expected, resultOfValidation);
  }

  @Test
  void shouldThrowExceptionWhenInvoiceEntryIsNull() {
    List<String> resultOfValidation = InvoiceEntryValidator.validate((InvoiceEntry) null);
    List<String> expected = Collections.singletonList("Invoice entry cannot be null");
    assertEquals(expected, resultOfValidation);
  }
}
