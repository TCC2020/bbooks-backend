package br.edu.ifsp.spo.bulls.users.api.service;

import java.util.HashSet;

import br.edu.ifsp.spo.bulls.common.api.service.EmailService;
import br.edu.ifsp.spo.bulls.users.api.bean.UserBeanUtil;
import br.edu.ifsp.spo.bulls.users.api.domain.Profile;
import br.edu.ifsp.spo.bulls.users.api.domain.User;
import br.edu.ifsp.spo.bulls.users.api.dto.CadastroUserTO;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.users.api.enums.EmailSubject;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceBadRequestException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.common.api.dto.UserTO;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;
import br.edu.ifsp.spo.bulls.users.api.repository.UserRepository;
import org.springframework.web.bind.annotation.RequestHeader;

@Service
public class UserService{

	private Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository rep;
	
	@Autowired
	private EmailService email;
	
	@Autowired
	private UserBeanUtil beanUtil;

	@Value("${app.front}")
	private String frontUrl;
	
	@Autowired
	private ProfileService profileService;

	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	
	public UserTO save(CadastroUserTO cadastroUserTO) throws Exception {
		User user = verifyUserToSave(cadastroUserTO);
		User retorno = null;

		try{
			retorno = rep.save(user);
			saveProfile(cadastroUserTO, user);
			sendEmail(retorno);
		}catch (Exception e){
			logger.error("Error while save user: " +  e);
		}

		return beanUtil.toUserTO(retorno);
	}

	private void sendEmail(User retorno) {
		// TODO: Colocar variável front
		new Thread(()-> {
			email.
			getInstance()
			.withTo(retorno.getEmail())
			.withTitle(" Bem vindo, " + retorno.getUserName())
			.withAction("Confirmar cadastro")
			.withLink(frontUrl + "/confirm")
			.withContent("Por favor, confirme seu endereço de e-mail clicando no botão abaixo para fazer parte do BBooks.")
			.withSubject(EmailSubject.VERIFY_EMAIL.name())
			.send();
		}).start();
	}

	private void saveProfile(CadastroUserTO cadastroUserTO, User user) {
		Profile profile = new Profile (cadastroUserTO.getName(), cadastroUserTO.getLastName(), cadastroUserTO.getProfileImage(), user);
		profileService.save(profile);
	}

	@NotNull
	private User verifyUserToSave(CadastroUserTO cadastroUserTO) throws Exception {
		validationPassword(cadastroUserTO);
		User user = beanUtil.toUser(cadastroUserTO);
		user.setPassword(bCryptPasswordEncoder.encode(cadastroUserTO.getPassword()));
		user.setUserName(user.getUserName().toLowerCase());
		user.setToken(UUID.randomUUID().toString());
		validationUserNameIsUnique(user);
		validationEmailIsUnique(user);

		return user;
	}

	private void validationPassword(CadastroUserTO entidade) {
		if(entidade.getPassword().isEmpty())
			throw new ResourceBadRequestException(CodeException.US003.getText(), CodeException.US003);
		
	}
	
	private void validationUserNameIsUnique(User entity) {
		if (rep.existsByUserName(entity.getUserName())) 
			throw new ResourceConflictException(CodeException.US005.getText() + ": " + entity.getUserName() , CodeException.US005);
	}
	
	private void validationEmailIsUnique(User entity) {
		Optional<User> user = rep.findByEmail(entity.getEmail());
		if ((user.isPresent()) && (!user.get().getUserName().equals(entity.getUserName())) )
			throw new ResourceConflictException(CodeException.US002.getText() + ": " + entity.getEmail() , CodeException.US002);
	}

	private void validationEmailIsUnique(String email, UserTO entity) {
		Optional<User> user = rep.findByEmail(email);
		if ((user.isPresent()) && (!user.get().getId().equals(entity.getId())) )
			throw new ResourceConflictException(CodeException.US002.getText() + ": " + entity.getEmail() , CodeException.US002);
	}

	public UserTO getById(UUID id) {
		User user = rep.findById(id).orElseThrow( () -> new ResourceNotFoundException(CodeException.US001.getText(), CodeException.US001));
		return beanUtil.toUserTO(user);
	}

	public User getByIdDomain(UUID id) {
		User user = rep.findById(id).orElseThrow( () -> new ResourceNotFoundException(CodeException.US001.getText(), CodeException.US001));
		return user;
	}

	public UserTO getByEmail(String email) {
		User user = rep.findByEmail(email).orElseThrow( () -> new ResourceNotFoundException(CodeException.US001.getText(), CodeException.US001));
		return beanUtil.toUserTO(user);
	}

	public UserTO getForGoogle(String email) {
		Optional<User> user = rep.findByEmail(email);
		if(user.isPresent()){
			return beanUtil.toUserTO(user.get());
		}else{
			return new UserTO();
		}
	}

	public void delete(UUID id) {
		User user = rep.findById(id).orElseThrow( () -> new ResourceNotFoundException(CodeException.US001.getText(), CodeException.US001));
		profileService.deleteByUser(user);
		rep.deleteById(id);
					
	}

	public UserTO update(UserTO entity) throws Exception {
		if(entity.getId() == null)
			throw new ResourceNotFoundException(CodeException.US001.getText(), CodeException.US001);

		validationEmailIsUnique(entity.getEmail(), entity);

		return beanUtil.toUserTO(rep.findById(entity.getId()).map( user1 -> {
			user1.setEmail(entity.getEmail());
			user1.setUserName(entity.getUserName());
			return rep.save(user1);
		}).orElseThrow( () -> new ResourceNotFoundException(CodeException.US001.getText(), CodeException.US001)));
	}

	public HashSet<UserTO> getAll() {
		HashSet<User> users = rep.findAll();
		
		return beanUtil.toUserTO(users);
	}

	public User getByToken(String token){
		return rep.findByToken(token).orElseThrow(() -> new ResourceNotFoundException(CodeException.US004.getText(), CodeException.US004));
	}
	 
   	public Optional<org.springframework.security.core.userdetails.User> findByToken(String token) {
        Optional<User> optionalUser = rep.findByToken(token);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            org.springframework.security.core.userdetails.User userFromSpringSecurity = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), true, true, true, true,
                    AuthorityUtils.createAuthorityList("USER"));
            return Optional.of(userFromSpringSecurity);
        }
        return  Optional.empty();
    }

	public UserTO getByUserName(String username, @RequestHeader(value = "AUTHORIZATION") String token) {
		return beanUtil.toUserTO(rep.findByUserName(username), token);
	}

	public User getByUsername(String username) {
		return rep.findByUserName(username);
	}

	public UserTO getByProfileId(int profileId) {
		return beanUtil.toUserTO(rep.findByProfileId(profileId).orElseThrow(() ->
				new ResourceNotFoundException(CodeException.US001.getText(), CodeException.US001)));
	}
}

