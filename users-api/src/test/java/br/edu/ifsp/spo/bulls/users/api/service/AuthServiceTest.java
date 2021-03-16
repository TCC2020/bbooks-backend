package br.edu.ifsp.spo.bulls.users.api.service;

import br.edu.ifsp.spo.bulls.common.api.dto.ProfileTO;
import br.edu.ifsp.spo.bulls.common.api.dto.UserTO;
import br.edu.ifsp.spo.bulls.users.api.bean.UserBeanUtil;
import br.edu.ifsp.spo.bulls.users.api.domain.Profile;
import br.edu.ifsp.spo.bulls.users.api.domain.User;
import br.edu.ifsp.spo.bulls.users.api.dto.*;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceUnauthorizedException;
import br.edu.ifsp.spo.bulls.users.api.repository.UserRepository;
import br.edu.ifsp.spo.bulls.users.api.service.impl.EmailServiceImpl;
import br.edu.ifsp.spo.bulls.users.api.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AuthServiceTest {

    @MockBean
    private UserRepository mockUserRepository;
    @MockBean
    private ProfileRepository mockProfileRepository;
    @Autowired
    private UserBeanUtil userBeanUtil;
    @Autowired
    private AuthService authService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    private LoginTO loginTo;
    private final UserTO userTO = new UserTO();
    private User user = new User();
    private User userSenhaDiferente;
    private User userTokenNull;
    private final ProfileTO profileTO = new ProfileTO();
    private final Profile profile = new Profile();
    private final RequestPassResetTO requestPassResetTO = new RequestPassResetTO();
    private final ResetPassTO resetPassTO = new ResetPassTO();

    @BeforeEach
    void setUp() {
        CadastroUserTO cadastroUserTO = new CadastroUserTO("testConfirm", "testeUp@confirm", "senhate", "nome", "sobrenome");
        loginTo = new LoginTO(cadastroUserTO.getUserName(),cadastroUserTO.getEmail(), cadastroUserTO.getPassword() );
        loginTo.setToken("token");

        profileTO.setId(1);
        profileTO.setUsername(cadastroUserTO.getUserName());

        userTO.setUserName(cadastroUserTO.getUserName());
        userTO.setEmail(cadastroUserTO.getEmail());
        userTO.setId(UUID.randomUUID());
        userTO.setProfile(profileTO);
        userTO.setToken(loginTo.getToken());

        user = userBeanUtil.toUser(userTO);
        user.setPassword(bCryptPasswordEncoder.encode(loginTo.getPassword()));
        user.setToken(loginTo.getToken());
        user.setEmail(userTO.getEmail());

        userSenhaDiferente = userBeanUtil.toUser(userTO);
        userSenhaDiferente.setId(UUID.randomUUID());
        userSenhaDiferente.setPassword("123");
        userSenhaDiferente.setToken("token3333");

        userTokenNull = userBeanUtil.toUser(userTO);
        userTokenNull.setPassword(bCryptPasswordEncoder.encode(loginTo.getPassword()));
        userTokenNull.setToken(null);

        profile.setId(1);
        profile.setUser(user);

        requestPassResetTO.setEmail(cadastroUserTO.getEmail());
        requestPassResetTO.setUrl("url");

        resetPassTO.setPassword(cadastroUserTO.getPassword());
        resetPassTO.setToken(loginTo.getToken());
    }

    @Test
    void shouldLogin() {
        Mockito.when(mockUserRepository.findByEmail(loginTo.getEmail())).thenReturn(Optional.of(user));
        Mockito.when(mockProfileRepository.findByUser(user)).thenReturn(profile);
        UserTO userTOResult = authService.authLogin(loginTo);
        assertEquals(userTO, userTOResult);
    }

    @Test
    void shouldntLoginWhenPasswordWrong() {
        Mockito.when(mockUserRepository.findByEmail(userSenhaDiferente.getEmail())).thenReturn(Optional.of(userSenhaDiferente));
        assertThrows(ResourceUnauthorizedException.class, () -> authService.authLogin(loginTo));
    }

    @Test
    void shouldntLoginWhenUserNotFound() {
        Mockito.when(mockUserRepository.findByEmail(userSenhaDiferente.getEmail())).thenReturn(Optional.ofNullable(null));
        assertThrows(ResourceUnauthorizedException.class, () -> authService.authLogin(loginTo));
    }

    @Test
    void shouldLoginWhenUserTokenIsNull() {
        Mockito.when(mockUserRepository.findByEmail(userTokenNull.getEmail())).thenReturn(Optional.ofNullable(userTokenNull));
        Mockito.when(mockProfileRepository.findByUser(userTokenNull)).thenReturn(profile);
        assertNull(userTokenNull.getToken());
        UserTO result = authService.authLogin(loginTo);
        assertNotNull(result.getToken());
    }

    @Test
    void shouldLoginUsingToken() {
        Mockito.when(mockUserRepository.findByEmail(loginTo.getEmail())).thenReturn(Optional.of(user));
        Mockito.when(mockProfileRepository.findByUser(user)).thenReturn(profile);
        UserTO userTOResult = authService.authLoginToken(loginTo);
        assertNotEquals(userTO.getToken(), userTOResult.getToken());
    }

    @Test
    void shouldntLoginUsingTokenWhenTokenNotFound(){
        Mockito.when(mockUserRepository.findByEmail(loginTo.getEmail())).thenReturn(Optional.of(userSenhaDiferente));
        assertThrows(ResourceNotFoundException.class,() -> authService.authLoginToken(loginTo));
    }

    @Test
    void shouldntLoginUsingTokenWhenUserNotFound(){
        Mockito.when(mockUserRepository.findByEmail(loginTo.getEmail())).thenReturn(Optional.ofNullable(null));
        assertThrows(ResourceUnauthorizedException.class,() -> authService.authLoginToken(loginTo));
    }

    @Test
    void shouldConfirmUser() throws Exception {
        Mockito.when(mockUserRepository.findByEmail(loginTo.getEmail())).thenReturn(Optional.ofNullable(user));
        Mockito.when(mockProfileRepository.findByUser(user)).thenReturn(profile);
        UserTO userTOResult = authService.verified(loginTo);
        assertTrue(userTOResult.getVerified());
    }

    @Test
    void shouldntConfirmUserWhenPasswordWrong() {
        Mockito.when(mockUserRepository.findByEmail(userSenhaDiferente.getEmail())).thenReturn(Optional.of(userSenhaDiferente));
        assertThrows(ResourceUnauthorizedException.class, () -> authService.verified(loginTo));
    }

    @Test
    void shouldntConfirmUserWhenUserNotFound() {
        Mockito.when(mockUserRepository.findByEmail(userSenhaDiferente.getEmail())).thenReturn(Optional.ofNullable(null));
        assertThrows(ResourceUnauthorizedException.class, () -> authService.verified(loginTo));
    }

    @Test
    void shouldConfirmUserWhenTokenNull() throws Exception {
        Mockito.when(mockUserRepository.findByEmail(userTokenNull.getEmail())).thenReturn(Optional.ofNullable(userTokenNull));
        Mockito.when(mockProfileRepository.findByUser(userTokenNull)).thenReturn(profile);
        assertNull(userTokenNull.getToken());
        UserTO result = authService.verified(loginTo);
        assertNotNull(result.getToken());
    }

    @Test
    void shouldntSendResetPasswordEmailUserNotFound(){
        Mockito.when(mockUserRepository.findByEmail(requestPassResetTO.getEmail())).thenThrow(new ResourceNotFoundException(CodeException.US001.getText(), CodeException.US001));
        assertThrows(ResourceNotFoundException.class, () -> authService.sendResetPasswordEmail(requestPassResetTO.getEmail(), requestPassResetTO.getUrl()));
    }

    @Test
    void shouldResetPassword(){
        Mockito.when(mockUserRepository.findByToken(resetPassTO.getToken())).thenReturn(Optional.of(user));
        Mockito.when(mockUserRepository.save(user)).thenReturn(user);
        Mockito.when(mockProfileRepository.findByUser(user)).thenReturn(profile);

        UserTO result = authService.resetPass(resetPassTO);
        assertNotEquals(userTO.getToken(), result.getToken());
    }

    @Test
    void shouldntResetPasswordUserNotFound(){
        Mockito.when(mockUserRepository.findByToken(resetPassTO.getToken())).thenThrow(new ResourceNotFoundException(CodeException.US001.getText(), CodeException.US001));
        assertThrows(ResourceNotFoundException.class, () -> authService.resetPass(resetPassTO));
    }

    @Test
    void shouldGetByToken(){
        Mockito.when(mockUserRepository.findByToken(resetPassTO.getToken())).thenReturn(Optional.of(user));
        Mockito.when(mockProfileRepository.findByUser(user)).thenReturn(profile);
        UserTO userTOResult = authService.getByToken(resetPassTO.getToken());
        assertEquals(userTO, userTOResult);
    }

    @Test
    void shouldGetByTokenWhenUserNotFound(){
        Mockito.when(mockUserRepository.findByToken(resetPassTO.getToken())).thenThrow(new ResourceNotFoundException(CodeException.US001.getText(), CodeException.US001));
        assertThrows(ResourceNotFoundException.class, () -> authService.getByToken(resetPassTO.getToken()));
    }

}
