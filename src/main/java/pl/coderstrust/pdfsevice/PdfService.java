package pl.coderstrust.pdfsevice;

import static com.itextpdf.text.DocWriter.NEWLINE;
import static com.itextpdf.text.PageSize.A4;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfString;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
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
    Paragraph companySeller = new Paragraph(companySeller(invoice.getSeller()));
    companySeller.setIndentationLeft(10);
    companySeller.setSpacingBefore(20);
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
    document.close();
    return stream.toByteArray();
  }

  public PdfPTable listInvoiceEntries(List<InvoiceEntry> invoiceEntries) throws DocumentException {
    PdfPTable table = new PdfPTable(7);
    table.setSpacingBefore(25);
    table.setSpacingAfter(25);
    for (InvoiceEntry invoiceEntry : invoiceEntries) {
      table.addCell(String.valueOf(invoiceEntry.getId()));
      table.addCell(invoiceEntry.getItem());
      table.addCell(String.valueOf(invoiceEntry.getQuantity()));
      table.addCell(String.valueOf(invoiceEntry.getPrice()));
      table.addCell(String.valueOf(invoiceEntry.getVatValue()));
      table.addCell(String.valueOf(invoiceEntry.getGrossValue()));
      table.addCell(String.valueOf(invoiceEntry.getVatRate()));
    }
    return table;
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
    Paragraph ibanNumber = new Paragraph(accountNumber.getIbanNumber());
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
    Paragraph addressData = new Paragraph(address.getCountry() + "/" + address.getCity() + "/" + address.getStreet() + "/" + address.getNumber());
    Paragraph postalCOde = new Paragraph("\nPostal code: " + address.getPostalCode());
    paragraph.add(addressData);
    paragraph.add(postalCOde);
    return paragraph;
  }
}