package br.edu.ifsp.spo.bulls.users.api.controller;

import br.edu.ifsp.spo.bulls.users.api.bean.UserBeanUtil;
import br.edu.ifsp.spo.bulls.users.api.domain.Profile;
import br.edu.ifsp.spo.bulls.users.api.domain.User;
import br.edu.ifsp.spo.bulls.users.api.dto.LoginTO;
import br.edu.ifsp.spo.bulls.common.api.dto.ProfileTO;
import br.edu.ifsp.spo.bulls.common.api.dto.UserTO;
import br.edu.ifsp.spo.bulls.users.api.dto.RequestPassResetTO;
import br.edu.ifsp.spo.bulls.users.api.dto.CadastroUserTO;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceUnauthorizedException;
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
    private final UserTO userTO = new UserTO();
    private final ProfileTO profileTO = new ProfileTO();
    private final Profile profile = new Profile();
    private final RequestPassResetTO requestPassResetTO = new RequestPassResetTO();
    private final ResetPassTO resetPassTO = new ResetPassTO();

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
    void shouldLogin() throws Exception {

        Mockito.when(mockAuthService.authLogin(loginTo)).thenReturn(userTO);

        mockMvc.perform(post("/auth/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginTo)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldntLoginWhenPasswordWrong() throws Exception {
        Mockito.when(mockAuthService.authLogin(loginTo)).thenThrow(new ResourceUnauthorizedException(CodeException.AT001.getText(), CodeException.AT001));

        mockMvc.perform(post("/auth/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginTo)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldLoginUsingToken() throws Exception {

        Mockito.when(mockAuthService.authLoginToken(loginTo)).thenReturn(userTO);

        mockMvc.perform(post("/auth/login/token")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginTo)))
                .andExpect(status().isOk());
    }


    @Test
    void shouldntLoginUsingTokenWhenTokenNotValid() throws Exception {
        Mockito.when(mockAuthService.authLoginToken(loginTo)).thenThrow(new ResourceUnauthorizedException(CodeException.AT001.getText(), CodeException.AT001));

        mockMvc.perform(post("/auth/login/token")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginTo)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldConfirmUser() throws Exception {

        Mockito.when(mockAuthService.verified(loginTo)).thenReturn(userTO);

        mockMvc.perform(post("/auth/confirm")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginTo)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldSendResetPasswordEmail() throws Exception {

        Mockito.doNothing().when(mockAuthService).sendResetPasswordEmail(requestPassResetTO.getEmail(), requestPassResetTO.getUrl());

        mockMvc.perform(post("/auth/reset-pass")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(requestPassResetTO)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldntSendResetPasswordEmailUserNotFound() throws Exception{

        Mockito.doThrow(new ResourceNotFoundException(CodeException.US001.getText(), CodeException.US001)).when(mockAuthService).sendResetPasswordEmail(requestPassResetTO.getEmail(), requestPassResetTO.getUrl());

        mockMvc.perform(post("/auth/reset-pass")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(requestPassResetTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldResetPassword() throws Exception {
        Mockito.when(mockAuthService.resetPass(resetPassTO)).thenReturn(userTO);

        mockMvc.perform(put("/auth/reset-pass")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(resetPassTO)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldntResetPasswordUserNotFound() throws Exception {
        Mockito.when(mockAuthService.resetPass(resetPassTO)).thenThrow(new ResourceConflictException(CodeException.US001.getText(), CodeException.US001));

        mockMvc.perform(put("/auth/reset-pass")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(resetPassTO)))
                .andExpect(status().isConflict());
    }

    @Test
    void shouldGetByToken() throws Exception {
        Mockito.when(mockAuthService.getByToken(resetPassTO.getToken())).thenReturn(userTO);

        mockMvc.perform(get("/auth/reset-pass/" + resetPassTO.getToken())
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetByTokenWhenUserNotFound() throws Exception {

        Mockito.when(mockAuthService.getByToken(resetPassTO.getToken())).thenThrow(new ResourceNotFoundException(CodeException.US001.getText(), CodeException.US001));

        mockMvc.perform(get("/auth/reset-pass/" +resetPassTO.getToken())
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }
}
