package pl.coderstrust.database.nosql.inFileInvoiceDatabase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import pl.coderstrust.configuration.ApplicationConfiguration;
import pl.coderstrust.configuration.InFileInvoiceDatabaseProperties;
import pl.coderstrust.database.DatabaseOperationException;
import pl.coderstrust.database.IdentifierGenerator;
import pl.coderstrust.database.IdentifierGeneratorException;
import pl.coderstrust.database.InvoiceDatabase;
import pl.coderstrust.database.nosql.NoSqlModelMapper;
import pl.coderstrust.database.nosql.model.NoSqlInvoice;
import pl.coderstrust.helpers.FileHelper;
import pl.coderstrust.model.Invoice;

@ConditionalOnProperty(name = "pl.coderstrust.database", havingValue = "in-file")
@Repository
public class InFileInvoiceDatabase implements InvoiceDatabase {

  private static Logger log = LoggerFactory.getLogger(InFileInvoiceDatabase.class);
  private ObjectMapper mapper;
  private FileHelper fileHelper;
  private InFileInvoiceDatabaseProperties databaseFilePath;
  private final IdentifierGenerator identifierGenerator;
  private final NoSqlModelMapper noSqlModelMapper;

  @Autowired
  public InFileInvoiceDatabase(ObjectMapper mapper
      , FileHelper fileHelper
      , InFileInvoiceDatabaseProperties databaseFilePath
      , IdentifierGenerator identifierGenerator
      , NoSqlModelMapper noSqlModelMapper) throws DatabaseOperationException {
    if (mapper == null) {
      throw new IllegalArgumentException("mapper cannot be null");
    }
    if (fileHelper == null) {
      throw new IllegalArgumentException("fileHelper cannot be null");
    }
    if (databaseFilePath == null) {
      throw new IllegalArgumentException("databaseFilePath cannot be null");
    }
    if (identifierGenerator == null) {
      throw new IllegalArgumentException("identifierGenerator cannot be null");
    }
    if (noSqlModelMapper == null) {
      throw new IllegalArgumentException("noSqlModelMapper cannot be null");
    }
    this.mapper = new ApplicationConfiguration().getObjectMapper();
    this.fileHelper = fileHelper;
    this.databaseFilePath = databaseFilePath;
    this.identifierGenerator = identifierGenerator;
    this.noSqlModelMapper = noSqlModelMapper;
    try {
      if (!fileHelper.exists(databaseFilePath.getFilePath())) {
        fileHelper.create(databaseFilePath.getFilePath());
      }
      this.identifierGenerator.initalize(getLastInvoiceId());
    } catch (IOException e) {
      throw new DatabaseOperationException("An error occurred during trying to build database file", e);
    }
  }

  @Override
  public synchronized Optional<Invoice> save(Invoice invoice) throws DatabaseOperationException {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice cannot be null");
    }
    try {
      if (invoice.getId() != null && isInvoiceExist(invoice.getId())) {
        return Optional.of(updateInvoice(invoice));
      }
      return Optional.of(addInvoice(invoice));
    } catch (IOException | IdentifierGeneratorException e) {
      throw new DatabaseOperationException("An error occurred during saving invoice", e);
    }
  }

  @Override
  public synchronized Optional<Invoice> findById(Long id) throws DatabaseOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    try {
      Optional<NoSqlInvoice> optionalInvoice = getAllInvoices().stream()
          .filter(Objects::nonNull)
          .filter(invoice -> invoice.getId().equals(id))
          .findFirst();
      if (optionalInvoice.isPresent()) {
        return Optional.of(noSqlModelMapper.mapInvoice(optionalInvoice.get()));
      }
      return Optional.empty();
    } catch (IOException e) {
      throw new DatabaseOperationException("An error occurred during getting invoice", e);
    }
  }

  @Override
  public synchronized boolean existsById(Long id) throws DatabaseOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    try {
      return isInvoiceExist(id);
    } catch (IOException e) {
      throw new DatabaseOperationException("An error occurred during looking for invoice", e);
    }
  }

  @Override
  public synchronized Optional<List<Invoice>> findAll() throws DatabaseOperationException {
    try {
      return Optional.of(noSqlModelMapper.mapInvoices(getAllInvoices()));
    } catch (IOException e) {
      throw new DatabaseOperationException("An error occurred during getting all invoices", e);
    }
  }

  @Override
  public synchronized long count() throws DatabaseOperationException {
    try {
      if (fileHelper.isEmpty(databaseFilePath.getFilePath())) {
        return 0;
      }
      return getAllInvoices().size();
    } catch (IOException e) {
      throw new DatabaseOperationException("An error occurred during counting invoices", e);
    }
  }

  @Override
  public synchronized void deleteById(Long id) throws DatabaseOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    try {
      deleteInvoice(id);
    } catch (IOException e) {
      throw new DatabaseOperationException("Ar error occurred during deleting id", e);
    }
  }

  @Override
  public synchronized void deleteAll() throws DatabaseOperationException {
    try {
      fileHelper.clear(databaseFilePath.getFilePath());
    } catch (IOException e) {
      throw new DatabaseOperationException("Ar error occurred during deleting all id", e);
    }
  }

  private NoSqlInvoice deserializeJsonToInvoice(String json) {
    try {
      return mapper.readValue(json, NoSqlInvoice.class);
    } catch (Exception e) {
      return null;
    }
  }

  private String serializeInvoiceToJson(NoSqlInvoice invoice) throws JsonProcessingException {
    return mapper.writeValueAsString(invoice);
  }

  private List<NoSqlInvoice> getAllInvoices() throws IOException {
    return fileHelper.readLines(databaseFilePath.getFilePath()).stream()
        .map(this::deserializeJsonToInvoice)
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  private boolean isInvoiceExist(long id) throws IOException {
    return getAllInvoices().stream()
        .filter(Objects::nonNull)
        .anyMatch(invoice -> invoice.getId().equals(id));
  }

  private void deleteInvoice(long id) throws IOException, DatabaseOperationException {
    List<NoSqlInvoice> invoices = getAllInvoices();
    Optional<NoSqlInvoice> invoice = invoices.stream()
        .filter(Objects::nonNull)
        .filter(i -> i.getId().equals(id))
        .findFirst();
    if (invoice.isPresent()) {
      fileHelper.removeLine(databaseFilePath.getFilePath(), invoices.indexOf(invoice.get()) + 1);
    } else {
      throw new DatabaseOperationException(String.format("There was no invoice in database by id: %s", id));
    }
  }

  private long getLastInvoiceId() throws IOException {
    String lastInvoiceAsJson = fileHelper.readLastLine(databaseFilePath.getFilePath());
    if (lastInvoiceAsJson == null) {
      return 0;
    }
    NoSqlInvoice invoice = deserializeJsonToInvoice(lastInvoiceAsJson);
    if (invoice == null) {
      return 0;
    }
    return invoice.getId();
  }

  private Invoice addInvoice(Invoice invoice) throws IOException, IdentifierGeneratorException {
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

    String invoiceAsJson = serializeInvoiceToJson(invoiceToAdd);
    fileHelper.writeLine(databaseFilePath.getFilePath(), invoiceAsJson);
    return noSqlModelMapper.mapInvoice(invoiceToAdd);
  }

  private Invoice updateInvoice(Invoice invoice) throws IOException, DatabaseOperationException {
    NoSqlInvoice mappedInvoice = noSqlModelMapper.mapInvoice(invoice);
    NoSqlInvoice invoiceToUpdate = NoSqlInvoice.builder()
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

    deleteInvoice(invoiceToUpdate.getId());
    String invoiceAsJson = serializeInvoiceToJson(invoiceToUpdate);
    fileHelper.writeLine(databaseFilePath.getFilePath(), invoiceAsJson);
    return noSqlModelMapper.mapInvoice(invoiceToUpdate);
  }
}
