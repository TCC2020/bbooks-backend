package br.edu.ifsp.spo.bulls.usersApi.service;

import java.util.HashSet;
import br.edu.ifsp.spo.bulls.usersApi.bean.UserBeanUtil;
import br.edu.ifsp.spo.bulls.usersApi.domain.Profile;
import br.edu.ifsp.spo.bulls.usersApi.domain.User;
import br.edu.ifsp.spo.bulls.usersApi.dto.CadastroUserTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserTO;
import br.edu.ifsp.spo.bulls.usersApi.enums.CodeException;
import br.edu.ifsp.spo.bulls.usersApi.enums.EmailSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;
import br.edu.ifsp.spo.bulls.usersApi.repository.UserRepository;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceBadRequestException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.RequestHeader;

@Service
public class UserService{

	@Autowired
	private UserRepository rep;
	
	@Autowired
	EmailService email;
	
	@Autowired
	private UserBeanUtil beanUtil;
	
	@Autowired
	private ProfileService profileService;

	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	
	public UserTO save(CadastroUserTO cadastroUserTO) throws Exception {
        
		User user = beanUtil.toUser(cadastroUserTO);
		user.setPassword(bCryptPasswordEncoder.encode(cadastroUserTO.getPassword()));
		user.setUserName(user.getUserName().toLowerCase());
		user.setToken(UUID.randomUUID().toString());

		Profile profile = new Profile (cadastroUserTO.getName(), cadastroUserTO.getLastName(), cadastroUserTO.getProfileImage(), user);
		validationUserNameIsUnique(user);
		validationEmailIsUnique(user);
		validationPassword(cadastroUserTO);
		
		User retorno = rep.save(user);
		profileService.save(profile);

		email.
			getInstance()
			.withUrls("https://bbooks-front.herokuapp.com/confirm")
			.withTo(retorno.getEmail())
			.withContent(" Bem Vindo " + retorno.getUserName())
			.withSubject(EmailSubject.VERIFY_EMAIL.name())
			.send();

		return beanUtil.toUserTO(retorno);
	}

	private void validationPassword(CadastroUserTO entidade) {
		if(entidade.getPassword().isEmpty())
			throw new ResourceBadRequestException(CodeException.US003.getText(), CodeException.US003);
		
	}
	
	private void validationUserNameIsUnique(User entity) throws Exception {
		if (rep.existsByUserName(entity.getUserName())) 
			throw new ResourceConflictException(CodeException.US005.getText() + ": " + entity.getUserName() , CodeException.US005);
	}
	
	private void validationEmailIsUnique(User entity) throws Exception {
		Optional<User> user = rep.findByEmail(entity.getEmail());
		if ((user.isPresent()) && (!user.get().getUserName().equals(entity.getUserName())) )
			throw new ResourceConflictException(CodeException.US002.getText() + ": " + entity.getEmail() , CodeException.US002);
	}

	private void validationEmailIsUnique(String email, UserTO entity) throws Exception {
		Optional<User> user = rep.findByEmail(email);
		if ((user.isPresent()) && (!user.get().getId().equals(entity.getId())) )
			throw new ResourceConflictException(CodeException.US002.getText() + ": " + entity.getEmail() , CodeException.US002);
	}

	public UserTO getById(UUID id) {
		User user = rep.findById(id).orElseThrow( () -> new ResourceNotFoundException(CodeException.US001.getText(), CodeException.US001));
		return beanUtil.toUserTO(user);
	}

	public UserTO getByEmail(String email) {
		User user = rep.findByEmail(email).orElseThrow( () -> new ResourceNotFoundException(CodeException.US001.getText(), CodeException.US001));
		return beanUtil.toUserTO(user);
	}

	public UserTO getForGoogle(String email) {
		Optional<User> user = rep.findByEmail(email);
		User user1;
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

	public UserTO getDTOByToken(String token) {
		return beanUtil.toUserTO(rep.findByToken(token).orElseThrow(() -> new ResourceNotFoundException(CodeException.US001.getText(), CodeException.US001)));
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

}

