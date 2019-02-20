package pl.coderstrust.pdfsevice;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import pl.coderstrust.model.Company;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.model.InvoiceEntry;

@Service
public class PdfService {

  public byte[] createPdf(Invoice invoice) throws DocumentException {
    Document document = new Document();
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    PdfWriter.getInstance(document, stream);
    document.open();
    Paragraph paragraph = new Paragraph();
    paragraph.add(String.valueOf(invoice.getId()));
    paragraph.add(invoice.getNumber());
    paragraph.add(String.valueOf(invoice.getIssueDate()));
    paragraph.add(String.valueOf(invoice.getDueDate()));
    paragraph.add(invoiceEntries((InvoiceEntry) invoice.getEntries()).;
   // paragraph.add(String.valueOf(invoice.getBuyer()));
  //  paragraph.add(String.valueOf(invoice.getEntries()));
    paragraph.add(String.valueOf(invoice.getTotalNetValue()));
    paragraph.add(String.valueOf(invoice.getTotalGrossValue()));
    document.add(paragraph);
    document.close();
    return stream.toByteArray();
  }
  public PdfPTable invoiceEntries(InvoiceEntry invoiceEntry) throws DocumentException {
    Document document = new Document();
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    PdfWriter.getInstance(document, stream);
    PdfPTable table = new PdfPTable(7);
    document.open();
    table.addCell(String.valueOf(invoiceEntry.getId()));
    table.addCell(invoiceEntry.getItem());
    table.addCell(String.valueOf(invoiceEntry.getQuantity()));
    table.addCell(String.valueOf(invoiceEntry.getPrice()));
    table.addCell(String.valueOf(invoiceEntry.getVatValue()));
    table.addCell(String.valueOf(invoiceEntry.getGrossValue()));
    table.addCell(String.valueOf(invoiceEntry.getVatRate()));
    document.close();
    return table;
  }
}
