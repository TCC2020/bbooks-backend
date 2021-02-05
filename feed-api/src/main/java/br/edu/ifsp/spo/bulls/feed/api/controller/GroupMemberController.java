package br.edu.ifsp.spo.bulls.feed.api.controller;

import br.edu.ifsp.spo.bulls.feed.api.domain.GroupMembers;
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
    public void enterGroup(@RequestBody GroupMembers member) {
        // TODO: Authorization e identificação da pessoa para diferenciar membro ou não - Para adicionar admin

        service.putMember(member);
    }

    @ApiOperation(value = "Sair de um grupo")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Membro retirado do grupo")
    })
    @DeleteMapping
    public void exitGroup(@RequestBody GroupMembers member) {
        // TODO: Authorization e identificação da pessoa - Para excluir outras pessoas do grupo (só para o dono e admin)
        service.exitMember(member);
    }

    @ApiOperation(value = "Buscar grupos por usuário")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Grupos encontrados")
    })
    @GetMapping("/user/{id}")
    public List<GroupMembers> getGroupByUser(@PathVariable UUID id) {
        return service.getGroupByUser(id);
    }

    @ApiOperation(value = "Buscar membros de um grupo")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Membros encontrados")
    })
    @GetMapping("/{id}")
    public List<GroupMembers> getGroupMembers(@PathVariable UUID id) {
        return service.getGroupMembers(id);
    }

}
