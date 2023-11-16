package com.evan.HTTPCatEmailer.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class EmailService {

    @Autowired
    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@HttpCatClone.org");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public Mono<Void> sendAttachmentMessage(String to, Mono<String> subject, String text, Mono<byte[]> imageAttachment) {
        return subject.flatMap(subjectString -> {
            return imageAttachment.flatMap(imageData -> {
                MimeMessage message = mailSender.createMimeMessage();
                try {
                    MimeMessageHelper helper = new MimeMessageHelper(message, true);
                    helper.setFrom("noreply@HttpCatClone.org");
                    helper.setTo(to);
                    helper.setSubject(subjectString);
                    helper.setText(text);
                    helper.addAttachment("statusImage", new ByteArrayResource(imageData), "image/jpeg");
                    mailSender.send(message);
                    return Mono.empty();
                } catch (MessagingException e) {
                    return Mono.error(e);
                }
            });
        });
    }
}
