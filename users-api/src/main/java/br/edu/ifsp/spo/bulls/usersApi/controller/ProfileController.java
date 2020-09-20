package br.edu.ifsp.spo.bulls.usersApi.controller;

import java.util.HashSet;

import javax.validation.Valid;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@RequestMapping(value = "/profiles", produces="application/json")
@CrossOrigin(origins = "*")
public class ProfileController {

	@Autowired
	private ProfileService service;

	@ApiOperation(value = "Alterar o cadastro de um profile")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna o profile alterado"),
			@ApiResponse(code = 404, message = "Profile não encontrado"),
	})
	@PutMapping(value = "{id}", consumes="application/json")
	public ProfileTO update(@RequestBody @Valid ProfileTO profileTO, @PathVariable int id) throws Exception {

		profileTO.setId(id);

		return service.update(profileTO);
	}

	@ApiOperation(value = "Deleta o profile")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Profile deletado"),
			@ApiResponse(code = 404, message = "Profile não encontrado"),
	})
	@DeleteMapping(value = "{id}")
	public void delete(@PathVariable int id){
		service.delete(id);
	}

	@ApiOperation(value = "Retorna um profile a partir do identificador")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna o profile"),
			@ApiResponse(code = 404, message = "Profile não encontrado"),
	})
	@GetMapping("{id}")
	public ProfileTO get(@PathVariable int id) {
		return service.getById(id);
	}

	@ApiOperation(value = "Retorna o profile a partir o nome de usuário")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna o profile"),
			@ApiResponse(code = 404, message = "Profile não encontrado"),
	})
	@GetMapping(value = "/user/{userName}")
	public ProfileTO getByUser(@PathVariable String userName) {
		
		return service.getByUser(userName);
		
	}

	@ApiOperation(value = "Retorna informações de todos os profiles")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna uma lista de profiles")
	})
	@GetMapping
	public HashSet<ProfileTO> getAll() {
		return service.getAll();
	}
}
