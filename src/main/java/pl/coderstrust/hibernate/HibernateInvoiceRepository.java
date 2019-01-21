package pl.coderstrust.hibernate;

import pl.coderstrust.database.Database;
import pl.coderstrust.model.Invoice;

public interface HibernateInvoiceRepository extends Database<Invoice, Long> {
}
