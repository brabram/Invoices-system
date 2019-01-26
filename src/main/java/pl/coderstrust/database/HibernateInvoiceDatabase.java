package pl.coderstrust.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.coderstrust.model.Invoice;
import pl.coderstrust.service.InvoiceService;
import pl.coderstrust.service.ServiceOperationException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Repository
public class HibernateInvoiceDatabase implements InvoiceDatabase {

  private HibernateInvoiceRepository hibernateInvoiceRepository;
  private InvoiceService invoiceService;

  @Autowired
  public HibernateInvoiceDatabase(HibernateInvoiceRepository hibernateInvoiceRepository) {
    this.hibernateInvoiceRepository = hibernateInvoiceRepository;
  }

  @Override
  public Optional<Invoice> save(Invoice invoice) throws ServiceOperationException {
    return invoiceService.addInvoice(invoice);
  }

  @Override
  public Optional<Invoice> findById(Long id) throws ServiceOperationException {
    return invoiceService.getInvoiceById(id);
  }

  @Override
  public boolean existsById(Long id) {
    return hibernateInvoiceRepository.existsById(id);
  }

  @Override
  public Optional<List<Invoice>> findAll() throws ServiceOperationException {
    return invoiceService.getAllInvoices();
  }

  @Override
  public long count() {
    return hibernateInvoiceRepository.count();
  }

  @Override
  public void deleteById(Long id) throws ServiceOperationException {
    invoiceService.deleteInvoiceById(id);
  }

  @Override
  public void deleteAll() throws ServiceOperationException {
    invoiceService.deleteAll();
  }
}
