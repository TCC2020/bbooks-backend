package br.edu.ifsp.spo.bulls.usersApi.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.edu.ifsp.spo.bulls.usersApi.domain.User;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceBadRequestException;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {

	private UserService service = new UserService();
	

	@Test
	public void testSave() throws ResourceBadRequestException, Exception {
		
		User user = new User("teste", "teste@teste", "senhateste"); 
		
		User user1 =  service.save(user);
		assertEquals(user, user1);
	}

	@Test
	void testGetById() {
		
		User user = new User("teste", "teste@teste", "senhateste"); 
		
		User user1 =  service.getById(user.getUserName());
		assertEquals(user, user1);	
		
	}

	@Test
	void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAll() {
		fail("Not yet implemented");
	}

	@Test
	void testFindByToken() {
		fail("Not yet implemented");
	}

}
