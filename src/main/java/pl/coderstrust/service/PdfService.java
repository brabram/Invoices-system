package pl.coderstrust.service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import java.io.ByteArrayOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.List;
import javax.swing.text.StyledEditorKit;
import org.springframework.stereotype.Service;
import pl.coderstrust.model.AccountNumber;
import pl.coderstrust.model.Address;
import pl.coderstrust.model.Company;
import pl.coderstrust.model.ContactDetails;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.model.InvoiceEntry;

@Service
public class PdfService {
  public byte[] createPdf(Invoice invoice) throws DocumentException {
    Document document = new Document(PageSize.A4);
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    PdfWriter.getInstance(document, stream);
    document.open();
    Paragraph paragraph = new Paragraph();
    Paragraph title = new Paragraph(invoice.getNumber(),
        FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD));
    title.setAlignment(title.ALIGN_RIGHT);
    title.add(String.valueOf("/ID" + invoice.getId()));
    Paragraph issueDate = new Paragraph(String.valueOf("Issue date: " + invoice.getIssueDate()));
    issueDate.setAlignment(issueDate.ALIGN_RIGHT);
    Paragraph dueDate = new Paragraph(String.valueOf("Due date: " + invoice.getDueDate()));
    issueDate.setAlignment(issueDate.ALIGN_RIGHT);
    dueDate.setAlignment(dueDate.ALIGN_RIGHT);
    document.add(title);
    document.add(issueDate);
    document.add(dueDate);
    PdfPTable table = new PdfPTable(2);
    PdfPCell seller = new PdfPCell();
    Paragraph sellerTitle = new Paragraph("Seller: ",
        FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
    seller.addElement(sellerTitle);
    seller.addElement(company(invoice.getSeller()));
    seller.setBorder(seller.NO_BORDER);
    PdfPCell buyer = new PdfPCell();
    Paragraph buyerTitle = new Paragraph("Buyer: ",
        FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
    buyer.setBorder(buyer.NO_BORDER);
    buyer.addElement(buyerTitle);
    buyer.addElement(company(invoice.getBuyer()));
    table.setSpacingBefore(10);
    table.setWidthPercentage(100);
    table.addCell(seller);
    table.addCell(buyer);
    document.add(table);
    document.add(listInvoiceEntries(invoice.getEntries()));
    Paragraph totalNetValue = new Paragraph("Total net value: " + invoice.getTotalNetValue(),
        FontFactory.getFont(FontFactory.HELVETICA_BOLD));
    Paragraph totalGrossValue = new Paragraph("Total gross value: " + invoice.getTotalGrossValue(),
        FontFactory.getFont(FontFactory.HELVETICA_BOLD));
    paragraph.add(totalNetValue);
    paragraph.add(totalGrossValue);
    document.add(paragraph);
    document.close();
    return stream.toByteArray();
  }

  private PdfPTable listInvoiceEntries(List<InvoiceEntry> invoiceEntries) throws DocumentException {
    String[] tableHeaders = new String[] {"Id", "Item", "Quantity", "Price", "Vat Value", "Gross value", "Vat rate"};
    PdfPTable table = new PdfPTable(7);
    table.setSpacingBefore(25);
    table.setSpacingAfter(25);
    table.setWidthPercentage(100);
    addInvoiceEntriestableHeander(table, tableHeaders);
    for (InvoiceEntry invoiceEntry : invoiceEntries) {
      table.addCell(invoiceEntry.getId().toString());
      table.addCell(invoiceEntry.getItem());
      table.addCell(invoiceEntry.getQuantity().toString());
      table.addCell(invoiceEntry.getPrice().toString());
      table.addCell(invoiceEntry.getVatValue().toString());
      table.addCell(invoiceEntry.getGrossValue().toString());
      table.addCell(invoiceEntry.getVatRate().toString());
    }
    return table;
  }

  private void addInvoiceEntriestableHeander(PdfPTable table, String[] headers) {
    for (String heander : headers) {
      PdfPCell title = new PdfPCell();
      title.setBackgroundColor(BaseColor.GRAY);
      title.setHorizontalAlignment(title.ALIGN_CENTER);
      title.setVerticalAlignment(title.ALIGN_CENTER);
      title.setBorderWidth(2);
      title.setPhrase(new Phrase(heander));
      table.addCell(title);
    }
  }

  private Paragraph company(Company company) {
    Paragraph paragraph = new Paragraph();
    Paragraph name = new Paragraph(company.getName());
    Paragraph id = new Paragraph(company.getId());
    Paragraph taxIdentificationNumber = new Paragraph("Tax id : " + company.getTaxIdentificationNumber());
    paragraph.add(name);
    paragraph.add(id);
    paragraph.add(taxIdentificationNumber);
    paragraph.add(contactDetails(company.getContactDetails()));
    paragraph.add(accountNumber(company.getAccountNumber()));
    return paragraph;
  }

  private Paragraph accountNumber(AccountNumber accountNumber) {
    Paragraph paragraph = new Paragraph();
    Paragraph title = new Paragraph("AccountNumber: ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
    Paragraph localNumber = new Paragraph("\n" + accountNumber.getLocalNumber());
    Paragraph ibanNumber = new Paragraph("\n" + accountNumber.getIbanNumber());
    paragraph.add(title);
    paragraph.add(ibanNumber);
    paragraph.add(localNumber);
    return paragraph;
  }

  private Paragraph contactDetails(ContactDetails contactDetails) {
    Paragraph paragraph = new Paragraph();
    Paragraph title = new Paragraph("Contact details: ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
    Paragraph email = new Paragraph("\nEmail: " + contactDetails.getEmail());
    Paragraph phoneNumber = new Paragraph("\nPhoneNumber: " + contactDetails.getPhoneNumber());
    Paragraph webSite = new Paragraph("\nWebsite: " + contactDetails.getWebsite());
    paragraph.add(title);
    paragraph.add(email);
    paragraph.add(phoneNumber);
    paragraph.add(webSite);
    paragraph.add(address(contactDetails.getAddress()));
    return paragraph;
  }

  private Paragraph address(Address address) {
    Paragraph paragraph = new Paragraph();
    Paragraph addressData = new Paragraph("\nAddress: " + address.getCountry() + "/" + address.getCity() + "/" + address.getStreet() + "/" + address.getNumber());
    Paragraph postalCOde = new Paragraph("\nPostal code: " + address.getPostalCode());
    paragraph.add(addressData);
    paragraph.add(postalCOde);
    return paragraph;
  }
}
