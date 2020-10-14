package br.edu.ifsp.spo.bulls.usersApi.controller;

import br.edu.ifsp.spo.bulls.usersApi.domain.Friendship;
import br.edu.ifsp.spo.bulls.usersApi.dto.AcceptTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.FriendRequestTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.FriendTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.FriendshipTO;
import br.edu.ifsp.spo.bulls.usersApi.service.FriendsService;
import br.edu.ifsp.spo.bulls.usersApi.service.FriendshipService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friends")
@CrossOrigin(value = "*")
public class FriendsController {
    @Autowired
    private FriendshipService service;

    @GetMapping
    public FriendshipTO getFriends(@RequestHeader(value = "AUTHORIZATION") String token) {
        token = StringUtils.removeStart(token, "Bearer").trim();
        return service.getFriends(token);
    }

    @DeleteMapping
    public HttpStatus deleteFriend(@RequestBody AcceptTO dto, @RequestHeader(value = "AUTHORIZATION") String token) {
        token = StringUtils.removeStart(token, "Bearer").trim();
        return service.deleteFriend(token, dto);
    }

    @GetMapping("/{username}")
    public FriendshipTO getByUsername(@PathVariable String username, @RequestHeader("AUTHORIZATION") String token) {
        return service.getFriendsByUsername(username, token);
    }

    @ApiOperation(value = "Adicionar usuário")
    @ApiResponses(value = {
            @ApiResponse(code = 102, message = "Solicitação já feita"),
            @ApiResponse(code = 202, message = "Solicitação feita"),
            @ApiResponse(code = 401, message = "Solicitação recusada por token inválido"),
            @ApiResponse(code = 404, message = "Usuário não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping(value = "/requests", consumes="application/json")
    public HttpStatus add(@RequestBody FriendTO friendTO, @RequestHeader(value = "AUTHORIZATION") String token) {
        token = StringUtils.removeStart(token, "Bearer").trim();
        return service.add(friendTO, token);
    }

    @GetMapping(value = "/requests")
    public List<FriendRequestTO> add(@RequestHeader(value = "AUTHORIZATION") String token) {
        token = StringUtils.removeStart(token, "Bearer").trim();
        return service.getRequests(token);
    }

    @PutMapping(value = "/requests")
    public HttpStatus responseRequest(@RequestBody AcceptTO dto, @RequestHeader(value = "AUTHORIZATION") String token) {
        token = StringUtils.removeStart(token, "Bearer").trim();
        return service.accept(token, dto.getId());
    }
    @DeleteMapping(value = "/requests")
    public HttpStatus reject(@RequestBody AcceptTO dto, @RequestHeader(value = "AUTHORIZATION") String token) {
        token = StringUtils.removeStart(token, "Bearer").trim();
        return service.reject(token, dto.getId());
    }

}
