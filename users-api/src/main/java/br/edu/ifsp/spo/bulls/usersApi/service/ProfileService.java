package br.edu.ifsp.spo.bulls.usersApi.service;

import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.edu.ifsp.spo.bulls.usersApi.bean.ProfileBeanUtil;
import br.edu.ifsp.spo.bulls.usersApi.bean.UserBeanUtil;
import br.edu.ifsp.spo.bulls.usersApi.domain.Profile;
import br.edu.ifsp.spo.bulls.usersApi.domain.User;
import br.edu.ifsp.spo.bulls.usersApi.dto.ProfileTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserTO;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.usersApi.repository.ProfileRepository;
import br.edu.ifsp.spo.bulls.usersApi.service.impl.EmailServiceImpl;

@Service
public class ProfileService {
	
	@Autowired
	private ProfileRepository profileRep;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	EmailServiceImpl email;
	
	@Autowired
	private ProfileBeanUtil beanUtil;
	
	@Autowired
	private UserBeanUtil userBeanUtil;
	
	
	public Profile save(Profile entity) throws Exception {
		
		return profileRep.save(entity);
	}
	
	public ProfileTO getByUser(String username) {
		
		UserTO user = userService.getById(username);
		Profile profile =  profileRep.findByUser(userBeanUtil.toUser(user));
		
		return beanUtil.toProfileTO(profile);
	}

	public ProfileTO getById(int id) {
		
		Profile profile = profileRep.findById(id).orElseThrow( () -> new ResourceNotFoundException("Profile not found"));
		
		return beanUtil.toProfileTO(profile); 
	}

	public void delete(int  id) {
		
		profileRep.findById(id).orElseThrow( () -> new ResourceNotFoundException("Profile not found"));
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
		}).orElseThrow( () -> new ResourceNotFoundException("Profile not found"));
		
		enviarEmail(beanUtil.toProfile(entity));
		
		return beanUtil.toProfileTO(retorno);
		
	}

	private void enviarEmail(Profile entity) {
		String texto = "Ola, " + entity.getName() + " " + entity.getLastName() + 
				"! Confirme seu email aqui: http://localhost:4200/confirm";
		
		email.sendEmailTo(entity.getUser().getEmail(), "BBooks - Confirme seu email", texto);
	}

	public HashSet<ProfileTO> getAll() {
		
		HashSet<Profile> profile =  profileRep.findAll();
		return beanUtil.toProfileTO(profile);
	}

}
