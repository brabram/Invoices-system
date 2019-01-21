package pl.coderstrust.validators;

import junit.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.coderstrust.generators.InvoiceGenerator;
import pl.coderstrust.model.Invoice;

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
  @MethodSource("invoiceIdParameters")
  void shouldNotValidateInvoiceId(long id, List<String> expected) {
    invoice.setId(id);
    List<String> resultOfValidation = InvoiceValidator.validate(invoice);
    Assert.assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> invoiceIdParameters() {
    return Stream.of(
        Arguments.of(-535, Collections.singletonList("Id cannot be less than 0"))
    );
  }

  @ParameterizedTest
  @MethodSource("invoiceNumberParameters")
  void shouldNotValidateInvoiceNumber(String number, List<String> expected) {
    invoice.setNumber(number);
    List<String> resultOfValidation = InvoiceValidator.validate(invoice);
    Assert.assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> invoiceNumberParameters() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Number cannot be null")),
        Arguments.of("", Collections.singletonList("Number cannot be empty")),
        Arguments.of("sdf35", Collections.singletonList("Incorrect number")),
        Arguments.of("35fewf", Collections.singletonList("Incorrect number")),
        Arguments.of("-535", Collections.singletonList("Incorrect number"))
    );
  }

  @ParameterizedTest
  @MethodSource("invoiceDateParameters")
  void shouldNotValidateInvoiceIssueDate(LocalDate issueDate, LocalDate dueDate, List<String> expected) {
    invoice.setIssueDate(issueDate);
    invoice.setDueDate(dueDate);
    List<String> resultOfValidation = InvoiceValidator.validate(invoice);
    Assert.assertEquals(expected, resultOfValidation);

  }

  private static Stream<Arguments> invoiceDateParameters() {
    LocalDate date = InvoiceGenerator.getRandomInvoice().getIssueDate();
    LocalDate dueDate = InvoiceGenerator.getRandomInvoice().getIssueDate();
    LocalDate issueDate = dueDate.plusDays(2);
    return Stream.of(
        Arguments.of(null, date, Collections.singletonList("Issue date cannot be null")),
        Arguments.of(date, null, Collections.singletonList("Due date cannot be null")),
        Arguments.of(dueDate, issueDate, Collections.singletonList("Issue date cannot be after due date"))
    );
  }

  @ParameterizedTest
  @MethodSource("totalNetValueParameters")
  void shouldNotValidateTotalNetValue(BigDecimal netValue, List<String> expected) {
    invoice.setTotalNetValue(netValue);
    List<String> resultOfValidation = InvoiceValidator.validate(invoice);
    Assert.assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> totalNetValueParameters() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Total net value cannot be null")),
        Arguments.of(BigDecimal.valueOf(-55), Collections.singletonList("Total net value cannot be less than 0")),
        Arguments.of(BigDecimal.valueOf(0), Collections.singletonList("Total net value cannot be equal to 0"))
    );
  }

  @ParameterizedTest
  @MethodSource("totalGrossValueParameters")
  void shouldNotValidateTotalGrossValue(BigDecimal grossValue, List<String> expected) {
    invoice.setTotalGrossValue(grossValue);
    List<String> resultOfValidation = InvoiceValidator.validate(invoice);
    Assert.assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> totalGrossValueParameters() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Total gross value cannot be null")),
        Arguments.of(BigDecimal.valueOf(-55), Collections.singletonList("Total gross value cannot be less than 0")),
        Arguments.of(BigDecimal.valueOf(0), Collections.singletonList("Total gross value cannot be equal to 0"))
    );
  }

  @Test
  void shouldApproveContactDetails() {
    List<String> resultOfValidation = InvoiceValidator.validate(invoice);
    List expected = new ArrayList<String>();
    Assert.assertEquals(expected, resultOfValidation);
  }
}