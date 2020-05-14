package br.edu.ifsp.spo.bulls.usersApi.domain.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import br.edu.ifsp.spo.bulls.usersApi.domain.User;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserTest {

	@Test
	public void userMustHaveUserName() {
		User user = new User();
		user.setUserName("testeUsername");
		
		assertEquals("testeUsername", user.getUserName());
	}
	
	@Test
	public void userMustHaveEmail() {
		User user = new User();
		user.setEmail("testeEmail");
		
		assertEquals("testeEmail", user.getEmail());
	}
	
	@Test
	public void userMustHavePassword() {
		User user = new User();
		user.setPassword("testePassword");
		
		assertEquals("testePassword", user.getPassword());
	}

}
