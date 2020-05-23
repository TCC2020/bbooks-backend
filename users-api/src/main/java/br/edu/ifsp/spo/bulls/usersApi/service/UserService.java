package br.edu.ifsp.spo.bulls.usersApi.service;

import java.util.HashSet;
import br.edu.ifsp.spo.bulls.usersApi.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import java.util.Optional;
import br.edu.ifsp.spo.bulls.usersApi.repository.UserRepository;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceBadRequestException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceNotFoundException;

@Service
public class UserService implements BaseService<User>{

	@Autowired
	private UserRepository rep;
	
	@Override
	public User save( User entity) throws Exception, ResourceBadRequestException {
        
		validationEmailIsUnique(entity);
		validationUserNameIsUnique(entity);
		
		return rep.save(entity);
	}

	private void validationEmailIsUnique(User entity) throws Exception {
		if (rep.existsByEmail(entity.getEmail()))
			throw new ResourceConflictException("Email ja cadastrado: " + entity.getEmail());
	}
	
	private void validationUserNameIsUnique(User entity) throws Exception {
		if (rep.existsByUserName(entity.getUserName())) 
			throw new ResourceConflictException("UserName ja esta sendo usado");
	}

	@Override
	public User getById(String id) {
		
		return rep.findById(id).orElseThrow( () -> new ResourceNotFoundException("User not found"));
	}

	@Override
	public void delete(String id) {
				
		rep.findById(id).orElseThrow( () -> new ResourceNotFoundException("User not found"));
		rep.deleteById(id);
					
	}

	@Override
	public User update(User entity) throws Exception {
		
		validationEmailIsUnique(entity);
	
		return rep.findById(entity.getUserName()).map( user -> {
			user.setEmail(entity.getEmail());
			user.setPassword(entity.getPassword());
			return rep.save(user);
		}).orElseThrow( () -> new ResourceNotFoundException("User not found"));
		
	}
	
	@Override
	public HashSet<User> getAll() {
		
		return (HashSet<User>) rep.findAll();
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

