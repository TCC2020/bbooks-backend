package br.edu.ifsp.spo.bulls.usersApi.controller;

import java.util.HashSet;
import java.util.UUID;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import br.edu.ifsp.spo.bulls.usersApi.bean.UserBeanUtil;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserTO;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceBadRequestException;
import br.edu.ifsp.spo.bulls.usersApi.service.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService service;

	@Autowired
	private UserBeanUtil beanUtil;

	@PostMapping
	public UserTO create(@RequestBody @Valid UserTO userTO) throws ResourceBadRequestException, Exception  {
		
		return service.save(userTO);
	}

	
	@GetMapping
	public HashSet<UserTO> getAll(){
		logger.info("Acessando dados de todos os usuários");
		HashSet<UserTO> users = service.getAll();
		logger.info("Usuarios retornados: " + users.toString());
		return users ;
	}
	
	@GetMapping ("/{id}")
	public UserTO getById(@PathVariable UUID id) {
		
		return service.getById(id);
		
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable UUID id) {
		service.delete(id);
	}
	
	@PutMapping("/{id}")
	public UserTO update(@RequestBody UserTO userTO, @PathVariable UUID id) throws Exception {
		
		return service.update(userTO);
	}

	@GetMapping("/info")
	public UserTO getInfoByToken(@RequestHeader(value = "AUTHORIZATION") String token){
		token = StringUtils.removeStart(token, "Bearer").trim();
		return beanUtil.toUserTO(service.getByToken(token));
	}

	@GetMapping("/email/{email}")
	public UserTO getByEmail(@PathVariable String email){
		return service.getByEmail(email);
	}
}
