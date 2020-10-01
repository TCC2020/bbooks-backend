package br.edu.ifsp.spo.bulls.usersApi.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LoginTOTest {

    @Test
    void setLoginTo(){
        LoginTO login = new LoginTO("nome", "teste@teste", "senha");

        assertEquals("LoginTO(userName=nome, email=teste@teste, password=senha)", login.toString());
    }
}
