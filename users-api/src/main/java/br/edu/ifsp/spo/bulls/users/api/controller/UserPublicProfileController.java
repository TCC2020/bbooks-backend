package br.edu.ifsp.spo.bulls.users.api.controller;

import br.edu.ifsp.spo.bulls.common.api.dto.user.UserPublicProfileCreateTO;
import br.edu.ifsp.spo.bulls.common.api.dto.user.UserPublicProfileTO;
import br.edu.ifsp.spo.bulls.common.api.dto.user.UserPublicProfileUpdateTO;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.users.api.service.UserPublicProfileService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/users/public-profiles", produces="application/json")
@CrossOrigin(origins = "*")
public class UserPublicProfileController {
    @Autowired
    private UserPublicProfileService service;

    @ApiOperation(value = "Retorna lista de perfil público por nome pesquisado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna uma lista de perfís públicos")
    })
    @GetMapping("/search")
    public List<UserPublicProfileTO> searchByName(@RequestParam String name) {
        return service.search(name);
    }

    @ApiOperation(value = "Retorna perfil público por id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna perfil por id"),
            @ApiResponse(code = 404, message = "Perfil não encontrado")
    })
    @GetMapping("/{id}")
    public UserPublicProfileTO getById(@PathVariable("id") UUID id) {
        return service.getById(id);
    }

    @ApiOperation(value = "Retorna perfil público por user id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna perfil por user id ou null"),
    })
    @GetMapping("/user/{id}")
    public UserPublicProfileTO getByUserId(@PathVariable("id") UUID userId) {
        UserPublicProfileTO to = service.getByUserId(userId);
        if(to != null)
            return to;
        throw new ResourceNotFoundException(CodeException.UPF001);
    }

    @ApiOperation(value = "Cria perfil público")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna perfil criado"),
            @ApiResponse(code = 409, message = "Perfil público já existe")
    })
    @PostMapping
    public UserPublicProfileTO save(@RequestHeader("AUTHORIZATION") String token, @RequestBody UserPublicProfileCreateTO publicProfile) {
        return service.save(token, publicProfile);
    }

    @ApiOperation(value = "Atualiza perfil público")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna perfil atualizado"),
            @ApiResponse(code = 404, message = "Perfil público não encotrado"),
            @ApiResponse(code = 409, message = "Perfil público já existe")
    })
    @PutMapping
    public UserPublicProfileTO update(@RequestHeader("AUTHORIZATION") String token, @RequestBody UserPublicProfileUpdateTO publicProfile) {
        return service.update(token, publicProfile);
    }

    @ApiOperation(value = "Segue perfil de determinado id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Perfil seguido"),
            @ApiResponse(code = 404, message = "Perfil público não encotrado"),
            @ApiResponse(code = 304, message = "Já segue o perfil")
    })
    @PutMapping("/follow/{id}")
    public HttpStatus follow(@RequestHeader("AUTHORIZATION") String token, @PathVariable("id") UUID publicProfileId) {
        return service.follow(token, publicProfileId);
    }

    @ApiOperation(value = "Deixar de seguir perfil de determinado id")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Perfil público não encotrado"),
    })
    @PutMapping("/unfollow/{id}")
    public void unfollow(@RequestHeader("AUTHORIZATION") String token, @PathVariable("id") UUID publicProfileId) {
        service.unfollow(token, publicProfileId);
    }

    @ApiOperation(value = "Deletar perfil de determinado id")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Perfil público não encotrado"),
    })
    @DeleteMapping("/{id}")
    public void delete(@RequestHeader("AUTHORIZATION") String token, @PathVariable("id") UUID publicProfileId) {
        service.delete(token, publicProfileId);
    }
}
