package br.edu.ifsp.spo.bulls.users.api.controller;

import java.util.HashSet;
import java.util.UUID;
import javax.validation.Valid;
import br.edu.ifsp.spo.bulls.users.api.bean.UserBeanUtil;
import br.edu.ifsp.spo.bulls.users.api.dto.CadastroUserTO;
import br.edu.ifsp.spo.bulls.users.api.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import br.edu.ifsp.spo.bulls.common.api.dto.UserTO;
import org.springframework.http.HttpStatus;


@RestController
@ControllerAdvice
@RequestMapping(value = "/users", produces="application/json")
@CrossOrigin(origins = "*")
public class UserController {

	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService service;

	@Autowired
	private UserBeanUtil beanUtil;

	@ApiOperation(value = "Cadastrar um usuário")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Retorna o usuário cadastrado"),
			@ApiResponse(code = 409, message = "Conflito ao cadastrar o usuário. O email ou nome de usuário já está sendo utilizado"),
			@ApiResponse(code = 500, message = "Foi gerada uma exceção"),
	})
	@PostMapping(consumes="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public UserTO create(@RequestBody @Valid CadastroUserTO userTO) throws Exception  {
		UserTO user = service.save(userTO);
		logger.info("Novo cadastro no sistema" + user);
		return user;
	}
	@GetMapping ("/username/{username}")
	public UserTO getByUserName(@PathVariable String username, @RequestHeader(value = "AUTHORIZATION") String token) {
		String tokenValue = StringUtils.removeStart(token, "Bearer").trim();
		return service.getByUserName(username, tokenValue);
	}

	@ApiOperation(value = "Retorna informações de todos os usuários")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna uma lista de usuários")
	})
	@GetMapping
	public HashSet<UserTO> getAll(){
		logger.info("Acessando dados de todos os usuários");
		HashSet<UserTO> users = service.getAll();
		logger.info("Usuarios retornados: " + users.toString());
		return users ;
	}

	@ApiOperation(value = "Retorna informações de um usuário a partir do código identificador")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna o usuário"),
			@ApiResponse(code = 404, message = "Usuário não encontrado")
	})
	@GetMapping ("/{id}")
	public UserTO getById(@PathVariable UUID id) {
		logger.info("Acessando dados do usuário: " + id);
		UserTO userTO = service.getById(id);
		logger.info("Usuario retornado: " + userTO.toString());
		return userTO;
	}

	@ApiOperation(value = "Apaga um usuário do sistema")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Usuário foi deletado"),
			@ApiResponse(code = 404, message = "Usuário não encontrado")
	})
	@DeleteMapping("/{id}")
	public void delete(@PathVariable UUID id) {
		logger.info("Deletando usuário: " + id);
		service.delete(id);
	}

	@ApiOperation(value = "Altera dados do usuário")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Usuário foi alterado"),
			@ApiResponse(code = 404, message = "Usuário não encontrado"),
			@ApiResponse(code = 409, message = "Conflito ao cadastrar o usuário. O email ou nome de usuário já está sendo utilizado")
	})
	@PutMapping(value = "/{id}", consumes="application/json")
	public UserTO update(@RequestBody UserTO userTO, @PathVariable UUID id) throws Exception {
		logger.info("Alterando usuário " + userTO);
		return service.update(userTO);
	}

	@ApiOperation(value = "Retorna informações de um usuário a partir do token")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna o usuário"),
			@ApiResponse(code = 404, message = "Autenticação não encontrada")
	})
	@GetMapping("/info")
	public UserTO getInfoByToken(@RequestHeader(value = "AUTHORIZATION") String token){
		logger.info("Tentando requisição de usuário por token ");
		String tokenValue = StringUtils.removeStart(token, "Bearer").trim();
		UserTO userTO = beanUtil.toUserTO(service.getByToken(tokenValue));
		logger.info("Usuário encontrado: " + userTO);
		return userTO;
	}

	@GetMapping("/profile/{id}")
	public UserTO getByProfileId(@PathVariable("id") int profileId) {
		logger.info("Buscando por profile com id: " + profileId);
		return service.getByProfileId(profileId);
	}

	@ApiOperation(value = "Retorna informações de um usuário a partir do email")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna o usuário"),
			@ApiResponse(code = 404, message = "Email não encontrado")
	})
	@GetMapping("/email/{email}")
	public UserTO getByEmail(@PathVariable String email){
		logger.info("Acessando dados do usuário: " + email);
		UserTO userTO = service.getByEmail(email);
		logger.info("Usuario retornado: " + userTO.toString());
		return userTO;
	}

	@ApiOperation(value = "Retorna informações de um usuário para realizar login com google")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna o usuário"),
			@ApiResponse(code = 404, message = "Email não encontrado")
	})
	@GetMapping("/google/{email}")
	public UserTO getForGoogle(@PathVariable String email){
		logger.info("Acessando dados do usuário: " + email);
		UserTO userTO = service.getForGoogle(email);
		logger.info("Usuario retornado: " + userTO.toString());
		return userTO;
	}

}
