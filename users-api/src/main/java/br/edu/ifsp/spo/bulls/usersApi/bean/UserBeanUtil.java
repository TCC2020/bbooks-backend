package br.edu.ifsp.spo.bulls.usersApi.bean;

import br.edu.ifsp.spo.bulls.usersApi.domain.Friendship;
import br.edu.ifsp.spo.bulls.usersApi.dto.CadastroUserTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserTO;

import java.util.HashSet;
import java.util.UUID;

import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.usersApi.repository.FriendshipRepository;
import br.edu.ifsp.spo.bulls.usersApi.repository.ProfileRepository;
import br.edu.ifsp.spo.bulls.usersApi.repository.UserRepository;
import br.edu.ifsp.spo.bulls.usersApi.service.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import br.edu.ifsp.spo.bulls.usersApi.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserBeanUtil {
	private Logger logger = LoggerFactory.getLogger(UserBeanUtil.class);

	@Autowired
	private UserRepository repository;

	@Autowired
	private ProfileBeanUtil profileBeanUtil;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private FriendshipRepository friendshipRepository;

	public User toUser(UserTO userTO) {
		User user = new User();
		
		try{
			BeanUtils.copyProperties(userTO, user);
		}catch(Exception e) {
			logger.error("Error while converting UserTO to User: " +  e);
		}

		return user;
	}

	public User toUser(CadastroUserTO cadastroUserTO) {
		User user = new User();

		try{
			BeanUtils.copyProperties(cadastroUserTO, user);
		}catch(Exception e) {
			logger.error("Error while converting CadastroUserTO to User: " +  e);
		}

		return user;
	}

	public UserTO toUserTO(User user) {
		UserTO userTO = new UserTO();
		
		try{
			BeanUtils.copyProperties(user, userTO);
		}catch(Exception e) {
			logger.error("Error while converting User to UserTO: " +  e);
		}
		userTO.setProfile(profileBeanUtil.toProfileTO(profileRepository.findByUser(user)));
		return userTO;
	}

	public UserTO toUserTO(User user, String token) {
		UserTO userTO = new UserTO();

		try{
			BeanUtils.copyProperties(user, userTO);
		}catch(Exception e) {
			logger.error("Error while converting User to UserTO: " +  e);
		}
		userTO.setProfile(profileBeanUtil.toProfileTO(profileRepository.findByUser(user)));
		if(token != null) {
			friendshipRepository.hasFriendship(profileService.getDomainByToken(token).getId(), userTO.getProfile().getId())
					.ifPresent(friendship1 -> {
						if(friendship1.getStatus() == Friendship.FriendshipStatus.pending){
							if(friendship1.getProfile1() == profileService.getDomainByToken(token).getId()) {
								userTO.getProfile().setFriendshipStatus("sent");
							}
							else
								userTO.getProfile().setFriendshipStatus("received");
						}
						else
							userTO.getProfile().setFriendshipStatus("added");
					});
		}
		return userTO;
	}

	public UserTO toUserTO(CadastroUserTO user) {
		UserTO userTO = new UserTO();

		try{
			BeanUtils.copyProperties(user, userTO);
		}catch(Exception e) {
			logger.error("Error while converting User to UserTO: " +  e);
		}
		userTO.setProfile(profileBeanUtil.toProfileTO(profileRepository.findByUser(this.toUser(user))));
		return userTO;
	}

	public CadastroUserTO toCadastroTO(UserTO user) {
		CadastroUserTO userTO = new CadastroUserTO();

		try{
			BeanUtils.copyProperties(user, userTO);
		}catch(Exception e) {
			logger.error("Error while converting UserTO to CadastroUserTO: " +  e);
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
