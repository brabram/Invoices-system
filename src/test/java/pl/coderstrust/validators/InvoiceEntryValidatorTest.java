package pl.coderstrust.validators;

import junit.framework.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.coderstrust.generators.InvoiceEntriesGenerator;
import pl.coderstrust.model.InvoiceEntry;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceEntryValidatorTest {

  private InvoiceEntry invoiceEntry = InvoiceEntriesGenerator.getRandomInvoiceEntry();

  @ParameterizedTest
  @MethodSource("InvoiceEntryIdParameters")
  void shouldNotValidateInvoiceEntryId(String id, List<String> expected) {
    invoiceEntry.setId(id);
    List<String> resultOfValidation = InvoiceEntryValidator.validate(invoiceEntry);
    Assert.assertEquals(resultOfValidation, expected);
  }

  private static Stream<Arguments> InvoiceEntryIdParameters() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Id cannot be null")),
        Arguments.of("", Collections.singletonList("Id cannot be empty")),
        Arguments.of("sdf35", Collections.singletonList("Incorrect id")),
        Arguments.of("35fewf", Collections.singletonList("Incorrect id")),
        Arguments.of("-535", Collections.singletonList("Incorrect id"))
    );
  }

  @ParameterizedTest
  @MethodSource("itemNameParameters")
  void shouldNotValidateItemName(String item, List<String> expected) {
    invoiceEntry.setItem(item);
    List<String> resultOfValidation = InvoiceEntryValidator.validate(invoiceEntry);
    Assert.assertEquals(resultOfValidation, expected);
  }

  private static Stream<Arguments> itemNameParameters() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Item cannot be null")),
        Arguments.of("", Collections.singletonList("Item cannot be empty")),
        Arguments.of("sdf35", Collections.singletonList("Incorrect item")),
        Arguments.of("35fewf", Collections.singletonList("IIncorrect item")),
        Arguments.of("-535", Collections.singletonList("Incorrect item"))
    );
  }

  @ParameterizedTest
  @MethodSource("quantityParameters")
  void shouldNotValidateQuantity(Long quantity, List<String> expected) {
    invoiceEntry.setQuantity(quantity);
    List<String> resultOfValidation = InvoiceEntryValidator.validate(invoiceEntry);
    Assert.assertEquals(resultOfValidation, expected);
  }

  private static Stream<Arguments> quantityParameters() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Quantity cannot be null")),
        Arguments.of("", Collections.singletonList("Quantity cannot be empty")),
        Arguments.of(-535, Collections.singletonList("Incorrect quantity"))
    );
  }

  @ParameterizedTest
  @MethodSource("priceParameters")
  void shouldNotValidatePrice(BigDecimal price, List<String> expected) {
    invoiceEntry.setPrice(price);
    List<String> resultOfValidation = InvoiceEntryValidator.validate(invoiceEntry);
    Assert.assertEquals(resultOfValidation, expected);
  }

  @ParameterizedTest
  @MethodSource("priceParameters")
  void shouldNotValidateGrossValue(BigDecimal grossValue, List<String> expected) {
    invoiceEntry.setGrossValue(grossValue);
    List<String> resultOfValidation = InvoiceEntryValidator.validate(invoiceEntry);
    Assert.assertEquals(resultOfValidation, expected);
  }

  @ParameterizedTest
  @MethodSource("priceParameters")
  void shouldNotValidateVatValue(BigDecimal vatValue, List<String> expected) {
    invoiceEntry.setVatValue(vatValue);
    List<String> resultOfValidation = InvoiceEntryValidator.validate(invoiceEntry);
    Assert.assertEquals(resultOfValidation, expected);
  }

  private static Stream<Arguments> priceParameters() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Price cannot be null")),
        Arguments.of("", Collections.singletonList("Price cannot be empty")),
        Arguments.of("-535", Collections.singletonList("Incorrect price")),
        Arguments.of("0", Collections.singletonList("Incorrect price")),
        Arguments.of("43frefe", Collections.singletonList("Incorrect price"))
    );
  }
}