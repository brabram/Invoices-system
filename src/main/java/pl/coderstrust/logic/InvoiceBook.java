package pl.coderstrust.logic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.repository.InvoiceRepository;
import pl.coderstrust.repository.InvoiceRepositoryOperationException;

public class InvoiceBook {

  private InvoiceRepository<Invoice, Integer> invoiceRepository;

  public InvoiceBook(InvoiceRepository<Invoice, Integer> invoiceRepository) {
    this.invoiceRepository = invoiceRepository;
  }

  public List<Invoice> getAllInvoices() throws InvoiceRepositoryOperationException {
    return invoiceRepository.findAll();
  }

  public List<Invoice> getAllInvoicesInGivenDateRange(LocalDate fromDate, LocalDate toDate) throws InvoiceRepositoryOperationException {
    List<Invoice> invoices = getAllInvoices();
    List<Invoice> invoicesInGivenDateRange = new ArrayList<>(invoices);
    for (Invoice invoice:
    invoicesInGivenDateRange) {
      if (!invoice.getIssueDate().isAfter(fromDate) && !invoice.getIssueDate().isBefore(toDate)) {
        invoicesInGivenDateRange.remove(invoice);
      }
    }
    return invoicesInGivenDateRange;
  }

  public Invoice getInvoiceById(Integer id) throws InvoiceRepositoryOperationException {
    return invoiceRepository.findById(id);
  }

  public Invoice addInvoice(Invoice invoice) throws InvoiceRepositoryOperationException {
    return invoiceRepository.save(invoice);
  }

  public void updateInvoice(Invoice invoice) throws InvoiceRepositoryOperationException {
    invoiceRepository.save(invoice);
  }

  public void deleteInvoiceById(Integer id) throws InvoiceRepositoryOperationException {
    invoiceRepository.deleteById(id);
  }

  public void deleteInvoice(Invoice invoice) throws InvoiceRepositoryOperationException {
    if (invoiceRepository.existsById(invoice.getId())) {
      deleteInvoiceById(invoice.getId());
    }
  }

  public void deleteAll() throws InvoiceRepositoryOperationException {
    invoiceRepository.deleteAll();
  }
}
