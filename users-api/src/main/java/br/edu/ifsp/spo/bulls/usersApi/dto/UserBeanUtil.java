package br.edu.ifsp.spo.bulls.usersApi.dto;

import org.springframework.beans.BeanUtils;

import br.edu.ifsp.spo.bulls.usersApi.domain.User;

public class UserBeanUtil {

	public User toUser(UserTO userTO) {
		User user = new User();
		try {
			BeanUtils.copyProperties(userTO, user);
		}catch(Exception e) {
			
		}
		
		
		return user;
	}
	
	public UserTO toUserTO(User user) {
		UserTO userTO = new UserTO();
		
		BeanUtils.copyProperties(user, userTO);
		
		return userTO;
	}
}
