package br.edu.ifsp.spo.bulls.usersApi.controller;

import java.util.HashSet;

import javax.validation.Valid;

import br.edu.ifsp.spo.bulls.usersApi.dto.ProfileRegisterTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.edu.ifsp.spo.bulls.usersApi.dto.ProfileTO;
import br.edu.ifsp.spo.bulls.usersApi.service.ProfileService;

@RestController
@RequestMapping("/profiles")
@CrossOrigin(origins = "*")
public class ProfileController {

	@Autowired
	private ProfileService service;
	
	@PutMapping()
	public ProfileTO update(@RequestBody @Valid ProfileTO profileTO, @PathVariable int id) throws Exception {

		profileTO.setId(id);

		return service.update(profileTO);
	}
	@PutMapping("/profileRegister")
	public ProfileTO updateProfileRegister(@RequestBody @Valid ProfileRegisterTO profileRegisterTO) throws Exception {
		return service.updateByProfileRegisterTO(profileRegisterTO);
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable int id){
		service.delete(id);
	}
	
	@GetMapping("{id}")
	public ProfileTO get(@PathVariable int id) {
		return service.getById(id);
	}
	
	@GetMapping("/user/{userName}")
	public ProfileTO getByUser(@PathVariable String userName) {
		
		return service.getByUser(userName);
		
	}
	
	@GetMapping
	public HashSet<ProfileTO> getAll() {
		return service.getAll();
	}
}
