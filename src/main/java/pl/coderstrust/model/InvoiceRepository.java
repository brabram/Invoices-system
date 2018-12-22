package pl.coderstrust.model;

import org.springframework.data.repository.Repository;

import java.io.Serializable;

public interface InvoiceRepository<Invoice, ID extends Serializable> extends Repository<Invoice, ID> {

    Invoice save(Invoice invoice);

    Invoice findById(ID id);

    boolean existsById(ID id);

    Iterable<Invoice> findAll();

    long count();

    void deleteById(ID id);

    void deleteAll();
}
