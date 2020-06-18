
package br.edu.ifsp.spo.bulls.usersApi.service;

import java.util.HashSet;

import br.edu.ifsp.spo.bulls.usersApi.bean.UserBeanUtil;
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
public class UserService implements BaseService<User>{

	@Autowired
	private UserRepository rep;
	
	@Autowired
	EmailServiceImpl email;
	@Autowired
    private UserBeanUtil utils;
	
	@Override
	public User save( User entity) throws Exception {
        
		validationUserNameIsUnique(entity);
		//validationEmailIsUnique(entity);
		String texto = "Ola, " + entity.getUserName() + "! Confirme seu email abaixo: <br>\r\n" + 
				"<a href='http://localhost:4200/confirm'>Aqui</a>\r\n";
		email.sendEmailTo(entity.getEmail(), "BBooks - Confirme seu email", texto);
		return rep.save(entity);
	}
	
	private void validationUserNameIsUnique(User entity) throws Exception {
		if (rep.existsByUserName(entity.getUserName())) 
			throw new ResourceConflictException("UserName ja esta sendo usado");
	}
	
	private void validationEmailIsUnique(User entity) throws Exception {
		User user = rep.findByEmail(entity.getEmail()).get();
		if ((user != null) && (!user.getUserName().equals(entity.getUserName())) )
			throw new ResourceConflictException("Email ja esta sendo usado");
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
			user.setUuid(entity.getUuid());
			return rep.save(user);
		}).orElseThrow( () -> new ResourceNotFoundException("User not found"));
		
	}
	
	@Override
	public HashSet<User> getAll() {
		return (HashSet<User>) rep.findAll();
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

