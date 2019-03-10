package pl.coderstrust.database.nosql.mongo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mongodb.MongoException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import pl.coderstrust.configuration.MongoDatabaseConfiguration;
import pl.coderstrust.configuration.MongoDatabaseProperties;
import pl.coderstrust.database.DatabaseOperationException;
import pl.coderstrust.database.IdentifierGenerator;
import pl.coderstrust.database.IdentifierGeneratorException;
import pl.coderstrust.database.InvoiceDatabase;
import pl.coderstrust.database.nosql.NoSqlModelMapper;
import pl.coderstrust.database.nosql.NoSqlModelMapperImpl;
import pl.coderstrust.database.nosql.model.NoSqlInvoice;
import pl.coderstrust.generators.InvoiceGenerator;
import pl.coderstrust.model.Invoice;

@ExtendWith(MockitoExtension.class)
class MongoInvoiceDatabaseTest {

  private InvoiceDatabase invoiceDatabase;
  private NoSqlModelMapper modelMapper;
  private MongoDatabaseProperties mongoDatabaseProperties;

  @Mock
  MongoTemplate mongoTemplate;

  @Mock
  IdentifierGenerator identifierGenerator;

  @BeforeEach
  void setup() {
    mongoDatabaseProperties = new MongoDatabaseProperties();
    mongoDatabaseProperties.setDatabaseName("invoices");
    modelMapper = new NoSqlModelMapperImpl();
    invoiceDatabase = new MongoInvoiceDatabase(mongoTemplate, modelMapper, mongoDatabaseProperties, identifierGenerator);
  }

  @Test
  void shouldAddInvoice() throws DatabaseOperationException, IdentifierGeneratorException {
    //given
    Invoice invoiceToSave = InvoiceGenerator.getRandomInvoiceWithoutIdInOtherEntities();
    NoSqlInvoice noSqlInvoice = modelMapper.mapInvoice(invoiceToSave);
    when(identifierGenerator.getNextId()).thenReturn(invoiceToSave.getId());
    when(mongoTemplate.save(noSqlInvoice, mongoDatabaseProperties.getCollectionName())).thenReturn(noSqlInvoice);

    //when
    Optional<Invoice> actualInvoice = invoiceDatabase.save(invoiceToSave);

    //then
    assertTrue(actualInvoice.isPresent());
    assertEquals(invoiceToSave, actualInvoice.get());
    verify(mongoTemplate).save(noSqlInvoice, mongoDatabaseProperties.getCollectionName());
  }

  @Test
  void shouldUpdateInvoice() throws DatabaseOperationException {
    //given
    Invoice invoiceToUpdate = InvoiceGenerator.getRandomInvoiceWithoutIdInOtherEntities();
    NoSqlInvoice noSqlInvoice = modelMapper.mapInvoice(invoiceToUpdate);
    Query findQuery = new Query();
    findQuery.addCriteria(Criteria.where("id").is(invoiceToUpdate.getId()));
    when(mongoTemplate.findOne(findQuery, NoSqlInvoice.class, mongoDatabaseProperties.getCollectionName())).thenReturn(noSqlInvoice);
    when(mongoTemplate.save(noSqlInvoice, mongoDatabaseProperties.getCollectionName())).thenReturn(noSqlInvoice);

    //when
    Optional<Invoice> actualInvoice = invoiceDatabase.save(invoiceToUpdate);

    //then
    assertTrue(actualInvoice.isPresent());
    assertEquals(invoiceToUpdate, actualInvoice.get());
    verify(mongoTemplate).findOne(findQuery, NoSqlInvoice.class, mongoDatabaseProperties.getCollectionName());
    verify(mongoTemplate).save(noSqlInvoice, mongoDatabaseProperties.getCollectionName());
  }

  @Test
  void shouldFindOneInvoice() throws DatabaseOperationException {
    //given
    Invoice invoice = InvoiceGenerator.getRandomInvoiceWithoutIdInOtherEntities();
    NoSqlInvoice noSqlInvoice = modelMapper.mapInvoice(invoice);
    Long id = invoice.getId();
    Query findQuery = new Query();
    findQuery.addCriteria(Criteria.where("id").is(id));
    when(mongoTemplate.findOne(findQuery, NoSqlInvoice.class, mongoDatabaseProperties.getCollectionName())).thenReturn(noSqlInvoice);

    //When
    Optional<Invoice> actualInvoice = invoiceDatabase.findById(id);

    //Then
    assertTrue(actualInvoice.isPresent());
    assertEquals(invoice, actualInvoice.get());
    verify(mongoTemplate).findOne(findQuery, NoSqlInvoice.class, mongoDatabaseProperties.getCollectionName());
  }

  @Test
  void shouldReturnEmptyInvoiceWhenInvoiceDoesNotExistInDatabase() throws DatabaseOperationException {
    //given
    Long id = 123L;
    Query findQuery = new Query();
    findQuery.addCriteria(Criteria.where("id").is(id));
    when(mongoTemplate.findOne(findQuery, NoSqlInvoice.class, mongoDatabaseProperties.getCollectionName())).thenReturn(null);

    //When
    Optional<Invoice> actualInvoice = invoiceDatabase.findById(id);

    //Then
    assertFalse(actualInvoice.isPresent());
    verify(mongoTemplate).findOne(findQuery, NoSqlInvoice.class, mongoDatabaseProperties.getCollectionName());
  }

  @Test
  void shouldReturnFalseWhenInvoiceDoesNotExistInDatabase() throws DatabaseOperationException {
    //given
    Long id = 123L;
    Query existsQuery = new Query();
    existsQuery.addCriteria(Criteria.where("id").is(id));
    when(mongoTemplate.exists(existsQuery, NoSqlInvoice.class, mongoDatabaseProperties.getCollectionName())).thenReturn(false);

    //When
    boolean actualInvoice = invoiceDatabase.existsById(id);

    //Then
    assertFalse(actualInvoice);
    verify(mongoTemplate).exists(existsQuery, NoSqlInvoice.class, mongoDatabaseProperties.getCollectionName());
  }

  @Test
  void shouldReturnTrueWhenInvoiceExistInDatabase() throws DatabaseOperationException {
    //given
    Long id = 123L;
    Query existsQuery = new Query();
    existsQuery.addCriteria(Criteria.where("id").is(id));
    when(mongoTemplate.exists(existsQuery, NoSqlInvoice.class, mongoDatabaseProperties.getCollectionName())).thenReturn(true);

    //When
    boolean actualInvoice = invoiceDatabase.existsById(id);

    //Then
    assertTrue(actualInvoice);
    verify(mongoTemplate).exists(existsQuery, NoSqlInvoice.class, mongoDatabaseProperties.getCollectionName());
  }

  @Test
  void shouldFindAllInvoices() throws DatabaseOperationException {
    //given
    List<NoSqlInvoice> noSqlInvoices = new ArrayList<>();
    noSqlInvoices.add(modelMapper.mapInvoice(InvoiceGenerator.getRandomInvoiceWithoutIdInOtherEntities()));
    noSqlInvoices.add(modelMapper.mapInvoice(InvoiceGenerator.getRandomInvoiceWithoutIdInOtherEntities()));
    when(mongoTemplate.findAll(NoSqlInvoice.class, mongoDatabaseProperties.getCollectionName())).thenReturn(noSqlInvoices);
    List<Invoice> expectedInvoices = noSqlInvoices
        .stream()
        .map(invoice -> modelMapper.mapInvoice(invoice))
        .collect(Collectors.toList());

    //When
    Optional<List<Invoice>> actualInvoices = invoiceDatabase.findAll();

    //Then
    assertTrue(actualInvoices.isPresent());
    assertEquals(expectedInvoices, actualInvoices.get());
    verify(mongoTemplate).findAll(NoSqlInvoice.class, mongoDatabaseProperties.getCollectionName());
  }

  @Test
  void shouldReturnNumberOfInvoices() throws DatabaseOperationException {
    //given
    long numberOfInvoices = 3L;
    when(mongoTemplate.count(new Query(), NoSqlInvoice.class, mongoDatabaseProperties.getCollectionName())).thenReturn(numberOfInvoices);

    //when
    long actualNumberOfInvoices = invoiceDatabase.count();

    //then
    Assert.assertEquals(numberOfInvoices, actualNumberOfInvoices);
    verify(mongoTemplate).count(new Query(), NoSqlInvoice.class, mongoDatabaseProperties.getCollectionName());
  }

  @Test
  void shouldDeleteById() throws DatabaseOperationException {
    //given
    Invoice invoice = InvoiceGenerator.getRandomInvoiceWithoutIdInOtherEntities();
    NoSqlInvoice noSqlInvoice = modelMapper.mapInvoice(invoice);
    Long id = invoice.getId();
    when(mongoTemplate.findAndRemove(
        Query.query(Criteria.where("id").is(id)),
        NoSqlInvoice.class,
        mongoDatabaseProperties.getCollectionName())).thenReturn(noSqlInvoice);

    //when
    invoiceDatabase.deleteById(id);

    //then
    verify(mongoTemplate).findAndRemove(Query.query(Criteria.where("id").is(id)), NoSqlInvoice.class, mongoDatabaseProperties.getCollectionName());
  }

  @Test
  void shouldDeleteAllInvoices() throws DatabaseOperationException {
    //given
    doNothing().when(mongoTemplate).dropCollection(mongoDatabaseProperties.getCollectionName());

    //when
    invoiceDatabase.deleteAll();

    //then
    verify(mongoTemplate).dropCollection(mongoDatabaseProperties.getCollectionName());
  }

  @Test
  void saveInvoiceMethodShouldThrowExceptionWhenInvoiceIsNull() {
    assertThrows(IllegalArgumentException.class, () -> invoiceDatabase.save(null));
  }

  @Test
  void findByIdMethodShouldThrowExceptionWhenIdIsNull() {
    assertThrows(IllegalArgumentException.class, () -> invoiceDatabase.findById(null));
  }

  @Test
  void existByIdMethodShouldThrowExceptionWhenIdIsNull() {
    assertThrows(IllegalArgumentException.class, () -> invoiceDatabase.existsById(null));
  }

  @Test
  void deleteByIdMethodShouldThrowExceptionWhenIdIsNull() {
    assertThrows(IllegalArgumentException.class, () -> invoiceDatabase.deleteById(null));
  }

  @Test
  void saveInvoiceMethodShouldThrowExceptionWhenAnErrorOccurDuringExecution() {
    //Given
    Invoice invoice = InvoiceGenerator.getRandomInvoiceWithoutIdInOtherEntities();
    NoSqlInvoice noSqlInvoice = modelMapper.mapInvoice(invoice);
    doThrow(OptimisticLockingFailureException.class).when(mongoTemplate).save(noSqlInvoice);

    //Then
    assertThrows(DatabaseOperationException.class, () -> invoiceDatabase.save(invoice));
    verify(mongoTemplate, never()).save(noSqlInvoice);
  }

  @Test
  void deleteInvoiceByIdMethodShouldThrowExceptionWhenAnErrorOccurDuringExecution() {
    //Given
    Long id = 123L;
    when(mongoTemplate.findAndRemove(
        Query.query(Criteria.where("id").is(id)),
        NoSqlInvoice.class,
        mongoDatabaseProperties.getCollectionName())).thenReturn(null);

    //Then
    assertThrows(DatabaseOperationException.class, () -> invoiceDatabase.deleteById(id));
    verify(mongoTemplate).findAndRemove(Query.query(Criteria.where("id").is(id)), NoSqlInvoice.class, mongoDatabaseProperties.getCollectionName());
  }

  @Test
  void deleteAllMethodShouldThrowExceptionWhenAnErrorOccurDuringExecution() {
    //Given
    doThrow(MongoException.class).when(mongoTemplate).dropCollection(mongoDatabaseProperties.getCollectionName());

    //Then
    assertThrows(DatabaseOperationException.class, () -> invoiceDatabase.deleteAll());
    verify(mongoTemplate).dropCollection(mongoDatabaseProperties.getCollectionName());
  }

  @Test
  void countMethodShouldThrowExceptionWhenAnErrorOccurDuringExecution() {
    //Given
    doThrow(MongoException.class).when(mongoTemplate).count(new Query(), NoSqlInvoice.class, mongoDatabaseProperties.getCollectionName());

    //Then
    assertThrows(DatabaseOperationException.class, () -> invoiceDatabase.count());
    verify(mongoTemplate).count(new Query(), NoSqlInvoice.class, mongoDatabaseProperties.getCollectionName());
  }

  @Test
  void findInvoiceByIdMethodShouldThrowExceptionWhenAnErrorOccurDuringExecution() {
    //Given
    Invoice invoice = InvoiceGenerator.getRandomInvoiceWithoutIdInOtherEntities();
    Long id = invoice.getId();
    Query findQuery = new Query();
    findQuery.addCriteria(Criteria.where("id").is(id));
    doThrow(MongoException.class).when(mongoTemplate).findOne(findQuery, NoSqlInvoice.class, mongoDatabaseProperties.getCollectionName());

    //Then
    assertThrows(DatabaseOperationException.class, () -> invoiceDatabase.findById(id));
    verify(mongoTemplate).findOne(findQuery, NoSqlInvoice.class, mongoDatabaseProperties.getCollectionName());
  }

  @Test
  void existInvoiceByIdMethodShouldThrowExceptionWhenAnErrorOccurDuringExecution() {
    //Given
    Long id = 123L;
    Query existsQuery = new Query();
    existsQuery.addCriteria(Criteria.where("id").is(id));
    doThrow(MongoException.class).when(mongoTemplate).exists(existsQuery, NoSqlInvoice.class, mongoDatabaseProperties.getCollectionName());

    //Then
    assertThrows(DatabaseOperationException.class, () -> invoiceDatabase.existsById(id));
    verify(mongoTemplate).exists(existsQuery, NoSqlInvoice.class, mongoDatabaseProperties.getCollectionName());
  }

  @Test
  void findAllInvoiceMethodShouldThrowExceptionWhenAnErrorOccurDuringExecution() {
    //Given
    doThrow(MongoException.class).when(mongoTemplate).findAll(NoSqlInvoice.class, mongoDatabaseProperties.getCollectionName());

    //Then
    assertThrows(DatabaseOperationException.class, () -> invoiceDatabase.findAll());
    verify(mongoTemplate).findAll(NoSqlInvoice.class, mongoDatabaseProperties.getCollectionName());
  }
}
