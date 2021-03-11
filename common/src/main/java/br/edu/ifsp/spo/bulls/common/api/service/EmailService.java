package br.edu.ifsp.spo.bulls.common.api.service;
import br.edu.ifsp.spo.bulls.common.api.dto.ExchangeTO;
public interface EmailService {
    EmailService getInstance();
    boolean send();
    boolean sendEmailTo(String to, String subject, String text, String title, String action, String link, ExchangeTO exchange);
    EmailService withTo(String to);
    EmailService withSubject(String subject);
    EmailService withUrls(String urls);
    EmailService withContent(String content);
    EmailService withTitle(String title);
    EmailService withAction(String action);
    EmailService withLink(String link);
    EmailService withExchangeTo(ExchangeTO exchangeTO );
    String getTo();
    String getSubject();
    String getContent();
}
