package br.edu.ifsp.spo.bulls.usersApi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.ifsp.spo.bulls.usersApi.Repository.UserRepository;
import br.edu.ifsp.spo.bulls.usersApi.dto.User;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(User entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User update(User entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
