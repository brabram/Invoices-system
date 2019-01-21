package pl.coderstrust.validators;

import junit.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.coderstrust.generators.InvoiceEntriesGenerator;
import pl.coderstrust.model.InvoiceEntry;
import pl.coderstrust.model.Vat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

import javax.management.StringValueExp;

class InvoiceEntryValidatorTest {
  private InvoiceEntry invoiceEntry;

  @BeforeEach
  void setup() {
    invoiceEntry = InvoiceEntriesGenerator.getRandomInvoiceEntry();
  }

  @ParameterizedTest
  @MethodSource("InvoiceEntryIdParameters")
  void shouldNotValidateInvoiceEntryId(long id, List<String> expected) {
    invoiceEntry.setId(id);
    List<String> resultOfValidation = InvoiceEntryValidator.validate(invoiceEntry);
    Assert.assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> InvoiceEntryIdParameters() {
    return Stream.of(
        Arguments.of(-535, Collections.singletonList("Id cannot be less than 0"))
    );
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
        Arguments.of(Long.valueOf(-535), Collections.singletonList("Quantity cannot be less than 0"))
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
        Arguments.of(BigDecimal.valueOf(-55), Collections.singletonList("Price cannot be less than 0")),
        Arguments.of(BigDecimal.valueOf(0), Collections.singletonList("Price cannot be equal to 0"))
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
        Arguments.of(BigDecimal.valueOf(-55), Collections.singletonList("Gross value cannot be less than 0")),
        Arguments.of(BigDecimal.valueOf(0), Collections.singletonList("Gross value cannot be equal to 0"))
    );
  }

  @ParameterizedTest
  @MethodSource("vatValueParameters")
  void shouldNotValidateVatValue(BigDecimal vatValue, List<String> expected) {
    invoiceEntry.setVatValue(vatValue);
    List<String> resultOfValidation = InvoiceEntryValidator.validate(invoiceEntry);
    Assert.assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> vatValueParameters() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Vat value cannot be null")),
        Arguments.of(BigDecimal.valueOf(-55), Collections.singletonList("Vat value cannot be less than 0")),
        Arguments.of(BigDecimal.valueOf(0), Collections.singletonList("Vat value cannot be equal to 0"))
    );
  }

  @ParameterizedTest
  @MethodSource("vatRateParameters")
  void shouldNotValidateVatRate(Vat vatRate, List<String> expected) {
    invoiceEntry.setVatRate(vatRate);
    List<String> resultOfValidation = InvoiceEntryValidator.validate(invoiceEntry);
    Assert.assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> vatRateParameters() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Vat rate cannot be null"))
    );
  }

  @Test
  void shouldApproveInvoiceEntry() {
    List<String> actual = InvoiceEntryValidator.validate(invoiceEntry);
    List<String> expected = new ArrayList<>();
    Assert.assertEquals(expected, actual);
  }
}
