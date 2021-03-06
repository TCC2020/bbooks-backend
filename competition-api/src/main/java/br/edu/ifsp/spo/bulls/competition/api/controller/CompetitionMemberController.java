package br.edu.ifsp.spo.bulls.competition.api.controller;

import br.edu.ifsp.spo.bulls.common.api.dto.CompetitionMemberTO;
import br.edu.ifsp.spo.bulls.common.api.enums.Role;
import br.edu.ifsp.spo.bulls.common.api.enums.Status;
import br.edu.ifsp.spo.bulls.competition.api.domain.Competition;
import br.edu.ifsp.spo.bulls.competition.api.domain.CompetitionMember;
import br.edu.ifsp.spo.bulls.common.api.dto.CompetitionMemberSaveTO;
import br.edu.ifsp.spo.bulls.competition.api.service.CompetitionMemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/competitions/member", produces="application/json")
@CrossOrigin(origins = "*")
public class CompetitionMemberController {

    private final Logger logger = LoggerFactory.getLogger(CompetitionMemberController.class);

    @Autowired
    private CompetitionMemberService service;

    @ApiOperation(value = "Retorna um membro por Id")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Membro encontrada"),
            @ApiResponse(code = 404, message = "Membro não encontrada")
    })
    @GetMapping("/{id}")
    public CompetitionMemberTO getMember(@PathVariable UUID id) {
        logger.info("Requisitando membro de uma competição");
        return service.getById(id);
    }

    @ApiOperation(value = "Retira um membro da competicação por Id")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Membro excluído da competição"),
            @ApiResponse(code = 404, message = "Membro não encontrada"),
            @ApiResponse(code = 401, message = "Usuário não tem permissão para realizar essa ação")
    })
    @DeleteMapping("/{id}")
    public void exitMember(@RequestHeader("AUTHORIZATION") String token, @PathVariable UUID id) {
        String tokenValue = StringUtils.removeStart(token, "Bearer").trim();
        logger.info("Excluindo membro da competição");
        service.exitMember(tokenValue,id);
    }

    @ApiOperation(value = "Cadastro um membro na competição")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Membro cadastrado"),
            @ApiResponse(code = 404, message = "Competicação não encontrada"),
            @ApiResponse(code = 409, message = "Usuário já é um membro da competição"),
            @ApiResponse(code = 401, message = "Usuário não tem permissão para realizar essa ação")
    })
    @PostMapping
    public CompetitionMemberTO saveMember(@RequestHeader("AUTHORIZATION") String token, @RequestBody CompetitionMemberSaveTO memberTO) {
        String tokenValue = StringUtils.removeStart(token, "Bearer").trim();
        logger.info("Salvando membro da competição");
        return service.saveMember(tokenValue,memberTO);
    }

    @ApiOperation(value = "Altera um membro da competicação por Id")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Membro alterado"),
            @ApiResponse(code = 404, message = "Membro não encontrada"),
            @ApiResponse(code = 401, message = "Usuário não tem permissão para realizar essa ação")
    })
    @PutMapping("/{id}")
    public CompetitionMemberTO updateMember(@RequestHeader("AUTHORIZATION") String token, @RequestBody CompetitionMemberSaveTO memberTO, @PathVariable UUID id) {
        String tokenValue = StringUtils.removeStart(token, "Bearer").trim();
        logger.info("Atualizando membro da competição");
        return service.updateMember(tokenValue, memberTO, id);
    }

    @ApiOperation(value = "Retorna membros por Id da competição")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Membros encontrados")
    })
    @GetMapping("/competitors/{id}")
    public Page<CompetitionMember> getMembers(@PathVariable UUID id, @RequestParam int page, @RequestParam int size) {
        logger.info("Requisitando competições");
        return service.getMembers(id, page, size);
    }

    @ApiOperation(value = "Retorna competicações por usuário")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Competicações encontradas")
    })
    @GetMapping("/profile/{id}")
    public Page<Competition> getCompetitionByProfile(@PathVariable int id, @RequestParam int page, @RequestParam int size) {
        logger.info("Requisitando competições");
        return service.getCompetitionsByProfile(id, page, size);
    }

    @ApiOperation(value = "Retorna uma competicação por Id")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Competicação encontrada"),
            @ApiResponse(code = 404, message = "Competicação não encontrada")
    })
    @GetMapping("/role/{competitionId}")
    public List<CompetitionMemberTO> getMembersByRoleAndStatus(@PathVariable UUID competitionId, @RequestParam Role role, @RequestParam(required=false) Status status) {
        logger.info("Requisitando competições");
        return service.getMembersByRoleAndStatus(competitionId, role, status);
    }

    //TODO: rota para aceitar um membro na competição
}
