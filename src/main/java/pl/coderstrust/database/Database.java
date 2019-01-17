package pl.coderstrust.database;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface Database<T, ID extends Serializable> {

  Optional<T> save(T entity) throws InvoiceDatabaseOperationException;

  Optional<T> findById(ID id) throws InvoiceDatabaseOperationException;

  boolean existsById(ID id) throws InvoiceDatabaseOperationException;

  Optional<List<T>> findAll() throws InvoiceDatabaseOperationException;

  long count() throws InvoiceDatabaseOperationException;

  void deleteById(ID id) throws InvoiceDatabaseOperationException;

  void deleteAll() throws InvoiceDatabaseOperationException;
}
