package br.edu.ifsp.spo.bulls.usersApi.bean;

import br.edu.ifsp.spo.bulls.usersApi.dto.UserTO;

import java.util.HashSet;
import org.springframework.beans.BeanUtils;
import br.edu.ifsp.spo.bulls.usersApi.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserBeanUtil {

	public User toUser(UserTO userTO) {
		User user = new User();
		
		BeanUtils.copyProperties(userTO, user);
		
		return user;
	}
	
	public UserTO toUserTO(User user) {
		UserTO userTO = new UserTO();
		
		BeanUtils.copyProperties(user, userTO);
		
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
