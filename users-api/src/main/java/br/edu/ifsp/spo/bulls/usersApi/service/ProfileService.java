package br.edu.ifsp.spo.bulls.usersApi.service;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.edu.ifsp.spo.bulls.usersApi.domain.Profile;
import br.edu.ifsp.spo.bulls.usersApi.domain.User;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.usersApi.repository.ProfileRepository;
import br.edu.ifsp.spo.bulls.usersApi.repository.UserRepository;

@Service
public class ProfileService {
	
	@Autowired
	private UserRepository userRep;
	
	@Autowired
	private ProfileRepository profileRep;
	
	public Profile save(Profile entity) throws Exception {
		
		validationUserNotFound(entity.getUser());
		
		validationUserAlreadyUsed(entity.getUser());
			
		return profileRep.save(entity);
	}

	private void validationUserAlreadyUsed(User entity) {
		if(profileRep.existsByUser(entity))
			throw new ResourceConflictException("User already used");
	}

	private void validationUserNotFound(User entity) {
		if(userRep.existsByUserName(entity.getUserName()) == false) 
			throw new ResourceNotFoundException("User not found");
	}

	public Profile getById(int id) {
		
		return profileRep.findById(id).orElseThrow( () -> new ResourceNotFoundException("Profile not found"));
	}

	public void delete(int  id) {
		
		profileRep.findById(id).orElseThrow( () -> new ResourceNotFoundException("Profile not found"));
		profileRep.deleteById(id);
	}

	public Profile update(Profile entity) throws Exception {
		
		return profileRep.findById(entity.getId()).map( profile -> {
			profile.setBirthDate(entity.getBirthDate());
			profile.setCity(entity.getCity());
			profile.setCountry(entity.getCountry());
			profile.setFullName(entity.getFullName());
			profile.setState(entity.getState());
			return profileRep.save(profile);
		}).orElseThrow( () -> new ResourceNotFoundException("Profile not found"));
	}

	public HashSet<Profile> getAll() {
		
		return profileRep.findAll();
	}

}
