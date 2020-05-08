package br.edu.ifsp.spo.bulls.usersApi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.spo.bulls.usersApi.domain.User;
import br.edu.ifsp.spo.bulls.usersApi.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@PostMapping ("")
	public User create(@RequestBody @Valid User user) throws Exception {
		
		return userService.save(user);
		
	}
	
	@GetMapping ("")
	public List<User> getAll(){
		return userService.getAll();
	}
	
	@GetMapping ("/{id}")
	public User getById(@PathVariable String id) {
		
		return userService.getById(id);
		//return rep.findById(id).orElseThrow( () -> new ResourceNotFoundException(""));
	}

	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable String id) {
		userService.delete(id);
		return "User deletado" ;
	}
	
	@PutMapping("")
	public User update(@RequestBody User user) {
		
		return userService.update(user);
	}
}
