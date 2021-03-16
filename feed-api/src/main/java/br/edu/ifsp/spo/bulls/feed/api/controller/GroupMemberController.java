package br.edu.ifsp.spo.bulls.feed.api.controller;

import br.edu.ifsp.spo.bulls.common.api.dto.GroupInviteTO;
import br.edu.ifsp.spo.bulls.feed.api.domain.GroupRead;
import br.edu.ifsp.spo.bulls.feed.api.dto.GroupMemberFull;
import br.edu.ifsp.spo.bulls.feed.api.dto.GroupMemberTO;
import br.edu.ifsp.spo.bulls.feed.api.service.GroupMemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/group/member", produces="application/json")
@CrossOrigin(origins = "*")
public class GroupMemberController {

    @Autowired
    private GroupMemberService service;

    @ApiOperation(value = "Entrar em um grupo")
    @ApiResponses( value = {
            @ApiResponse(code = 201, message = "Membro adicionado")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void enterGroup(@RequestHeader("AUTHORIZATION") String token, @RequestBody GroupMemberTO member) {
        service.putMember(token, member);
    }

    @PutMapping("/invites")
    public GroupInviteTO invite(@RequestHeader("AUTHORIZATION") String token, @RequestBody GroupInviteTO dto) {
        return service.invite(token, dto);
    }

    @GetMapping("/invites/user/{userId}")
    public List<GroupInviteTO> getInvites(@RequestHeader("AUTHORIZATION") String token, @PathVariable("userId") UUID userId) {
        return service.getInvites(token, userId);
    }

    @PutMapping("/invites/{id}/accept")
    public GroupMemberTO accept(@RequestHeader("AUTHORIZATION") String token, @PathVariable("id") UUID id) {
        return service.accept(token, id);
    }

    @DeleteMapping("/invites/{id}/refuse")
    public HttpStatus refuse(@RequestHeader("AUTHORIZATION") String token, @PathVariable("id") UUID id) {
        service.refuse(token, id);
        return HttpStatus.ACCEPTED;
    }

    @ApiOperation(value = "Sair de um grupo")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Membro retirado do grupo")
    })
    @DeleteMapping
    public void exitGroup(@RequestHeader("AUTHORIZATION") String token, @RequestBody GroupMemberTO member) {
        service.exitMember(token, member);
    }

    @ApiOperation(value = "Buscar grupos por usuário")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Grupos encontrados")
    })
    @GetMapping("/user/{id}")
    public List<GroupRead> getGroupByUser(@PathVariable UUID id) {
        return service.getGroupByUser(id);
    }

    @ApiOperation(value = "Buscar membros de um grupo")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Membros encontrados")
    })
    @GetMapping("/{id}")
    public List<GroupMemberFull> getGroupMembers(@PathVariable UUID id) {
        return service.getGroupMembers(id);
    }

}
