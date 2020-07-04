package br.edu.ifsp.spo.bulls.usersApi.controller;

import br.edu.ifsp.spo.bulls.usersApi.dto.UserBooksTO;
import br.edu.ifsp.spo.bulls.usersApi.service.impl.UserBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookcase")
@CrossOrigin(origins = "*")
public class UserBooksController {
    @Autowired
    private UserBooksService service;

    @PostMapping
    public UserBooksTO post(@RequestBody UserBooksTO dto) {
        return service.save(dto);
    }
}
