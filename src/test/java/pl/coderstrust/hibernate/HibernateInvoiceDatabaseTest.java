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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
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
  void shouldSaveInvoice() throws DatabaseOperationException {
    //given
    Invoice invoiceToSave = InvoiceGenerator.getRandomInvoice();
    Invoice expectedInvoice = InvoiceGenerator.getRandomInvoice();
    when(hibernateInvoiceRepository.save(invoiceToSave)).thenReturn(expectedInvoice);

    //when
    Optional actualInvoice = invoiceDatabase.save(invoiceToSave);

    //then
    assertEquals(Optional.of(expectedInvoice), actualInvoice);
    verify(hibernateInvoiceRepository).save(invoiceToSave);
  }

  @Test
  void shouldFindOneInvoice() throws DatabaseOperationException {
    //given
    Optional<Invoice> expectedInvoice = Optional.of(InvoiceGenerator.getRandomInvoice());
    Long id = expectedInvoice.get().getId();
    when(hibernateInvoiceRepository.findById(id)).thenReturn(expectedInvoice);

    //When
    Optional<Invoice> actualInvoice = invoiceDatabase.findById(id);

    //Then
    assertEquals(expectedInvoice, actualInvoice);
    verify(hibernateInvoiceRepository).findById(id);
  }

  @Test
  void shouldReturnTrueIfInvoiceExistsInDatabase() throws DatabaseOperationException {
    //given
    Invoice invoiceToSave = InvoiceGenerator.getRandomInvoice();
    Invoice expectedInvoice = InvoiceGenerator.getRandomInvoice();
    when(hibernateInvoiceRepository.save(invoiceToSave)).thenReturn(expectedInvoice);
    when(hibernateInvoiceRepository.existsById(invoiceToSave.getId())).thenReturn(true);

    //when
    invoiceDatabase.save(invoiceToSave);
    boolean isInvoiceExists = invoiceDatabase.existsById(invoiceToSave.getId());

    //then
    Assert.assertTrue(isInvoiceExists);
    verify(hibernateInvoiceRepository).save(invoiceToSave);
    verify(hibernateInvoiceRepository).existsById(invoiceToSave.getId());
  }

  @Test
  void shouldFindAllInvoices() throws DatabaseOperationException {
    //given
    List<Invoice> expectedInvoices = new ArrayList<>();
    Invoice invoiceToSave1 = InvoiceGenerator.getRandomInvoice();
    Invoice invoiceToSave2 = InvoiceGenerator.getRandomInvoice();
    expectedInvoices.add(invoiceToSave1);
    expectedInvoices.add(invoiceToSave2);
    when(hibernateInvoiceRepository.findAll()).thenReturn(expectedInvoices);

    //When
    Optional<List<Invoice>> actualInvoices = invoiceDatabase.findAll();

    //Then
    assertTrue(actualInvoices.isPresent());
    assertEquals(expectedInvoices, actualInvoices.get());
    verify(hibernateInvoiceRepository).findAll();
  }


  @Test
  void shouldReturnNumberOfInvoices() throws DatabaseOperationException {
    //given
    Invoice invoiceToSave1 = InvoiceGenerator.getRandomInvoice();
    Invoice invoiceToSave2 = InvoiceGenerator.getRandomInvoice();
    Invoice savedInvoice1 = hibernateInvoiceRepository.save(invoiceToSave1);
    Invoice savedInvoice2 = hibernateInvoiceRepository.save(invoiceToSave2);
    List<Invoice> arrayOfInvoices = new ArrayList<>();
    arrayOfInvoices.add(savedInvoice1);
    arrayOfInvoices.add(savedInvoice2);
    when(hibernateInvoiceRepository.findAll()).thenReturn(arrayOfInvoices);
    when(hibernateInvoiceRepository.count()).thenReturn(Long.valueOf(arrayOfInvoices.size()));

    //when
    invoiceDatabase.findAll();
    long invoicesFromDatabase = invoiceDatabase.count();

    //then
    Assert.assertNotNull(invoicesFromDatabase);
    Assert.assertEquals(arrayOfInvoices.size(), invoicesFromDatabase);
    verify(hibernateInvoiceRepository).findAll();
    verify(hibernateInvoiceRepository).count();
  }

  @Test
  void shouldDeleteById() throws DatabaseOperationException {
    Invoice invoiceToSave = InvoiceGenerator.getRandomInvoice();
    when(hibernateInvoiceRepository.existsById(invoiceToSave.getId())).thenReturn(true);
    assertTrue(hibernateInvoiceRepository.existsById(invoiceToSave.getId()));
    doNothing().when(hibernateInvoiceRepository).deleteById(invoiceToSave.getId());
    invoiceDatabase.deleteById(invoiceToSave.getId());
    verify(hibernateInvoiceRepository).existsById(invoiceToSave.getId());
    verify(hibernateInvoiceRepository).deleteById(invoiceToSave.getId());
  }

  @Test
  void shouldDeleteAllInvoices() throws DatabaseOperationException {
    //given
    doNothing().when(hibernateInvoiceRepository).deleteAll();

    //when
    invoiceDatabase.deleteAll();

    //then
    verify(hibernateInvoiceRepository).deleteAll();
  }
}