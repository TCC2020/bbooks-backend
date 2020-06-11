package br.edu.ifsp.spo.bulls.usersApi.bean;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import br.edu.ifsp.spo.bulls.usersApi.domain.User;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserTO;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import org.junit.jupiter.api.Test;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserBeanUtilTest {

	@Autowired
	UserBeanUtil bean;
	
	@Test
	void testToUser() {
		UserTO userTO = new UserTO("teste", "teste@teste", "senhateste", "123");
		
		User userResultado = bean.toUser(userTO);
		
		assertEquals(userTO.getUserName(), userResultado.getUserName());
		assertEquals(userTO.getEmail(), userResultado.getEmail());
		assertEquals(userTO.getPassword(), userResultado.getPassword());
		assertEquals(userTO.getToken(), userResultado.getToken());
		
	}
	
	@Test
	void testToUserTO() {
		User user = new User("teste", "teste@teste", "senhateste");
		user.setToken("123");
		user.prePersist();

		UserTO userTO = bean.toUserTO(user);
		
		assertEquals(user.getUserName(), userTO.getUserName());
		assertEquals(user.getEmail(), userTO.getEmail());
		assertEquals(user.getPassword(), userTO.getPassword());
		assertEquals(user.getToken(), userTO.getToken());
	}
	
	@Test
	void toHashUserTO() {
//		User user0 = new User("teste 1", "teste@teste", "senhateste");
//		User user1 = new User("teste 2", "teste@teste", "senhateste");
//		User user2 = new User("teste 3", "teste@teste", "senhateste");
//
//		HashSet<User> listaUsers = new HashSet<User>();
//		listaUsers.add(user0);
//		listaUsers.add(user1);
//		listaUsers.add(user2);
//		
//		HashSet<UserTO> listaTOusers = bean.toUserTO(listaUsers);
//		
//		UserTO user0TO = null, user1TO = null, user2TO = null;
//		
//		System.out.println(listaTOusers.toString());
//		System.out.println(listaUsers.toString());
//		int i = 2 ;
//		 for (UserTO userTO: listaTOusers ) {
//			 switch(i) {
//			 case 2:
//				 user0TO = userTO;
//				 break;
//			 case 1:
//				 user1TO = userTO;
//				 break;
//			 case 0: 
//				 user2TO = userTO;
//				 break;
//			 default:
//			 }
//			 i--;
//		 }
//		 
//		 assertEquals(user0.getUserName(), user0TO.getUserName());
//		 assertEquals(user0.getEmail(), user0TO.getEmail());
//		 assertEquals(user0.getPassword(), user0TO.getPassword());
//		 assertEquals(user0.getToken(), user0TO.getToken());
//		 
//		 assertEquals(user1.getUserName(), user1TO.getUserName());
//		 assertEquals(user1.getEmail(), user1TO.getEmail());
//		 assertEquals(user1.getPassword(), user1TO.getPassword());
//		 assertEquals(user1.getToken(), user1TO.getToken());
//		 
//		 assertEquals(user2.getUserName(), user2TO.getUserName());
//		 assertEquals(user2.getEmail(), user2TO.getEmail());
//		 assertEquals(user2.getPassword(), user2TO.getPassword());
//		 assertEquals(user2.getToken(), user2TO.getToken());
		
	}
}
