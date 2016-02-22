/*import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;




public class EmailConfirmation {

    public static void main(String[] args) {

        DesktopGUI desk = new DesktopGUI();

        String to = "oconnorjsean@aol.co.uk";  //desk.email.getText();
        String from = "softwaremongoose@gmail.com";
        final String username = "softwaremongoose";
        final String password = "mongoose123";


        System.setProperty("java.rmi.server.hostname","192.168.0.25");
        Properties properties = new Properties();
        properties.put("mail.stmp.auth", "true");
        properties.put("mail.stmp.starttls.enable", "true");
        properties.put("mail.stmp.host", "smtp.gmail.com");
        properties.put("mail.stmp.port", "587");


        Session session = Session.getInstance(properties, new javax.mail.Authenticator(){
            protected  PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject("Booking Confirmation at Mongoose Restaurant, Reference:"
                    + desk.referenceNumber);
            message.setText("Hello " + desk.customer.getText());
            message.setText("Your table is booked for " + desk.timePick.getSelectedItem().toString()
                    + " on: " + desk.dateField.getText());
            message.setText("We look forward to seeing you, reply to this email if you have any queries!");


            Transport.send(message);
            System.out.println("Confirmation Sent");

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }





    }


}

*/