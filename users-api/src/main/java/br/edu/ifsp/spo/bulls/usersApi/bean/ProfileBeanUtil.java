package br.edu.ifsp.spo.bulls.usersApi.bean;

import java.util.HashSet;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import br.edu.ifsp.spo.bulls.usersApi.domain.Profile;
import br.edu.ifsp.spo.bulls.usersApi.dto.ProfileTO;

@Component
public class ProfileBeanUtil {

	public Profile toProfile(ProfileTO profileTO) {
		Profile profile = new Profile();
		
		try{
			BeanUtils.copyProperties(profileTO, profile);
		}catch(Exception e) {
			
		}
		
		return profile;
	}
	
	public ProfileTO toProfileTO(Profile profile) {
		ProfileTO profileTO = new ProfileTO();
		
		try{
			BeanUtils.copyProperties(profile, profileTO);
		}catch(Exception e) {
			
		}
		
		return profileTO;
	}
	public HashSet<ProfileTO> toProfileTO(HashSet<Profile> profiles){
		
		HashSet<ProfileTO> profilesTO = new HashSet<ProfileTO>();

	    for (Profile profile: profiles ) {
	        profilesTO.add(toProfileTO(profile));
	     }
		
		return profilesTO;
	}
}
