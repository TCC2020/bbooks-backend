package br.edu.ifsp.spo.bulls.usersApi.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import br.edu.ifsp.spo.bulls.usersApi.bean.UserBeanUtil;
import br.edu.ifsp.spo.bulls.usersApi.dto.CadastroUserTO;
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

    @Autowired
    private UserBeanUtil userBeanUtil;


    @Test
    void testSave() throws ResourceBadRequestException, Exception {

        CadastroUserTO user = new CadastroUserTO("testeS", "testeS@teste12", "senhate", "nome", "sobrenome");

        UserTO user1 = service.save(user);

        // Testando campos obrigatorios
        assertEquals(user.getUserName().toLowerCase(), user1.getUserName());
        assertEquals(user.getEmail(), user1.getEmail());
    }

    @Test
    void testFailSaveEmail() throws Exception {
        CadastroUserTO userUp = new CadastroUserTO("testeSEmail1234", "testeS12@teste", "senhate", "nome", "sobrenome");
        service.save(userUp);

        CadastroUserTO userUpEmail = new CadastroUserTO("testeSEmail123", "testeS12@teste", "senhate", "nome", "sobrenome");

        ResourceConflictException e = assertThrows(ResourceConflictException.class, () -> service.save(userUpEmail));
        assertEquals("Email ja esta sendo usado testeS12@teste", e.getMessage());
    }

    @Test
    void testFailSaveUserName() throws Exception {
        CadastroUserTO userUp = new CadastroUserTO("testeSEmail", "testeS1@teste", "senhate", "nome", "sobrenome");
        service.save(userUp);

        CadastroUserTO userUpEmail = new CadastroUserTO("testeSEmail", "testeS2@teste", "senhate", "nome", "sobrenome");

        ResourceConflictException e = assertThrows(ResourceConflictException.class, () -> service.save(userUpEmail));
        assertEquals("UserName ja esta sendo usado testesemail", e.getMessage());
    }

    @Test
    void testFailSaveEmailMandatory() throws Exception {
        CadastroUserTO userUp = new CadastroUserTO();
        userUp.setPassword("senhateste");
        userUp.setUserName("testeSPasswordMandatory");

        assertThrows(TransactionSystemException.class, () -> service.save(userUp));
    }

    @Test
    void testFailSavePasswordMandatory() throws Exception {

        CadastroUserTO userUp = new CadastroUserTO();
        userUp.setEmail("testeS6@teste");
        userUp.setUserName("testeSPasswordMandatory");
        userUp.setPassword("");

        assertThrows(ResourceBadRequestException.class, () -> service.save(userUp));
    }

    @Test
    void testGetById() throws ResourceBadRequestException, Exception {

        CadastroUserTO cadastroUserTO = new CadastroUserTO("testeGI", "testeGi@teste", "senhate", "nome", "sobrenome");
        UserTO user = service.save(cadastroUserTO);
        UserTO user1 = service.getById(user.getId());

        // Testando se campos obrigatorios foram gravados corretamente
        assertEquals(user.getUserName(), user1.getUserName());
        assertEquals(user.getEmail(), user1.getEmail());
    }

    @Test
    void testFailGetByIdUserNotFound() {

        Throwable exception = assertThrows(ResourceNotFoundException.class, () -> service.getById(UUID.randomUUID()));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testUpdate() throws Exception {
        CadastroUserTO userUp = new CadastroUserTO("testeUppppp", "testeUp1@teste", "senhate", "nome", "sobrenome");

        UserTO u = service.save(userUp);

        u.setEmail("testUp2@testeeeee");
        UserTO userUpdated = service.update(userBeanUtil.toCadastroTO(u));

        assertEquals("testUp2@testeeeee", userUpdated.getEmail());
    }

    @Test
    void testFailUpdateUserNotFound() throws Exception {
        CadastroUserTO userUp = new CadastroUserTO("testeUpUser", "testeUp2@teste", "senhate");
        Throwable exception = assertThrows(ResourceNotFoundException.class, () -> service.update(userUp));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testFailUpdateEmail() throws Exception {
        UserTO userUp = service.save(new CadastroUserTO("testeUpEmail", "testeUp3@teste", "senhate", "nome", "sobrenome"));


        CadastroUserTO userUpEmail = new CadastroUserTO("testeUpEmail2", "testeUp4@teste", "senhate", "nome", "sobrenome");
        service.save(userUpEmail);

        userUp.setEmail("testeUp4@teste");
        Set<UserTO> us = service.getAll();

        assertThrows(ResourceConflictException.class, () -> service.update(userBeanUtil.toCadastroTO(userUp)));
    }

    @Test
    void testFailUpdateEmailMandatory() throws Exception {
        CadastroUserTO userUp = new CadastroUserTO("testeUpEmailMandatory", "testeUp5@teste", "senhate", "nome", "sobrenome");
        UserTO u = service.save(userUp);

        u.setEmail("");

        assertThrows(TransactionSystemException.class, () -> service.update(userBeanUtil.toCadastroTO(u)));
    }

    @Test
    void testFailUpdatePasswordMandatory() throws Exception {
        CadastroUserTO userUp = new CadastroUserTO("testeUpPasswordMandatory", "testeUp6@teste", "senhate", "nome", "sobrenome");
        UserTO u = service.save(userUp);

        CadastroUserTO alterado = userBeanUtil.toCadastroTO(u);

        alterado.setPassword("");
        assertThrows(ResourceBadRequestException.class, () -> service.update(alterado));
    }

    @Test
    void testDelete() throws ResourceBadRequestException, Exception {
        CadastroUserTO user = new CadastroUserTO("testeDel1", "testeDel1@teste", "senhate", "nome", "sobrenome");

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
        CadastroUserTO user = new CadastroUserTO("testeGA", "testeGA@teste", "senhate", "nome", "sobrenome");
        service.save(user);

        HashSet<UserTO> listaUser = service.getAll();

        assertFalse(listaUser.isEmpty());
    }

    @Test
    void testFindByToken() throws ResourceBadRequestException, Exception {
        CadastroUserTO user = new CadastroUserTO("testeDel", "testeDel@teste", "senhate", "nome", "sobrenome");
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
