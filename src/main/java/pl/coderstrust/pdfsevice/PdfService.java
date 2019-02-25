package pl.coderstrust.pdfsevice;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfChunk;
import com.itextpdf.text.pdf.PdfPCell;
import java.io.ByteArrayOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.List;
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
    Paragraph issueDate = new Paragraph(String.valueOf(invoice.getIssueDate() + " - " + invoice.getDueDate()));
    issueDate.setAlignment(issueDate.ALIGN_RIGHT);
    document.add(title);
    document.add(issueDate);
    PdfPTable table = new PdfPTable(2);
    PdfPCell seller = new PdfPCell();
    seller.addElement(companySeller(invoice.getSeller()));
    seller.setBorder(seller.NO_BORDER);
    PdfPCell buyer = new PdfPCell();
    buyer.setBorder(buyer.NO_BORDER);
    buyer.addElement(companyBuyer(invoice.getBuyer()));
    table.setSpacingBefore(10);
    table.setWidthPercentage(100);
    table.addCell(seller);
    table.addCell(buyer);
    document.add(table);
    paragraph.add(String.valueOf(invoice.getTotalGrossValue()));
    document.add(listInvoiceEntries(invoice.getEntries()));
    document.close();
    return stream.toByteArray();
  }

  private PdfPTable listInvoiceEntries(List<InvoiceEntry> invoiceEntries) throws DocumentException {
    String[] tableHeaders = new String[]{"a", "b", "c", "d", "e", "F", "g"};

    PdfPTable table = new PdfPTable(7);
    table.setSpacingBefore(25);
    table.setSpacingAfter(25);
    table.setWidthPercentage(100);
    addInvoiceEntriestableHeander(table, tableHeaders);
    for (InvoiceEntry invoiceEntry : invoiceEntries) {
      table.addCell(invoiceEntry.getId().toString());
      table.addCell(invoiceEntry.getItem());
      table.addCell(invoiceEntry.getPrice().toString());
      table.addCell(invoiceEntry.getPrice().toString());
      table.addCell(invoiceEntry.getVatValue().toString());
      table.addCell(invoiceEntry.getGrossValue().toString());
      table.addCell(invoiceEntry.getVatRate().toString());
    }
    return table;
  }

  private void addInvoiceEntriestableHeander(PdfPTable table, String[] headers){
    for(String heander: headers){
      PdfPCell header = new PdfPCell();
      header.setBackgroundColor(BaseColor.GRAY);
      header.setBorderWidth(2);
      header.setPhrase(new Phrase(heander));
      table.addCell(header);
    }
  }

  private Paragraph invoiceEntry(InvoiceEntry invoiceEntry){
//     PdfPCell cell = new PdfPCell();
//    PdfPCell id = new PdfPCell(new Paragraph(invoiceEntry.getId()));
//    PdfPCell item = new PdfPCell(new Paragraph(invoiceEntry.getItem()));
//    PdfPCell quantity = new PdfPCell(new Paragraph(invoiceEntry.getQuantity()));
//    PdfPCell price = new PdfPCell(new Paragraph(String.valueOf(invoiceEntry.getPrice())));
//    PdfPCell vatValue = new PdfPCell(new Paragraph(String.valueOf(invoiceEntry.getVatValue())));
//    PdfPCell grossValue = new PdfPCell(new Paragraph(String.valueOf(invoiceEntry.getGrossValue())));
//    PdfPCell vatRate = new PdfPCell(new Paragraph(String.valueOf(invoiceEntry.getVatRate())));
//    cell.addElement(id);
//    cell.addElement(item);
//    cell.addElement(quantity);
//    cell.addElement(price);
//    cell.addElement(vatValue);
//    cell.addElement(grossValue);
//    cell.addElement(vatRate);

    Paragraph id = new Paragraph(invoiceEntry.getId());
    Paragraph item = new Paragraph(invoiceEntry.getItem());
    Paragraph quantity = new Paragraph(invoiceEntry.getQuantity());
    Paragraph price = new Paragraph(String.valueOf(invoiceEntry.getPrice()));
    Paragraph vatValue = new Paragraph(String.valueOf(invoiceEntry.getVatValue()));
    Paragraph grossValue = new Paragraph(String.valueOf(invoiceEntry.getGrossValue()));
    Paragraph vatRate = new Paragraph(String.valueOf(invoiceEntry.getVatRate()));
    Paragraph paragraph = new Paragraph();
    paragraph.add(id);
    paragraph.add(item);
    paragraph.add(quantity);
    paragraph.add(price);
    paragraph.add(vatValue);
    paragraph.add(grossValue);
    paragraph.add(vatRate);
    return paragraph;
  }

  private Paragraph companySeller(Company company) {
    Paragraph seller = new Paragraph("Seller: ",
        FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
    Paragraph paragraph = new Paragraph();
    Paragraph name = new Paragraph(company.getName());
    Paragraph id = new Paragraph(company.getId());
    Paragraph taxIdentificationNumber = new Paragraph("Tax id : " + company.getTaxIdentificationNumber());
    paragraph.add(seller);
    paragraph.add(name);
    paragraph.add(id);
    paragraph.add(taxIdentificationNumber);
    paragraph.add(contactDetails(company.getContactDetails()));
    paragraph.add(accountNumber(company.getAccountNumber()));
    return paragraph;
  }

  private Paragraph companyBuyer(Company company) {
    Paragraph buyer = new Paragraph("Buyer: ",
        FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
    Paragraph name = new Paragraph(company.getName());
    Paragraph id = new Paragraph(company.getId());
    Paragraph taxIdentificationNumber = new Paragraph("Tax id : " + company.getTaxIdentificationNumber());
    Paragraph paragraph = new Paragraph();
    paragraph.add(buyer);
    paragraph.add(name);
    paragraph.add(id);
    paragraph.add(taxIdentificationNumber);
    Paragraph contactDetails = contactDetails(company.getContactDetails());
    paragraph.add(contactDetails);
    Paragraph accountNumber = accountNumber(company.getAccountNumber());
    paragraph.add(accountNumber);
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
