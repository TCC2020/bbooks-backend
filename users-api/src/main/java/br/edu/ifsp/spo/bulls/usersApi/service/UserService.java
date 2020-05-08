package br.edu.ifsp.spo.bulls.usersApi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.ifsp.spo.bulls.usersApi.Repository.UserRepository;
import br.edu.ifsp.spo.bulls.usersApi.dto.User;
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
	public User getById(Long id) {
		
		return rep.findById(id).orElseThrow( () -> new ResourceNotFoundException("User not found"));
	}

	@Override
	public void delete(Long id) {
		
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


}
