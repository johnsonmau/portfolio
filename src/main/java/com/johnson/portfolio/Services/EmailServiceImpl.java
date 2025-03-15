package com.johnson.portfolio.Services;

import com.johnson.portfolio.Models.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendSimpleMessage(String to, Contact contact) {
        String fullName = contact.getFirstName() + " " + contact.getLastName();

        String contactHtmlContent = "<html>" +
                "<body>" +
                "<h2>New Consultation Request</h2>" +
                "<p><strong>Full Name:</strong> " + fullName + "</p>" +
                "<p><strong>Email:</strong> " + contact.getEmail() + "</p>" +
                "<p><strong>Message:</strong></p>" +
                "<p>" + contact.getMessageBody() + "</p>" +
                "</body>" +
                "</html>";

        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject("CONSULTATION CONTACT: From: " + contact.getEmail());
            helper.setText(contactHtmlContent, true);
            helper.setFrom(fromEmail);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        //send consultation html email
        try {
            mailSender.send(message);
            logger.info("Consultation contact email sent to {}", to);
        } catch (Exception e) {
            logger.error("Failed to send consultation contact email to {}: {}", to, e.getMessage());
        }

        //send confirmation email
        String htmlContent = "Error creating HTML content. Request still received.";

        try {
            try {
                htmlContent = loadHtmlTemplate("templates/email.html");
                logger.info("HTML email template loaded successfully.");
            } catch (IOException e) {
                logger.error("Error loading email HTML template: {}", e.getMessage());
            }
            sendHtmlEmail(contact.getEmail(), "Message Received!", htmlContent);
        } catch (MessagingException e) {
            logger.error("Error sending HTML email to {}: {}", contact.getEmail(), e.getMessage());
        }
    }

    private String loadHtmlTemplate(String filePath) throws IOException {
        Path path = Paths.get(new ClassPathResource(filePath).getURI());
        return new String(Files.readAllBytes(path));
    }

    public void sendHtmlEmail(String to, String subject, String htmlContent) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);
        helper.setFrom(fromEmail);
        mailSender.send(message);

        logger.info("HTML email sent to {}", to);
    }
}
