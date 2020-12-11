package br.edu.ifsp.spo.bulls.users.api.controller;

import br.edu.ifsp.spo.bulls.users.api.bean.UserBeanUtil;
import br.edu.ifsp.spo.bulls.users.api.domain.Profile;
import br.edu.ifsp.spo.bulls.users.api.domain.User;
import br.edu.ifsp.spo.bulls.users.api.dto.LoginTO;
import br.edu.ifsp.spo.bulls.users.api.dto.ProfileTO;
import br.edu.ifsp.spo.bulls.users.api.dto.UserTO;
import br.edu.ifsp.spo.bulls.users.api.dto.RequestPassResetTO;
import br.edu.ifsp.spo.bulls.users.api.dto.CadastroUserTO;
import br.edu.ifsp.spo.bulls.users.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.users.api.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.users.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.users.api.exception.ResourceUnauthorizedException;
import br.edu.ifsp.spo.bulls.users.api.service.AuthService;
import br.edu.ifsp.spo.bulls.users.api.dto.ResetPassTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.UUID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService mockAuthService;

    @Autowired
    private UserBeanUtil userBeanUtil;

    private LoginTO loginTo;
    private UserTO userTO = new UserTO();
    private ProfileTO profileTO = new ProfileTO();
    private Profile profile = new Profile();
    private RequestPassResetTO requestPassResetTO = new RequestPassResetTO();
    private ResetPassTO resetPassTO = new ResetPassTO();

    @BeforeEach
    void setUp() {
        CadastroUserTO cadastroUserTO = new CadastroUserTO("testConfirm", "testeUp@confirm", "senhate", "nome", "sobrenome");
        loginTo = new LoginTO(cadastroUserTO.getUserName(),cadastroUserTO.getEmail(), cadastroUserTO.getPassword() );

        profileTO.setId(1);
        profileTO.setUsername(cadastroUserTO.getUserName());

        userTO.setUserName(cadastroUserTO.getUserName());
        userTO.setEmail(cadastroUserTO.getEmail());
        userTO.setId(UUID.randomUUID());
        userTO.setProfile(profileTO);

        User user = userBeanUtil.toUser(userTO);
        user.setPassword(cadastroUserTO.getPassword());

        profile.setId(1);
        profile.setUser(user);

        requestPassResetTO.setEmail(cadastroUserTO.getEmail());
        requestPassResetTO.setUrl("url");

        resetPassTO.setPassword(cadastroUserTO.getPassword());
        resetPassTO.setToken("token");


    }

    @Test
    void should_login() throws Exception {

        Mockito.when(mockAuthService.authLogin(loginTo)).thenReturn(userTO);

        mockMvc.perform(post("/auth/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginTo)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldnt_login_when_password_wrong() throws Exception {
        Mockito.when(mockAuthService.authLogin(loginTo)).thenThrow(new ResourceUnauthorizedException(CodeException.AT001.getText(), CodeException.AT001));

        mockMvc.perform(post("/auth/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginTo)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void should_login_using_token() throws Exception {

        Mockito.when(mockAuthService.authLoginToken(loginTo)).thenReturn(userTO);

        mockMvc.perform(post("/auth/login/token")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginTo)))
                .andExpect(status().isOk());
    }


    @Test
    void shouldnt_login_using_token_when_token_not_valid() throws Exception {
        Mockito.when(mockAuthService.authLoginToken(loginTo)).thenThrow(new ResourceUnauthorizedException(CodeException.AT001.getText(), CodeException.AT001));

        mockMvc.perform(post("/auth/login/token")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginTo)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void should_confirm_user() throws Exception {

        Mockito.when(mockAuthService.verified(loginTo)).thenReturn(userTO);

        mockMvc.perform(post("/auth/confirm")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginTo)))
                .andExpect(status().isOk());
    }

    @Test
    void should_send_reset_password_email() throws Exception {

        Mockito.doNothing().when(mockAuthService).sendResetPasswordEmail(requestPassResetTO.getEmail(), requestPassResetTO.getUrl());

        mockMvc.perform(post("/auth/reset-pass")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(requestPassResetTO)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldnt_send_reset_password_email_user_not_found() throws Exception{

        Mockito.doThrow(new ResourceNotFoundException(CodeException.US001.getText(), CodeException.US001)).when(mockAuthService).sendResetPasswordEmail(requestPassResetTO.getEmail(), requestPassResetTO.getUrl());

        mockMvc.perform(post("/auth/reset-pass")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(requestPassResetTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_reset_password() throws Exception {
        Mockito.when(mockAuthService.resetPass(resetPassTO)).thenReturn(userTO);

        mockMvc.perform(put("/auth/reset-pass")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(resetPassTO)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldnt_reset_password_user_not_found() throws Exception {
        Mockito.when(mockAuthService.resetPass(resetPassTO)).thenThrow(new ResourceConflictException(CodeException.US001.getText(), CodeException.US001));

        mockMvc.perform(put("/auth/reset-pass")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(resetPassTO)))
                .andExpect(status().isConflict());
    }

    @Test
    void should_get_by_token() throws Exception {
        Mockito.when(mockAuthService.getByToken(resetPassTO.getToken())).thenReturn(userTO);

        mockMvc.perform(get("/auth/reset-pass/" + resetPassTO.getToken())
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void should_get_by_token_when_user_not_found() throws Exception {

        Mockito.when(mockAuthService.getByToken(resetPassTO.getToken())).thenThrow(new ResourceNotFoundException(CodeException.US001.getText(), CodeException.US001));

        mockMvc.perform(get("/auth/reset-pass/" +resetPassTO.getToken())
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }
}
