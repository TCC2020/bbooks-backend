package br.edu.ifsp.spo.bulls.users.api.controller;

import br.edu.ifsp.spo.bulls.users.api.dto.TrackingTO;
import br.edu.ifsp.spo.bulls.users.api.service.TrackingService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/tracking-group", produces="application/json")
@CrossOrigin(origins = "*")
public class TrackingController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    TrackingService service;

    @ApiOperation(value = "Retorna um grupo acompanhamentos de um livro")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna um grupo de acompanhamentos"),
            @ApiResponse(code = 404, message = "O livro do usuário não existe em sua estante")
    })
    @GetMapping("/book/{userBook}")
    public List<TrackingTO> getAllByBook(@PathVariable Long userBook){
        logger.info("Acessando dados de todos os acompanhamento do userBook: " + userBook);
        List<TrackingTO> acompanhamntos = service.getAllByBook(userBook);
        logger.info("Grupo de acompanhamento retornado: " + acompanhamntos.toString());
        return acompanhamntos ;
    }

    @ApiOperation(value = "Retorna um grupo acompanhamentos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna um grupo de acompanhamentos"),
            @ApiResponse(code = 404, message = "Este grupo de acompanhamentos não existe")
    })
    @GetMapping("/{trackingGroupId}")
    public TrackingTO getOne(@PathVariable UUID trackingGroupId){
        logger.info("Acessando um grupo de acompanhamento: " + trackingGroupId);
        TrackingTO grupo = service.findById(trackingGroupId);
        logger.info("Grupo de acompanhamento retornado: " + grupo.toString());
        return grupo ;
    }

    @ApiOperation(value = "Cadastra um grupo de acompanhamento de leitura")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Grupo de acompanhamento cadastrado"),
            @ApiResponse(code = 404, message = "O livro do usuário não existe em sua estante"),
            @ApiResponse(code = 409, message = "O livro indicado está com status lido")
    })
    @PostMapping()
    public TrackingTO post(@RequestBody TrackingTO trackingTo){
        logger.info("Cadastrando um novo acompanhamento: " + trackingTo);
        TrackingTO grupoAcompanhamento = service.save(trackingTo);
        logger.info("Acompanhamento cadastrado: " + grupoAcompanhamento.toString());
        return grupoAcompanhamento ;
    }

    @ApiOperation(value = "Deletar um grupo de acompanhamentos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Grupo de acompanhamento deletado")
    })
    @DeleteMapping("/{trackingId}")
    public void delete(@PathVariable UUID trackingId){
        System.out.println(trackingId);
        service.deleteById(trackingId);
    }

    @ApiOperation(value = "Alterar um grupo de acompanhamentos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Grupo de acompanhamento alterado"),
            @ApiResponse(code = 404, message = "Grupo de acompanhamento não existe"),
            @ApiResponse(code = 409, message = "O livro indicado está com status lido")
    })
    @PutMapping("/{trackingId}")
    public void update(@PathVariable UUID trackingId, @RequestBody TrackingTO tracking){
        service.update(trackingId, tracking);
    }
}
