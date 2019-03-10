package pl.coderstrust.model.validators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.coderstrust.generators.CompanyGenerator;
import pl.coderstrust.generators.InvoiceGenerator;
import pl.coderstrust.model.Invoice;

class InvoiceValidatorTest {

  @ParameterizedTest
  @MethodSource("invoiceIdArguments")
  void shouldValidateInvoiceId(Long id, List<String> expected) {
    Invoice invoice = Invoice.builder()
        .withId(id)
        .withNumber("FV/1234a")
        .withIssueDate(LocalDate.of(2019, 2, 4))
        .withDueDate(LocalDate.of(2019, 2, 18))
        .withSeller(CompanyGenerator.getRandomCompany())
        .withBuyer(CompanyGenerator.getRandomCompany())
        .withEntries(new ArrayList<>())
        .withTotalNetValue(BigDecimal.valueOf(1000.00))
        .withTotalGrossValue(BigDecimal.valueOf(1023.00))
        .build();
    List<String> resultOfValidation = InvoiceValidator.validate(invoice, true);
    assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> invoiceIdArguments() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Id cannot be null")),
        Arguments.of(-535L, Collections.singletonList("Id cannot be less than or equal to 0")),
        Arguments.of(0L, Collections.singletonList("Id cannot be less than or equal to 0")),
        Arguments.of(30L, new ArrayList<String>())
    );
  }

  @Test
  void shouldValidateInvoiceWhenIdNotRequired() {
    Invoice invoice = Invoice.builder()
        .withId(2L)
        .withNumber("FV/1234a")
        .withIssueDate(LocalDate.of(2019, 2, 4))
        .withDueDate(LocalDate.of(2019, 2, 18))
        .withSeller(CompanyGenerator.getRandomCompany())
        .withBuyer(CompanyGenerator.getRandomCompany())
        .withEntries(new ArrayList<>())
        .withTotalNetValue(BigDecimal.valueOf(1000.00))
        .withTotalGrossValue(BigDecimal.valueOf(1023.00))
        .build();
    List<String> resultOfValidation = InvoiceValidator.validate(invoice, false);
    List expected = new ArrayList<String>();
    assertEquals(expected, resultOfValidation);
  }

  @ParameterizedTest
  @MethodSource("invoiceNumberArguments")
  void shouldValidateInvoiceNumber(String number, List<String> expected) {
    Invoice invoice = Invoice.builder()
        .withId(1L)
        .withNumber(number)
        .withIssueDate(LocalDate.of(2019, 2, 4))
        .withDueDate(LocalDate.of(2019, 2, 18))
        .withSeller(CompanyGenerator.getRandomCompany())
        .withBuyer(CompanyGenerator.getRandomCompany())
        .withEntries(new ArrayList<>())
        .withTotalNetValue(BigDecimal.valueOf(1000.00))
        .withTotalGrossValue(BigDecimal.valueOf(1023.00))
        .build();
    List<String> resultOfValidation = InvoiceValidator.validate(invoice, true);
    assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> invoiceNumberArguments() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Number cannot be null")),
        Arguments.of("", Collections.singletonList("Number cannot be empty")),
        Arguments.of("-erfer", Collections.singletonList("Incorrect withNumber")),
        Arguments.of("535", new ArrayList<String>()),
        Arguments.of("sdf35/", new ArrayList<String>()),
        Arguments.of("35fewf", new ArrayList<String>()),
        Arguments.of("Werw34 Fdfd efe43", new ArrayList<String>()),
        Arguments.of("werw34-fdfd-efe43", new ArrayList<String>())
    );
  }

  @ParameterizedTest
  @MethodSource("invoiceDateArguments")
  void shouldValidateInvoiceIssueDate(LocalDate issueDate, LocalDate dueDate, List<String> expected) {
    Invoice invoice = Invoice.builder()
        .withId(1L)
        .withNumber("FV/1234a")
        .withIssueDate(issueDate)
        .withDueDate(dueDate)
        .withSeller(CompanyGenerator.getRandomCompany())
        .withBuyer(CompanyGenerator.getRandomCompany())
        .withEntries(new ArrayList<>())
        .withTotalNetValue(BigDecimal.valueOf(1000.00))
        .withTotalGrossValue(BigDecimal.valueOf(1023.00))
        .build();
    List<String> resultOfValidation = InvoiceValidator.validate(invoice, true);
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
    Invoice invoice = Invoice.builder()
        .withId(1L)
        .withNumber("FV/1234a")
        .withIssueDate(LocalDate.of(2019, 2, 4))
        .withDueDate(LocalDate.of(2019, 2, 18))
        .withSeller(CompanyGenerator.getRandomCompany())
        .withBuyer(CompanyGenerator.getRandomCompany())
        .withEntries(new ArrayList<>())
        .withTotalNetValue(netValue)
        .withTotalGrossValue(BigDecimal.valueOf(1023.00))
        .build();
    List<String> resultOfValidation = InvoiceValidator.validate(invoice, true);
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
    Invoice invoice = Invoice.builder()
        .withId(1L)
        .withNumber("FV/1234a")
        .withIssueDate(LocalDate.of(2019, 2, 4))
        .withDueDate(LocalDate.of(2019, 2, 18))
        .withSeller(CompanyGenerator.getRandomCompany())
        .withBuyer(CompanyGenerator.getRandomCompany())
        .withEntries(new ArrayList<>())
        .withTotalNetValue(BigDecimal.valueOf(1000.00))
        .withTotalGrossValue(grossValue)
        .build();
    List<String> resultOfValidation = InvoiceValidator.validate(invoice, true);
    assertEquals(expected, resultOfValidation);
  }

  private static Stream<Arguments> totalGrossValueArguments() {
    return Stream.of(
        Arguments.of(null, Collections.singletonList("Total gross value cannot be null")),
        Arguments.of(BigDecimal.valueOf(-55), Collections.singletonList("Total gross value cannot be less than or equal to 0")),
        Arguments.of(BigDecimal.valueOf(0), Collections.singletonList("Total gross value cannot be less than or equal to 0")),
        Arguments.of(BigDecimal.valueOf(30.09), new ArrayList<String>())
    );
  }

  @Test
  void shouldThrowExceptionWhenInvoiceIsNull() {
    List<String> resultOfValidation = InvoiceValidator.validate(null, true);
    List<String> expected = Collections.singletonList("Invoice cannot be null");
    assertEquals(expected, resultOfValidation);
  }

  @Test
  void shouldValidateCompanySeller() {
    Invoice invoice = Invoice.builder()
        .withId(1L)
        .withNumber("FV/1234a")
        .withIssueDate(LocalDate.of(2019, 2, 4))
        .withDueDate(LocalDate.of(2019, 2, 18))
        .withSeller(null)
        .withBuyer(CompanyGenerator.getRandomCompany())
        .withEntries(new ArrayList<>())
        .withTotalNetValue(BigDecimal.valueOf(1000.00))
        .withTotalGrossValue(BigDecimal.valueOf(1023.00))
        .build();
    List<String> expected = Collections.singletonList("Company cannot be null");
    List<String> resultOfValidation = InvoiceValidator.validate(invoice, true);
    assertEquals(expected, resultOfValidation);
  }

  @Test
  void shouldValidateCompanyBuyer() {
    Invoice invoice = Invoice.builder()
        .withId(1L)
        .withNumber("FV/1234a")
        .withIssueDate(LocalDate.of(2019, 2, 4))
        .withDueDate(LocalDate.of(2019, 2, 18))
        .withSeller(CompanyGenerator.getRandomCompany())
        .withBuyer(null)
        .withEntries(new ArrayList<>())
        .withTotalNetValue(BigDecimal.valueOf(1000.00))
        .withTotalGrossValue(BigDecimal.valueOf(1023.00))
        .build();
    List<String> expected = Collections.singletonList("Company cannot be null");
    List<String> resultOfValidation = InvoiceValidator.validate(invoice, true);
    assertEquals(expected, resultOfValidation);
  }

  @Test
  void shouldValidateInvoiceEntries() {
    Invoice invoice = Invoice.builder()
        .withId(1L)
        .withNumber("FV/1234a")
        .withIssueDate(LocalDate.of(2019, 2, 4))
        .withDueDate(LocalDate.of(2019, 2, 18))
        .withSeller(CompanyGenerator.getRandomCompany())
        .withBuyer(CompanyGenerator.getRandomCompany())
        .withEntries(null)
        .withTotalNetValue(BigDecimal.valueOf(1000.00))
        .withTotalGrossValue(BigDecimal.valueOf(1023.00))
        .build();
    List<String> expected = Collections.singletonList("Invoice entries cannot be null");
    List<String> resultOfValidation = InvoiceValidator.validate(invoice, true);
    assertEquals(expected, resultOfValidation);
  }
}
