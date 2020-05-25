package br.edu.ifsp.spo.bulls.usersApi.controller;

import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.edu.ifsp.spo.bulls.usersApi.domain.Profile;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceBadRequestException;
import br.edu.ifsp.spo.bulls.usersApi.service.ProfileService;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

	@Autowired
	private ProfileService service;


	@PostMapping ("")
	public Profile create(@RequestBody @Valid Profile profile) throws ResourceBadRequestException, Exception  {
		
		return service.save(profile);
	}
	
	@PutMapping("{id}")
	public Profile update(@RequestBody Profile profile, @PathVariable int id) throws Exception {
		
		profile.setId(id);
		return service.update(profile);
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable int id){
		service.delete(id);
	}
	
	@GetMapping("{id}")
	public Profile get(@PathVariable int id) {
		return service.getById(id);
	}
	
	@GetMapping("")
	public HashSet<Profile> getAll() {
		return service.getAll();
	}
}
