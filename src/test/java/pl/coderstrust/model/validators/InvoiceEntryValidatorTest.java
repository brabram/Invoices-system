package pl.coderstrust.model.validators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.coderstrust.model.InvoiceEntry;
import pl.coderstrust.model.Vat;

class InvoiceEntryValidatorTest {

  @ParameterizedTest
  @MethodSource("itemNameArguments")
  void shouldValidateItemName(String item, List<String> expected) {
    InvoiceEntry invoiceEntry = InvoiceEntry.builder()
        .withId(1L)
        .withItem(item)
        .withQuantity(10L)
        .withPrice(BigDecimal.valueOf(100.00))
        .withVatValue(BigDecimal.valueOf(23.00))
        .withGrossValue(BigDecimal.valueOf(1230.00))
        .withVatRate(Vat.VAT_23)
        .build();
    List<String> resultOfValidation = InvoiceEntryValidator.validate(invoiceEntry);
    assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> itemNameArguments() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Item cannot be null")),
        Arguments.of("", Collections.singletonList("Item cannot be empty")),
        Arguments.of("sdf35", new ArrayList<String>()),
        Arguments.of("35fewf", new ArrayList<String>()),
        Arguments.of("-535", Collections.singletonList("Incorrect withItem")),
        Arguments.of("Something", new ArrayList<String>()),
        Arguments.of("something", new ArrayList<String>()),
        Arguments.of("free wwd fre", new ArrayList<String>()),
        Arguments.of("Free Wwd Fre", new ArrayList<String>()),
        Arguments.of("Free-Wwd-Fre", new ArrayList<String>())
    );
  }

  @ParameterizedTest
  @MethodSource("quantityArguments")
  void shouldValidateQuantity(Long quantity, List<String> expected) {
    InvoiceEntry invoiceEntry = InvoiceEntry.builder()
        .withId(1L)
        .withItem("10w40 Castrol engine oil")
        .withQuantity(quantity)
        .withPrice(BigDecimal.valueOf(100.00))
        .withVatValue(BigDecimal.valueOf(23.00))
        .withGrossValue(BigDecimal.valueOf(1230.00))
        .withVatRate(Vat.VAT_23)
        .build();
    List<String> resultOfValidation = InvoiceEntryValidator.validate(invoiceEntry);
    assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> quantityArguments() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Quantity cannot be null")),
        Arguments.of(-535L, Collections.singletonList("Quantity cannot be less than or equal to 0")),
        Arguments.of(0L, Collections.singletonList("Quantity cannot be less than or equal to 0")),
        Arguments.of(30L, new ArrayList<String>())
    );
  }


  @ParameterizedTest
  @MethodSource("priceArguments")
  void shouldValidatePrice(BigDecimal price, List<String> expected) {
    InvoiceEntry invoiceEntry = InvoiceEntry.builder()
        .withId(1L)
        .withItem("10w40 Castrol engine oil")
        .withQuantity(10L)
        .withPrice(price)
        .withVatValue(BigDecimal.valueOf(23.00))
        .withGrossValue(BigDecimal.valueOf(1230.00))
        .withVatRate(Vat.VAT_23)
        .build();
    List<String> resultOfValidation = InvoiceEntryValidator.validate(invoiceEntry);
    assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> priceArguments() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Price cannot be null")),
        Arguments.of(BigDecimal.valueOf(-55), Collections.singletonList("Price cannot be less than or equal to 0")),
        Arguments.of(BigDecimal.valueOf(0), Collections.singletonList("Price cannot be less than or equal to 0")),
        Arguments.of(BigDecimal.valueOf(30.09), new ArrayList<String>())
    );
  }

  @ParameterizedTest
  @MethodSource("grossValueArguments")
  void shouldValidateGrossValue(BigDecimal grossValue, List<String> expected) {
    InvoiceEntry invoiceEntry = InvoiceEntry.builder()
        .withId(1L)
        .withItem("10w40 Castrol engine oil")
        .withQuantity(10L)
        .withPrice(BigDecimal.valueOf(100.00))
        .withVatValue(BigDecimal.valueOf(23.00))
        .withGrossValue(grossValue)
        .withVatRate(Vat.VAT_23)
        .build();
    List<String> resultOfValidation = InvoiceEntryValidator.validate(invoiceEntry);
    assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> grossValueArguments() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Gross value cannot be null")),
        Arguments.of(BigDecimal.valueOf(-55), Collections.singletonList("Gross value cannot be less than or equal to 0")),
        Arguments.of(BigDecimal.valueOf(0), Collections.singletonList("Gross value cannot be less than or equal to 0")),
        Arguments.of(BigDecimal.valueOf(30.09), new ArrayList<String>())
    );
  }

  @ParameterizedTest
  @MethodSource("vatValueArguments")
  void shouldValidateVatValue(BigDecimal vatValue, List<String> expected) {
    InvoiceEntry invoiceEntry = InvoiceEntry.builder()
        .withId(1L)
        .withItem("10w40 Castrol engine oil")
        .withQuantity(10L)
        .withPrice(BigDecimal.valueOf(100.00))
        .withVatValue(vatValue)
        .withGrossValue(BigDecimal.valueOf(1230.00))
        .withVatRate(Vat.VAT_23)
        .build();
    List<String> resultOfValidation = InvoiceEntryValidator.validate(invoiceEntry);
    assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> vatValueArguments() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Vat value cannot be null")),
        Arguments.of(BigDecimal.valueOf(-55), Collections.singletonList("Vat value cannot be less than or equal to 0")),
        Arguments.of(BigDecimal.valueOf(0), Collections.singletonList("Vat value cannot be less than or equal to 0")),
        Arguments.of(BigDecimal.valueOf(30.09), new ArrayList<String>())
    );
  }

  @Test
  void shouldValidateVatRate() {
    InvoiceEntry invoiceEntry = InvoiceEntry.builder()
        .withId(1L)
        .withItem("10w40 Castrol engine oil")
        .withQuantity(10L)
        .withPrice(BigDecimal.valueOf(100.00))
        .withVatValue(BigDecimal.valueOf(23.00))
        .withGrossValue(BigDecimal.valueOf(1230.00))
        .withVatRate(null)
        .build();
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

  @Test
  void shouldThrowExceptionWhenInvoiceEntryListIsNull1() {
    List<String> resultOfValidation = InvoiceEntryValidator.validate((List<InvoiceEntry>) null);
    List<String> expected = Collections.singletonList("Invoice entries cannot be null");
    assertEquals(expected, resultOfValidation);
  }
}
