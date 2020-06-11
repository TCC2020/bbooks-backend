package br.edu.ifsp.spo.bulls.usersApi.controller;


import java.util.HashSet;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import br.edu.ifsp.spo.bulls.usersApi.domain.User;
import br.edu.ifsp.spo.bulls.usersApi.bean.UserBeanUtil;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserTO;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceBadRequestException;
import br.edu.ifsp.spo.bulls.usersApi.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService service;

	@Autowired
	private UserBeanUtil beanUtil;

	@PostMapping ("")
	public UserTO create(@RequestBody @Valid UserTO userTO) throws ResourceBadRequestException, Exception  {
		
		User user = beanUtil.toUser(userTO);
		service.save(user);
		
		return beanUtil.toUserTO(user);
		
	}
	
	@GetMapping ("")
	public HashSet<UserTO> getAll(){
		
		return beanUtil.toUserTO(service.getAll());
	}
	
	@GetMapping ("/{id}")
	public UserTO getById(@PathVariable String id) {
		
		return beanUtil.toUserTO(service.getById(id));
		
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable String id) {
		service.delete(id);
	}
	
	@PutMapping("/{id}")
	public UserTO update(@RequestBody UserTO userTO) throws Exception {
		User user = beanUtil.toUser(userTO);
		service.update(user);
		return beanUtil.toUserTO(user);
	}

	@GetMapping("/info")
	public UserTO getInfoByToken(@RequestHeader(value = "AUTHORIZATION") String token){
		token = StringUtils.removeStart(token, "Bearer").trim();
		return beanUtil.toUserTO(service.getByToken(token));
	}
}
