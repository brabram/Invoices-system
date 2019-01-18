package pl.coderstrust.hibernate;

import org.springframework.stereotype.Repository;
import pl.coderstrust.database.InvoiceDatabaseOperationException;
import pl.coderstrust.model.Invoice;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class HibernateInvoiceDatabase implements HibernateInvoiceRepository {

  private HibernateInvoiceRepository hibernateInvoiceRepository;


  @Override
  public <S extends Invoice> S save(S entity) throws InvoiceDatabaseOperationException {
    return hibernateInvoiceRepository.save(entity);
  }

  @Override
  public Invoice findById(Long aLong) throws InvoiceDatabaseOperationException {
    return hibernateInvoiceRepository.findById(aLong);
  }

  @Override
  public boolean existsById(Long aLong) throws InvoiceDatabaseOperationException {
    return hibernateInvoiceRepository.existsById(aLong);
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
  public void deleteById(Long aLong) throws InvoiceDatabaseOperationException {
    hibernateInvoiceRepository.deleteById(aLong);
  }

  @Override
  public void deleteAll() throws InvoiceDatabaseOperationException {
    hibernateInvoiceRepository.deleteAll();
  }
}
