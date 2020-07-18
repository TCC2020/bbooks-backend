package br.edu.ifsp.spo.bulls.usersApi.bean;

import br.edu.ifsp.spo.bulls.usersApi.dto.UserTO;

import java.util.HashSet;
import java.util.UUID;

import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.usersApi.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import br.edu.ifsp.spo.bulls.usersApi.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserBeanUtil {
	@Autowired
	private UserRepository repository;

	public User toUser(UserTO userTO) {
		User user = new User();
		
		try{
			BeanUtils.copyProperties(userTO, user);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return user;
	}

	public User toUser(UUID id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
	}
	
	public UserTO toUserTO(User user) {
		UserTO userTO = new UserTO();
		
		try{
			BeanUtils.copyProperties(user, userTO);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return userTO;
	}

	public HashSet<UserTO> toUserTO(HashSet<User> users){
		HashSet<UserTO> usersTO = new HashSet<UserTO>();
	    for (User user: users ) {
	        usersTO.add(toUserTO(user));
	     }
		return usersTO;
	}
}
