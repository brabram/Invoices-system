package pl.coderstrust.repository;

import org.springframework.data.repository.Repository;

import java.util.List;

public interface InvoiceRepository<Invoice, Integer> extends Repository<Invoice, Integer> {

  Invoice save(Invoice invoice) throws InvoiceRepositoryOperationException;

  Invoice findById(Integer id) throws InvoiceRepositoryOperationException;

  boolean existsById(Integer id) throws InvoiceRepositoryOperationException;

  List<Invoice> findAll() throws InvoiceRepositoryOperationException;

  long count() throws InvoiceRepositoryOperationException;

  void deleteById(Integer id) throws InvoiceRepositoryOperationException;

  void deleteAll() throws InvoiceRepositoryOperationException;
}
