package br.edu.ifsp.spo.bulls.users.api.bean;

import java.util.HashSet;
import br.edu.ifsp.spo.bulls.users.api.domain.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import br.edu.ifsp.spo.bulls.common.api.dto.ProfileTO;

@Component
public class ProfileBeanUtil {

	private Logger logger = LoggerFactory.getLogger(ProfileBeanUtil.class);

	public Profile toProfile(ProfileTO profileTO) {
		Profile profile = new Profile();
		
		try{
			BeanUtils.copyProperties(profileTO, profile);
		}catch(Exception e) {
			logger.error("Error while converting ProfileTO to Profile: " +  e);
		}
		
		return profile;
	}
	
	public ProfileTO toProfileTO(Profile profile) {
		ProfileTO profileTO = new ProfileTO();
		
		try{
			BeanUtils.copyProperties(profile, profileTO);
			profileTO.setUsername(profile.getUser().getUserName());
		}catch(Exception e) {
			logger.error("Error while converting Profile to ProfileTO: " +  e);
		}
		
		return profileTO;
	}

	public HashSet<ProfileTO> toProfileTO(HashSet<Profile> profiles){
		
		HashSet<ProfileTO> profilesTO = new HashSet<>();

	    for (Profile profile: profiles ) {
	        profilesTO.add(toProfileTO(profile));
	     }
		
		return profilesTO;
	}
}
