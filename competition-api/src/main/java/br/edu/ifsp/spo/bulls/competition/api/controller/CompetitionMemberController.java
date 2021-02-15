package br.edu.ifsp.spo.bulls.competition.api.controller;

import br.edu.ifsp.spo.bulls.competition.api.domain.CompetitionMemberTO;
import br.edu.ifsp.spo.bulls.competition.api.service.CompetitionMemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void exitMember(@PathVariable UUID id) {
        logger.info("Requisitando competições");
        service.exitMember(id);
    }

    @ApiOperation(value = "Retorna uma competicação por Id")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Competicação encontrada"),
            @ApiResponse(code = 404, message = "Competicação não encontrada")
    })
    @PostMapping
    public CompetitionMemberTO saveMember(@RequestBody CompetitionMemberTO memberTO) {
        logger.info("Requisitando competições");
        return service.saveMember(memberTO);
    }

    @ApiOperation(value = "Retorna uma competicação por Id")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Competicação encontrada"),
            @ApiResponse(code = 404, message = "Competicação não encontrada")
    })
    @PutMapping("/{id}")
    public CompetitionMemberTO updateMember(@RequestBody CompetitionMemberTO memberTO, @PathVariable UUID id) {
        logger.info("Requisitando competições");
        return service.updateMember(memberTO, id);
    }
}
