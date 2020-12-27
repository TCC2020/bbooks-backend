package br.edu.ifsp.spo.bulls.users.api.controller;

import br.edu.ifsp.spo.bulls.users.api.dto.ReadingTargetTO;
import br.edu.ifsp.spo.bulls.users.api.service.ReadingTargetService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(value = "/reading-targets", produces="application/json")
@CrossOrigin(origins = "*")
public class ReadingTargetController {
    @Autowired
    private ReadingTargetService service;

    private Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @ApiOperation(value = "Criar nova meta de leitura")
    @ApiResponses(value =
            {@ApiResponse(code = 200, message = "Retorna uma lista de meta de leituras por ano"),
            @ApiResponse(code = 409, message = "Uma meta de leitura para esse ano já foi criada")})
    @PostMapping(value = "/profile/{profileId}")
    public ReadingTargetTO save(@PathVariable Long profileId, @RequestBody ReadingTargetTO dto) throws Exception {
        logger.info("Salvando reading target para o profile: " + profileId);
        return service.save(dto);
    }

    @ApiOperation(value = "Buscar todas a metas de leitura por profile id")
    @ApiResponses(value =
            {@ApiResponse(code = 200, message = "Retorna uma lista de meta de leituras por ano")})
    @GetMapping(value = "/profile/{profileId}")
    public List<ReadingTargetTO> getAllByProfileId(@PathVariable Long profileId) throws Exception {
        logger.info("Buscando lista de metas de leitura para o profile: " + profileId);
        return service.getAllByProfileId(profileId);
    }

    @ApiOperation(value = "Buscar todas a metas de leitura por id")
    @ApiResponses(value =
            {@ApiResponse(code = 200, message = "Retorna uma lista de meta de leitura por id")})
    @GetMapping(value = "/{id}")
    public ReadingTargetTO getAllById(@PathVariable UUID id) throws Exception {
        logger.info("Buscando lista de metas de leitura com id: " + id);
        return service.getById(id);
    }

    @ApiOperation(value = "Deleta a meta de leitura")
    @ApiResponses(value =
            {@ApiResponse(code = 202, message = "Meta de leitura excluída com sucesso")})
    @DeleteMapping(value = "{id}")
    public void delete(@PathVariable UUID id) throws Exception {
        logger.info("Deletando meta para o id: " + id);
        service.delete(id);
    }
}
