package com.eheiste.laureatnet.service;

public interface EmailService {
    void sendPasswordEmail(String to, String subject, String text);
}
