package br.edu.ifsp.spo.bulls.users.api.service;

public interface EmailService {
    EmailService getInstance();
    boolean send();
    boolean sendEmailTo(String to, String subject, String text);
    EmailService withTo(String to);
    EmailService withSubject(String subject);
    EmailService withUrls(String urls);
    EmailService withContent(String content);

    String getTo();
    String getSubject();
    String getContent();
}
