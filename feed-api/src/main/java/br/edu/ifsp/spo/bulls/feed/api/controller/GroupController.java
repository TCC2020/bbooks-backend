package br.edu.ifsp.spo.bulls.feed.api.controller;

import br.edu.ifsp.spo.bulls.feed.api.domain.Group;
import br.edu.ifsp.spo.bulls.feed.api.service.PostService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping(value = "/group", produces="application/json")
@CrossOrigin(origins = "*")
public class GroupController {

    private final Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    private PostService service;

    @ApiOperation(value = "Cria um novo grupo de leitura")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Grupo criado")
    })
    @PostMapping
    public Group post(@RequestBody Group group) {
        logger.info("Criando um grupo: " + group.toString());
        //TODO: Chamar o service aqui
        return new Group();
    }

    @ApiOperation(value = "Editar um grupo")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Grupo editad")
    })
    @PutMapping("/{idGroup}")
    public Group put(@RequestBody Group group, @PathVariable UUID idGroup) {
        logger.info("Editando group: " + group.toString());
        //TODO: Chamar o service aqui
        return new Group();
    }

    @ApiOperation(value = "Buscar um grupo por id")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Grupo encontrado"),
            @ApiResponse(code = 404, message = "Grupo não existe")
    })
    @GetMapping("/{idGroup}")
    public Group get(@PathVariable UUID idGroup) {
        logger.info("Buscando group: " + idGroup);
        //TODO: Chamar o service aqui
        return new Group();
    }

    @ApiOperation(value = "Deletar um grupo pelo id")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Grupo deletada"),
            @ApiResponse(code = 404, message = "Grupo não existe")
    })
    @DeleteMapping("/{idGroup}")
    public void delete(@PathVariable UUID idGroup) {
        logger.info("Buscando group: " + idGroup);
        //TODO: Chamar o service aqui

    }

}
