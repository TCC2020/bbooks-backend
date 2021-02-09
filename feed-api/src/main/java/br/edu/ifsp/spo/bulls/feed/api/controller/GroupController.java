package br.edu.ifsp.spo.bulls.feed.api.controller;

import br.edu.ifsp.spo.bulls.feed.api.domain.Group;
import br.edu.ifsp.spo.bulls.feed.api.dto.GroupTO;
import br.edu.ifsp.spo.bulls.feed.api.service.GroupService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import java.util.UUID;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(value = "/group", produces="application/json")
@CrossOrigin(origins = "*")
public class GroupController {

    private final Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    private GroupService service;

    @ApiOperation(value = "Cria um novo grupo de leitura")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Grupo criado")
    })
    @PostMapping
    public GroupTO post(@RequestBody GroupTO group) {
        logger.info("Criando um grupo: " + group.toString());
        GroupTO result = service.save(group);
        logger.info("Groupo criado " + result.toString());
        return result ;
    }

    @ApiOperation(value = "Editar um grupo")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Grupo editad")
    })
    @PutMapping("/{idGroup}")
    public GroupTO put(@RequestBody GroupTO group, @PathVariable UUID idGroup) {
        logger.info("Editando group: " + group.toString());
        GroupTO result = service.update(group, idGroup);
        logger.info("Groupo editado " + result.toString());
        return result;
    }

    @ApiOperation(value = "Buscar um grupo por id")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Grupo encontrado"),
            @ApiResponse(code = 404, message = "Grupo não existe")
    })
    @GetMapping("/{idGroup}")
    public GroupTO getById(@PathVariable UUID idGroup) {
        logger.info("Buscando group: " + idGroup);
        GroupTO result = service.getById( idGroup);
        logger.info("Groupo encontrado " + result.toString());
        return result;
    }

    @ApiOperation(value = "Deletar um grupo pelo id")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Grupo deletada"),
            @ApiResponse(code = 404, message = "Grupo não existe")
    })
    @DeleteMapping("/{idGroup}")
    public void delete(@PathVariable UUID idGroup) {
        logger.info("Deletando group: " + idGroup);
        service.delete( idGroup);
        logger.info("Groupo deletado " + idGroup);
    }

    @ApiOperation(value = "Buscar grupos pelo nome")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Grupos encontrados")
    })
    @GetMapping
    public Page<Group> get(@RequestParam String name, @RequestParam int page, @RequestParam int size) {
        logger.info("Buscando groups pelo nome: " + name);
        Page<Group> result = service.search(name, page, size);
        logger.info("Groupos encontrados " + result.toString());
        return result;
    }
}
