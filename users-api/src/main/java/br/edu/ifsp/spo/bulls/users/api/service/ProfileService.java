package br.edu.ifsp.spo.bulls.users.api.service;

import java.util.HashSet;
import br.edu.ifsp.spo.bulls.users.api.bean.ProfileBeanUtil;
import br.edu.ifsp.spo.bulls.users.api.domain.Profile;
import br.edu.ifsp.spo.bulls.users.api.domain.User;
import br.edu.ifsp.spo.bulls.common.api.dto.ProfileTO;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import br.edu.ifsp.spo.bulls.users.api.repository.ProfileRepository;

@Service
public class ProfileService {
	
	@Autowired
	private ProfileRepository profileRep;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProfileBeanUtil beanUtil;
	
	public Profile save(Profile entity){
		return profileRep.save(entity);
	}
	
	public ProfileTO getByUser(String username) {
		User user = userService.getByUsername( username);
		Profile profile =  profileRep.findByUser(user);
		
		return beanUtil.toProfileTO(profile);
	}

	public Profile getByUsername(String username) {
		return profileRep.findByUser(userService.getByUsername(username));
	}

	public ProfileTO getById(int id) {
		Profile profile = profileRep.findById(id).orElseThrow( () -> new ResourceNotFoundException(CodeException.PF001.getText(), CodeException.PF001));
		return beanUtil.toProfileTO(profile); 
	}

	public Profile getDomainById(int id) {
		return profileRep.findById(id).orElseThrow( () -> new ResourceNotFoundException(CodeException.PF001.getText(), CodeException.PF001));
	}

	public void delete(int  id) {
		profileRep.findById(id).orElseThrow( () -> new ResourceNotFoundException(CodeException.PF001.getText(), CodeException.PF001));
		profileRep.deleteById(id);
	}
	
	public void deleteByUser(User  user) {
		Profile profile = profileRep.findByUser(user);
		profileRep.deleteById(profile.getId());
	}

	public ProfileTO update(ProfileTO entity) throws Exception {
		
		Profile retorno = profileRep.findById(entity.getId()).map( profile -> {
			profile.setBirthDate(entity.getBirthDate());
			profile.setCity(entity.getCity());
			profile.setCountry(entity.getCountry());
			profile.setName(entity.getName());
			profile.setLastName(entity.getLastName());
			profile.setState(entity.getState());
			return profileRep.save(profile);
		}).orElseThrow( () -> new ResourceNotFoundException(CodeException.PF001.getText(), CodeException.PF001));

		return beanUtil.toProfileTO(retorno);
	}

	public HashSet<ProfileTO> getAll() {

		HashSet<Profile> profile =  profileRep.findAll();
		return beanUtil.toProfileTO(profile);
	}

	public Profile getDomainByToken(String token) {
		User user = userService.getByToken(token);

		if(user != null) {
			return  profileRep.findByUser(user);
		}
		return null;
	}

	public HashSet<Profile> getAllDomainById(Iterable<Integer> ids) {
		return profileRep.findAllById(ids);
	}

	public HttpStatus updateProfileImage(String url, String token) {
		Profile profile = getDomainByToken(token);
		profile.setProfileImage(url);
		profileRep.save(profile);
		return HttpStatus.CREATED;
	}

	public ProfileTO getByToken(String token) {
		User user = userService.getByToken(token);
		if(user != null) {
			return beanUtil.toProfileTO(profileRep.findByUser(user));
		}
		return null;
	}

}
