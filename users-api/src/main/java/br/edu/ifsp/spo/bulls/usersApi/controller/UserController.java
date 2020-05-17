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
	
	@PutMapping("")
	public UserTO update(@RequestBody UserTO userTO) throws Exception {
		
		User user = beanUtil.toUser(userTO);
		service.update(user);
		return beanUtil.toUserTO(user);

		
	}
}
