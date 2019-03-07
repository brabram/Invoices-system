package pl.coderstrust.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.coderstrust.model.Invoice;

@Service
@PropertySource("classpath:mail.properties")
@ConfigurationProperties(prefix = "spring.mail")
public class InvoiceEmailService {

  private static Logger log = LoggerFactory.getLogger(InvoiceEmailService.class);
  private String host;
  private int port;
  private String username;
  private String password;
  private String sendTo;
  private InvoicePdfService invoicePdfService = new InvoicePdfService();
  private String filePath = "src/main/resources/mail.pdf";
  private FileOutputStream writer = new FileOutputStream(new File(filePath));
  private File file = new File(filePath);

  public InvoiceEmailService() throws FileNotFoundException {
  }

  @Async
  public CompletableFuture<Transport> sendMail(Invoice invoice) throws NoSuchProviderException {
    Session session = getSession();
    try {
      log.debug("Sending email with PDF for invoice. Invoice id: {}", invoice.getId());
      Transport transport = getTransport(invoice, session);
      return CompletableFuture.completedFuture(transport);
    } catch (Exception e) {
      String message = String.format("An error occurred during sending email with PDF for invoice. Invoice id: %d", invoice.getId());
      log.error(message, e);
      throw new NoSuchProviderException(String.format(message, invoice.getId()), e);
    }
  }

  private Transport getTransport(Invoice invoice, Session session) throws MessagingException, IOException, ServiceOperationException, InterruptedException {
    Message message = new MimeMessage(session);
    message.setFrom(new InternetAddress(username));
    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendTo));
    message.setSubject("Mail from Invoice Database");
    String msg = "New Invoice was added";
    MimeBodyPart mimeBodyPart = new MimeBodyPart();
    mimeBodyPart.setContent(msg, "text/html");
    MimeBodyPart attachmentBodyPart = new MimeBodyPart();
    file.createNewFile();
    writer.write(invoicePdfService.createPdf(invoice));
    attachmentBodyPart.attachFile(new File(filePath));
    Multipart multipart = new MimeMultipart();
    multipart.addBodyPart(mimeBodyPart);
    multipart.addBodyPart(attachmentBodyPart);
    message.setContent(multipart);
    Transport transport = session.getTransport("smtp");
    transport.connect(username, password);
    Thread.sleep(1000L);
    transport.sendMessage(message, message.getAllRecipients());
    transport.close();
    file.delete();
    writer.close();
    return transport;
  }

  private Session getSession() {
    Properties prop = new Properties();
    prop.put("mail.smtp.host", host);
    prop.put("mail.smtp.port", port);
    prop.put("mail.smtp.auth", true);
    prop.put("mail.smtp.starttls.enable", "true");
    prop.put("mail.smtp.user", username);
    return Session.getDefaultInstance(prop);
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getSendTo() {
    return sendTo;
  }

  public void setSendTo(String sentTo) {
    this.sendTo = sentTo;
  }
}
