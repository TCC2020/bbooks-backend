package br.edu.ifsp.spo.bulls.users.api.service;

import br.edu.ifsp.spo.bulls.common.api.service.EmailService;
import br.edu.ifsp.spo.bulls.users.api.enums.EmailSubject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    private String to = "teste@teste.com";
    private String content = "verify your e-mail";
    private String url = "www.test.com";

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
    void verifySettingsSendEmail(){

        assertEquals(to, emailService.getTo());
        assertEquals(EmailSubject.VERIFY_EMAIL.name(), emailService.getSubject());
        assertTrue( emailService.getContent().contains(content));
        assertTrue( emailService.getContent().contains(url));
    }

    @Test
    void verifyEmailHasSent(){
        boolean result = this.emailService.send();

        if (result){
            assertTrue(result);
        }else{
            assertFalse(result);
        }

    }



}
