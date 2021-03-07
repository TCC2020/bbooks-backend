package br.edu.ifsp.spo.bulls.users.api.controller;

import java.util.HashSet;
import javax.validation.Valid;
import br.edu.ifsp.spo.bulls.common.api.dto.ProfileTO;
import br.edu.ifsp.spo.bulls.common.api.dto.profile.BaseProfileTO;
import br.edu.ifsp.spo.bulls.users.api.service.ProfileService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/profiles", produces="application/json")
@CrossOrigin(origins = "*")
public class ProfileController {

	private Logger logger = LoggerFactory.getLogger(ProfileController.class);

	@Autowired
	private ProfileService service;

	@ApiOperation(value = "Alterar o cadastro de um profile")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna o profile alterado"),
			@ApiResponse(code = 404, message = "Profile não encontrado"),
	})
	@PutMapping(value = "{id}", consumes="application/json")
	public ProfileTO update(@RequestBody @Valid ProfileTO profileTO, @PathVariable int id) throws Exception {
		logger.info("Requisicao para atualizar o cadastro de um profile " + profileTO);
		return service.update(profileTO);
	}

	@ApiOperation(value = "Deleta o profile")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Profile deletado"),
			@ApiResponse(code = 404, message = "Profile não encontrado"),
	})
	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable int id){
		logger.info("Requisião solicitada para deletar um profile " + id);
		service.delete(id);
	}

	@GetMapping("/basic/{profileId}")
	public BaseProfileTO getBaseProfileById(@PathVariable("profileId") int profileId) {
		return service.getBaseProfileById(profileId);
	}

	@ApiOperation(value = "Retorna um profile a partir do identificador")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna o profile"),
			@ApiResponse(code = 404, message = "Profile não encontrado"),
	})
	@GetMapping("/{id}")
	public ProfileTO get(@PathVariable int id) {
		logger.info("Requisitando informações de um profile: " + id);
		ProfileTO profile = service.getById(id);
		logger.info("Profile encontrado: " + profile);
		return profile;
	}

	@ApiOperation(value = "Retorna o profile a partir o nome de usuário")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna o profile"),
			@ApiResponse(code = 404, message = "Profile não encontrado"),
	})
	@GetMapping(value = "/user/{userName}")
	public ProfileTO getByUser(@PathVariable String userName) {
		logger.info("Requisitando informações de um profile a partir do usuario: " + userName);
		ProfileTO profile = service.getByUser(userName);
		logger.info("Profile encontrado: " + profile);
		return profile;
	}


	@ApiOperation(value = "Retorna informações de todos os profiles")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna uma lista de profiles")
	})
	@GetMapping
	public HashSet<ProfileTO> getAll() {
		logger.info("Requisitando todos os profiles do cadastro");
		HashSet<ProfileTO> profiles = service.getAll();
		logger.info("Profiles encontrados: " + profiles);
		return profiles;
	}

	@GetMapping("/token/{token}")
	public ProfileTO getByToken(@PathVariable String token) {
		logger.info("Requisitando profile por token cadastro");
		return service.getByToken(token);
	}
}
