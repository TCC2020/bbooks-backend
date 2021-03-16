package br.edu.ifsp.spo.bulls.competition.api.controller;

import br.edu.ifsp.spo.bulls.common.api.dto.CompetitionVoteReturnTO;
import br.edu.ifsp.spo.bulls.common.api.dto.CompetitionVotesSaveTO;
import br.edu.ifsp.spo.bulls.common.api.enums.Role;
import br.edu.ifsp.spo.bulls.competition.api.service.CompetitionVoteService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/competitions/votes", produces="application/json")
@CrossOrigin(origins = "*")
public class CompetitionVotesController {

    private final Logger logger = LoggerFactory.getLogger(CompetitionVotesController.class);

    @Autowired
    private CompetitionVoteService service;

    @ApiOperation(value = "Requisitando votos de um membro")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Votações encontradas"),
            @ApiResponse(code = 404, message = "Membro não encontrado")
    })
    @GetMapping("/{memberId}")
    public List<CompetitionVoteReturnTO> getVotesByMember(@PathVariable UUID memberId) {
        logger.info("Requisitando votos de um membro");
        return service.getVotesByMember(memberId);
    }

    @ApiOperation(value = "Requisitando voto de uma pessoa em um membro")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Voto encontrada"),
            @ApiResponse(code = 404, message = "Membro não encontrado")
    })
    @GetMapping("/{memberId}/profile/{profileId}")
    public CompetitionVoteReturnTO getVoteByMemberAndProfile(@PathVariable UUID memberId, @PathVariable int profileId) {
        logger.info("Buscando voto do usuário " + profileId + " para o membro " + memberId);
        return service.getVoteByMemberAndProfile(memberId, profileId);
    }

    @ApiOperation(value = "Votar em um membro da competição")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Votação realizada"),
            @ApiResponse(code = 404, message = "Membro não encontrado"),
            @ApiResponse(code = 409, message = "Usuário já votou nesse competidor")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompetitionVoteReturnTO vote(@RequestHeader("AUTHORIZATION") String token, @RequestBody CompetitionVotesSaveTO vote) {
        String tokenValue = StringUtils.removeStart(token, "Bearer").trim();
        logger.info("Votando em um membro");
        return service.vote(vote, tokenValue);
    }

    @ApiOperation(value = "Alterar voto ")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Votação alterada"),
            @ApiResponse(code = 404, message = "Votação não encontrado"),
            @ApiResponse(code = 401, message = "Usuário não tem permissão para realizar essa ação")
    })
    @PutMapping("/{voteId}")
    public CompetitionVoteReturnTO updateVote(@RequestHeader("AUTHORIZATION") String token, @PathVariable UUID voteId, @RequestBody CompetitionVotesSaveTO vote) {
        String tokenValue = StringUtils.removeStart(token, "Bearer").trim();
        logger.info("Votando em um membro");
        return service.updateVote(vote, voteId, tokenValue);
    }

    @ApiOperation(value = "Excluir um voto")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Voto excluído"),
            @ApiResponse(code = 404, message = "Voto não encontradk"),
            @ApiResponse(code = 401, message = "Usuário não tem permissão para realizar essa ação")
    })
    @DeleteMapping("/{voteId}")
    public void deleteVote(@RequestHeader("AUTHORIZATION") String token, @PathVariable UUID voteId) {
        String tokenValue = StringUtils.removeStart(token, "Bearer").trim();
        logger.info("Excluindo um voto");
        service.deleteVote(voteId, tokenValue);
    }
}
