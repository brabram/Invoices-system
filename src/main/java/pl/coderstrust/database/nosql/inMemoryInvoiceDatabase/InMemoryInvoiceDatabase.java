package pl.coderstrust.database.nosql.inMemoryInvoiceDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import pl.coderstrust.database.DatabaseOperationException;
import pl.coderstrust.database.IdentifierGenerator;
import pl.coderstrust.database.IdentifierGeneratorException;
import pl.coderstrust.database.InvoiceDatabase;
import pl.coderstrust.database.nosql.NoSqlModelMapper;
import pl.coderstrust.database.nosql.model.NoSqlInvoice;
import pl.coderstrust.model.Invoice;

@ConditionalOnProperty(name = "pl.coderstrust.database", havingValue = "in-memory")
@Repository
public class InMemoryInvoiceDatabase implements InvoiceDatabase {

  private static Logger log = LoggerFactory.getLogger(InMemoryInvoiceDatabase.class);
  private final IdentifierGenerator identifierGenerator;
  private final NoSqlModelMapper noSqlModelMapper;
  private Map<Long, NoSqlInvoice> invoices = Collections.synchronizedMap(new HashMap<>());

  @Autowired
  public InMemoryInvoiceDatabase(IdentifierGenerator identifierGenerator, NoSqlModelMapper noSqlModelMapper) {
    if (identifierGenerator == null) {
      throw new IllegalArgumentException("identifierGenerator cannot be null");
    }
    if (noSqlModelMapper == null) {
      throw new IllegalArgumentException("noSqlModelMapper cannot be null");
    }
    this.identifierGenerator = identifierGenerator;
    this.identifierGenerator.initalize(0);
    this.noSqlModelMapper = noSqlModelMapper;
  }

  @Override
  public synchronized Optional<Invoice> save(Invoice invoice) throws DatabaseOperationException {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice cannot be null");
    }
    try {
      log.debug("Saving invoice: {}", invoice);
      if (invoice.getId() != null && isInvoiceExist(invoice.getId())) {
        return Optional.of(updateInvoice(invoice));
      }
      return Optional.of(addInvoice(invoice));
    }catch (IdentifierGeneratorException e){
      throw new DatabaseOperationException("An error occurred during saving invoice", e);
    }
  }

  @Override
  public synchronized Optional<Invoice> findById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    log.debug("Searching invoice by id: {}", id);
    NoSqlInvoice invoice = invoices.get(id);
    if (invoice != null) {
      return Optional.of(noSqlModelMapper.mapInvoice(invoice));
    }
    return Optional.empty();
  }

  @Override
  public synchronized boolean existsById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    log.debug("Checking if invoice exists by id: {}", id);
    return isInvoiceExist(id);
  }

  @Override
  public synchronized Optional<List<Invoice>> findAll() {
    log.debug("Getting all invoices.");
    return Optional.of(noSqlModelMapper.mapInvoices(new ArrayList<>(invoices.values())));
  }

  @Override
  public synchronized long count() {
    log.debug("Getting withNumber of invoices.");
    return invoices.size();
  }

  @Override
  public synchronized void deleteById(Long id) throws DatabaseOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    if (!isInvoiceExist(id)) {
      throw new DatabaseOperationException(String.format("There was no invoice in database by id: %s", id));
    }
    log.debug("Removing invoice by id: {}", id);
    invoices.remove(id);
  }

  @Override
  public synchronized void deleteAll() {
    log.debug("Removing all invoices.");
    invoices.clear();
  }

  private boolean isInvoiceExist(Long id) {
    log.info("Checking if invoice exists by id: {}", id);
    return invoices.containsKey(id);
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

    invoices.put(invoiceToAdd.getId(), invoiceToAdd);
    return noSqlModelMapper.mapInvoice(invoiceToAdd);
  }

  private Invoice updateInvoice(Invoice invoice) {
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

    invoices.put(invoiceToUpdate.getId(), invoiceToUpdate);
    return noSqlModelMapper.mapInvoice(invoiceToUpdate);
  }
}
