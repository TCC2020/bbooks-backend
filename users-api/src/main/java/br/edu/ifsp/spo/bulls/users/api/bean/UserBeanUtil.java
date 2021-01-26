package br.edu.ifsp.spo.bulls.users.api.bean;

import br.edu.ifsp.spo.bulls.common.api.domain.User;
import br.edu.ifsp.spo.bulls.users.api.dto.CadastroUserTO;
import br.edu.ifsp.spo.bulls.common.api.domain.Friendship;
import br.edu.ifsp.spo.bulls.users.api.dto.UserTO;
import java.util.HashSet;
import br.edu.ifsp.spo.bulls.users.api.repository.FriendshipRepository;
import br.edu.ifsp.spo.bulls.users.api.repository.ProfileRepository;
import br.edu.ifsp.spo.bulls.users.api.service.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserBeanUtil {
	private Logger logger = LoggerFactory.getLogger(UserBeanUtil.class);

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

	public HashSet<UserTO> toUserTO(HashSet<User> users){
		HashSet<UserTO> usersTO = new HashSet<>();

	    for (User user: users ) {
	        usersTO.add(toUserTO(user));
	     }
		return usersTO;
	}
}
