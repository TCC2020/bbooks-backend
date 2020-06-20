package br.edu.ifsp.spo.bulls.usersApi.dto;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserTOTest {

	@Test 
	void testGetSet() {
		UserTO userTo = new UserTO();
		
		userTo.setUserName("testeSet");
		userTo.setEmail("teste");
		userTo.setPassword("teste");
		userTo.setToken("12");
		
		assertEquals("testeSet", userTo.getUserName());
		assertEquals("teste", userTo.getEmail());
		assertEquals("teste", userTo.getPassword());
		assertEquals("12", userTo.getToken());
		
	}
	
	@Test
	void testEquals() {
		UserTO userTo = new UserTO("teste", "teste@teste", "senhateste", "123", false);
		UserTO userTo2 = new UserTO("teste", "teste@teste", "senhateste", "123", false);
		
		boolean resultado = userTo.equals(userTo2);
		
		assertTrue(resultado);
	}

	@Test
	void testNotEquals() {
		UserTO userTo = new UserTO("teste", "teste@teste", "senhateste", "123", false);
		UserTO userTo2 = new UserTO("teste123", "teste@teste123", "senhateste", "123", true);
		
		boolean resultado = userTo.equals(userTo2);
		
		assertFalse(resultado);
	}

	@Test
	void testHashCode() {
		UserTO userTo = new UserTO("teste", "teste@teste", "senhateste", "123", false);
		UserTO userTo2 = new UserTO("teste", "teste@teste", "senhateste", "123", false);
		
		
		assertEquals(userTo.hashCode(), userTo2.hashCode());
	}
	
	@Test
	void testToString() {
		
		UserTO userTo = new UserTO("teste", "teste@teste", "senhateste");
		assertEquals("UserTO(userName=teste, email=teste@teste, password=senhateste, token=null, verified=null, name=null, lastName=null)", userTo.toString());
	}
}
