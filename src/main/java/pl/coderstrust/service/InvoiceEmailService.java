package pl.coderstrust.service;

import java.io.File;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceEmailService {

  private String host;
  private int port;
  private String username;
  private String password;

  @Autowired
  public InvoiceEmailService(String host, int port, String username, String password) {
    this.host = host;
    this.port = port;
    this.username = username;
    this.password = password;
    sendMail();
  }

  private void sendMail() {

    Properties prop = new Properties();
    prop.put("mail.smtp.host", host);
    prop.put("mail.smtp.port", port);
    prop.put("mail.smtp.auth", true);
    prop.put("mail.smtp.starttls.enable", "true");
    prop.put("mail.smtp.user", username);

    Session session = Session.getDefaultInstance(prop);

    try {

      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(username));
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("barbara.mrugalska@gmail.com"));
      message.setSubject("Mail from Invoice Database");

      String msg = "New Invoice was added";

      MimeBodyPart mimeBodyPart = new MimeBodyPart();
      mimeBodyPart.setContent(msg, "text/html");

      MimeBodyPart attachmentBodyPart = new MimeBodyPart();
      attachmentBodyPart.attachFile(new File("src/main/resources/mail.pdf"));

      Multipart multipart = new MimeMultipart();
      multipart.addBodyPart(mimeBodyPart);
      multipart.addBodyPart(attachmentBodyPart);

      message.setContent(multipart);

      Transport transport = session.getTransport("smtp");
      transport.connect(username, password);
      transport.sendMessage(message, message.getAllRecipients());
      transport.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String... args) {
    new InvoiceEmailService("smtp.gmail.com", 587, "group8.teamb", "kussmrroeuboqfdx");
  }
}
