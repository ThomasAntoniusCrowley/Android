import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;




public class EmailConfirmation {

    public static void main(String[] args) {

        DesktopGUI desk = new DesktopGUI();


        String to = desk.email.getText();
        String from = "mongooserestaurant@gmail.com";
        String host = "localhost";

        Properties properties = System.getProperties();
        properties.setProperty("mail.stmp.host", host);

        Session session = Session.getDefaultInstance(properties);

        try {

            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Booking Confirmation at Mongoose Restaurant, Reference:"
                    + desk.referenceNumber);


        } catch (MessagingException mex) {
            mex.printStackTrace();
        }





    }


}

