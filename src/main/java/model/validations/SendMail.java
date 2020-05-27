package model.validations;

import model.Main;
import model.elements.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMail
{

    public static void sendMail(String from, User user) {
        String text = prepareMessage(user);
        try {
            MimeMessage message = new MimeMessage(prepareConfig());
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            message.setSubject("Password recover");
            message.setText(text);
            System.out.println("sending...");
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }


    public static Session prepareConfig() {
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();

        String userName = Main.properties.getProperty("email.username");
        String password = Main.properties.getProperty("email.pass");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.user", userName);
        properties.put("mail.password", password);
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator()
        {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(userName, password);
            }

        });
        session.setDebug(true);
        return session;
    }

    public static String prepareMessage(User user) {
        return "Hello, " + user.getLogin() + "!\n\n"
                + "This is your forgotten password: " + user.getPassword() + "\n\n"
                + "Regards, \nLibra Support";
    }
}