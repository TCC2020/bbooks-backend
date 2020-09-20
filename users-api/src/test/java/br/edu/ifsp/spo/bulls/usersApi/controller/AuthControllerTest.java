package br.edu.ifsp.spo.bulls.usersApi.controller;

import br.edu.ifsp.spo.bulls.usersApi.dto.CadastroUserTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.LoginTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.ProfileTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserTO;
import br.edu.ifsp.spo.bulls.usersApi.service.UserService;
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

    @Autowired
    private UserService userService;

    @Test
    void testLogin() throws Exception {
        CadastroUserTO userTo = new CadastroUserTO("testexLogxin", "tesxteUp@login", "senhate", "nome", "sobrenome");
        LoginTO loginTo = new LoginTO(userTo.getUserName(),userTo.getEmail(), userTo.getPassword() );

        userService.save(userTo);

        mockMvc.perform(post("/auth/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginTo)))
                .andExpect(status().isOk());
    }

    @Test
    void testLoginPasswordFail() throws Exception {
        CadastroUserTO userTo = new CadastroUserTO("testeLogin", "testeUp@login", "senhate", "nome", "sobrenome");
        LoginTO loginTo = new LoginTO(userTo.getUserName(),userTo.getEmail(), "123" );

        userService.save(userTo);

        mockMvc.perform(post("/auth/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginTo)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testConfirm() throws Exception {
        CadastroUserTO userTo = new CadastroUserTO("testConfirm", "testeUp@confirm", "senhate", "nome", "sobrenome");
        LoginTO loginTo = new LoginTO(userTo.getUserName(),userTo.getEmail(), userTo.getPassword() );

        userService.save(userTo);

        mockMvc.perform(post("/auth/confirm")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginTo)))
                .andExpect(status().isOk());
    }
}
