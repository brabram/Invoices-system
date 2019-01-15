package pl.coderstrust.database;

import java.io.Serializable;
import java.util.List;

public interface Database<T, ID extends Serializable> {

  <S extends T> S save(S entity) throws InvoiceDatabaseOperationException;

  T findById(ID id) throws InvoiceDatabaseOperationException;

  boolean existsById(ID id) throws InvoiceDatabaseOperationException;

  List<T> findAll() throws InvoiceDatabaseOperationException;

  long count() throws InvoiceDatabaseOperationException;

  void deleteById(ID id) throws InvoiceDatabaseOperationException;

  void deleteAll() throws InvoiceDatabaseOperationException;
}
