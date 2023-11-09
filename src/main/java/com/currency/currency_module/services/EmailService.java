package com.currency.currency_module.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;  // Use the Jakarta Mail MimeMessage

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;


    public void sendEmailWithAttachment(String to, String subject, String text, byte[] attachment, String attachmentName) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            helper.addAttachment(attachmentName, new ByteArrayResource(attachment));

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new EmailServiceException("Failed to send email with attachment", e);
        }
    }
    
}