package br.edu.ifsp.spo.bulls.users.api.service;

import br.edu.ifsp.spo.bulls.users.api.bean.ProfileBeanUtil;
import br.edu.ifsp.spo.bulls.users.api.domain.Profile;
import br.edu.ifsp.spo.bulls.users.api.domain.User;
import br.edu.ifsp.spo.bulls.users.api.dto.ProfileTO;
import br.edu.ifsp.spo.bulls.users.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.users.api.exception.ResourceBadRequestException;
import br.edu.ifsp.spo.bulls.users.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.users.api.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.HashSet;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.atLeastOnce;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProfileServiceTest {

	@Autowired
	private ProfileService service;
	
	@Autowired
	private ProfileBeanUtil profileBeanUtil;

	@MockBean
	private ProfileRepository mockProfileRespository;

	@MockBean
	private UserService mockUserService;

	private User user;
	private Profile profile;
	private ProfileTO profileTO;
	private HashSet<Profile> profileList;

	@BeforeEach
	public void setUp(){
		user = new User("testeProfile3", "testeS@teste13", "senhate");
		user.setToken("token");

		profile = new Profile("nome", "sobrenome", "pais", "sao paulo", "SP", "10/10/1998",user );
		profile.setId(1);

		profileTO = profileBeanUtil.toProfileTO(profile);

		profileList = new HashSet<>();
		profileList.add(profile);

	}
	
	@Test
	void shouldSaveProfile() throws Exception {
		Mockito.when(mockProfileRespository.save(profile)).thenReturn(profile);
		Profile resultado = service.save(profile);
		assertEquals(profile, resultado);

	}

	@Test
	void shouldGetProfileById() throws Exception {
		Mockito.when(mockProfileRespository.findById(profile.getId())).thenReturn(Optional.ofNullable(profile));
		ProfileTO profileGet = service.getById(profile.getId());
		assertEquals(profileTO, profileGet);
	}

	@Test
	void shouldntGetByIdProfileNotFound() throws Exception {
		Throwable e = assertThrows(ResourceNotFoundException.class, ()-> service.getById(123456789));
		assertEquals(CodeException.PF001.getText(), e.getMessage());
	}

	@Test
	void shouldDeleteProfile() throws ResourceBadRequestException, Exception {
		Mockito.doNothing().when(mockProfileRespository).deleteById(profile.getId());
		Mockito.when(mockProfileRespository.findById(profile.getId())).thenReturn(Optional.ofNullable(profile));
		service.delete(profile.getId());
	}

	@Test
	void shouldntDeleteProfileNotFound() throws ResourceBadRequestException, Exception {
		Throwable e = assertThrows(ResourceNotFoundException.class, ()-> service.delete(12345678));
		assertEquals(CodeException.PF001.getText(), e.getMessage());

	}

	@Test
	void shouldUpdateProfile() throws ResourceBadRequestException, Exception {
		Mockito.when(mockProfileRespository.findById(profile.getId())).thenReturn(Optional.ofNullable(profile));
		Mockito.when(mockProfileRespository.save(profile)).thenReturn(profile);
		ProfileTO resultado = service.update(profileTO);
		assertEquals(profileTO, resultado);
	}

	@Test
	void shouldntUpdateProfileNotFound() throws ResourceBadRequestException, Exception {
		Mockito.when(mockProfileRespository.findById(profile.getId()))
				.thenThrow(new ResourceNotFoundException(CodeException.PF001.getText(), CodeException.PF001));
		Throwable e = assertThrows(ResourceNotFoundException.class, ()-> service.update(profileBeanUtil.toProfileTO(profile)));
		assertEquals(CodeException.PF001.getText(), e.getMessage());
	}

	@Test
	void shouldGetAllProfiles() throws ResourceBadRequestException, Exception {
		Mockito.when(mockProfileRespository.findAll()).thenReturn(profileList);
		HashSet<ProfileTO> allProfiles = service.getAll();
		assertFalse(allProfiles.isEmpty());
	}

	@Test
	void shouldGetProfileByUser(){
		Mockito.when(mockUserService.getByUsername( user.getUserName()))
				.thenReturn(user);
		Mockito.when(mockProfileRespository.findByUser(user)).thenReturn(profile);

		ProfileTO resultado = service.getByUser(user.getUserName());

		assertEquals(profileTO, resultado);
	}

	@Test
	void shouldGetProfileByUserName(){
		Mockito.when(mockUserService.getByUsername(user.getUserName()))
				.thenReturn(user);
		Mockito.when(mockProfileRespository.findByUser(user)).thenReturn(profile);

		Profile resultado = service.getByUsername(user.getUserName());

		assertEquals(profile, resultado);
	}

	@Test
	void shouldGetProfileDomainByToken(){
		Mockito.when(mockUserService.getByToken(user.getToken())).thenReturn(user);
		Mockito.when(mockProfileRespository.findByUser(user)).thenReturn(profile);
		Profile resultado = service.getDomainByToken(user.getToken());
		assertEquals(profile, resultado);
	}

	@Test
	void shouldntGetProfileDomainByTokenWhenUserNotFound(){
		Mockito.when(mockUserService.getByToken(user.getToken())).thenReturn(null);
		Profile resultado = service.getDomainByToken(user.getToken());
		assertNull(resultado);
	}

	@Test
	void shouldGetAllDomainById(){
		HashSet<Integer> idList = new HashSet<>(profile.getId());
		Iterable<Integer> ids = idList;
		Mockito.when(mockProfileRespository.findAllById(ids))
				.thenReturn(profileList);
		HashSet<Profile> resultado = service.getAllDomainById(ids);
		assertNotNull(resultado);
	}

	@Test
	void shouldGetDomainById(){
		Mockito.when(mockProfileRespository.findById(profile.getId()))
				.thenReturn(Optional.ofNullable(profile));
		Profile resultado = service.getDomainById(profile.getId());
		assertEquals(profile, resultado);
	}

	@Test
	void shouldntGetDomainByIdWhenProfileNotFound(){
		Mockito.when(mockProfileRespository.findById(profile.getId()))
				.thenThrow( new ResourceNotFoundException(CodeException.PF001.getText(), CodeException.PF001));
		assertThrows(ResourceNotFoundException.class, () -> service.getDomainById(profile.getId()));
	}

	@Test
	void shouldUpdateProfileImage(){
		Mockito.when(mockUserService.getByToken(user.getToken())).thenReturn(user);
		Mockito.when(mockProfileRespository.findByUser(user)).thenReturn(profile);
		Mockito.when(mockProfileRespository.save(profile)).thenReturn(profile);
		HttpStatus resultado = service.updateProfileImage("url", user.getToken());
		assertEquals(HttpStatus.CREATED, resultado);
	}

	@Test
	void shouldDeleteProfileByUser(){
		Mockito.when(mockProfileRespository.findByUser(user)).thenReturn(profile);
		Mockito.doNothing().when(mockProfileRespository).deleteById(profile.getId());

		service.deleteByUser(user);

		Mockito.verify(mockProfileRespository, atLeastOnce()).deleteById(profile.getId());
	}
}
