package pl.coderstrust.database.nosql.infile;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.coderstrust.configuration.ApplicationConfiguration;
import pl.coderstrust.configuration.InFileInvoiceDatabaseProperties;
import pl.coderstrust.database.DatabaseOperationException;
import pl.coderstrust.database.IdentifierGenerator;
import pl.coderstrust.database.nosql.NoSqlModelMapper;
import pl.coderstrust.database.nosql.NoSqlModelMapperImpl;
import pl.coderstrust.generators.InvoiceGenerator;
import pl.coderstrust.helpers.FileHelper;
import pl.coderstrust.model.Invoice;

@ExtendWith(MockitoExtension.class)
class InFileInvoiceDatabaseTest {

  private static final String DATABASE_FILEPATH = "database.txt";
  private ObjectMapper mapper = new ApplicationConfiguration().getObjectMapper();

  @Mock
  private FileHelper fileHelper;
  private InFileInvoiceDatabase database;
  private NoSqlModelMapper noSqlModelMapper;

  @BeforeEach
  void setup() throws DatabaseOperationException {
    noSqlModelMapper = new NoSqlModelMapperImpl();
    IdentifierGenerator identifierGenerator = new IdentifierGenerator();
    InFileInvoiceDatabaseProperties inFileInvoiceDatabaseProperties = new InFileInvoiceDatabaseProperties();
    inFileInvoiceDatabaseProperties.setFilePath(DATABASE_FILEPATH);
    database = new InFileInvoiceDatabase(mapper, fileHelper, inFileInvoiceDatabaseProperties, identifierGenerator, noSqlModelMapper);
  }

  @Test
  void shouldReturnNumberOfInvoices() throws IOException, DatabaseOperationException {
    //given
    List<String> invoicesInDatabaseFile = new ArrayList<>();
    invoicesInDatabaseFile.add(mapper.writeValueAsString(noSqlModelMapper.mapInvoice(InvoiceGenerator.getRandomInvoice())));
    invoicesInDatabaseFile.add(mapper.writeValueAsString(noSqlModelMapper.mapInvoice(InvoiceGenerator.getRandomInvoice())));
    when(fileHelper.isEmpty(DATABASE_FILEPATH)).thenReturn(false);
    when(fileHelper.readLines(DATABASE_FILEPATH)).thenReturn(invoicesInDatabaseFile);
    long expectedInvoicesCount = invoicesInDatabaseFile.size();

    //when
    long actualInvoicesCount = database.count();

    //then
    assertEquals(expectedInvoicesCount, actualInvoicesCount);
    verify(fileHelper).isEmpty(DATABASE_FILEPATH);
    verify(fileHelper).readLines(DATABASE_FILEPATH);
  }

  @Test
  void shouldReturnZeroWhenDatabaseIsEmpty() throws IOException, DatabaseOperationException {
    //given
    long expected = 0;
    when(fileHelper.isEmpty(DATABASE_FILEPATH)).thenReturn(true);

    //when
    long actualInvoices = database.count();

    //then
    assertEquals(expected, actualInvoices);
    verify(fileHelper).isEmpty(DATABASE_FILEPATH);
    verify(fileHelper, never()).readLines(DATABASE_FILEPATH);
  }

  @Test
  void shouldReturnZeroWhenDatabaseContainsInvalidData() throws IOException, DatabaseOperationException {
    //given
    List<String> invalidInvoices = new ArrayList<>();
    invalidInvoices.add("asdasd");
    long expected = 0;
    when(fileHelper.readLines(DATABASE_FILEPATH)).thenReturn(invalidInvoices);
    when(fileHelper.isEmpty(DATABASE_FILEPATH)).thenReturn(false);

    //when
    long actual = database.count();

    //then
    assertEquals(expected, actual);
    verify(fileHelper).readLines(DATABASE_FILEPATH);
    verify(fileHelper).isEmpty(DATABASE_FILEPATH);
  }

  @Test
  void countMethodShouldThrowExceptionWhenFileHelperThrowException() throws FileNotFoundException {
    //given
    doThrow(FileNotFoundException.class).when(fileHelper).isEmpty(DATABASE_FILEPATH);

    //then
    assertThrows(DatabaseOperationException.class, () -> database.count());
    verify(fileHelper).isEmpty(DATABASE_FILEPATH);
  }

  @Test
  void shouldDeleteInvoice() throws DatabaseOperationException, IOException {
    //given
    Invoice invoiceToDelete = InvoiceGenerator.getRandomInvoice();
    List<String> invoicesOfDatabase = new ArrayList<>();
    invoicesOfDatabase.add(mapper.writeValueAsString(noSqlModelMapper.mapInvoice(InvoiceGenerator.getRandomInvoice())));
    invoicesOfDatabase.add(mapper.writeValueAsString(noSqlModelMapper.mapInvoice(invoiceToDelete)));
    invoicesOfDatabase.add(mapper.writeValueAsString(noSqlModelMapper.mapInvoice(InvoiceGenerator.getRandomInvoice())));
    when(fileHelper.readLines(DATABASE_FILEPATH)).thenReturn(invoicesOfDatabase);
    doNothing().when(fileHelper).removeLine(DATABASE_FILEPATH, 2);

    //when
    database.deleteById(invoiceToDelete.getId());

    //then
    verify(fileHelper).readLines(DATABASE_FILEPATH);
    verify(fileHelper).removeLine(DATABASE_FILEPATH, 2);
  }

  @Test
  void shouldThrowExceptionDuringRemovingInvoiceWhenInvoiceDoesNotExist() throws IOException {
    //given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    List<String> invoicesOfDatabase = new ArrayList<>();
    invoicesOfDatabase.add(mapper.writeValueAsString(invoice));
    when(fileHelper.readLines(DATABASE_FILEPATH)).thenReturn(invoicesOfDatabase);

    //then
    assertThrows(DatabaseOperationException.class, () -> database.deleteById(invoice.getId() + 1));
    verify(fileHelper).readLines(DATABASE_FILEPATH);
    verify(fileHelper, never()).removeLine(anyString(), anyInt());
  }

  @Test
  void deleteByIdMethodShouldThrowExceptionWhenPassedIdIsNull() {
    //then
    assertThrows(IllegalArgumentException.class, () -> database.deleteById(null));
  }

  @Test
  void deleteByIdMethodShouldThrowExceptionWhenFileHelperThrowException() throws IOException {
    //given
    doThrow(FileNotFoundException.class).when(fileHelper).readLines(DATABASE_FILEPATH);

    //then
    assertThrows(DatabaseOperationException.class, () -> database.deleteById(1L));
    verify(fileHelper).readLines(DATABASE_FILEPATH);
  }

  @Test
  void shouldDeleteAllInvoices() throws IOException, DatabaseOperationException {
    //given
    doNothing().when(fileHelper).clear(DATABASE_FILEPATH);

    //when
    database.deleteAll();

    //then
    verify(fileHelper).clear(DATABASE_FILEPATH);
  }

  @Test
  void deleteAllInvoicesShouldThrowExceptionWhenFileHelperThrowException() throws IOException {
    //given
    doThrow(IOException.class).when(fileHelper).clear(DATABASE_FILEPATH);

    //then
    assertThrows(DatabaseOperationException.class, () -> database.deleteAll());
  }

  @Test
  void shouldReturnAllInvoices() throws IOException, DatabaseOperationException {
    //given
    Invoice invoice1 = InvoiceGenerator.getRandomInvoiceWithoutIdInOtherEntities();
    Invoice invoice2 = InvoiceGenerator.getRandomInvoiceWithoutIdInOtherEntities();
    Invoice invoice3 = InvoiceGenerator.getRandomInvoiceWithoutIdInOtherEntities();
    List<Invoice> expectedInvoices = new ArrayList<>();
    expectedInvoices.add(invoice1);
    expectedInvoices.add(invoice2);
    expectedInvoices.add(invoice3);
    List<String> invoicesInDatabaseFile = new ArrayList<>();
    invoicesInDatabaseFile.add(mapper.writeValueAsString(noSqlModelMapper.mapInvoice(invoice1)));
    invoicesInDatabaseFile.add(mapper.writeValueAsString(noSqlModelMapper.mapInvoice(invoice2)));
    invoicesInDatabaseFile.add(mapper.writeValueAsString(noSqlModelMapper.mapInvoice(invoice3)));
    when(fileHelper.readLines(DATABASE_FILEPATH)).thenReturn(invoicesInDatabaseFile);

    //when
    Optional<List<Invoice>> actualInvoices = database.findAll();

    //then
    assertTrue(actualInvoices.isPresent());
    assertEquals(expectedInvoices, actualInvoices.get());
    verify(fileHelper).readLines(DATABASE_FILEPATH);
  }

  @Test
  void shouldReturnEmptyListWhenDatabaseIsEmpty() throws IOException, DatabaseOperationException {
    //given
    List<String> invalidInvoices = new ArrayList<>();
    when(fileHelper.readLines(DATABASE_FILEPATH)).thenReturn(invalidInvoices);

    //when
    Optional<List<Invoice>> actual = database.findAll();

    //then
    assertTrue(actual.isPresent());
    assertEquals(invalidInvoices, actual.get());
    verify(fileHelper).readLines(DATABASE_FILEPATH);
  }

  @Test
  void findAllMethodShouldThrowExceptionWhenFileHelperThrowException() throws IOException {
    //given
    doThrow(FileNotFoundException.class).when(fileHelper).readLines(DATABASE_FILEPATH);

    //then
    assertThrows(DatabaseOperationException.class, () -> database.findAll());
    verify(fileHelper).readLines(DATABASE_FILEPATH);
  }

  @Test
  void shouldReturnTrueWhenInvoiceExists() throws IOException, DatabaseOperationException {
    //given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    List<String> invoicesList = new ArrayList<>();
    invoicesList.add(mapper.writeValueAsString(noSqlModelMapper.mapInvoice(invoice)));
    when(fileHelper.readLines(DATABASE_FILEPATH)).thenReturn(invoicesList);

    //when
    boolean invoiceExist = database.existsById(invoice.getId());

    //then
    assertTrue(invoiceExist);
    verify(fileHelper).readLines(DATABASE_FILEPATH);
  }

  @Test
  void shouldReturnFalseWhenInvoiceDoesNotExist() throws IOException, DatabaseOperationException {
    //given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    List<String> invoicesList = new ArrayList<>();
    invoicesList.add(mapper.writeValueAsString(invoice));
    when(fileHelper.readLines(DATABASE_FILEPATH)).thenReturn(invoicesList);

    //when
    boolean invoiceExist = database.existsById(invoice.getId() + 1);

    //then
    assertFalse(invoiceExist);
    verify(fileHelper).readLines(DATABASE_FILEPATH);
  }

  @Test
  void existByIdMethodShouldThrowExceptionWhenFileHelperThrowException() throws IOException {
    //given
    doThrow(FileNotFoundException.class).when(fileHelper).readLines(DATABASE_FILEPATH);

    //then
    assertThrows(DatabaseOperationException.class, () -> database.existsById(1L));
    verify(fileHelper).readLines(DATABASE_FILEPATH);
  }

  @Test
  void shouldReturnInvoice() throws IOException, DatabaseOperationException {
    //given
    Invoice invoice1 = InvoiceGenerator.getRandomInvoiceWithoutIdInOtherEntities();
    Invoice invoice2 = InvoiceGenerator.getRandomInvoiceWithoutIdInOtherEntities();
    Invoice invoice3 = InvoiceGenerator.getRandomInvoiceWithoutIdInOtherEntities();
    List<String> invoicesInDatabase = new ArrayList<>();
    invoicesInDatabase.add(mapper.writeValueAsString(noSqlModelMapper.mapInvoice(invoice1)));
    invoicesInDatabase.add(mapper.writeValueAsString(noSqlModelMapper.mapInvoice(invoice2)));
    invoicesInDatabase.add(mapper.writeValueAsString(noSqlModelMapper.mapInvoice(invoice3)));
    when(fileHelper.readLines(DATABASE_FILEPATH)).thenReturn(invoicesInDatabase);

    //when
    Optional<Invoice> actualInvoice = database.findById(invoice2.getId());

    //then
    assertTrue(actualInvoice.isPresent());
    assertEquals(invoice2, actualInvoice.get());
    verify(fileHelper).readLines(DATABASE_FILEPATH);
  }

  @Test
  void shouldReturnEmptyOptionalWhenInvoiceDoesNotExist() throws IOException, DatabaseOperationException {
    //given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    List<String> invoicesInDatabase = new ArrayList<>();
    invoicesInDatabase.add(mapper.writeValueAsString(invoice));
    when(fileHelper.readLines(DATABASE_FILEPATH)).thenReturn(invoicesInDatabase);

    //when
    Optional<Invoice> actualInvoice = database.findById(invoice.getId() + 1);

    //then
    assertFalse(actualInvoice.isPresent());
    verify(fileHelper).readLines(DATABASE_FILEPATH);
  }

  @Test
  void findByIdShouldThrowExceptionWhenFileHelperThrowException() throws IOException {
    //then
    doThrow(FileNotFoundException.class).when(fileHelper).readLines(DATABASE_FILEPATH);

    //then
    assertThrows(DatabaseOperationException.class, () -> database.findById(1L));
    verify(fileHelper).readLines(DATABASE_FILEPATH);
  }

  @Test
  void shouldAddInvoice() throws IOException, DatabaseOperationException {
    Invoice invoiceToAdd = InvoiceGenerator.getRandomInvoiceWithoutIdInOtherEntities();
    Invoice expectedInvoice = Invoice.builder()
            .withId(1L)
            .withNumber(invoiceToAdd.getNumber())
            .withIssueDate(invoiceToAdd.getIssueDate())
            .withDueDate(invoiceToAdd.getDueDate())
            .withSeller(invoiceToAdd.getSeller())
            .withBuyer(invoiceToAdd.getBuyer())
            .withEntries(invoiceToAdd.getEntries())
            .withTotalNetValue(invoiceToAdd.getTotalNetValue())
            .withTotalGrossValue(invoiceToAdd.getTotalGrossValue())
            .build();

    Invoice invoiceInDatabase1 = InvoiceGenerator.getRandomInvoiceWithoutIdInOtherEntities();
    Invoice invoiceInDatabase2 = InvoiceGenerator.getRandomInvoiceWithoutIdInOtherEntities();
    List<String> invoicesInDatabase = new ArrayList<>();
    invoicesInDatabase.add(mapper.writeValueAsString(noSqlModelMapper.mapInvoice(invoiceInDatabase1)));
    invoicesInDatabase.add(mapper.writeValueAsString(noSqlModelMapper.mapInvoice(invoiceInDatabase2)));
    String invoiceToAddAsJson = mapper.writeValueAsString(noSqlModelMapper.mapInvoice(expectedInvoice));
    when(fileHelper.readLines(DATABASE_FILEPATH)).thenReturn(invoicesInDatabase);
    doNothing().when(fileHelper).writeLine(DATABASE_FILEPATH, invoiceToAddAsJson);

    //when
    Optional<Invoice> addedInvoice = database.save(invoiceToAdd);

    //then
    assertTrue(addedInvoice.isPresent());
    assertEquals(expectedInvoice, addedInvoice.get());
    verify(fileHelper).readLines(DATABASE_FILEPATH);
    verify(fileHelper).writeLine(DATABASE_FILEPATH, invoiceToAddAsJson);
  }

  @Test
  void shouldUpdateInvoice() throws IOException, DatabaseOperationException {
    //given
    Invoice invoiceInDatabase = InvoiceGenerator.getRandomInvoiceWithoutIdInOtherEntities();
    String invoiceInDatabaseAsJson = mapper.writeValueAsString(noSqlModelMapper.mapInvoice(invoiceInDatabase));
    Invoice invoiceToUpdate = Invoice.builder()
            .withId(invoiceInDatabase.getId())
            .withNumber("3")
            .withIssueDate(invoiceInDatabase.getIssueDate())
            .withDueDate(invoiceInDatabase.getDueDate())
            .withSeller(invoiceInDatabase.getSeller())
            .withBuyer(invoiceInDatabase.getBuyer())
            .withEntries(invoiceInDatabase.getEntries())
            .withTotalNetValue(invoiceInDatabase.getTotalNetValue())
            .withTotalGrossValue(invoiceInDatabase.getTotalGrossValue())
            .build();
    String invoiceToUpdateAsJson = mapper.writeValueAsString(noSqlModelMapper.mapInvoice(invoiceToUpdate));
    when(fileHelper.readLines(DATABASE_FILEPATH)).thenReturn(Collections.singletonList(invoiceInDatabaseAsJson));
    doNothing().when(fileHelper).removeLine(DATABASE_FILEPATH, 1);
    doNothing().when(fileHelper).writeLine(DATABASE_FILEPATH, invoiceToUpdateAsJson);

    //when
    Optional<Invoice> updatedInvoice = database.save(invoiceToUpdate);

    //then
    assertTrue(updatedInvoice.isPresent());
    assertEquals(invoiceToUpdate, updatedInvoice.get());
    verify(fileHelper).removeLine(DATABASE_FILEPATH, 1);
    verify(fileHelper, times(2)).readLines(DATABASE_FILEPATH);
    verify(fileHelper).writeLine(DATABASE_FILEPATH, invoiceToUpdateAsJson);
  }

  @Test
  void saveMethodShouldThrowExceptionWhenPassedIdIsNull() {
    //given
    assertThrows(IllegalArgumentException.class, () -> database.save(null));
  }

  @Test
  void saveMethodShouldThrowExceptionWhenFileHelperThrowException() throws IOException {
    //given
    Invoice invoice = InvoiceGenerator.getRandomInvoice();
    doThrow(FileNotFoundException.class).when(fileHelper).readLines(DATABASE_FILEPATH);

    //then
    assertThrows(DatabaseOperationException.class, () -> database.save(invoice));
  }
}
