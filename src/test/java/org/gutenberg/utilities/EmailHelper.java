package org.gutenberg.utilities;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class EmailHelper {
    public static void sendEmail(){

        final String username = FileReader.getProperty("username","configuration.properties");
        final String password = FileReader.getProperty("password","configuration.properties");

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            System.out.println("Preparing the E-mail content...");
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("abc@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("abc@gmail.com")
            );
            message.setSubject("Test Result");
            message.setText("Hello team,"
                    + "\n\n Test execution result is attached!");

            String  filePath = System.getProperty("user.dir") + "/target/default-cucumber-reports";
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(new File(filePath));

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            System.out.println("Email is sending...");
            Transport.send(message);

            System.out.println("Sent!");

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}