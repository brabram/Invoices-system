package pl.coderstrust.pdfsevice;

import java.io.ByteArrayOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import pl.coderstrust.model.Invoice;

public class PdfService {

  public byte[] createPdf(Invoice invoice) throws Exception {
    Document document = new Document();
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    PdfWriter.getInstance(document, stream);
    document.open();
    Paragraph paragraph = new Paragraph();
    paragraph.add(String.valueOf(invoice.getId()));
    paragraph.add(invoice.getNumber());
    paragraph.add(String.valueOf(invoice.getIssueDate()));
    paragraph.add(String.valueOf(invoice.getDueDate()));
    paragraph.add(String.valueOf(invoice.getSeller()));
    paragraph.add(String.valueOf(invoice.getBuyer()));
    paragraph.add(String.valueOf(invoice.getEntries()));
    paragraph.add(String.valueOf(invoice.getTotalNetValue()));
    paragraph.add(String.valueOf(invoice.getTotalGrossValue()));
    document.add(paragraph);
    document.close();
    return stream.toByteArray();
  }
}
