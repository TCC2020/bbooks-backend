package br.edu.ifsp.spo.bulls.competition.api.controller;

import br.edu.ifsp.spo.bulls.competition.api.domain.CompetitionMemberTO;
import br.edu.ifsp.spo.bulls.competition.api.dto.CompetitionTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/competitions", produces="application/json")
@CrossOrigin(origins = "*")
public class CompetitionController {

    private final Logger logger = LoggerFactory.getLogger(CompetitionController.class);

    @ApiOperation(value = "Retorna uma competicação por Id")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Competicação encontrada"),
            @ApiResponse(code = 404, message = "Competicação não encontrada")
    })
    @GetMapping
    public Page<CompetitionTO> search(@RequestParam String term, @RequestParam int page, @RequestParam int size) {
        logger.info("Requisitando competições");
        return null;
    }

    @ApiOperation(value = "Retorna uma competicação por Id")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Competicação encontrada"),
            @ApiResponse(code = 404, message = "Competicação não encontrada")
    })
    @PostMapping
    public CompetitionTO save(@RequestBody CompetitionTO competition) {
        logger.info("Requisitando competições");
        return null;
    }

    @ApiOperation(value = "Retorna uma competicação por Id")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Competicação encontrada"),
            @ApiResponse(code = 404, message = "Competicação não encontrada")
    })
    @PutMapping("/{id}")
    public CompetitionTO update(@RequestBody CompetitionTO competition, @PathVariable UUID id) {
        logger.info("Requisitando competições");
        return null;
    }

    @ApiOperation(value = "Retorna uma competicação por Id")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Competicação encontrada"),
            @ApiResponse(code = 404, message = "Competicação não encontrada")
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        logger.info("Requisitando competições");
    }

    @ApiOperation(value = "Retorna uma competicação por Id")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Competicação encontrada"),
            @ApiResponse(code = 404, message = "Competicação não encontrada")
    })
    @GetMapping("/competitors/{id}")
    public Page<CompetitionMemberTO> getMembers( @PathVariable UUID id) {
        logger.info("Requisitando competições");
        return null;
    }

    @ApiOperation(value = "Retorna uma competicação por Id")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Competicação encontrada"),
            @ApiResponse(code = 404, message = "Competicação não encontrada")
    })
    @GetMapping("/profile/{id}")
    public Page<CompetitionTO> getCompetitionByProfile( @PathVariable UUID id) {
        logger.info("Requisitando competições");
        return null;
    }

    @ApiOperation(value = "Retorna uma competicação por Id")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Competicação encontrada"),
            @ApiResponse(code = 404, message = "Competicação não encontrada")
    })
    @GetMapping("/{id}")
    public CompetitionTO getById(@PathVariable UUID id) {
        logger.info("Requisitando competições");
        return null;
    }

}