package com.eheiste.laureatnet.service.Impl;

import com.eheiste.laureatnet.exception.exceptions.InternalServerErrorException;
import com.eheiste.laureatnet.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender emailSender;

    @Async
    @Override
    public void sendPasswordEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            // message.setFrom("noreply@baeldung.com");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            emailSender.send(message);
        } catch (Exception e) {
            throw new InternalServerErrorException("Couldn't send an email to the created user.");
        }
    }
}
