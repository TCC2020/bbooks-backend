package br.edu.ifsp.spo.bulls.usersApi.controller;

import javax.validation.Valid;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.spo.bulls.usersApi.dto.User;
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
	public User criar(@RequestBody @Valid User user) throws Exception {
		
		return userService.save(user);
		
	}

}
