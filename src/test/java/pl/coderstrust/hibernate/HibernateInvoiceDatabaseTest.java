package pl.coderstrust.hibernate;

import junit.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.coderstrust.database.InvoiceDatabaseOperationException;
import pl.coderstrust.generators.InvoiceGenerator;
import pl.coderstrust.model.Invoice;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class HibernateInvoiceDatabaseTest {

  private HibernateInvoiceDatabase hibernateInvoiceDatabase;

  @BeforeEach
  void setup() {
    hibernateInvoiceDatabase = new HibernateInvoiceDatabase(hibernateInvoiceDatabase);
  }

  @Test
  void save() throws InvoiceDatabaseOperationException {
      hibernateInvoiceDatabase.save(InvoiceGenerator.getRandomInvoice());
      Invoice invoice = hibernateInvoiceDatabase.findById(1L);
      assertEquals(1L, invoice.getId());
  }

  @Test
  void findById() {
  }

  @Test
  void existsById() {
  }

  @Test
  void findAll() throws InvoiceDatabaseOperationException {
    //given
    Invoice invoiceToSave1 = InvoiceGenerator.getRandomInvoice();
    Invoice invoiceToSave2 = InvoiceGenerator.getRandomInvoice();
    Invoice invoiceToSave3 = InvoiceGenerator.getRandomInvoice();
    Invoice savedInvoice1 = hibernateInvoiceDatabase.save(invoiceToSave1);
    Invoice savedInvoice2 = hibernateInvoiceDatabase.save(invoiceToSave2);
    Invoice savedInvoice3 = hibernateInvoiceDatabase.save(invoiceToSave3);
    List<Invoice> expectedInvoices = new ArrayList<>();
    expectedInvoices.add(savedInvoice1);
    expectedInvoices.add(savedInvoice2);
    expectedInvoices.add(savedInvoice3);

    //when
    List<Invoice> invoicesFromDatabase = hibernateInvoiceDatabase.findAll();

    //then
    Assert.assertNotNull(invoicesFromDatabase);
    Assert.assertEquals(expectedInvoices, invoicesFromDatabase);
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