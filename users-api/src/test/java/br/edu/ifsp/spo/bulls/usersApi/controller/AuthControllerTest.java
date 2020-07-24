package br.edu.ifsp.spo.bulls.usersApi.controller;

import br.edu.ifsp.spo.bulls.usersApi.dto.LoginTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.ProfileTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testLogin() throws Exception {
        UserTO userTo = new UserTO("testeLogin", "testeUp@login", "senhate", "nome", "sobrenome");
        LoginTO loginTo = new LoginTO(userTo.getUserName(),userTo.getEmail(), userTo.getPassword() );

        mockMvc.perform(post("/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userTo)));

        mockMvc.perform(post("/auth/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginTo)))
                .andExpect(status().isOk());
    }

    @Test
    void testLoginGoogle() throws Exception {
        UserTO userTo = new UserTO("1010101012291", "testegooglep@login", "1010101012291", "nome", "sobrenome");
        userTo.setUserName("1010101012291");
        userTo.setIdToken("ewrdsfqwefdsfqwefdsfwefwqefweerw");
        userTo.setToken("sdfoiwhefiodsfoiweahfoidsajfoiewajfoidsajfoiewajfoidsjfa");
        userTo.setIdSocial("1010101012291");
        mockMvc.perform(post("/auth/login/google")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userTo)))
                .andExpect(status().isOk());
    }

    @Test
    void testLoginPasswordFail() throws Exception {
        UserTO userTo = new UserTO("testeLogin", "testeUp@login", "senhate", "nome", "sobrenome");
        LoginTO loginTo = new LoginTO(userTo.getUserName(),userTo.getEmail(), "123" );

        mockMvc.perform(post("/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userTo)));

        mockMvc.perform(post("/auth/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginTo)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testConfirm() throws Exception {
        UserTO userTo = new UserTO("testConfirm", "testeUp@confirm", "senhate", "nome", "sobrenome");
        LoginTO loginTo = new LoginTO(userTo.getUserName(),userTo.getEmail(), userTo.getPassword() );

        mockMvc.perform(post("/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userTo)));

        mockMvc.perform(post("/auth/confirm")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginTo)))
                .andExpect(status().isOk());
    }
}
