package pl.coderstrust.database;

import pl.coderstrust.service.ServiceOperationException;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface Database<T, ID extends Serializable> {

  Optional<T> save(T entity) throws DatabaseOperationException, ServiceOperationException;

  Optional<T> findById(ID id) throws DatabaseOperationException, ServiceOperationException;

  boolean existsById(ID id) throws DatabaseOperationException;

  Optional<List<T>> findAll() throws DatabaseOperationException, ServiceOperationException;

  long count() throws DatabaseOperationException;

  void deleteById(ID id) throws DatabaseOperationException, ServiceOperationException;

  void deleteAll() throws DatabaseOperationException, ServiceOperationException;
}
