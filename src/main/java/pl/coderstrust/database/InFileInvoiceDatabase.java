package pl.coderstrust.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import pl.coderstrust.configuration.ApplicationConfiguration;
import pl.coderstrust.helpers.FileHelper;
import pl.coderstrust.model.Invoice;

@ConditionalOnProperty(name = "pl.coderstrust.database", havingValue = "in-file")
@Repository
public class InFileInvoiceDatabase implements InvoiceDatabase {
  private ObjectMapper mapper;
  private FileHelper fileHelper;
  private String databaseFilePath;
  private long lastInvoiceId;
  private final Object lock = new Object();

  @Autowired
  public InFileInvoiceDatabase(ObjectMapper mapper, FileHelper fileHelper, String databaseFilePath) throws DatabaseOperationException {
    if (mapper == null) {
      throw new IllegalArgumentException("Mapper cannot be null");
    }
    if (fileHelper == null) {
      throw new IllegalArgumentException("FileHelper cannot be null");
    }
    if (databaseFilePath == null) {
      throw new IllegalArgumentException("DatabaseFilePath cannot be null");
    }
    this.mapper = new ApplicationConfiguration().getObjectMapper();
    this.fileHelper = fileHelper;
    this.databaseFilePath = databaseFilePath;
    try {
      if (!fileHelper.exists(databaseFilePath)) {
        fileHelper.create(databaseFilePath);
      }
      this.lastInvoiceId = getLastInvoiceId();
    } catch (IOException e) {
      throw new DatabaseOperationException("An error occurred during trying to create database file", e);
    }
  }

  @Override
  public Optional<Invoice> save(Invoice invoice) throws DatabaseOperationException {
    synchronized (lock) {
      if (invoice == null) {
        throw new IllegalArgumentException("Invoice cannot be null");
      }
      try {
        Invoice invoiceToAddOrUpdate = new Invoice(invoice);
        if (invoiceToAddOrUpdate.getId() != null && isInvoiceExist(invoiceToAddOrUpdate.getId())) {
          deleteInvoice(invoiceToAddOrUpdate.getId());
        } else {
          invoiceToAddOrUpdate.setId(getNextInvoiceId());
        }
        String invoiceAsJson = serializeInvoiceToJson(invoiceToAddOrUpdate);
        fileHelper.writeLine(databaseFilePath, invoiceAsJson);
        return Optional.of(invoiceToAddOrUpdate);
      } catch (IOException e) {
        throw new DatabaseOperationException("An error occurred during saving invoice", e);
      }
    }
  }

  @Override
  public Optional<Invoice> findById(Long id) throws DatabaseOperationException {
    synchronized (lock) {
      if (id == null) {
        throw new IllegalArgumentException("Id cannot be null");
      }
      try {
        return getAllInvoices().stream()
            .filter(Objects::nonNull)
            .filter(invoice -> invoice.getId().equals(id))
            .findFirst();
      } catch (IOException e) {
        throw new DatabaseOperationException("An error occurred during getting invoice", e);
      }
    }
  }

  @Override
  public boolean existsById(Long id) throws DatabaseOperationException {
    synchronized (lock) {
      if (id == null) {
        throw new IllegalArgumentException("Id cannot be null");
      }
      try {
        return isInvoiceExist(id);
      } catch (IOException e) {
        throw new DatabaseOperationException("An error occurred during looking for invoice", e);
      }
    }
  }

  @Override
  public Optional<List<Invoice>> findAll() throws DatabaseOperationException {
    synchronized (lock) {
      try {
        return Optional.of(getAllInvoices());
      } catch (IOException e) {
        throw new DatabaseOperationException("An error occurred during getting all invoices", e);
      }
    }
  }

  @Override
  public long count() throws DatabaseOperationException {
    synchronized (lock) {
      try {
        if (fileHelper.isEmpty(databaseFilePath)) {
          return 0;
        }
        return getAllInvoices().size();
      } catch (IOException e) {
        throw new DatabaseOperationException("An error occurred during counting invoices", e);
      }
    }
  }

  @Override
  public void deleteById(Long id) throws DatabaseOperationException {
    synchronized (lock) {
      if (id == null) {
        throw new IllegalArgumentException("Id cannot be null");
      }
      try {
        deleteInvoice(id);
      } catch (IOException e) {
        throw new DatabaseOperationException("Ar error occurred during deleting id", e);
      }
    }
  }

  @Override
  public void deleteAll() throws DatabaseOperationException {
    synchronized (lock) {
      try {
        fileHelper.clear(databaseFilePath);
      } catch (IOException e) {
        throw new DatabaseOperationException("Ar error occurred during deleting all id", e);
      }
    }
  }

  private Invoice deserializeJsonToInvoice(String json) {
    try {
      return mapper.readValue(json, Invoice.class);
    } catch (Exception e) {
      return null;
    }
  }

  private String serializeInvoiceToJson(Invoice invoice) throws JsonProcessingException {
    return mapper.writeValueAsString(invoice);
  }

  private List<Invoice> getAllInvoices() throws IOException {
    return fileHelper.readLines(databaseFilePath).stream()
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
    List<Invoice> invoices = getAllInvoices();
    Optional<Invoice> invoice = invoices.stream()
        .filter(Objects::nonNull)
        .filter(i -> i.getId().equals(id))
        .findFirst();
    if (invoice.isPresent()) {
      fileHelper.removeLine(databaseFilePath, invoices.indexOf(invoice.get()) + 1);
    } else {
      throw new DatabaseOperationException("Invoice does not exist");
    }
  }

  private long getLastInvoiceId() throws IOException {
    String lastInvoiceAsJson = fileHelper.readLastLine(databaseFilePath);
    if (lastInvoiceAsJson == null) {
      return 0;
    }
    Invoice invoice = deserializeJsonToInvoice(lastInvoiceAsJson);
    if (invoice == null) {
      return 0;
    }
    return invoice.getId();
  }

  private long getNextInvoiceId() {
    return ++lastInvoiceId;
  }
}
