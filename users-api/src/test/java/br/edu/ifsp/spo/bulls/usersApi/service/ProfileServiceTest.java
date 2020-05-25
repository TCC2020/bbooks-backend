package br.edu.ifsp.spo.bulls.usersApi.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import br.edu.ifsp.spo.bulls.usersApi.domain.Profile;
import br.edu.ifsp.spo.bulls.usersApi.domain.User;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceBadRequestException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceNotFoundException;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProfileServiceTest {

	@Autowired
	ProfileService service;
	
	@Autowired
	UserService userService;
	
	@Test
	void testSave() throws ResourceBadRequestException, Exception {
		User user = userService.save(new User("testeProfile3", "testeS@teste13", "senhateste"));
	
		Profile profile = new Profile("nome completo", "pais", "sao paulo", "SP", "10/10/1998", user);
		
		Profile profile2 = service.save(profile);
		
		assertEquals(profile, profile2);
		
	}
	
	@Test
	void testSaveUserNotFound() {

		Profile profile = new Profile("nome completo", "pais", "sao paulo", "SP", "10/10/1998", new User("testeS", "testeS@testeFail", "senhateste"));
		
		Throwable e = assertThrows(ResourceNotFoundException.class, ()-> service.save(profile));
		assertEquals("User not found", e.getMessage());
	}
	
	@Test
	void testSaveUserAlreadyUsed() throws ResourceBadRequestException, Exception {

		User user = userService.save(new User("testeProfile", "testeS@teste", "senhateste"));
		
		Profile profile = new Profile("nome completo", "pais", "sao paulo", "SP", "10/10/1998", user);
		service.save(profile);
		
		Profile profile2 = new Profile("nome completo1", "pais", "sao paulo", "SP", "10/10/1998", user);
		
		Throwable e = assertThrows(ResourceConflictException.class, ()-> service.save(profile2));
		assertEquals("User already used", e.getMessage());
	}
	
	@Test
	void testGetById() throws Exception {
		
		User user = userService.save(new User("testeProfileGet", "testeS@gert", "senhateste"));
		
		Profile profileSet = service.save(new Profile("nome completo", "pais", "sao paulo", "SP", "10/10/1998", user));
		
		Profile profileGet = service.getById(profileSet.getId());
		
		assertEquals(profileSet, profileGet);
	}
	
	@Test
	void testGetByIdProfileNotFound() throws Exception {
		
		Throwable e = assertThrows(ResourceNotFoundException.class, ()-> service.getById(123456789));
		assertEquals("Profile not found", e.getMessage());
	}
	
	@Test
	void testDelete() throws ResourceBadRequestException, Exception {
		
		User user = userService.save(new User("testeProfDelete", "testeS@delete", "senhateste"));
		
		Profile profile = service.save(new Profile("nome completo", "pais", "sao paulo", "SP", "10/10/1998", user));
		
		service.delete(profile.getId());
		
		Throwable e = assertThrows(ResourceNotFoundException.class, ()-> {service.getById(profile.getId());});
		assertEquals("Profile not found", e.getMessage());
		
	}
	
	@Test
	void testDeleteProfileNotFound() throws ResourceBadRequestException, Exception {
			
		Throwable e = assertThrows(ResourceNotFoundException.class, ()-> service.delete(12345678));
		assertEquals("Profile not found", e.getMessage());
		
	}

	@Test
	void testUpdate() throws ResourceBadRequestException, Exception {
		
		User user = userService.save(new User("testeUpdateOk", "testeS@updateOk", "senhateste"));
		
		Profile profile = service.save(new Profile("nome completo", "pais", "sao paulo", "SP", "10/10/1998", user));
		
		profile.setFullName("Mudando o nome");
		
		Profile profileUpdate = service.update(profile);
		
		assertEquals("Mudando o nome", profileUpdate.getFullName());
	}
	
	@Test
	void testUpdateProfileNotFound() throws ResourceBadRequestException, Exception {
		
		User user = userService.save(new User("testeProfileUpdateFail", "testeS@updateFail", "senhateste"));
		
		Profile profile = new Profile("nome completo", "pais", "sao paulo", "SP", "10/10/1998", user);
		
		Throwable e = assertThrows(ResourceNotFoundException.class, ()-> service.update(profile));
		assertEquals("Profile not found", e.getMessage());
		
	}

	@Test
	void testGetAllProfiles() throws ResourceBadRequestException, Exception {
		
		User user = userService.save(new User("testeProfileGetAll", "testeS@getAll", "senhateste"));
		service.save(new Profile("nome completo", "pais", "sao paulo", "SP", "10/10/1998", user));
	
		HashSet<Profile> allProfiles = service.getAll();
		
		assertFalse(allProfiles.isEmpty());
	}
}
