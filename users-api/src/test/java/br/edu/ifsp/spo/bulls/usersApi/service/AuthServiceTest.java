package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.bean.UserBeanUtil;
import br.edu.ifsp.spo.bulls.usersApi.domain.Profile;
import br.edu.ifsp.spo.bulls.usersApi.domain.User;
import br.edu.ifsp.spo.bulls.usersApi.dto.CadastroUserTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.LoginTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.ProfileTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserTO;
import br.edu.ifsp.spo.bulls.usersApi.enums.CodeException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceUnauthorizedException;
import br.edu.ifsp.spo.bulls.usersApi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.UUID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AuthServiceTest {

    @MockBean
    private UserRepository mockUserRepository;
    @Autowired
    private UserBeanUtil userBeanUtil;
    @Autowired
    private AuthService authservice;

    private CadastroUserTO cadastroUserTO;
    private LoginTO loginTo;
    private UserTO userTO = new UserTO();
    private User user = new User();
    private ProfileTO profileTO = new ProfileTO();
    private Profile profile = new Profile();

    @BeforeEach
    void setUp() {
        cadastroUserTO = new CadastroUserTO("testConfirm", "testeUp@confirm", "senhate", "nome", "sobrenome");
        loginTo = new LoginTO(cadastroUserTO.getUserName(),cadastroUserTO.getEmail(), cadastroUserTO.getPassword() );

        profileTO.setId(1);
        profileTO.setUsername(cadastroUserTO.getUserName());

        userTO.setUserName(cadastroUserTO.getUserName());
        userTO.setEmail(cadastroUserTO.getEmail());
        userTO.setId(UUID.randomUUID());
        userTO.setProfile(profileTO);

        user = userBeanUtil.toUser(userTO);
        user.setPassword(cadastroUserTO.getPassword());

        profile.setId(1);
        profile.setUser(user);
    }

    @Test
    void should_login() {

    }

    @Test
    void shouldnt_login_when_password_wrong() {

    }

    @Test
    void should_login_using_token() {

    }


    @Test
    void shouldnt_login_using_token_when_token_not_valid(){

    }


    @Test
    void should_confirm_user() {

    }

    @Test
    void should_send_reset_password_email(){

    }

    @Test
    void shouldnt_send_reset_password_email_user_not_found(){

    }

    @Test
    void should_reset_password(){

    }

    @Test
    void shouldnt_reset_password_user_not_found(){

    }

    @Test
    void should_get_by_token(){

    }

    @Test
    void should_get_by_token_when_user_not_found(){

    }

}
