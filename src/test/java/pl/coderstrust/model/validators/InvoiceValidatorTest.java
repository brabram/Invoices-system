package pl.coderstrust.model.validators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.coderstrust.generators.InvoiceGenerator;
import pl.coderstrust.model.Invoice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

class InvoiceValidatorTest {
  private Invoice invoice;

  @BeforeEach
  void setup() {
    invoice = InvoiceGenerator.getRandomInvoice();
  }

  @ParameterizedTest
  @MethodSource("invoiceIdArguments")
  void shouldValidateInvoiceId(Long id, List<String> expected) {
    invoice.setId(id);
    List<String> resultOfValidation = InvoiceValidator.validate(invoice);
    assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> invoiceIdArguments() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Id cannot be null")),
        Arguments.of(Long.valueOf(-535), Collections.singletonList("Id cannot be less than or equal to 0")),
        Arguments.of(Long.valueOf(0), Collections.singletonList("Id cannot be less than or equal to 0")),
        Arguments.of(Long.valueOf(30), new ArrayList<String>())
    );
  }

  @ParameterizedTest
  @MethodSource("invoiceNumberArguments")
  void shouldValidateInvoiceNumber(String number, List<String> expected) {
    invoice.setNumber(number);
    List<String> resultOfValidation = InvoiceValidator.validate(invoice);
    assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> invoiceNumberArguments() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Number cannot be null")),
        Arguments.of("", Collections.singletonList("Number cannot be empty")),
        Arguments.of("-erfer", Collections.singletonList("Incorrect number")),
        Arguments.of("535", new ArrayList<String>()),
        Arguments.of("sdf35", new ArrayList<String>()),
        Arguments.of("35fewf", new ArrayList<String>()),
        Arguments.of("Werw34 Fdfd efe43", new ArrayList<String>()),
        Arguments.of("werw34-fdfd-efe43", new ArrayList<String>())
    );
  }

  @ParameterizedTest
  @MethodSource("invoiceDateArguments")
  void shouldValidateInvoiceIssueDate(LocalDate issueDate, LocalDate dueDate, List<String> expected) {
    invoice.setIssueDate(issueDate);
    invoice.setDueDate(dueDate);
    List<String> resultOfValidation = InvoiceValidator.validate(invoice);
    assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> invoiceDateArguments() {
    LocalDate dueDate = InvoiceGenerator.getRandomInvoice().getDueDate();
    LocalDate issueDate = dueDate.plusDays(2);
    return Stream.of(
        Arguments.of(null, dueDate, Collections.singletonList("Issue date cannot be null")),
        Arguments.of(issueDate, null, Collections.singletonList("Due date cannot be null")),
        Arguments.of(issueDate, dueDate, Collections.singletonList("Issue date cannot be after due date")),
        Arguments.of(dueDate.minusDays(3), dueDate, new ArrayList<String>())
    );
  }

  @ParameterizedTest
  @MethodSource("totalNetValueArguments")
  void shouldValidateTotalNetValue(BigDecimal netValue, List<String> expected) {
    invoice.setTotalNetValue(netValue);
    List<String> resultOfValidation = InvoiceValidator.validate(invoice);
    assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> totalNetValueArguments() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Total net value cannot be null")),
        Arguments.of(BigDecimal.valueOf(-55), Collections.singletonList("Total net value cannot be less than or equal to 0")),
        Arguments.of(BigDecimal.valueOf(0), Collections.singletonList("Total net value cannot be less than or equal to 0")),
        Arguments.of(BigDecimal.valueOf(30), new ArrayList<String>())
    );
  }

  @ParameterizedTest
  @MethodSource("totalGrossValueArguments")
  void shouldValidateTotalGrossValue(BigDecimal grossValue, List<String> expected) {
    invoice.setTotalGrossValue(grossValue);
    List<String> resultOfValidation = InvoiceValidator.validate(invoice);
    assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> totalGrossValueArguments() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Total gross value cannot be null")),
        Arguments.of(BigDecimal.valueOf(-55), Collections.singletonList("Total gross value cannot be less than or equal to 0")),
        Arguments.of(BigDecimal.valueOf(0), Collections.singletonList("Total gross value cannot be less than or equal to 0")),
        Arguments.of(BigDecimal.valueOf(30), new ArrayList<String>())
    );
  }

  @Test
  void shouldThrowExceptionWhenInvoiceIsNull() {
    List<String> resultOfValidation = InvoiceValidator.validate(null);
    List<String> expected = Collections.singletonList("Invoice cannot be null");
    assertEquals(expected, resultOfValidation);
  }

  @Test
  void shouldThrowExceptionWhenCompanySellerIsNull() {
    invoice.setSeller(null);
    List<String> expected = Collections.singletonList("Company cannot be null");
    List<String> resultOfValidation = InvoiceValidator.validate(invoice);
    assertEquals(expected, resultOfValidation);
  }

  @Test
  void shouldThrowExceptionWhenCompanyBuyerIsNull() {
    invoice.setBuyer(null);
    List<String> expected = Collections.singletonList("Company cannot be null");
    List<String> resultOfValidation = InvoiceValidator.validate(invoice);
    assertEquals(expected, resultOfValidation);
  }
}
