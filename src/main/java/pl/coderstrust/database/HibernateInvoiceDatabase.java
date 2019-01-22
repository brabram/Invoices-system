package pl.coderstrust.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.coderstrust.model.Invoice;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Repository
public class HibernateInvoiceDatabase implements InvoiceDatabase {

  private HibernateInvoiceRepository hibernateInvoiceRepository;

  @Autowired
  public HibernateInvoiceDatabase(HibernateInvoiceRepository hibernateInvoiceRepository) {
    this.hibernateInvoiceRepository = hibernateInvoiceRepository;
  }

   @Override
  public Invoice save(Invoice invoice) throws InvoiceDatabaseOperationException {
    return hibernateInvoiceRepository.save(invoice);
  }

  @Override
  public Invoice findById(Long id) throws InvoiceDatabaseOperationException {
    return hibernateInvoiceRepository.findById(id).get();
  }

  @Override
  public boolean existsById(Long id) throws InvoiceDatabaseOperationException {
    return hibernateInvoiceRepository.existsById(id);
  }

  @Override
  public List<Invoice> findAll() throws InvoiceDatabaseOperationException {
    Iterable<Invoice> invoices = hibernateInvoiceRepository.findAll();
    return StreamSupport.stream(invoices.spliterator(), false).collect(Collectors.toList());
  }

  @Override
  public long count() throws InvoiceDatabaseOperationException {
    return hibernateInvoiceRepository.count();
  }

  @Override
  public void deleteById(Long id) throws InvoiceDatabaseOperationException {
    hibernateInvoiceRepository.deleteById(id);
  }

  @Override
  public void deleteAll() throws InvoiceDatabaseOperationException {
    hibernateInvoiceRepository.deleteAll();
  }
}
