package com.johnson.portfolio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // Set mail server properties programmatically if needed
        mailSender.setHost(System.getenv("portfolioMailServer"));
        mailSender.setPort(Integer.parseInt(System.getenv("portfolioMailPort")));
        mailSender.setUsername(System.getenv("portfolioMailEmail"));
        mailSender.setPassword(System.getenv("portfolioMailPassword"));

        System.out.println("**********");
        System.out.println(System.getenv("portfolioMailEmail"));
        System.out.println(System.getenv("portfolioMailPassword"));
        System.out.println("**********");

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", System.getenv("portfolioMailServer"));

        return mailSender;
    }
}
