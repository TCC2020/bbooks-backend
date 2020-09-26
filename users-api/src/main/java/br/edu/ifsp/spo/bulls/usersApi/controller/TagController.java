package br.edu.ifsp.spo.bulls.usersApi.controller;

import br.edu.ifsp.spo.bulls.usersApi.domain.Tag;
import br.edu.ifsp.spo.bulls.usersApi.service.TagService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashSet;
import java.util.List;

@RestController
@RequestMapping(value = "/tags", produces="application/json", consumes="application/json")
@CrossOrigin(origins = "*")
public class TagController {
    @Autowired
    private TagService service;

    @ApiOperation(value = "Cadastrar uma tag")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a Tag cadastrada"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping
    public Tag save(@RequestBody Tag tag) {
        return service.save(tag);
    }

    @ApiOperation(value = "Retorna uma Tag pelo profile vinculado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a Tag"),
            @ApiResponse(code = 404, message = "Profile não existe na base de dados")
    })
    @GetMapping("/profile/{profileId}")
    public List<Tag> getByProfile(@PathVariable int profileId) {
        return service.getByProfile(profileId);
    }

    @ApiOperation(value = "Retorna as Tags de determinado livro")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna uma lista de tags")
    })
    @GetMapping("/book/{idUserBook}")
    public List<Tag> getByBook(@PathVariable Long idUserBook) {
        return service.getByIdBook(idUserBook);
    }

    @ApiOperation(value = "Retorna uma tag pelo identificador dela")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna uma tag"),
            @ApiResponse(code = 404, message = "Tag não encontrada")
    })
    @GetMapping("/{idTag}")
    public Tag getById(@PathVariable Long idTag) {
        return service.getbyId(idTag);
    }

    @ApiOperation(value = "Alterar o cadastro de uma tag")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a tag alterada"),
            @ApiResponse(code = 404, message = "Tag não encontrada"),
            @ApiResponse(code = 400, message = "Tag enviada e ID não correspondem")
    })
    @PutMapping("/{idTag}")
    public Tag update(@PathVariable Long idTag, @RequestBody Tag tag){
        return service.update(idTag, tag);
    }

    @ApiOperation(value = "Adicionar tag a um livro")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tag inserida"),
            @ApiResponse(code = 404, message = "Tag ou livro não encontrados")
    })
    @PutMapping("/{tagId}/book/{userBookId}")
    public Tag putTagOnBook(@PathVariable Long tagId,@PathVariable Long userBookId) {
        return service.tagBook(tagId, userBookId);
    }

    @ApiOperation(value = "Retirar tag de um livro")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tag retirada"),
            @ApiResponse(code = 404, message = "Tag ou livro não encontrados")
    })
    @DeleteMapping("/{tagId}/book/{userBookId}")
    public HttpStatus untag(@PathVariable Long tagId, @PathVariable Long userBookId) {
        return service.untagBook(tagId, userBookId);
    }

    @ApiOperation(value = "Deletar uma tag pelo identificador dela")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tag deletada"),
            @ApiResponse(code = 404, message = "Tag não encontrada")
    })
    @DeleteMapping("/{tagId}")
    public void delete(@PathVariable Long tagId) {
        service.delete(tagId);
    }
}
