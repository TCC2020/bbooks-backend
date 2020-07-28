package br.edu.ifsp.spo.bulls.usersApi.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import br.edu.ifsp.spo.bulls.usersApi.bean.UserBeanUtil;
import br.edu.ifsp.spo.bulls.usersApi.domain.User;
import br.edu.ifsp.spo.bulls.usersApi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.TransactionSystemException;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserTO;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceBadRequestException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceNotFoundException;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService service;


    @Test
    void testSave() throws ResourceBadRequestException, Exception {

        UserTO user = new UserTO("testeS", "testeS@teste12", "senhate", "nome", "sobrenome");

        UserTO user1 = service.save(user);

        // Testando campos obrigatorios
        assertEquals(user.getUserName(), user1.getUserName());
        assertEquals(user.getEmail(), user1.getEmail());
        assertEquals(user.getPassword(), user1.getPassword());
    }

    @Test
    void testSaveByGoogle() throws Exception {

        UserTO userTo = new UserTO("202002020220", "testegooglep@loginsave", "202002020220", "nome", "sobrenome");
        userTo.setIdToken("dsifjoewjfosdjfowef");
        userTo.setToken("dsfaewfdsfawfdsadfawefdfweff");
        userTo.setIdSocial("202002020220");
        UserTO user1;
        user1 = service.saveGoogle(userTo);
        assertEquals(userTo.getUserName(), user1.getUserName());
        assertEquals(userTo.getEmail(), user1.getEmail());
        assertEquals(userTo.getPassword(), user1.getPassword());
        assertEquals(userTo.getIdSocial(), user1.getIdSocial());
        assertEquals(userTo.getToken(), user1.getToken());
    }

    @Test
    void testFailSaveEmail() throws Exception {
        UserTO userUp = new UserTO("testeSEmail1234", "testeS12@teste", "senhate", "nome", "sobrenome");
        service.save(userUp);

        UserTO userUpEmail = new UserTO("testeSEmail123", "testeS12@teste", "senhate", "nome", "sobrenome");

        ResourceConflictException e = assertThrows(ResourceConflictException.class, () -> service.save(userUpEmail));
        assertEquals("Email ja esta sendo usado", e.getMessage());
    }

    @Test
    void testFailSaveUserName() throws Exception {
        UserTO userUp = new UserTO("testeSEmail", "testeS1@teste", "senhate", "nome", "sobrenome");
        service.save(userUp);

        UserTO userUpEmail = new UserTO("testeSEmail", "testeS2@teste", "senhate", "nome", "sobrenome");

        ResourceConflictException e = assertThrows(ResourceConflictException.class, () -> service.save(userUpEmail));
        assertEquals("UserName ja esta sendo usado", e.getMessage());
    }

    @Test
    void testFailSaveEmailMandatory() throws Exception {
        UserTO userUp = new UserTO();
        userUp.setPassword("senhateste");
        userUp.setUserName("testeSPasswordMandatory");

        assertThrows(TransactionSystemException.class, () -> service.save(userUp));
    }

    @Test
    void testFailSavePasswordMandatory() throws Exception {

        UserTO userUp = new UserTO();
        userUp.setEmail("testeS6@teste");
        userUp.setUserName("testeSPasswordMandatory");
        userUp.setPassword("");

        assertThrows(ResourceBadRequestException.class, () -> service.save(userUp));
    }

    @Test
    void testGetById() throws ResourceBadRequestException, Exception {

        UserTO user = new UserTO("testeGI", "testeGi@teste", "senhate", "nome", "sobrenome");
        user = service.save(user);
        UserTO user1 = service.getById(user.getId());

        // Testando se campos obrigatorios foram gravados corretamente

        assertEquals(user.getUserName(), user1.getUserName());
        assertEquals(user.getEmail(), user1.getEmail());
        assertEquals(user.getPassword(), user1.getPassword());
    }

    @Test
    void testFailGetByIdUserNotFound() {

        Throwable exception = assertThrows(ResourceNotFoundException.class, () -> service.getById(UUID.randomUUID()));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testUpdate() throws Exception {
        UserTO userUp = new UserTO("testeUp", "testeUp1@teste", "senhate", "nome", "sobrenome");

        UserTO u = service.save(userUp);

        u.setEmail("testUp2@teste");
        UserTO userUpdated = service.update(u);

        assertEquals("testUp2@teste", userUpdated.getEmail());
    }

    @Test
    void testFailUpdateUserNotFound() throws Exception {
        UserTO userUp = new UserTO("testeUpUser", "testeUp2@teste", "senhate");
        Throwable exception = assertThrows(ResourceNotFoundException.class, () -> service.update(userUp));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testFailUpdateEmail() throws Exception {
        UserTO userUp = service.save(new UserTO("testeUpEmail", "testeUp3@teste", "senhate", "nome", "sobrenome"));


        UserTO userUpEmail = new UserTO("testeUpEmail2", "testeUp4@teste", "senhate", "nome", "sobrenome");
        service.save(userUpEmail);

        userUp.setEmail("testeUp4@teste");
        Set<UserTO> us = service.getAll();

        assertThrows(ResourceConflictException.class, () -> service.update(userUp));
    }

    @Test
    void testFailUpdateEmailMandatory() throws Exception {
        UserTO userUp = new UserTO("testeUpEmailMandatory", "testeUp5@teste", "senhate", "nome", "sobrenome");
        UserTO u = service.save(userUp);

        u.setEmail("");

        assertThrows(TransactionSystemException.class, () -> service.update(u));
    }

    @Test
    void testFailUpdatePasswordMandatory() throws Exception {
        UserTO userUp = new UserTO("testeUpPasswordMandatory", "testeUp6@teste", "senhate", "nome", "sobrenome");
        UserTO u = service.save(userUp);

        u.setPassword("");

        assertThrows(ResourceBadRequestException.class, () -> service.update(u));
    }

    @Test
    void testDelete() throws ResourceBadRequestException, Exception {
        UserTO user = new UserTO("testeDel1", "testeDel1@teste", "senhate", "nome", "sobrenome");

        UserTO u = service.save(user);
        service.delete(u.getId());

        // Se realmente apagou o "getById" nao ira achar

        Throwable exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.getById(u.getId());
        });
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testFailDeleteUserNotFound() {

        Throwable exception = assertThrows(ResourceNotFoundException.class, () -> service.delete(UUID.randomUUID()));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testGetAll() throws ResourceBadRequestException, Exception {
        UserTO user = new UserTO("testeGA", "testeGA@teste", "senhate", "nome", "sobrenome");
        service.save(user);

        HashSet<UserTO> listaUser = service.getAll();

        assertFalse(listaUser.isEmpty());
    }


    @Test
    void testFindByToken() throws ResourceBadRequestException, Exception {
        UserTO user = new UserTO("testeDel", "testeDel@teste", "senhate", "nome", "sobrenome");
        user.setToken("123");
        service.save(user);

        Optional<org.springframework.security.core.userdetails.User> resposta = service.findByToken(user.getToken());

        assertTrue(resposta.isPresent());
    }

    @Test
    void testFailFindByToken() throws ResourceBadRequestException, Exception {

        Optional<org.springframework.security.core.userdetails.User> resposta = service.findByToken("erro");

        assertFalse(resposta.isPresent());
    }

}
