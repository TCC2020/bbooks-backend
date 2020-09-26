package br.edu.ifsp.spo.bulls.usersApi.controller;

import br.edu.ifsp.spo.bulls.usersApi.dto.BookCaseTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserBookUpdateStatusTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserBooksTO;
import br.edu.ifsp.spo.bulls.usersApi.service.UserBooksService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/bookcases", produces="application/json", consumes="application/json")
@CrossOrigin(origins = "*")
public class UserBooksController {
    @Autowired
    private UserBooksService service;

    @ApiOperation(value = "Adicionar um livro na estante do usuário")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
    })
    @PostMapping
    public UserBooksTO post(@RequestBody UserBooksTO dto) {
        return service.save(dto);
    }

    @ApiOperation(value = "Retorna um usuário a partir do identificador")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna o usuário"),
    })
    @GetMapping("/profile/{profileId}")
    public BookCaseTO getAllByProfile(@PathVariable int profileId) {
        return service.getByProfileId(profileId);
    }

    @ApiOperation(value = "Editar um livro na estante do usuário")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Livro editado"),
    })
    @PutMapping()
    public UserBooksTO putUserBook(@RequestBody UserBooksTO dto){
        return service.update(dto);
    }

    @ApiOperation(value = "Inserir status em um livro na estante virtual")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Status do livro alterado"),
            @ApiResponse(code = 404, message = "Livro não encontrado na estante"),
            @ApiResponse(code = 409, message = "Status inválido, livro não alterado")
    })
    @PutMapping("/status")
    public UserBooksTO putStatus(@RequestBody UserBookUpdateStatusTO dto) {
        return service.updateStatus(dto);
    }

    @ApiOperation(value = "Deletar um livro da estante do usuário")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Livro deletado da estante")
    })
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        service.deleteById(id);
    }
}
