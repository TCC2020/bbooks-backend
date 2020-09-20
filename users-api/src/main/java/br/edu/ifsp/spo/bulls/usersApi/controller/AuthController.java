package br.edu.ifsp.spo.bulls.usersApi.controller;

import br.edu.ifsp.spo.bulls.usersApi.dto.LoginTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.RequestPassResetTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.ResetPassTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserTO;
import br.edu.ifsp.spo.bulls.usersApi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth", produces="application/json", consumes="application/json")
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

    @PostMapping("/reset-pass")
    public HttpStatus sendResetPassEmail(@RequestBody RequestPassResetTO dto) {
        service.sendResetPasswordEmail(dto.getEmail(), dto.getUrl());
        return HttpStatus.OK;
    }

    @PutMapping("/reset-pass")
    public UserTO resetPass(@RequestBody ResetPassTO dto) {
        return service.resetPass(dto);
    }

    @GetMapping("/reset-pass/{token}")
    public UserTO getByToken(@PathVariable String token) {
        return service.getByToken(token);
    }
}