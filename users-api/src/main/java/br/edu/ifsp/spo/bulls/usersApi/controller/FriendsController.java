package br.edu.ifsp.spo.bulls.usersApi.controller;

import br.edu.ifsp.spo.bulls.usersApi.dto.FriendTO;
import br.edu.ifsp.spo.bulls.usersApi.service.FriendsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friends")
public class FriendsController {
    @Autowired
    private FriendsService service;

    @ApiOperation(value = "Adicionar usuário")
    @ApiResponses(value = {
            @ApiResponse(code = 102, message = "Solicitação já feita"),
            @ApiResponse(code = 202, message = "Solicitação feita"),
            @ApiResponse(code = 401, message = "Solicitação recusada por token inválido"),
            @ApiResponse(code = 404, message = "Usuário não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping(value = "/add", consumes="application/json")
    public HttpStatus add(@RequestBody FriendTO friendTO, @RequestHeader(value = "AUTHORIZATION") String token) {
        return service.add(friendTO, token);
    }
}
