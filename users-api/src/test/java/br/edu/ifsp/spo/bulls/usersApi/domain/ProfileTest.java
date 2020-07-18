package br.edu.ifsp.spo.bulls.usersApi.domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProfileTest {

	
	@Test
	void testToString() {
		
		Profile profile = new Profile(1, "nome", "sobrenome", "pais", "sao paulo", "SP", "10/10/1998", new User());
		assertEquals("Profile(id=1, name=nome, lastName=sobrenome, country=pais, city=sao paulo, state=SP, birthDate=10/10/1998, user=User(id=null, userName=null, email=null, password=null, token=null, creationDate=null, verified=null))", profile.toString());
	}
	
	@Test
	void testEquals() {
		Profile profile = new Profile(1, "nome", "sobrenome", "pais", "sao paulo", "SP", "10/10/1998", new User());
		Profile profile1 = new Profile(1, "nome", "sobrenome", "pais", "sao paulo", "SP", "10/10/1998", new User());
		
		boolean result = profile.equals(profile1);
		
		assertTrue(result);
	}
	
	@Test
	void testNotEquals() {
		Profile profile = new Profile(1, "nome", "sobrenome", "pais", "sao paulo", "SP", "10/10/1998", new User());
		Profile profile1 = new Profile(1, "nome2", "sobrenome", "pais", "sao paulo", "SP", "10/10/1998", new User());
		
		boolean result = profile.equals(profile1);
		
		assertFalse(result);
	}
	
	
	@Test
	void testHashCode() {
		Profile profile = new Profile(1, "nome", "sobrenome", "pais", "sao paulo", "SP", "10/10/1998", new User());
		Profile profile1 = new Profile(1, "nome", "sobrenome", "pais", "sao paulo", "SP", "10/10/1998", new User());
		

		assertEquals(profile.hashCode(),profile1.hashCode());
	}
}
