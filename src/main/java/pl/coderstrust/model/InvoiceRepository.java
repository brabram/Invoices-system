package pl.coderstrust.model;

import org.springframework.data.repository.Repository;

import java.io.Serializable;

public interface InvoiceRepository<Invoice, ID extends Serializable> extends Repository<Invoice, ID> {

    Invoice save(Invoice invoice) throws InvoiceRepositoryOperationException;

    Invoice findById(ID id) throws InvoiceRepositoryOperationException;

    boolean existsById(ID id) throws InvoiceRepositoryOperationException;

    Iterable<Invoice> findAll();

    long count();

    void deleteById(ID id) throws InvoiceRepositoryOperationException;

    void deleteAll();
}
