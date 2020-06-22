
package br.edu.ifsp.spo.bulls.usersApi.service;

import java.util.HashSet;
import br.edu.ifsp.spo.bulls.usersApi.bean.UserBeanUtil;
import br.edu.ifsp.spo.bulls.usersApi.domain.Profile;
import br.edu.ifsp.spo.bulls.usersApi.domain.User;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import java.util.Optional;
import br.edu.ifsp.spo.bulls.usersApi.repository.UserRepository;
import br.edu.ifsp.spo.bulls.usersApi.service.impl.EmailServiceImpl;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceNotFoundException;

@Service
public class UserService{

	@Autowired
	private UserRepository rep;
	
	@Autowired
	EmailServiceImpl email;
	
	@Autowired
	private UserBeanUtil beanUtil;
	
	@Autowired
	private ProfileService profileService;
	
	public UserTO save( UserTO userTO) throws Exception {
        
		User user = beanUtil.toUser(userTO);
		Profile profile = new Profile (userTO.getName(), userTO.getLastName(), user);
		
		validationUserNameIsUnique(user);
		validationEmailIsUnique(user);
	
		User retorno = rep.save(user);
		profileService.save(profile);
		return beanUtil.toUserTO(retorno);
	}
	
	private void validationUserNameIsUnique(User entity) throws Exception {
		if (rep.existsByUserName(entity.getUserName())) 
			throw new ResourceConflictException("UserName ja esta sendo usado");
	}
	
	private void validationEmailIsUnique(User entity) throws Exception {
		Optional<User> user = rep.findByEmail(entity.getEmail());
		if ((user.isPresent()) && (!user.get().getUserName().equals(entity.getUserName())) )
			throw new ResourceConflictException("Email ja esta sendo usado");
	}

	public UserTO getById(String id) {
		User user = rep.findById(id).orElseThrow( () -> new ResourceNotFoundException("User not found"));
		return beanUtil.toUserTO(user);
	}

	public void delete(String id) {
			
		User user = rep.findById(id).orElseThrow( () -> new ResourceNotFoundException("User not found"));
		profileService.deleteByUser(user);
		rep.deleteById(id);
					
	}

	public UserTO update(UserTO entity) throws Exception {
		
		User user = beanUtil.toUser(entity);
		validationEmailIsUnique(user);
		
		return beanUtil.toUserTO(rep.findById(user.getUserName()).map( user1 -> {
			user1.setEmail(user.getEmail());
			user1.setPassword(user.getPassword());
			return rep.save(user);
		}).orElseThrow( () -> new ResourceNotFoundException("User not found")));
		
	}

	public HashSet<UserTO> getAll() {
		HashSet<User> users = rep.findAll();
		
		return beanUtil.toUserTO(users);
	}

	public User getByToken(String token){
		return rep.findByToken(token).orElseThrow(() -> new ResourceNotFoundException("Autenticação não encontrada."));
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
	
}

