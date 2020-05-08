package br.edu.ifsp.spo.bulls.usersApi.service;


import java.util.List;
import br.edu.ifsp.spo.bulls.usersApi.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import br.edu.ifsp.spo.bulls.usersApi.Repository.UserRepository;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceNotFoundException;

@Service
public class UserService implements BaseService<User>{

	UserRepository rep;
	
	public UserService(UserRepository repository) {
		this.rep = repository;
	}
	
	@Override
	public User save(User entity) throws Exception {
        
		if (rep.existsByEmail(entity.getEmail())){
			throw new Exception("Email ja cadastrado");
		}
		return rep.save(entity);
	}

	@Override
	public User getById(String id) {
		
		return rep.findById(id).orElseThrow( () -> new ResourceNotFoundException("User not found"));
	}

	@Override
	public void delete(String id) {
		
		rep.deleteById(id);
		
	}

	@Override
	public User update(User entity) {
		
		return rep.findById(entity.getId()).map( user -> {
			user.setEmail(entity.getEmail());
			user.setPassword(entity.getPassword());
			user.setUserName(entity.getUserName());
			return rep.save(user);
		}).orElseThrow( () -> new ResourceNotFoundException("User not found"));
	}

	@Override
	public List<User> getAll() {
		
		return (List<User>) rep.findAll();
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

