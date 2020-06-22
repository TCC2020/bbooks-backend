package br.edu.ifsp.spo.bulls.usersApi.service;

import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.edu.ifsp.spo.bulls.usersApi.domain.Profile;
import br.edu.ifsp.spo.bulls.usersApi.domain.User;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.usersApi.repository.ProfileRepository;
import br.edu.ifsp.spo.bulls.usersApi.service.impl.EmailServiceImpl;

@Service
public class ProfileService {
	
	@Autowired
	private ProfileRepository profileRep;
	
	@Autowired
	EmailServiceImpl email;
	
	public Profile save(Profile entity) throws Exception {
		
		return profileRep.save(entity);
	}
	
	public Profile getByUser(User user) {
		return profileRep.findByUser(user);
	}

	public Profile getById(int id) {
		
		return profileRep.findById(id).orElseThrow( () -> new ResourceNotFoundException("Profile not found"));
	}

	public void delete(int  id) {
		
		profileRep.findById(id).orElseThrow( () -> new ResourceNotFoundException("Profile not found"));
		profileRep.deleteById(id);
	}
	
	public void deleteByUser(User  user) {
		
		Profile profile = profileRep.findByUser(user);
		profileRep.deleteById(profile.getId());
	}

	public Profile update(Profile entity) throws Exception {
		
		Profile retorno = profileRep.findById(entity.getId()).map( profile -> {
			profile.setBirthDate(entity.getBirthDate());
			profile.setCity(entity.getCity());
			profile.setCountry(entity.getCountry());
			profile.setName(entity.getName());
			profile.setLastName(entity.getLastName());
			profile.setState(entity.getState());
			return profileRep.save(profile);
		}).orElseThrow( () -> new ResourceNotFoundException("Profile not found"));
		
		enviarEmail(entity);
		
		return retorno; // depois mudar para ProfileTO 
		
	}

	private void enviarEmail(Profile entity) {
		String texto = "Ola, " + entity.getName() + " " + entity.getLastName() + 
				"! Confirme seu email aqui: http://localhost:4200/confirm";
		
		email.sendEmailTo(entity.getUser().getEmail(), "BBooks - Confirme seu email", texto);
	}

	public HashSet<Profile> getAll() {
		
		return profileRep.findAll();
	}

}
