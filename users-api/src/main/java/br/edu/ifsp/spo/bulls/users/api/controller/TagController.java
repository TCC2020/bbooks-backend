package br.edu.ifsp.spo.bulls.users.api.controller;

import br.edu.ifsp.spo.bulls.users.api.domain.Tag;
import br.edu.ifsp.spo.bulls.users.api.service.TagService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/tags", produces="application/json")
@CrossOrigin(origins = "*")
public class TagController {

    private Logger logger = LoggerFactory.getLogger(TagController.class);

    @Autowired
    private TagService service;

    @ApiOperation(value = "Cadastrar uma tag")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Retorna a Tag cadastrada"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tag save(@RequestBody Tag tag) {
        logger.info("Cadastrando uma nova tag " + tag);
        return service.save(tag);
    }

    @ApiOperation(value = "Retorna uma Tag pelo profile vinculado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a Tag"),
            @ApiResponse(code = 404, message = "Profile não existe na base de dados")
    })
    @GetMapping("/profile/{profileId}")
    public List<Tag> getByProfile(@PathVariable int profileId) {
        logger.info("Requisitando tags do usuario " + profileId);
        List<Tag> tags = service.getByProfile(profileId);
        logger.info("Tags encontradas: " + tags);
        return tags;
    }

    @ApiOperation(value = "Retorna as Tags de determinado livro")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna uma lista de tags")
    })
    @GetMapping("/book/{idUserBook}")
    public List<Tag> getByBook(@PathVariable Long idUserBook) {
        logger.info("Requisitando tags do livro " + idUserBook);
        List<Tag> tags = service.getByIdBook(idUserBook);
        logger.info("Tags encontradas: " + tags);
        return tags;
    }

    @ApiOperation(value = "Retorna uma tag pelo identificador dela")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna uma tag"),
            @ApiResponse(code = 404, message = "Tag não encontrada")
    })
    @GetMapping("/{idTag}")
    public Tag getById(@PathVariable Long idTag) {
        logger.info("Requisitando dados de uma tag " + idTag);
        Tag tag = service.getbyId(idTag);
        logger.info("Tag retornada " + tag);
        return tag;
    }

    @ApiOperation(value = "Alterar o cadastro de uma tag")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a tag alterada"),
            @ApiResponse(code = 404, message = "Tag não encontrada"),
            @ApiResponse(code = 400, message = "Tag enviada e ID não correspondem")
    })
    @PutMapping("/{idTag}")
    public Tag update(@PathVariable Long idTag, @RequestBody Tag tag){
        logger.info("Requisicao para atualizar o cadastro de uma tag" + tag);
        Tag tag1 = service.update(idTag, tag);
        logger.info("Tag atualizada" + tag1);
        return tag ;
    }

    @ApiOperation(value = "Adicionar tag a um livro")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tag inserida"),
            @ApiResponse(code = 404, message = "Tag ou livro não encontrados")
    })
    @PutMapping("/{tagId}/book/{userBookId}")
    public Tag putTagOnBook(@PathVariable Long tagId,@PathVariable Long userBookId) {
        logger.info("Requisicao para inserir tag em um livro. Tag: " + tagId + " Livro: "+ userBookId);
        return service.tagBook(tagId, userBookId);
    }

    @ApiOperation(value = "Retirar tag de um livro")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tag retirada"),
            @ApiResponse(code = 404, message = "Tag ou livro não encontrados")
    })
    @DeleteMapping("/{tagId}/book/{userBookId}")
    public HttpStatus untag(@PathVariable Long tagId, @PathVariable Long userBookId) {
        logger.info("Requisicao para retirar tag de um livro. Tag: " + tagId + " Livro: "+ userBookId);
        return service.untagBook(tagId, userBookId);
    }

    @ApiOperation(value = "Deletar uma tag pelo identificador dela")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tag deletada"),
            @ApiResponse(code = 404, message = "Tag não encontrada")
    })
    @DeleteMapping("/{tagId}")
    public void delete(@PathVariable Long tagId) {
        logger.info("Requisião solicitada para deletar uma tag " + tagId);
        service.delete(tagId);
    }
}
