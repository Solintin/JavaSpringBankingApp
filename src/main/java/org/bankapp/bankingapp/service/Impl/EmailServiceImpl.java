package org.bankapp.bankingapp.service.Impl;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.bankapp.bankingapp.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;


@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailServiceImpl(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    @Override
    public void sendHtmlEmail(String to, String subject, Map<String, Object> variables, String emailTemplate) throws MessagingException {
        if (to == null || to.isEmpty()) {
            throw new IllegalArgumentException("Recipient email address cannot be null or empty");
        }
        if (subject == null || subject.isEmpty()) {
            throw new IllegalArgumentException("Email subject cannot be null or empty");
        }
        if (variables == null) {
            throw new IllegalArgumentException("Variables map cannot be null");
        }

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            // Populate the context with variables
            Context context = new Context();
            variables.forEach(context::setVariable);

            // Generate HTML body from template
            String htmlBody = templateEngine.process(emailTemplate, context);

            // Configure email details
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);

            // Send the email
            javaMailSender.send(mimeMessage);
            logger.info("Email sent successfully to {}", to);
        } catch (MailException e) {
            logger.error("Error sending email to {}: {}", to, e.getMessage());
            throw new MessagingException("Failed to send email", e);
        }
    }
}
