package pl.coderstrust.service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;
import pl.coderstrust.model.AccountNumber;
import pl.coderstrust.model.Company;
import pl.coderstrust.model.ContactDetails;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.model.InvoiceEntry;

@Service
public class InvoicePdfService {
  private static final String NEW_LINE = "\n";

  public byte[] createPdf(Invoice invoice) throws ServiceOperationException {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice cannot be null.");
    }

    try {
      Document document = new Document(PageSize.A4);
      ByteArrayOutputStream stream = new ByteArrayOutputStream();
      PdfWriter.getInstance(document, stream);

      document.open();
      document.add(getInvoiceDetails(invoice));
      document.add(getCompanies(invoice));
      document.add(getInvoiceEntries(invoice));
      document.add(getInvoiceValue(invoice));
      document.close();

      return stream.toByteArray();
    } catch (Exception e) {
      throw new ServiceOperationException(String.format("An error occurred during creating pdf for invoice. Invoice id: %s",
          invoice.getId()), e);
    }
  }

  private Paragraph getInvoiceDetails(Invoice invoice) {
    Paragraph title = new Paragraph(String.format("Invoice number: %s", invoice.getNumber()),
        FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD));
    Paragraph issueDate = new Paragraph(String.format("Issue date: %s", invoice.getIssueDate()));
    Paragraph dueDate = new Paragraph(String.format("Due date: %s", invoice.getDueDate()));

    Paragraph paragraph = new Paragraph();
    paragraph.add(title);
    paragraph.add(issueDate);
    paragraph.add(dueDate);

    return paragraph;
  }

  private PdfPTable getCompanies(Invoice invoice) {
    PdfPTable table = new PdfPTable(2);
    table.setSpacingBefore(10);
    table.setWidthPercentage(100);
    table.addCell(getCompany(invoice.getSeller(), "Seller"));
    table.addCell(getCompany(invoice.getBuyer(), "Buyer"));

    return table;
  }

  private PdfPCell getCompany(Company company, String type) {
    Paragraph companyType = new Paragraph(type, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
    Paragraph name = new Paragraph(company.getName());
    Paragraph address = getCompanyAddress(company.getContactDetails());
    Paragraph contactDetails = getCompanyContactDetails(company.getContactDetails());
    Paragraph taxIdentifier = new Paragraph(String.format("Tax identifier: %s", company.getTaxIdentificationNumber()));
    Paragraph accountNumber = getCompanyAccountNumber(company.getAccountNumber());

    Paragraph paragraph = new Paragraph();
    paragraph.add(companyType);
    paragraph.add(name);
    paragraph.add(address);
    paragraph.add(NEW_LINE);
    paragraph.add(accountNumber);
    paragraph.add(taxIdentifier);
    paragraph.add(NEW_LINE);
    paragraph.add(contactDetails);

    PdfPCell cell = new PdfPCell();
    cell.setBorder(0);
    cell.addElement(paragraph);
    return cell;
  }

  private Paragraph getCompanyAddress(ContactDetails contactDetails) {
    return new Paragraph(String.format("%s %s\n%s %s\n%s",
        contactDetails.getAddress().getStreet(),
        contactDetails.getAddress().getNumber(),
        contactDetails.getAddress().getPostalCode(),
        contactDetails.getAddress().getCity(),
        contactDetails.getAddress().getCountry()));
  }

  private Paragraph getCompanyContactDetails(ContactDetails contactDetails) {
    Paragraph email = new Paragraph(contactDetails.getEmail());
    Paragraph phoneNumber = new Paragraph(contactDetails.getPhoneNumber());
    Paragraph webSite = new Paragraph(contactDetails.getWebsite());

    Paragraph paragraph = new Paragraph();
    paragraph.add(email);
    paragraph.add(NEW_LINE);
    paragraph.add(phoneNumber);
    paragraph.add(NEW_LINE);
    paragraph.add(webSite);
    return paragraph;
  }

  private Paragraph getCompanyAccountNumber(AccountNumber accountNumber) {
    Paragraph localNumber = new Paragraph(String.format("Local: %S", accountNumber.getLocalNumber()));
    Paragraph ibanNumber = new Paragraph(String.format("IBAN: %S", accountNumber.getIbanNumber()));

    Paragraph paragraph = new Paragraph();
    paragraph.add(ibanNumber);
    paragraph.add(NEW_LINE);
    paragraph.add(localNumber);
    return paragraph;
  }

  private PdfPTable getInvoiceEntries(Invoice invoice) {
    PdfPTable table = new PdfPTable(6);
    table.setSpacingBefore(25);
    table.setSpacingAfter(25);
    table.setWidthPercentage(100);
    String[] tableHeaders = new String[] {"Item", "Quantity", "Price", "Vat rate", "Vat Value", "Gross value"};
    addTableHeader(table, tableHeaders);
    for (InvoiceEntry invoiceEntry : invoice.getEntries()) {
      table.addCell(invoiceEntry.getItem());
      table.addCell(invoiceEntry.getQuantity().toString());
      table.addCell(invoiceEntry.getPrice().toString());
      table.addCell(String.valueOf(invoiceEntry.getVatRate().getValue()));
      table.addCell(invoiceEntry.getVatValue().toString());
      table.addCell(invoiceEntry.getGrossValue().toString());
    }
    return table;
  }

  private void addTableHeader(PdfPTable table, String[] headers) {
    Stream.of(headers)
        .forEach(columnTitle -> {
          PdfPCell header = new PdfPCell();
          header.setBackgroundColor(BaseColor.LIGHT_GRAY);
          header.setBorderWidth(2);
          header.setPhrase(new Phrase(columnTitle));
          table.addCell(header);
        });
  }

  private Paragraph getInvoiceValue(Invoice invoice) {
    Paragraph totalNetValue = new Paragraph("Total net value: " + invoice.getTotalNetValue(),
        FontFactory.getFont(FontFactory.HELVETICA_BOLD));
    Paragraph totalGrossValue = new Paragraph("Total gross value: " + invoice.getTotalGrossValue(),
        FontFactory.getFont(FontFactory.HELVETICA_BOLD));
    Paragraph paragraph = new Paragraph();
    paragraph.add(totalNetValue);
    paragraph.add(totalGrossValue);

    return paragraph;
  }
}
