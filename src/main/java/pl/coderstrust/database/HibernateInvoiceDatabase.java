package pl.coderstrust.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.coderstrust.model.Invoice;

import java.util.List;
import java.util.Optional;


@Repository
public class HibernateInvoiceDatabase implements InvoiceDatabase {

  private HibernateInvoiceRepository hibernateInvoiceRepository;

  @Autowired
  public HibernateInvoiceDatabase(HibernateInvoiceRepository hibernateInvoiceRepository) {
    this.hibernateInvoiceRepository = hibernateInvoiceRepository;
  }

  @Override
  public Optional<Invoice> save(Invoice invoice) {
    return Optional.of(hibernateInvoiceRepository.save(invoice));
  }

  @Override
  public Optional<Invoice> findById(Long id) {
    return hibernateInvoiceRepository.findById(id);
  }

  @Override
  public boolean existsById(Long id) {
    return hibernateInvoiceRepository.existsById(id);
  }

  @Override
  public Optional<List<Invoice>> findAll() {
    return Optional.of(hibernateInvoiceRepository.findAll());
  }

  @Override
  public long count() {
    return hibernateInvoiceRepository.count();
  }

  @Override
  public void deleteById(Long id) {
    hibernateInvoiceRepository.deleteById(id);
  }

  @Override
  public void deleteAll() {
    hibernateInvoiceRepository.deleteAll();
  }
}
