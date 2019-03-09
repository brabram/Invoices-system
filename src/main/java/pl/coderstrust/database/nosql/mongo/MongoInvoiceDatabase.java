package pl.coderstrust.database.nosql.mongo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import pl.coderstrust.configuration.MongoDatabaseProperties;
import pl.coderstrust.database.DatabaseOperationException;
import pl.coderstrust.database.IdentifierGenerator;
import pl.coderstrust.database.IdentifierGeneratorException;
import pl.coderstrust.database.InvoiceDatabase;
import pl.coderstrust.database.nosql.NoSqlModelMapper;
import pl.coderstrust.database.nosql.model.NoSqlInvoice;
import pl.coderstrust.model.Invoice;


@ConditionalOnProperty(name = "pl.coderstrust.database", havingValue = "mongo")
@Repository
public class MongoInvoiceDatabase implements InvoiceDatabase {

  private static Logger log = LoggerFactory.getLogger(MongoInvoiceDatabase.class);
  private final IdentifierGenerator identifierGenerator;
  private final MongoDatabaseProperties properties;
  private MongoTemplate mongoTemplate;
  private final NoSqlModelMapper noSqlModelMapper;

  @Autowired
  public MongoInvoiceDatabase(MongoTemplate mongoTemplate,
      NoSqlModelMapper noSqlModelMapper,
      MongoDatabaseProperties properties,
      IdentifierGenerator identifierGenerator) {
    if (mongoTemplate == null) {
      throw new IllegalArgumentException("mongoTemplate cannot be null");
    }
    if (noSqlModelMapper == null) {
      throw new IllegalArgumentException("noSqlModelMapper cannot be null");
    }
    if (properties == null) {
      throw new IllegalArgumentException("properties cannot be null");
    }
    if (identifierGenerator == null) {
      throw new IllegalArgumentException("identifierGenerator cannot be null");
    }
    this.mongoTemplate = mongoTemplate;
    this.noSqlModelMapper = noSqlModelMapper;
    this.properties = properties;
    this.identifierGenerator = identifierGenerator;
    this.identifierGenerator.initialize(getLastInvoiceId());
  }

  @Override
  public Optional<Invoice> save(Invoice invoice) throws DatabaseOperationException {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice cannot be null");
    }
    try {
      log.debug("Saving invoice: {}", invoice);
      NoSqlInvoice invoiceInDatabase = getInvoice(invoice.getId());
      if (invoiceInDatabase == null) {
        return Optional.of(addInvoice(invoice));
      }
      return Optional.of(updateInvoice(invoice, invoiceInDatabase.getMongoId()));
    } catch (Exception e) {
      String message = String.format("An error while saving invoice: %s", invoice);
      log.error(message, e);
      throw new DatabaseOperationException(message, e);
    }
  }

  @Override
  public Optional<Invoice> findById(Long id) throws DatabaseOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    try {
      log.debug("Getting invoice by id: {} ", id);
      NoSqlInvoice invoice = getInvoice(id);
      if (invoice != null) {
        return Optional.of(noSqlModelMapper.mapInvoice(invoice));
      }
      return Optional.empty();
    } catch (Exception e) {
      String message = String.format("An error while getting invoice by id: %s", id);
      log.error(message, e);
      throw new DatabaseOperationException(message, e);
    }
  }

  @Override
  public boolean existsById(Long id) throws DatabaseOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    try {
      log.debug("Checking if invoice exists by id: {}", id);
      return isInvoiceExist(id);
    } catch (Exception e) {
      String message = String.format("An error while checking if invoice exist by id: %s", id);
      log.error(message, e);
      throw new DatabaseOperationException(message, e);
    }
  }

  @Override
  public Optional<List<Invoice>> findAll() throws DatabaseOperationException {
    try {
      log.debug("Getting all invoices.");
      List<Invoice> invoices = mongoTemplate.findAll(NoSqlInvoice.class, properties.getCollectionName())
          .stream()
          .map(invoice -> noSqlModelMapper.mapInvoice(invoice))
          .collect(Collectors.toList());
      return Optional.of(invoices);
    } catch (Exception e) {
      String message = "An error while getting all invoices.";
      log.error(message, e);
      throw new DatabaseOperationException(message, e);
    }
  }

  @Override
  public long count() throws DatabaseOperationException {
    try {
      log.debug("Getting number of invoices.");
      return mongoTemplate.count(new Query(), NoSqlInvoice.class, properties.getCollectionName());
    } catch (Exception e) {
      String message = "An error while getting number of invoices.";
      log.error(message, e);
      throw new DatabaseOperationException(message, e);
    }
  }

  @Override
  public void deleteById(Long id) throws DatabaseOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    try {
      log.debug("Deleting invoice by id: {}", id);
      NoSqlInvoice removedInvoice = mongoTemplate.findAndRemove(Query.query(Criteria.where("id").is(id)), NoSqlInvoice.class, properties.getCollectionName());
      if (removedInvoice == null) {
        throw new DatabaseOperationException(String.format("There was no invoice in database by id: %d", id));
      }
    } catch (Exception e) {
      String message = String.format("There was no invoice in database by id: %s", id);
      log.error(message, e);
      throw new DatabaseOperationException(message, e);
    }
  }

  @Override
  public void deleteAll() throws DatabaseOperationException {
    try {
      log.debug("Removing all invoices.");
      mongoTemplate.dropCollection(properties.getCollectionName());
    } catch (Exception e) {
      String message = "An error while removing  all invoices.";
      log.error(message, e);
      throw new DatabaseOperationException(message, e);
    }
  }

  private boolean isInvoiceExist(Long id) {
    log.info("Checking if invoice exists by id: {}", id);
    Query existsQuery = new Query();
    existsQuery.addCriteria(Criteria.where("id").is(id));
    return mongoTemplate.exists(existsQuery, NoSqlInvoice.class, properties.getCollectionName());
  }

  private long getLastInvoiceId() {
    Query query = new Query();
    query.with(new Sort(Sort.Direction.DESC, "id"));
    query.limit(1);
    NoSqlInvoice invoice = mongoTemplate.findOne(query, NoSqlInvoice.class, properties.getCollectionName());
    if (invoice == null) {
      return 0;
    }
    return invoice.getId();
  }

  private NoSqlInvoice getInvoice(Long id) {
    Query findQuery = new Query();
    findQuery.addCriteria(Criteria.where("id").is(id));
    return mongoTemplate.findOne(findQuery, NoSqlInvoice.class, properties.getCollectionName());
  }

  private Invoice addInvoice(Invoice invoice) throws IdentifierGeneratorException {
    NoSqlInvoice mappedInvoice = noSqlModelMapper.mapInvoice(invoice);
    NoSqlInvoice invoiceToAdd = NoSqlInvoice.builder()
        .withId(identifierGenerator.getNextId())
        .withNumber(mappedInvoice.getNumber())
        .withBuyer(mappedInvoice.getBuyer())
        .withSeller(mappedInvoice.getSeller())
        .withIssueDate(mappedInvoice.getIssueDate())
        .withDueDate(mappedInvoice.getDueDate())
        .withTotalNetValue(mappedInvoice.getTotalNetValue())
        .withTotalGrossValue(mappedInvoice.getTotalGrossValue())
        .withEntries(mappedInvoice.getEntries())
        .build();
    NoSqlInvoice addedInvoice = mongoTemplate.save(invoiceToAdd, properties.getCollectionName());
    return noSqlModelMapper.mapInvoice(addedInvoice);
  }

  private Invoice updateInvoice(Invoice invoice, String mongoId) {
    NoSqlInvoice mappedInvoice = noSqlModelMapper.mapInvoice(invoice);
    NoSqlInvoice invoiceToUpdate = NoSqlInvoice.builder()
        .withMongoId(mongoId)
        .withId(mappedInvoice.getId())
        .withNumber(mappedInvoice.getNumber())
        .withBuyer(mappedInvoice.getBuyer())
        .withSeller(mappedInvoice.getSeller())
        .withIssueDate(mappedInvoice.getIssueDate())
        .withDueDate(mappedInvoice.getDueDate())
        .withTotalNetValue(mappedInvoice.getTotalNetValue())
        .withTotalGrossValue(mappedInvoice.getTotalGrossValue())
        .withEntries(mappedInvoice.getEntries())
        .build();
    return noSqlModelMapper.mapInvoice(mongoTemplate.save(invoiceToUpdate, properties.getCollectionName()));
  }
}
