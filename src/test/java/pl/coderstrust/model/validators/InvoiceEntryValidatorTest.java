package pl.coderstrust.model.validators;

import junit.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.coderstrust.generators.InvoiceEntriesGenerator;
import pl.coderstrust.generators.InvoiceGenerator;
import pl.coderstrust.model.InvoiceEntry;
import pl.coderstrust.model.Vat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

class InvoiceEntryValidatorTest {
  private InvoiceEntry invoiceEntry;

  @BeforeEach
  void setup() {
    invoiceEntry = InvoiceEntriesGenerator.getRandomInvoiceEntry();
  }

  @ParameterizedTest
  @MethodSource("itemNameParameters")
  void shouldNotValidateItemName(String item, List<String> expected) {
    invoiceEntry.setItem(item);
    List<String> resultOfValidation = InvoiceEntryValidator.validate(invoiceEntry);
    Assert.assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> itemNameParameters() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Item cannot be null")),
        Arguments.of("", Collections.singletonList("Item cannot be empty")),
        Arguments.of("sdf35", Collections.singletonList("Incorrect item")),
        Arguments.of("35fewf", Collections.singletonList("Incorrect item")),
        Arguments.of("-535", Collections.singletonList("Incorrect item"))
    );
  }

  @ParameterizedTest
  @MethodSource("quantityParameters")
  void shouldNotValidateQuantity(Long quantity, List<String> expected) {
    invoiceEntry.setQuantity(quantity);
    List<String> resultOfValidation = InvoiceEntryValidator.validate(invoiceEntry);
    Assert.assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> quantityParameters() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Quantity cannot be null")),
        Arguments.of(Long.valueOf(-535), Collections.singletonList("Quantity cannot be less than or equal to 0")),
        Arguments.of(Long.valueOf(0), Collections.singletonList("Quantity cannot be less than or equal to 0")),
        Arguments.of(Long.valueOf(30), new ArrayList<String>())
    );
  }

  @ParameterizedTest
  @MethodSource("priceParameters")
  void shouldNotValidatePrice(BigDecimal price, List<String> expected) {
    invoiceEntry.setPrice(price);
    List<String> resultOfValidation = InvoiceEntryValidator.validate(invoiceEntry);
    Assert.assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> priceParameters() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Price cannot be null")),
        Arguments.of(BigDecimal.valueOf(-55), Collections.singletonList("Price cannot be less than or equal to 0")),
        Arguments.of(BigDecimal.valueOf(0), Collections.singletonList("Price cannot be less than or equal to 0")),
        Arguments.of(BigDecimal.valueOf(30), new ArrayList<String>())
    );
  }

  @ParameterizedTest
  @MethodSource("grossValueParameters")
  void shouldNotValidateGrossValue(BigDecimal grossValue, List<String> expected) {
    invoiceEntry.setGrossValue(grossValue);
    List<String> resultOfValidation = InvoiceEntryValidator.validate(invoiceEntry);
    Assert.assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> grossValueParameters() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Gross value cannot be null")),
        Arguments.of(BigDecimal.valueOf(-55), Collections.singletonList("Gross value cannot be less than or equal to 0")),
        Arguments.of(BigDecimal.valueOf(0), Collections.singletonList("Gross value cannot be less than or equal to 0")),
        Arguments.of(BigDecimal.valueOf(30), new ArrayList<String>())
    );
  }

  @ParameterizedTest
  @MethodSource("vatValueParameters")
  void shouldValidateVatValue(BigDecimal vatValue, List<String> expected) {
    invoiceEntry.setVatValue(vatValue);
    List<String> resultOfValidation = InvoiceEntryValidator.validate(invoiceEntry);
    Assert.assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> vatValueParameters() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Vat value cannot be null")),
        Arguments.of(BigDecimal.valueOf(-55), Collections.singletonList("Vat value cannot be less than or equal to 0")),
        Arguments.of(BigDecimal.valueOf(0), Collections.singletonList("Vat value cannot be less than or equal to 0")),
        Arguments.of(BigDecimal.valueOf(30), new ArrayList<String>())
    );
  }

  @Test
  void shouldThrowExceptionWhenVatRateIsNull() {
    invoiceEntry.setVatRate(null);
    List<String> resultOfValidation = InvoiceEntryValidator.validate(invoiceEntry);
    List<String> expected = Collections.singletonList("Vat rate cannot be null");
    Assert.assertEquals(expected, resultOfValidation);
  }

  @Test
  void shouldThrowExceptionWhenInvoiceEntryIsNull() {
    List<String> resultOfValidation = InvoiceEntryValidator.validate(null);
    List<String> expected = Collections.singletonList("Invoice entry cannot be null");
    Assert.assertEquals(expected, resultOfValidation);
  }
}
