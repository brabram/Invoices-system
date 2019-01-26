package pl.coderstrust.hibernate;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.coderstrust.database.DatabaseOperationException;
import pl.coderstrust.database.HibernateInvoiceDatabase;
import pl.coderstrust.database.HibernateInvoiceRepository;
import pl.coderstrust.database.InvoiceDatabase;
import pl.coderstrust.generators.InvoiceGenerator;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.service.ServiceOperationException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class HibernateInvoiceDatabaseTest {

  @Mock
  private HibernateInvoiceRepository hibernateInvoiceRepository;

  private InvoiceDatabase invoiceDatabase;

  @BeforeEach
  void setup() {
    invoiceDatabase = new HibernateInvoiceDatabase(hibernateInvoiceRepository);
  }

  @Test
  void shouldSaveInvoice() throws DatabaseOperationException, ServiceOperationException {
    //given
    Invoice invoice1 = InvoiceGenerator.getRandomInvoice();
    Invoice invoice2 = InvoiceGenerator.getRandomInvoice();
    when(hibernateInvoiceRepository.save(invoice1)).thenReturn(invoice2);

    //when
    Optional actualInvoice = invoiceDatabase.save(invoice1);

    //then
    assertEquals(invoice2, actualInvoice);
    verify(hibernateInvoiceRepository).save(invoice1);
  }

//  @Test
//  void shouldFindOneInvoice() throws DatabaseOperationException {
//    //given
//    Invoice invoice1 = InvoiceGenerator.getRandomInvoice();
//    Invoice invoice2 = InvoiceGenerator.getRandomInvoice();
//    when(hibernateInvoiceRepository.save(invoice1)).thenReturn(invoice2);
//    when(hibernateInvoiceRepository.findById(invoice1.getId())).thenReturn(java.util.Optional.ofNullable(invoice2));
//
//    //when
//    Optional<Invoice> actualInvoice = invoiceDatabase.save(invoice1);
//    Invoice expectedInvoice = invoiceDatabase.findById(invoice1.getId());
//
//    //then
//    assertEquals(actualInvoice, expectedInvoice);
//    verify(hibernateInvoiceRepository).save(invoice1);
//    verify(hibernateInvoiceRepository).findById(invoice1.getId());
//  }
//
//  @Test
//  void shouldReturnTrueIfInvoiceExistsInDatabase() throws InvoiceDatabaseOperationException {
//    //given
//    Invoice invoice1 = InvoiceGenerator.getRandomInvoice();
//    Invoice invoice2 = InvoiceGenerator.getRandomInvoice();
//    when(hibernateInvoiceRepository.save(invoice1)).thenReturn(invoice2);
//    when(hibernateInvoiceRepository.existsById(invoice1.getId())).thenReturn(true);
//
//    //when
//    invoiceDatabase.save(invoice1);
//    boolean isInvoiceExists = invoiceDatabase.existsById(invoice1.getId());
//
//    //then
//    Assert.assertTrue(isInvoiceExists);
//    verify(hibernateInvoiceRepository).save(invoice1);
//    verify(hibernateInvoiceRepository).existsById(invoice1.getId());
//  }
//
//  @Test
//  void shouldFindAllInvoices() throws InvoiceDatabaseOperationException {
//    //given
//    Invoice invoiceToSave1 = InvoiceGenerator.getRandomInvoice();
//    Invoice invoiceToSave2 = InvoiceGenerator.getRandomInvoice();
//    Invoice savedInvoice1 = hibernateInvoiceRepository.save(invoiceToSave1);
//    Invoice savedInvoice2 = hibernateInvoiceRepository.save(invoiceToSave2);
//    List<Invoice> arrayOfInvoices = new ArrayList<>();
//    arrayOfInvoices.add(savedInvoice1);
//    arrayOfInvoices.add(savedInvoice2);
//
//    //when
//    when(hibernateInvoiceRepository.findAll()).thenReturn(arrayOfInvoices);
//    List<Invoice> invoicesFromDatabase = invoiceDatabase.findAll();
//
//    //then
//    Assert.assertNotNull(invoicesFromDatabase);
//    Assert.assertEquals(arrayOfInvoices, invoicesFromDatabase);
//    verify(hibernateInvoiceRepository).findAll();
//  }
//
//
//  @Test
//  void shouldReturnNumberOfInvoices() throws InvoiceDatabaseOperationException {
//    //given
//    Invoice invoiceToSave1 = InvoiceGenerator.getRandomInvoice();
//    Invoice invoiceToSave2 = InvoiceGenerator.getRandomInvoice();
//    Invoice savedInvoice1 = hibernateInvoiceRepository.save(invoiceToSave1);
//    Invoice savedInvoice2 = hibernateInvoiceRepository.save(invoiceToSave2);
//    List<Invoice> arrayOfInvoices = new ArrayList<>();
//    arrayOfInvoices.add(savedInvoice1);
//    arrayOfInvoices.add(savedInvoice2);
//
//    //when
//    invoiceDatabase.count();
//
//    //then
//    assertNotNull(arrayOfInvoices);
//    verify(hibernateInvoiceRepository).count();
//  }
//
//  @Test
//  void deleteById() throws InvoiceDatabaseOperationException {
//    //given
//    Invoice invoice1 = InvoiceGenerator.getRandomInvoice();
//    Invoice invoice2 = InvoiceGenerator.getRandomInvoice();
//    when(hibernateInvoiceRepository.save(invoice1)).thenReturn(invoice2);
//
//    //when
//    Invoice actualInvoice = invoiceDatabase.save(invoice1);
//    invoiceDatabase.deleteById(actualInvoice.getId());
//
//    //then
//    assertEquals(invoice2, actualInvoice);
//    verify(hibernateInvoiceRepository).deleteById(actualInvoice.getId());
//  }
//
//  @Test
//  void shouldDeleteAllInvoices() throws InvoiceDatabaseOperationException {
//    //given
//    Invoice invoiceToSave1 = InvoiceGenerator.getRandomInvoice();
//    Invoice invoiceToSave2 = InvoiceGenerator.getRandomInvoice();
//    Invoice savedInvoice1 = hibernateInvoiceRepository.save(invoiceToSave1);
//    Invoice savedInvoice2 = hibernateInvoiceRepository.save(invoiceToSave2);
//    List<Invoice> arrayOfInvoices = new ArrayList<>();
//    arrayOfInvoices.add(savedInvoice1);
//    arrayOfInvoices.add(savedInvoice2);
//
//    //when
//    invoiceDatabase.deleteAll();
//    long numberOfInvoices = invoiceDatabase.count();
//
//    //then
//    Assert.assertEquals(0, numberOfInvoices);
//    verify(hibernateInvoiceRepository).deleteAll();
//  }
}