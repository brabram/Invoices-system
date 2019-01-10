package pl.coderstrust.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.Repository;

public interface BaseRepository<T, ID extends Serializable> extends Repository<T, ID> {

  <S extends T> S save(S entity) throws InvoiceRepositoryOperationException;

  T findById(ID id) throws InvoiceRepositoryOperationException;

  boolean existsById(ID id) throws InvoiceRepositoryOperationException;

  List<T> findAll() throws InvoiceRepositoryOperationException;

  long count() throws InvoiceRepositoryOperationException;

  void deleteById(ID id) throws InvoiceRepositoryOperationException;

  void deleteAll() throws InvoiceRepositoryOperationException;
}
