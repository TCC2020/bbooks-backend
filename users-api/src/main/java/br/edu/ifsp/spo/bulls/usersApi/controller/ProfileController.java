package br.edu.ifsp.spo.bulls.usersApi.controller;

import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.edu.ifsp.spo.bulls.usersApi.bean.ProfileBeanUtil;
import br.edu.ifsp.spo.bulls.usersApi.domain.Profile;
import br.edu.ifsp.spo.bulls.usersApi.dto.ProfileTO;
import br.edu.ifsp.spo.bulls.usersApi.service.ProfileService;

@RestController
@RequestMapping("/profiles")
@CrossOrigin(origins = "*")
public class ProfileController {

	@Autowired
	private ProfileService service;

	@Autowired
	private ProfileBeanUtil beanUtil;
	
	@PutMapping("{id}")
	public ProfileTO update(@RequestBody @Valid ProfileTO profileTO, @PathVariable int id) throws Exception {
		
		profileTO.setId(id);
		Profile profile = beanUtil.toProfile(profileTO);
		Profile profile1 = service.update(profile);
		
		return beanUtil.toProfileTO(profile1);
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable int id){
		service.delete(id);
	}
	
	@GetMapping("{id}")
	public ProfileTO get(@PathVariable int id) {
		
		Profile profile1 = service.getById(id);
		return beanUtil.toProfileTO(profile1);
		
	}
	
	@GetMapping("")
	public HashSet<ProfileTO> getAll() {
		
		HashSet<Profile> profile1 = service.getAll();
		return beanUtil.toProfileTO(profile1);
	}
}
