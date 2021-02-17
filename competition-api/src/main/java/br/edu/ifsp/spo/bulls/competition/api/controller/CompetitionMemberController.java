package br.edu.ifsp.spo.bulls.competition.api.controller;

import br.edu.ifsp.spo.bulls.competition.api.domain.Competition;
import br.edu.ifsp.spo.bulls.competition.api.domain.CompetitionMember;
import br.edu.ifsp.spo.bulls.common.api.dto.CompetitionMemberTO;
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
import java.util.UUID;

@RestController
@RequestMapping(value = "/competitions/member", produces="application/json")
@CrossOrigin(origins = "*")
public class CompetitionMemberController {

    private final Logger logger = LoggerFactory.getLogger(CompetitionMemberController.class);

    @Autowired
    private CompetitionMemberService service;

    @ApiOperation(value = "Retorna uma competicação por Id")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Competicação encontrada"),
            @ApiResponse(code = 404, message = "Competicação não encontrada")
    })
    @GetMapping("/{id}")
    public CompetitionMemberTO getMember(@PathVariable UUID id) {
        logger.info("Requisitando competições");
        return service.getById(id);
    }

    @ApiOperation(value = "Retorna uma competicação por Id")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Competicação encontrada"),
            @ApiResponse(code = 404, message = "Competicação não encontrada")
    })
    @DeleteMapping("/{id}")
    public void exitMember(@RequestHeader("AUTHORIZATION") String token, @PathVariable UUID id) {
        String tokenValue = StringUtils.removeStart(token, "Bearer").trim();
        logger.info("Requisitando competições");
        service.exitMember(tokenValue,id);
    }

    @ApiOperation(value = "Cadastro um membro na competição")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Competicação encontrada"),
            @ApiResponse(code = 404, message = "Competicação não encontrada")
    })
    @PostMapping
    public CompetitionMemberTO saveMember(@RequestHeader("AUTHORIZATION") String token, @RequestBody CompetitionMemberTO memberTO) {
        String tokenValue = StringUtils.removeStart(token, "Bearer").trim();
        logger.info("Requisitando competições");
        return service.saveMember(tokenValue,memberTO);
    }

    @ApiOperation(value = "Retorna uma competicação por Id")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Competicação encontrada"),
            @ApiResponse(code = 404, message = "Competicação não encontrada")
    })
    @PutMapping("/{id}")
    public CompetitionMemberTO updateMember(@RequestHeader("AUTHORIZATION") String token, @RequestBody CompetitionMemberTO memberTO, @PathVariable UUID id) {
        String tokenValue = StringUtils.removeStart(token, "Bearer").trim();
        logger.info("Requisitando competições");
        return service.updateMember(tokenValue, memberTO, id);
    }

    @ApiOperation(value = "Retorna uma competicação por Id")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Competicação encontrada"),
            @ApiResponse(code = 404, message = "Competicação não encontrada")
    })
    @GetMapping("/competitors/{id}")
    public Page<CompetitionMember> getMembers(@PathVariable UUID id, @RequestParam int page, @RequestParam int size) {
        logger.info("Requisitando competições");
        return service.getMembers(id, page, size);
    }

    @ApiOperation(value = "Retorna uma competicação por Id")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Competicação encontrada"),
            @ApiResponse(code = 404, message = "Competicação não encontrada")
    })
    @GetMapping("/profile/{id}")
    public Page<Competition> getCompetitionByProfile(@PathVariable int id, @RequestParam int page, @RequestParam int size) {
        logger.info("Requisitando competições");
        return service.getCompetitionsByProfile(id, page, size);
    }


    //TODO: rota para aceitar um membro na competição
}
