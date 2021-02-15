package br.edu.ifsp.spo.bulls.feed.api.controller;

import br.edu.ifsp.spo.bulls.feed.api.domain.GroupRead;
import br.edu.ifsp.spo.bulls.feed.api.dto.GroupMemberFull;
import br.edu.ifsp.spo.bulls.feed.api.dto.GroupMemberTO;
import br.edu.ifsp.spo.bulls.feed.api.service.GroupMemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.GetMapping;
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
            @ApiResponse(code = 200, message = "Membro adicionado")
    })
    @PostMapping
    public void enterGroup(@RequestHeader("AUTHORIZATION") String token, @RequestBody GroupMemberTO member) {
        service.putMember(token, member);
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
