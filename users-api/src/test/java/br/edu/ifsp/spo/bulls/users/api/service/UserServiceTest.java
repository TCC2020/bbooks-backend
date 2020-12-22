package br.edu.ifsp.spo.bulls.users.api.service;

import br.edu.ifsp.spo.bulls.users.api.bean.UserBeanUtil;
import br.edu.ifsp.spo.bulls.users.api.domain.Profile;
import br.edu.ifsp.spo.bulls.users.api.domain.User;
import br.edu.ifsp.spo.bulls.users.api.dto.CadastroUserTO;
import br.edu.ifsp.spo.bulls.users.api.dto.UserTO;
import br.edu.ifsp.spo.bulls.users.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.users.api.exception.ResourceBadRequestException;
import br.edu.ifsp.spo.bulls.users.api.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.users.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.users.api.repository.ProfileRepository;
import br.edu.ifsp.spo.bulls.users.api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService service;

    @MockBean
    private UserRepository mockUserRepository;

    @MockBean
    private ProfileService mockProfileService;

    @MockBean
    private ProfileRepository mockProfileRepository;

    @Autowired
    private UserBeanUtil userBeanUtil;

    private User user;
    private UserTO userTO;
    private CadastroUserTO cadastroUserTO;
    private HashSet<User> userHashSet;
    private Profile profile;

    @BeforeEach
    void setUp(){
        cadastroUserTO = new CadastroUserTO("testes", "testeS@teste12", "senhate", "nome", "sobrenome");
        cadastroUserTO.setId(UUID.randomUUID());
        user = userBeanUtil.toUser(cadastroUserTO);

        profile = new Profile();
        profile.setId(1);

        Mockito.when(mockProfileRepository.findByUser(user)).thenReturn(profile);
        userTO = userBeanUtil.toUserTO(user);
    }

    @Test
    void testSave() throws  Exception {
        Mockito.when(mockUserRepository.existsByUserName(user.getUserName())).thenReturn(false);
        Mockito.when(mockUserRepository.findByEmail(user.getEmail())).thenReturn(Optional.ofNullable(null));
        Mockito.when(mockUserRepository.save(user)).thenReturn(user);
        Mockito.when(mockProfileRepository.findByUser(user)).thenReturn(profile);
        Mockito.when(mockProfileService.save(profile)).thenReturn(profile);

        UserTO resultado = service.save(cadastroUserTO);

        assertNotNull(resultado);
    }

    @Test
    void testFailSaveEmailWhenEmailIsUsed(){
        User userOne = user;
        userOne.setUserName("novoUserName");
        Mockito.when(mockUserRepository.existsByUserName(user.getUserName())).thenReturn(false);
        Mockito.when(mockUserRepository.findByEmail(user.getEmail())).thenReturn(Optional.ofNullable(userOne));

        ResourceConflictException e = assertThrows(ResourceConflictException.class, () -> service.save(cadastroUserTO));
        assertEquals(CodeException.US002.getText() + ": " + user.getEmail(), e.getMessage());
    }

    @Test
    void testFailSaveUserName() {
        Mockito.when(mockUserRepository.existsByUserName(user.getUserName())).thenReturn(true);
        ResourceConflictException e = assertThrows(ResourceConflictException.class, () -> service.save(cadastroUserTO));
        assertEquals(CodeException.US005.getText() + ": " + user.getUserName().toLowerCase(), e.getMessage());
    }

    @Test
    void testFailSavePasswordMandatory() {
        Mockito.when(mockUserRepository.existsByUserName(user.getUserName())).thenReturn(false);
        Mockito.when(mockUserRepository.findByEmail(user.getEmail())).thenReturn(Optional.ofNullable(null));
        Mockito.when(mockUserRepository.save(user)).thenReturn(user);
        Mockito.when(mockProfileRepository.findByUser(user)).thenReturn(profile);
        Mockito.when(mockProfileService.save(profile)).thenReturn(profile);

        cadastroUserTO.setPassword("");
        assertThrows(ResourceBadRequestException.class, () -> service.save(cadastroUserTO));
    }

    @Test
    void testGetById() {
        Mockito.when(mockUserRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
        UserTO resultado = service.getById(user.getId());

        assertEquals(userTO, resultado);
    }

    @Test
    void testFailGetByIdUserNotFound() {
        Mockito.when(mockUserRepository.findById(user.getId())).thenReturn(Optional.ofNullable(null));
        Throwable exception = assertThrows(ResourceNotFoundException.class, () -> service.getById(user.getId()));
        assertEquals(CodeException.US001.getText(), exception.getMessage());
    }

    @Test
    void testUpdate() throws Exception {
        Mockito.when(mockUserRepository.save(user)).thenReturn(user);
        Mockito.when(mockUserRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
        Mockito.when(mockProfileRepository.findByUser(user)).thenReturn(profile);

        UserTO userUpdated = service.update(userTO);

        assertEquals(userTO.getEmail(), userUpdated.getEmail());
    }

    @Test
    void testFailUpdateUserNotFound(){
        Mockito.when(mockUserRepository.findByEmail(user.getEmail())).thenReturn(Optional.ofNullable(user));

        assertThrows(ResourceNotFoundException.class, () -> service.update(userTO));
    }

    @Test
    void testFailUpdateEmailIsUsed(){
        user.setId(UUID.randomUUID());
        Mockito.when(mockUserRepository.findByEmail(user.getEmail())).thenReturn(Optional.ofNullable(user));

        assertThrows(ResourceConflictException.class, () -> service.update(userTO));
   }

    @Test
    void testFailUpdateEmailShouldFailWhenUserIdIsNull(){
        userTO.setId(null);

        assertThrows(ResourceNotFoundException.class, () -> service.update(userTO));
    }

    @Test
    void testDelete(){
        Mockito.when(mockUserRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
        Mockito.doNothing().when(mockProfileService).deleteByUser(user);
        Mockito.doNothing().when(mockUserRepository).deleteById(user.getId());

        service.delete(user.getId());

        Mockito.verify(mockUserRepository).deleteById(user.getId());
    }

    @Test
    void testFailDeleteUserNotFound() {
        Mockito.when(mockUserRepository.findById(user.getId())).thenThrow(new ResourceNotFoundException(CodeException.US001.getText(), CodeException.US001));
        Mockito.doNothing().when(mockProfileService).deleteByUser(user);
        Throwable exception = assertThrows(ResourceNotFoundException.class, () -> service.delete(UUID.randomUUID()));
        assertEquals(CodeException.US001.getText(), exception.getMessage());
    }

    @Test
    void testGetAll(){
        Mockito.when(mockUserRepository.findAll()).thenReturn(userHashSet);
        Mockito.when(mockProfileRepository.findByUser(user)).thenReturn(profile);

        HashSet<UserTO> listaUser = service.getAll();
        assertFalse(listaUser.isEmpty());
    }

    @Test
    void testFindByToken() {
        Mockito.when(mockUserRepository.findByToken(user.getToken())).thenReturn(Optional.ofNullable(user));

        Optional<org.springframework.security.core.userdetails.User> resposta = service.findByToken(user.getToken());

        assertTrue(resposta.isPresent());
    }

    @Test
    void testFailFindByToken(){
        Mockito.when(mockUserRepository.findByToken("erro")).thenReturn(Optional.ofNullable(null));
        Optional<org.springframework.security.core.userdetails.User> resposta = service.findByToken("erro");

        assertFalse(resposta.isPresent());
    }

    @Test
    void shoulGetUserByToken(){
        Mockito.when(mockUserRepository.findByToken(user.getToken())).thenReturn(Optional.ofNullable(user));
        User resultado = service.getByToken(user.getToken());

        assertEquals(user, resultado);
    }

    @Test
    void shoulndtGetUserByTokenUserNotFound(){
        Mockito.when(mockUserRepository.findByToken(user.getToken())).thenReturn(Optional.ofNullable(null));

        assertThrows(ResourceNotFoundException.class, () -> service.getByToken(user.getToken()));
    }

    @Test
    void shouldGetUserByEmail(){
        Mockito.when(mockUserRepository.findByEmail(user.getEmail())).thenReturn(Optional.ofNullable(user));
        Mockito.when(mockProfileRepository.findByUser(user)).thenReturn(profile);
        UserTO resultado = service.getByEmail(user.getEmail());

        assertEquals(userTO, resultado);
    }

    @Test
    void shouldGetUserForGoogle(){
        Mockito.when(mockUserRepository.findByEmail(user.getEmail())).thenReturn(Optional.ofNullable(user));
        Mockito.when(mockProfileRepository.findByUser(user)).thenReturn(profile);

        UserTO resultado = service.getForGoogle(user.getEmail());

        assertEquals(userTO, resultado);
    }

    @Test
    void shouldGetUserForGoogleIfUserNotFoundReturnNewUserTO(){
        Mockito.when(mockUserRepository.findByEmail(user.getEmail())).thenReturn(Optional.ofNullable(null));

        UserTO resultado = service.getForGoogle(user.getEmail());

        assertNotNull(resultado);
        assertNull(resultado.getId());
    }

    @Test
    void shouldGetUserByUserNameDomain(){
        Mockito.when(mockUserRepository.findByUserName(user.getUserName())).thenReturn(user);

        User resultado = service.getByUsername(user.getUserName());

        assertEquals(user, resultado);
    }

    @Test
    void shouldGetUserByUserNameDTO(){
        Mockito.when(mockProfileRepository.findByUser(user)).thenReturn(profile);
        Mockito.when(mockUserRepository.findByUserName(user.getUserName())).thenReturn(user);

        UserTO resultado = service.getByUserName(user.getUserName(), user.getToken());

        assertEquals(userTO, resultado);
    }


}
