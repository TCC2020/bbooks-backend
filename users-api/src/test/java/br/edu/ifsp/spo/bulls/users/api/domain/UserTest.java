package br.edu.ifsp.spo.bulls.users.api.domain;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

import br.edu.ifsp.spo.bulls.common.api.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserTest {

	@Test
	void userMustHaveUserName() {
		User user = new User();
		user.setUserName("testeUsername");
		
		assertEquals("testeUsername", user.getUserName());
	}
	
	@Test
	void userMustHaveEmail() {
		User user = new User();
		user.setEmail("testeEmail");
		
		assertEquals("testeEmail", user.getEmail());
	}
	
	@Test
	void userMustHavePassword() {
		User user = new User();
		user.setPassword("testePassword");
		
		assertEquals("testePassword", user.getPassword());
	}
	
	@Test
	void construtor() {
		User user = new User("testeConstrutor", "testeC@teste", "senhateste");
		
		assertEquals("testeConstrutor", user.getUserName());
		assertEquals("testeC@teste", user.getEmail());
		assertEquals("senhateste", user.getPassword());
	}

	@Test
	void prePersist() {
		
		User user = new User("teste", "testeC@teste", "senhateste");
		
		user.prePersist();
		
		assertEquals(LocalDateTime.now().toLocalDate(), user.getCreationDate().toLocalDate());
		
	}
	
	@Test
	void testEquals() {
		User user = new User("teste", "testeC@teste", "senhateste");
		User user1 = new User("teste", "testeC@teste", "senhateste");
		
		boolean result = user.equals(user1);
		
		assertTrue(result);
	}
	
	@Test
	void testNotEquals() {
		User user = new User("teste", "testeC@teste", "senhateste");
		User user1 = new User("teste123", "testeC@teste123", "senhateste123");
		
		boolean result = user.equals(user1);
		
		assertFalse(result);
	}
	
	
	@Test
	void testHashCode() {
		User user = new User("teste", "testeC@teste", "senhateste");
		User user1 = new User("teste", "testeC@teste", "senhateste");

		assertEquals(user.hashCode(),user1.hashCode());
	}
	
	@Test
	void testSetToken() {
		User user = new User();
		user.setToken("teste");
		
		assertEquals("teste", user.getToken());
	}
	
	@Test
	void testSetCreationDate() {
		User user = new User();
		user.setCreationDate(LocalDateTime.now());
		
		assertEquals(LocalDateTime.now().toLocalDate(), user.getCreationDate().toLocalDate());
	}
	
	@Test
	void testToString() {
		
		User user = new User("teste", "testeC@teste", "senhateste");
		assertEquals("User(id=null, userName=teste, email=testeC@teste, password=senhateste, token=null, idSocial=null, creationDate=null, verified=null)", user.toString());
	}
	
}
