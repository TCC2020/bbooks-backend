package br.edu.ifsp.spo.bulls.usersApi.service.impl;

import br.edu.ifsp.spo.bulls.usersApi.service.EmailContentBuilder;
import br.edu.ifsp.spo.bulls.usersApi.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDateTime;

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender emailSender;

    private Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    private EmailContentBuilder emailContentBuilder;

    private String to;
    private String subject;
    private StringBuilder content;

    @Autowired
    public EmailServiceImpl(EmailContentBuilder emailContentBuilder, JavaMailSender emailSender) {
        this.emailContentBuilder = emailContentBuilder;
        this.content = new StringBuilder();
        this.emailSender = emailSender;
    }
    public EmailServiceImpl getInstance() 	{
        this.to = "";
        this.subject = "";
        this.content.setLength(0);
        return this;
    }

    @Override
    public boolean send() {
        return this.sendEmailTo(
                this.to,
                this.subject,
                this.content.toString()
        );
    }

    @Override
    public boolean sendEmailTo(String to, String subject, String text) {
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setTo(to);
            message.setFrom("equipebull2020@gmail.com");
            message.setSubject(subject);
            String content = emailContentBuilder.build(text);
            message.setText(content, true);
        };

        try {
            this.emailSender.send(preparator);
            logger.info("Email has been sent to  "
                    + to + "  Hora: " + LocalDateTime.now());
            return true;
        }
        catch (MailException e) {
            e.printStackTrace();
            logger.error("Error while sending email to  "
                    + to + "  Hora: " + LocalDateTime.now());
            return false;
        }
    }

    @Override
    public EmailService withTo(String to) {
        this.to +=to;
        return this;
    }

    @Override
    public EmailService withSubject(String subject) {
        this.subject += subject;
        return this;
    }

    @Override
    public EmailService withUrls(String urls) {
        this.content.append(urls);
        return this;
    }
    @Override
    public EmailService withContent(String content) {
        this.content.append(content);
        return this;
    }

    @Override
    public String getTo() {
        return to;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    @Override
    public String getContent() {
        return content.toString();
    }
}
