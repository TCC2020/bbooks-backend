package br.edu.ifsp.spo.bulls.usersApi.service;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
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

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {

	@Autowired
	private UserService service;
	
	
	@Test
	void testSave() throws ResourceBadRequestException, Exception {
		
		UserTO user = new UserTO("testeS", "testeS@teste12", "senhateste", "nome", "sobrenome"); 
		
		UserTO user1 =  service.save(user);	
		
		// Testando campos obrigatorios 
		assertEquals(user.getUserName(), user1.getUserName());
		assertEquals(user.getEmail(), user1.getEmail());
		assertEquals(user.getPassword(), user1.getPassword());
	}

	@Test
	void testFailSaveEmail() throws Exception {
		UserTO userUp = new UserTO("testeSEmail1234", "testeS12@teste", "senhateste","nome", "sobrenome"); 
		service.save(userUp);
		
		UserTO userUpEmail = new UserTO("testeSEmail123", "testeS12@teste", "senhateste","nome", "sobrenome"); 

		ResourceConflictException e = assertThrows(ResourceConflictException.class, ()-> service.save(userUpEmail));
		assertEquals("Email ja esta sendo usado", e.getMessage());
	}
	
	@Test
	void testFailSaveUserName() throws Exception {
		UserTO userUp = new UserTO("testeSEmail", "testeS1@teste", "senhateste","nome", "sobrenome"); 
		service.save(userUp);
		
		UserTO userUpEmail = new UserTO("testeSEmail", "testeS2@teste", "senhateste","nome", "sobrenome"); 

		ResourceConflictException e = assertThrows(ResourceConflictException.class, ()-> service.save(userUpEmail));
		assertEquals("UserName ja esta sendo usado", e.getMessage());
	}
	
	@Test
	void testFailSaveEmailMandatory() throws Exception {
		UserTO userUp = new UserTO(); 
		userUp.setPassword("senhateste");
		userUp.setUserName("testeSPasswordMandatory");
		
		assertThrows(TransactionSystemException.class, ()-> service.save(userUp));
	}
	
	@Test
	void testFailSavePasswordMandatory() throws Exception {
		
		UserTO userUp = new UserTO(); 
		userUp.setEmail("testeS6@teste");
		userUp.setUserName("testeSPasswordMandatory");
		
		assertThrows(TransactionSystemException.class, ()-> service.save(userUp));
	}
	
	@Test
	void testGetById() throws ResourceBadRequestException, Exception {
 
		UserTO user = new UserTO("testeGI", "testeGi@teste", "senhateste","nome", "sobrenome"); 
		service.save(user);	
		UserTO user1 =  service.getById(user.getUserName());
				
		// Testando se campos obrigatorios foram gravados corretamente
		
		assertEquals(user.getUserName(), user1.getUserName());
		assertEquals(user.getEmail(), user1.getEmail());
		assertEquals(user.getPassword(), user1.getPassword());
	}

	@Test
	void testFailGetByIdUserNotFound() {
		
		Throwable exception = assertThrows(ResourceNotFoundException.class, ()-> service.getById("testeFail"));
		assertEquals("User not found", exception.getMessage());
	}

	@Test
	void testUpdate() throws Exception {
		UserTO userUp = new UserTO("testeUp", "testeUp1@teste", "senhateste","nome", "sobrenome"); 
		
		service.save(userUp);
		
		userUp.setEmail("testUp2@teste");
		UserTO userUpdated = service.update(userUp);
		
		assertEquals(userUp.getEmail(), userUpdated.getEmail());
	}
	
	@Test
	void testFailUpdateUserNotFound() throws Exception {
		UserTO userUp = new UserTO("testeUpUser", "testeUp2@teste", "senhateste"); 
		Throwable exception = assertThrows(ResourceNotFoundException.class, ()-> service.update(userUp));
		assertEquals("User not found", exception.getMessage());
	}
	
	@Test
	void testFailUpdateEmail() throws Exception {
		UserTO userUp = new UserTO("testeUpEmail", "testeUp3@teste", "senhateste","nome", "sobrenome"); 
		service.save(userUp);
		
		UserTO userUpEmail = new UserTO("testeUpEmail2", "testeUp4@teste", "senhateste","nome", "sobrenome"); 
		service.save(userUpEmail);
		
		userUp.setEmail("testeUp4@teste");
		
		assertThrows(ResourceConflictException.class, ()-> service.update(userUp));
	}
	
	@Test
	void testFailUpdateEmailMandatory() throws Exception {
		UserTO userUp = new UserTO("testeUpEmailMandatory", "testeUp5@teste", "senhateste","nome", "sobrenome"); 
		service.save(userUp);

		userUp.setEmail("");
		
		assertThrows(TransactionSystemException.class, ()-> service.update(userUp));
	}
	
	@Test
	void testFailUpdatePasswordMandatory() throws Exception {
		UserTO userUp = new UserTO("testeUpPasswordMandatory", "testeUp6@teste", "senhateste","nome", "sobrenome"); 
		service.save(userUp);

		userUp.setPassword("");
		
		assertThrows(TransactionSystemException.class, ()-> service.update(userUp));
	}
	
	@Test
	void testDelete() throws ResourceBadRequestException, Exception  {
		UserTO user = new UserTO("testeDel1", "testeDel1@teste", "senhateste","nome", "sobrenome"); 
		
		service.save(user);
		service.delete("testeDel");
		
		// Se realmente apagou o "getById" não irá achar
		
		Throwable exception = assertThrows(ResourceNotFoundException.class, ()-> {service.getById("testeDel");});
		assertEquals("User not found", exception.getMessage());
	}
	
	@Test
	void testFailDeleteUserNotFound() {
		
		Throwable exception = assertThrows(ResourceNotFoundException.class, ()-> service.delete("testeFail"));
		assertEquals("User not found", exception.getMessage());
	}

	@Test
	void testGetAll() throws ResourceBadRequestException, Exception {
		UserTO user = new UserTO("testeGA", "testeGA@teste", "senhateste","nome", "sobrenome"); 
		service.save(user);
		
		HashSet<UserTO> listaUser = service.getAll();
		
		assertFalse(listaUser.isEmpty());
	}


	@Test
	void testFindByToken() throws ResourceBadRequestException, Exception {
		UserTO user = new UserTO("testeDel", "testeDel@teste", "senhateste","nome", "sobrenome"); 
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
