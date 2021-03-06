package br.edu.ifsp.spo.bulls.users.api.service;

public interface EmailService {
    EmailService getInstance();
    boolean send();
    boolean sendEmailTo(String to, String subject, String text, String title, String action, String link);
    EmailService withTo(String to);
    EmailService withSubject(String subject);
    EmailService withUrls(String urls);
    EmailService withContent(String content);
    EmailService withTitle(String title);
    EmailService withAction(String action);
    EmailService withLink(String link);

    String getTo();
    String getSubject();
    String getContent();
}
