import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendEmail {
	
	static String username = "filenotifierbot@gmail.com";
	static String password = "bot12345";
	
	static void sendMail(String input, String cusMessage) {
		Properties props = System.getProperties();
	    props.put("mail.smtp.starttls.enable", true); // added this line
	    props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.user", username);
	    props.put("mail.smtp.password", password);
	    props.put("mail.smtp.port", "587");
	    props.put("mail.smtp.auth", true);
	
	    Session session = Session.getInstance(props,null);
	    MimeMessage message = new MimeMessage(session);
	
	    // Create the email addresses involved
	    try {
	        InternetAddress from = new InternetAddress("BOT");
	        message.setFrom(from);
	        message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(input));
	
	        // Create a multi-part to combine the parts
	        Multipart multipart = new MimeMultipart("alternative");
	
	        // Create your text message part
	        BodyPart messageBodyPart = new MimeBodyPart();
	        messageBodyPart.setText(cusMessage);
	
	        // Add the text part to the multipart
	        multipart.addBodyPart(messageBodyPart);
	
	        // Create the html part
	        messageBodyPart = new MimeBodyPart();
	        String htmlMessage = "Our html text";
	        messageBodyPart.setContent(htmlMessage, "text/html");
	
	
	        // Add html part to multi part
	        multipart.addBodyPart(messageBodyPart);
	
	        // Associate multi-part with message
	        message.setContent(multipart);
	
	        // Send message
	        Transport transport = session.getTransport("smtp");
	        transport.connect("smtp.gmail.com", username, password);
	        System.out.println("- Sent Notification -");
	        transport.sendMessage(message, message.getAllRecipients());
	
	
	    } catch (AddressException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (MessagingException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	}
}
