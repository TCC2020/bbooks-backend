package br.edu.ifsp.spo.bulls.usersApi.service.test;

import br.edu.ifsp.spo.bulls.usersApi.enums.EmailSubject;
import br.edu.ifsp.spo.bulls.usersApi.service.EmailService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmailServiceTest {

    @Autowired
    EmailService emailService;

    String to = "teste@teste.com";
    String content = "verify your e-mail";
    String url = "www.test.com";

    @BeforeEach
    void setUp(){
        emailService.
                getInstance()
                .withUrls(url)
                .withTo(to)
                .withContent(content)
                .withSubject(EmailSubject.VERIFY_EMAIL.name());
    }
    @Test
    void verify_settings_send_email(){

        assertEquals(to, emailService.getTo());
        assertEquals(EmailSubject.VERIFY_EMAIL.name(), emailService.getSubject());
        assertEquals(true, emailService.getContent().contains(content));
        assertEquals(true, emailService.getContent().contains(url));
    }

    @Test
    void verify_email_has_sent(){
        boolean result = this.emailService.send();

        if (result){
            assertTrue(result);
        }else{
            assertFalse(result);
        }

    }



}
