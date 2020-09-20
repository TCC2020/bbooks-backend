package br.edu.ifsp.spo.bulls.usersApi.bean;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import br.edu.ifsp.spo.bulls.usersApi.domain.User;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserTO;
import static org.junit.jupiter.api.Assertions.*;
//import java.util.HashSet;
import org.junit.jupiter.api.Test;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserBeanUtilTest {

	@Autowired
	UserBeanUtil bean;
	
	@Test
	void testToUser() {
		UserTO userTO = new UserTO("teste", "teste@teste","123", false);

		User userResultado = bean.toUser(userTO);

		assertEquals(userTO.getUserName(), userResultado.getUserName());
		assertEquals(userTO.getEmail(), userResultado.getEmail());
		assertEquals(userTO.getToken(), userResultado.getToken());
		
	}
}
