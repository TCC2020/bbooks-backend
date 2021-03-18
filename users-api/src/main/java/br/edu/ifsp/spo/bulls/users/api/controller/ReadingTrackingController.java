package br.edu.ifsp.spo.bulls.users.api.controller;

import br.edu.ifsp.spo.bulls.users.api.dto.ReadingTrackingTO;
import br.edu.ifsp.spo.bulls.users.api.service.ReadingTrackingService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/tracking", produces="application/json")
@CrossOrigin(origins = "*")
public class ReadingTrackingController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private ReadingTrackingService service;

    @ApiOperation(value = "Retorna todos acompanhamentos de um livro")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna um acompanhamento"),
            @ApiResponse(code = 404, message = "Acompanhamento não encontrado")
    })
    @GetMapping("/{readingTracking}")
    public ReadingTrackingTO getOne(@PathVariable UUID readingTracking){
        logger.info("Acessando dados de um acompanhamento");
        ReadingTrackingTO acompanhamento = service.get(readingTracking);
        logger.info("Acompanhamento retornado: " + acompanhamento.toString());
        return acompanhamento ;
    }

    @ApiOperation(value = "Cadastra um acompanhamento de leitura")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Acompanhamento cadastrado"),
            @ApiResponse(code = 404, message = "O livro do usuário não existe em sua estante"),
            @ApiResponse(code = 409, message = "O livro indicado está com status lido")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReadingTrackingTO post(@RequestBody ReadingTrackingTO readingTracking){
        logger.info("Cadastrando um novo acompanhamento: " + readingTracking);
        ReadingTrackingTO acompanhamento = service.save(readingTracking);
        logger.info("Acompanhamento cadastrado: " + acompanhamento.toString());
        return acompanhamento ;
    }

    @ApiOperation(value = "Altera um acompanhemnto")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Acompanhamento atualizado"),
            @ApiResponse(code = 404, message = "Acompanhamento/ UserBook não encontrado")
    })
    @PutMapping("/{trackingID}")
    public ReadingTrackingTO put(@RequestBody ReadingTrackingTO readingTracking, @PathVariable UUID trackingID){
        logger.info("Alterando um acompanhamento: " + readingTracking);
        ReadingTrackingTO acompanhamento = service.update(readingTracking, trackingID);
        logger.info("Acompanhamento alterado: " + acompanhamento.toString());
        return acompanhamento ;
    }

    @ApiOperation(value = "Deleta um acompanhemnto")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Acompanhamento deletado"),
            @ApiResponse(code = 404, message = "Acompanhamento não encontrado")
    })
    @DeleteMapping("/{trackingID}")
    public void delete(@PathVariable UUID trackingID){
        logger.info("Deletando o acompanhamento: " + trackingID);
        service.delete(trackingID);
        logger.info("Acompanhamento deletado: " + trackingID);
    }
}
