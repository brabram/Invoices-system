package pl.coderstrust.repository;

import org.springframework.data.repository.Repository;
import java.util.List;

public interface InvoiceRepository<Invoice, String> extends Repository<Invoice, String> {

  Invoice save(Invoice invoice) throws InvoiceRepositoryOperationException;

  Invoice findById(String id) throws InvoiceRepositoryOperationException;

  boolean existsById(String id) throws InvoiceRepositoryOperationException;

  List<Invoice> findAll() throws InvoiceRepositoryOperationException;

  long count() throws InvoiceRepositoryOperationException;

  void deleteById(String id) throws InvoiceRepositoryOperationException;

  void deleteAll() throws InvoiceRepositoryOperationException;
}
