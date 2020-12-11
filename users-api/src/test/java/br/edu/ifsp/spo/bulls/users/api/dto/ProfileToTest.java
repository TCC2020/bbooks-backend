package br.edu.ifsp.spo.bulls.users.api.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProfileToTest {

//	@Test
//	void testToString() {
//
//		ProfileTO profile = new ProfileTO(1, "nome", "sobrenome", "pais", "sao paulo", "SP", "10/10/1998");
//		assertEquals("ProfileTO(id=1, name=nome, lastName=sobrenome, country=pais, city=sao paulo, state=SP, birthDate=10/10/1998)", profile.toString());
//	}

//	@Test
//	void testCadastro2() {
//
//		ProfileTO profile = new ProfileTO("pais", "sao paulo", "SP", "10/10/1998");
//		assertEquals("ProfileTO(id=0, name=null, lastName=null, country=pais, city=sao paulo, state=SP, birthDate=10/10/1998)", profile.toString());
//	}

	@Test
	void testEquals() {
		ProfileTO profile = new ProfileTO(1, "nome", "sobrenome", "pais", "sao paulo", "SP", "10/10/1998");
		ProfileTO profile1 = new ProfileTO(1, "nome", "sobrenome", "pais", "sao paulo", "SP", "10/10/1998");
		
		boolean result = profile.equals(profile1);
		
		assertTrue(result);
	}
	
	@Test
	void testNotEquals() {
		ProfileTO profile = new ProfileTO(1, "nome", "sobrenome", "pais", "sao paulo", "SP", "10/10/1998");
		ProfileTO profile1 = new ProfileTO(1, "nome2", "sobrenome", "pais", "sao paulo", "SP", "10/10/1998");
		
		boolean result = profile.equals(profile1);
		
		assertFalse(result);
	}
	
	
	@Test
	void testHashCode() {
		ProfileTO profile = new ProfileTO(1, "nome", "sobrenome", "pais", "sao paulo", "SP", "10/10/1998");
		ProfileTO profile1 = new ProfileTO(1, "nome", "sobrenome", "pais", "sao paulo", "SP", "10/10/1998");
		

		assertEquals(profile.hashCode(),profile1.hashCode());
	}
}
