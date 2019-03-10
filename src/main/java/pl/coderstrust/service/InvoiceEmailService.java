package pl.coderstrust.service;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.coderstrust.model.Invoice;

@Service
@PropertySource("classpath:mail.properties")
@ConfigurationProperties(prefix = "spring.mail")
public class InvoiceEmailService {

  private static Logger log = LoggerFactory.getLogger(InvoiceEmailService.class);
  private final JavaMailSender mailSender;
  private final InvoicePdfService invoicePdfService;
  private final MailProperties mailProperties;


  @Autowired
  public InvoiceEmailService(InvoicePdfService invoicePdfService, JavaMailSender mailSender, MailProperties mailProperties) {
    if (invoicePdfService == null) {
      throw new IllegalArgumentException("Invoice Pdf service cannot be null.");
    }
    if (mailSender == null) {
      throw new IllegalArgumentException("Mail sender cannot be null.");
    }
    if (mailProperties == null) {
      throw new IllegalArgumentException("Mail properties cannot be null.");
    }

    this.mailSender = mailSender;
    this.invoicePdfService = invoicePdfService;
    this.mailProperties = mailProperties;
  }

  @Async
  public void sendMailWithNewInvoice(Invoice invoice) {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice cannot be null.");
    }

    try {
      log.debug("Sending e-mail with invoice: {}", invoice);

      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setTo(mailProperties.getProperties().get("receiver"));
      helper.setSubject("New invoice has been added.");
      helper.setText(String.format("New Invoice with number: %s has been added to database.", invoice.getNumber()));

      helper.addAttachment(String.format("%s.pdf", invoice.getNumber()), new ByteArrayResource(invoicePdfService.createPdf(invoice)));

      mailSender.send(message);
    } catch (MessagingException | ServiceOperationException e) {
      log.error("Ann error occurred during sending e-mail with new invoice.", e);
    }
  }
}
