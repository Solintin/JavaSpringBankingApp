package org.bankapp.bankingapp.service;


import jakarta.mail.MessagingException;

import java.util.Map;

public interface EmailService {
    void sendHtmlEmail(String to, String subject, Map<String, Object> variables, String emailTemplate) throws MessagingException;
}

