package pl.coderstrust.hibernate;

import junit.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.coderstrust.database.InvoiceDatabaseOperationException;
import pl.coderstrust.generators.InvoiceGenerator;
import pl.coderstrust.model.Invoice;
import static org.junit.jupiter.api.Assertions.*;

class HibernateInvoiceDatabaseTest {

  private HibernateInvoiceDatabase hibernateInvoiceDatabase;

  @BeforeEach
  void setup() {
    hibernateInvoiceDatabase = new HibernateInvoiceDatabase();
  }

  @Test
  void save() throws InvoiceDatabaseOperationException {
    //given
    Invoice invoiceToSave = InvoiceGenerator.getRandomInvoice();
    Invoice savedInvoice = hibernateInvoiceDatabase.save(invoiceToSave);

    //when
    Invoice invoiceFromDatabase = hibernateInvoiceDatabase.findById(savedInvoice.getId());

    //then
    Assert.assertNotNull(invoiceFromDatabase);
    Assert.assertEquals(invoiceFromDatabase, savedInvoice);
  }

  @Test
  void findById() {
  }

  @Test
  void existsById() {
  }

  @Test
  void findAll() {
  }

  @Test
  void count() {
  }

  @Test
  void deleteById() {
  }

  @Test
  void deleteAll() {
  }
}