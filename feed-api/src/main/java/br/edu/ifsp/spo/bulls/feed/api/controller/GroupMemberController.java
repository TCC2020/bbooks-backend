package br.edu.ifsp.spo.bulls.feed.api.controller;

import br.edu.ifsp.spo.bulls.feed.api.domain.Group;
import br.edu.ifsp.spo.bulls.feed.api.domain.GroupMembers;
import br.edu.ifsp.spo.bulls.feed.api.service.GroupMemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/group/member", produces="application/json")
@CrossOrigin(origins = "*")
public class GroupMemberController {
    
    private final Logger logger = LoggerFactory.getLogger(GroupMemberController.class);

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

}
