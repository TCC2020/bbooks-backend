package br.edu.ifsp.spo.bulls.usersApi.controller;

import br.edu.ifsp.spo.bulls.usersApi.dto.LoginTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserTO;
import br.edu.ifsp.spo.bulls.usersApi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private AuthService service;

    @PostMapping("/login")
    public UserTO login(@RequestBody LoginTO loginTO){
        return service.authLogin(loginTO);
    }
    
    @PostMapping("/confirm")
    public UserTO confirm( @RequestBody LoginTO loginTO) throws Exception{
        return service.verified(loginTO);
    }
}