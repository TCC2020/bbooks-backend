package br.edu.ifsp.spo.bulls.feed.api.controller;

import br.edu.ifsp.spo.bulls.common.api.dto.BookMonthTO;
import br.edu.ifsp.spo.bulls.feed.api.domain.BookMonth;
import br.edu.ifsp.spo.bulls.feed.api.domain.GroupRead;
import br.edu.ifsp.spo.bulls.feed.api.domain.Survey;
import br.edu.ifsp.spo.bulls.feed.api.dto.GroupTO;
import br.edu.ifsp.spo.bulls.feed.api.service.GroupService;
import br.edu.ifsp.spo.bulls.feed.api.service.SurveyService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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

    @Autowired
    private SurveyService surveyService;

    @ApiOperation(value = "Cria um novo grupo de leitura")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Grupo criado")
    })
    @PostMapping
    public GroupTO post(@RequestBody GroupTO group) {
        logger.info("Criando um grupo: " + group.toString());
        GroupTO result = service.save(group);
        logger.info("Grupo criado " + result.toString());
        return result;
    }

    @ApiOperation(value = "Cria um livro do mês no grupo de leitura")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Livro do mês criado")
    })
    @PostMapping("/{groupId}/book")
    public BookMonthTO postBookMonth(@PathVariable UUID groupId, @RequestBody BookMonthTO bookMonth) {
        logger.info("Criando um livro do mês para o grupo : " + groupId);
        BookMonthTO result = surveyService.saveBookMonth(bookMonth);
        logger.info("Groupo criado " + result.toString());
        return result;
    }

    @ApiOperation(value = "Busca livros do mês por grupo")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Livros encontrados")
    })
    @GetMapping("/book/{groupId}")
    public List<BookMonthTO> getBookMonth(@PathVariable UUID groupId) {
        logger.info("Criando um livro do mês para o grupo : " + groupId);
        List<BookMonthTO> result = surveyService.getBookByGroup(groupId);
        logger.info("Groupo criado " + result.toString());
        return result ;
    }

    @ApiOperation(value = "Busca livros do mês por grupo")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Livros encontrados")
    })
    @DeleteMapping("/{groupId}/book/{bookId}")
    public void deleteBookMoth(@PathVariable UUID bookId) {
        logger.info("Deletando um livro do mês com o id : " + bookId);
        surveyService.deleteBookMonth(bookId);
    }

    @ApiOperation(value = "Busca livros do mês por grupo")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Livros encontrados")
    })
    @GetMapping("/{groupId}/book/{bookId}")
    public BookMonthTO getBookMonthById(@PathVariable UUID bookId) {
        logger.info("Buscando um livro do mês com o id : " + bookId);
        return surveyService.getBookMonthById(bookId);
    }

    @ApiOperation(value = "Editar um grupo")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Grupo editad")
    })
    @PutMapping("/{idGroup}")
    public GroupTO put(@RequestHeader("AUTHORIZATION") String token, @RequestBody GroupTO group, @PathVariable UUID idGroup) {
        logger.info("Editando group: " + group.toString());
        GroupTO result = service.update(token, group, idGroup);
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
    public void delete(@RequestHeader("AUTHORIZATION") String token , @PathVariable UUID idGroup) {
        logger.info("Deletando group: " + idGroup);
        service.delete(token, idGroup);
        logger.info("Groupo deletado " + idGroup);
    }

    @ApiOperation(value = "Buscar grupos pelo nome")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Grupos encontrados")
    })
    @GetMapping
    public Page<GroupRead> get(@RequestParam String name, @RequestParam int page, @RequestParam int size) {
        logger.info("Buscando groups pelo nome: " + name);
        Page<GroupRead> result = service.search(name, page, size);
        logger.info("Groupos encontrados " + result.toString());
        return result;
    }
}
