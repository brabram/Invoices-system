package pl.coderstrust.database.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderstrust.database.sql.model.SqlInvoice;

public interface HibernateInvoiceRepository extends JpaRepository<SqlInvoice, Long> {
}
