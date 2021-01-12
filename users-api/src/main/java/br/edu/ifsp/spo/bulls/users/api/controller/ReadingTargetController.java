package br.edu.ifsp.spo.bulls.users.api.controller;

import br.edu.ifsp.spo.bulls.users.api.dto.AddTargetTO;
import br.edu.ifsp.spo.bulls.users.api.dto.ReadingTargetTO;
import br.edu.ifsp.spo.bulls.users.api.service.ReadingTargetService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/reading-targets", produces ="application/json")
@CrossOrigin(origins = "*")
public class ReadingTargetController {
    @Autowired
    private ReadingTargetService service;

    private Logger logger = LoggerFactory.getLogger(ReadingTargetController.class);

    @ApiOperation(value = "Buscar todas a metas de leitura por profile id")
    @ApiResponses(value =
            {@ApiResponse(code = 200, message = "Retorna uma lista de meta de leituras por ano")})
    @GetMapping(value = "/profile/{profileId}")
    public List<ReadingTargetTO> getAllByProfileId(@PathVariable int profileId) throws Exception {
        logger.info("Buscando lista de metas de leitura para o profile: " + profileId);
        return service.getAllByProfileId(profileId);
    }

    @ApiOperation(value = "Buscar meta de leitura por id")
    @ApiResponses(value =
            {@ApiResponse(code = 200, message = "Retorna meta de leitura por id")})
    @GetMapping(value = "/{id}")
    public ReadingTargetTO getAById(@PathVariable UUID id) throws Exception {
        logger.info("Buscando lista de metas de leitura com id: " + id);
        return service.getById(id);
    }

    @ApiOperation(value = "Adicionar um livro a meta de leitura")
    @ApiResponses(value =
            {@ApiResponse(code = 200, message = "Retorna a meta atualizada")})
    @PutMapping("")
    public ReadingTargetTO addTarget(@RequestBody AddTargetTO dto) throws Exception {
        logger.info("Adicionando na meta o livro com id: " + dto.getUserBookId());
        return service.addTarget(dto.getProfileId(), dto.getUserBookId());
    }

    @ApiOperation(value = "Adicionar um livro a meta de leitura")
    @ApiResponses(value =
            {@ApiResponse(code = 200, message = "Retorna a meta atualizada")})
    @DeleteMapping("/delete/profile/{profileId}/user-book/{userBookId}")
    public ReadingTargetTO removeTarget(@PathVariable int profileId, @PathVariable Long userBookId) throws Exception {
        logger.info("remomento na meta o livro com id: " + userBookId);
        return service.removeTarget(profileId, userBookId);
    }

    @ApiOperation(value = "Deleta a meta de leitura")
    @ApiResponses(value =
            {@ApiResponse(code = 202, message = "Meta de leitura excluída com sucesso")})
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable UUID id) throws Exception {
        logger.info("Deletando meta para o id: " + id);
        service.delete(id);
    }

    @ApiOperation(value = "Buscar Target por UserBook")
    @ApiResponses(value =
            {@ApiResponse(code = 200, message = "Retorna um TargetTO")})
    @GetMapping(value = "/search/profile/{profileId}/user-book/{userBookId}")
    public ReadingTargetTO getByUserBookId(@PathVariable int profileId, @PathVariable Long userBookId) throws Exception {
        logger.info("procurando target com id: " + userBookId);

        return service.getByUserBookId(profileId, userBookId);
    }
}
