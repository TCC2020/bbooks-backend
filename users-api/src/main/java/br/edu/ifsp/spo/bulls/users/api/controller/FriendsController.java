package br.edu.ifsp.spo.bulls.users.api.controller;

import br.edu.ifsp.spo.bulls.common.api.domain.Friendship;
import br.edu.ifsp.spo.bulls.users.api.dto.AcceptTO;
import br.edu.ifsp.spo.bulls.users.api.dto.FriendRequestTO;
import br.edu.ifsp.spo.bulls.users.api.dto.FriendTO;
import br.edu.ifsp.spo.bulls.users.api.service.FriendshipService;
import br.edu.ifsp.spo.bulls.users.api.dto.FriendshipTO;
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
        String tokenValue = StringUtils.removeStart(token, "Bearer").trim();
        return service.getFriends(tokenValue);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteFriend(@PathVariable int id, @RequestHeader(value = "AUTHORIZATION") String token) {
        String tokenValue = StringUtils.removeStart(token, "Bearer").trim();
        return service.deleteFriend(tokenValue, id);
    }

    @GetMapping("/{username}")
    public FriendshipTO getByUsername(@PathVariable String username, @RequestHeader("AUTHORIZATION") String token) {
        String tokenValue = StringUtils.removeStart(token, "Bearer").trim();
        return service.getFriendsByUsername(username, tokenValue);
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
        String tokenValue = StringUtils.removeStart(token, "Bearer").trim();
        return service.add(friendTO, tokenValue);
    }

    @GetMapping(value = "/requests")
    public List<FriendRequestTO> add(@RequestHeader(value = "AUTHORIZATION") String token) {
        String tokenValue = StringUtils.removeStart(token, "Bearer").trim();
        return service.getRequests(tokenValue);
    }

    @GetMapping(value = "/requests/{username}")
    public Friendship getRequestsByUsername(@RequestHeader(value = "AUTHORIZATION") String token, @PathVariable String username) {
        String tokenValue = StringUtils.removeStart(token, "Bearer").trim();
        return service.getRequestByRequest(tokenValue, username);
    }

    @PutMapping(value = "/requests")
    public HttpStatus responseRequest(@RequestBody AcceptTO dto, @RequestHeader(value = "AUTHORIZATION") String token) {
        String tokenValue = StringUtils.removeStart(token, "Bearer").trim();
        return service.accept(tokenValue, dto.getId());
    }

    @DeleteMapping(value = "/requests")
    public HttpStatus reject(@RequestBody AcceptTO dto, @RequestHeader(value = "AUTHORIZATION") String token) {
        String tokenValue = StringUtils.removeStart(token, "Bearer").trim();
        return service.reject(tokenValue, dto.getId());
    }

}
