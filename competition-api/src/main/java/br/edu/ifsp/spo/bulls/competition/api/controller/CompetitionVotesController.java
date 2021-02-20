package br.edu.ifsp.spo.bulls.competition.api.controller;

import br.edu.ifsp.spo.bulls.common.api.dto.CompetitionVotesSaveTO;
import br.edu.ifsp.spo.bulls.competition.api.service.CompetitionVoteService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/competitions/votes", produces="application/json")
@CrossOrigin(origins = "*")
public class CompetitionVotesController {

    private final Logger logger = LoggerFactory.getLogger(CompetitionVotesController.class);

    @Autowired
    private CompetitionVoteService service;

    @ApiOperation(value = "Retorna uma competicação por Id")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Competicação encontrada"),
            @ApiResponse(code = 404, message = "Competicação não encontrada")
    })
    @GetMapping("/{memberId}")
    public List<CompetitionVotesSaveTO> getVotesByMember(@PathVariable UUID memberId) {
        logger.info("Requisitando votos de um membro");
        return service.getVotesByMember(memberId);
    }

    @ApiOperation(value = "Retorna uma competicação por Id")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Competicação encontrada"),
            @ApiResponse(code = 404, message = "Competicação não encontrada")
    })
    @PostMapping
    public CompetitionVotesSaveTO vote(@RequestBody CompetitionVotesSaveTO vote) {
        logger.info("Votando em um membro");
        return service.vote(vote);
    }

    @ApiOperation(value = "Retorna uma competicação por Id")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Competicação encontrada"),
            @ApiResponse(code = 404, message = "Competicação não encontrada")
    })
    @PutMapping("/{voteId}")
    public CompetitionVotesSaveTO updateVote(@RequestHeader("AUTHORIZATION") String token, @PathVariable UUID voteId, @RequestBody CompetitionVotesSaveTO vote) {
        String tokenValue = StringUtils.removeStart(token, "Bearer").trim();
        logger.info("Votando em um membro");
        return service.updateVote(vote, voteId, tokenValue);
    }

    @ApiOperation(value = "Retorna uma competicação por Id")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Competicação encontrada"),
            @ApiResponse(code = 404, message = "Competicação não encontrada")
    })
    @DeleteMapping("/{voteId}")
    public void deleteVote(@RequestHeader("AUTHORIZATION") String token, @PathVariable UUID voteId) {
        String tokenValue = StringUtils.removeStart(token, "Bearer").trim();
        logger.info("Votando em um membro");
        service.deleteVote(voteId, tokenValue);
    }
}
