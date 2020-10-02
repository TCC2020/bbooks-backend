package br.edu.ifsp.spo.bulls.usersApi.controller;

import br.edu.ifsp.spo.bulls.usersApi.domain.ReadingTracking;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserTO;
import br.edu.ifsp.spo.bulls.usersApi.service.ReadingTrackingService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.UUID;

@RestController
@RequestMapping(value = "/tracking", produces="application/json")
@CrossOrigin(origins = "*")
public class ReadingTrackingController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    ReadingTrackingService service;

    @ApiOperation(value = "Retorna todos acompanhamentos de um livro")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna uma lista de acompanhamentos")
    })
    @GetMapping("/{userBook}")
    public HashSet<ReadingTracking> getAllByBook(@PathVariable Long userBook){
        logger.info("Acessando dados de todos os acompanhamento do userBook: " + userBook);
        HashSet<ReadingTracking> acompanhamntos = service.getAllByBook(userBook);
        logger.info("Acompanhamentos retornados: " + acompanhamntos.toString());
        return acompanhamntos ;
    }

    @ApiOperation(value = "Retorna todos acompanhamentos de um livro")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna um acompanhamento")
    })
    @GetMapping("/{readingTracking}")
    public ReadingTracking getOne(@PathVariable UUID readingTracking){
        logger.info("Acessando dados de um acompanhamento");
        ReadingTracking acompanhamento = service.get(readingTracking);
        logger.info("Acompanhamento retornado: " + acompanhamento.toString());
        return acompanhamento ;
    }

    @ApiOperation(value = "Cadastra um acompanhamento de leitura")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Acompanhamento cadastrado")
    })
    @PostMapping()
    public ReadingTracking post(@RequestBody ReadingTracking readingTracking){
        logger.info("Cadastrando um novo acompanhamento: " + readingTracking);
        ReadingTracking acompanhamento = service.save(readingTracking);
        logger.info("Acompanhamento cadastrado: " + acompanhamento.toString());
        return acompanhamento ;
    }

    @ApiOperation(value = "Altera um acompanhemnto")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Acompanhamento atualizado")
    })
    @PutMapping("/trackingID")
    public ReadingTracking put(@RequestBody ReadingTracking readingTracking, @PathVariable UUID trackingID){
        logger.info("Alterando um acompanhamento: " + readingTracking);
        ReadingTracking acompanhamento = service.update(readingTracking, trackingID);
        logger.info("Acompanhamento alterado: " + acompanhamento.toString());
        return acompanhamento ;
    }

    @ApiOperation(value = "Deleta um acompanhemnto")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Acompanhamento deletado")
    })
    @DeleteMapping("/trackingID")
    public void delete(@PathVariable UUID trackingID){
        logger.info("Deletando o acompanhamento: " + trackingID);
        service.delete(trackingID);
        logger.info("Acompanhamento deletado: " + trackingID);
    }
}
