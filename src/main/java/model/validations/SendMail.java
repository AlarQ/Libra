package model.validations;

import model.elements.User;

import java.util.Properties;

import javax.jws.soap.SOAPBinding;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {

    public static void sendMail(String from,User user)
    {
        String text = prepareMessage(user);
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(prepareConfig());

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));

            // Set Subject: header field
            message.setSubject("Password recover");

            // Now set the actual message
            message.setText(text);

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }


    public static Session prepareConfig()
    {
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();

        String userName = "libra.service.supp@gmail.com";
        String password = "Bulka123";
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.user", userName);
        properties.put("mail.password", password);
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(userName, password);

            }

        });

        session.setDebug(true);
        return session;


    }
    public static String prepareMessage(User user)
    {
        return "Hello, "+user.getLogin()+"!\n\n"
                +"This is your forgotten password: "+user.getPassword()+"\n\n"
                +"Regards, \nLibra Support";
    }
}