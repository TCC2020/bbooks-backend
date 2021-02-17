package br.edu.ifsp.spo.bulls.competition.api.controller;

import br.edu.ifsp.spo.bulls.common.api.dto.CompetitionVotesTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/competitions/votes", produces="application/json")
@CrossOrigin(origins = "*")
public class CompetitionVotesController {

    private final Logger logger = LoggerFactory.getLogger(CompetitionVotesController.class);

    @ApiOperation(value = "Retorna uma competicação por Id")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Competicação encontrada"),
            @ApiResponse(code = 404, message = "Competicação não encontrada")
    })
    @GetMapping("/{memberId}")
    public List<CompetitionVotesTO> getVotesByMember(@PathVariable UUID memberId) {
        logger.info("Requisitando votos de um membro");
        // service.getVotesByMember(memberId);
        return null;
    }

    @ApiOperation(value = "Retorna uma competicação por Id")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Competicação encontrada"),
            @ApiResponse(code = 404, message = "Competicação não encontrada")
    })
    @PostMapping
    public CompetitionVotesTO vote(@RequestBody CompetitionVotesTO vote) {
        logger.info("Votando em um membro");
        // service.vote(vote);
        return null;
    }

    @ApiOperation(value = "Retorna uma competicação por Id")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Competicação encontrada"),
            @ApiResponse(code = 404, message = "Competicação não encontrada")
    })
    @PutMapping("/{voteId}")
    public CompetitionVotesTO updateVote(@RequestHeader("AUTHORIZATION") String token, @PathVariable UUID voteId, @RequestBody CompetitionVotesTO vote) {
        String tokenValue = StringUtils.removeStart(token, "Bearer").trim();
        logger.info("Votando em um membro");
        // service.updateVote(vote, voteId, tokenValue);
        return null;
    }

    @ApiOperation(value = "Retorna uma competicação por Id")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Competicação encontrada"),
            @ApiResponse(code = 404, message = "Competicação não encontrada")
    })
    @DeleteMapping("/{voteId}")
    public CompetitionVotesTO updateVote(@RequestHeader("AUTHORIZATION") String token, @PathVariable UUID voteId) {
        String tokenValue = StringUtils.removeStart(token, "Bearer").trim();
        logger.info("Votando em um membro");
        // service.deleteVote(voteId, tokenValue);
        return null;
    }
}
