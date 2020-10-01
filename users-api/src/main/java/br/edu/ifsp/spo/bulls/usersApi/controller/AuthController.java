package br.edu.ifsp.spo.bulls.usersApi.controller;

import br.edu.ifsp.spo.bulls.usersApi.domain.User;
import br.edu.ifsp.spo.bulls.usersApi.dto.LoginTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.RequestPassResetTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.ResetPassTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserTO;
import br.edu.ifsp.spo.bulls.usersApi.service.AuthService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth", produces="application/json")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private AuthService service;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @ApiOperation(value = "Fazer login na plataforma")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Login concluído, retorna o usuário"),
            @ApiResponse(code = 401, message = "Não autorizado, senha incorreta"),
            @ApiResponse(code = 404, message = "Usuário não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção")
    })
    @PostMapping("/login")
    public UserTO login(@RequestBody LoginTO loginTO){
        logger.info("Tentando realizar login: " + loginTO.getEmail());
        UserTO userTO = service.authLogin(loginTO);
        logger.info("Login realizado com sucesso! " + loginTO.getEmail());
        return userTO;
    }

    @ApiOperation(value = "Confirmar cadastro no sistema")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Login concluído e usuário confirmado, retorna o usuário"),
            @ApiResponse(code = 401, message = "Não autorizado, senha incorreta"),
            @ApiResponse(code = 404, message = "Usuário não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção")
    })
    @PostMapping("/confirm")
    public UserTO confirm( @RequestBody LoginTO loginTO) throws Exception{
        logger.info("Verificando usuário: " + loginTO.getEmail());
        UserTO userTO = service.verified(loginTO);
        logger.info("Usuario verificado com sucesso! " + loginTO.getEmail());
        return userTO;
    }

    @ApiOperation(value = "Enviar email para trocar senha")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Email enviado"),
            @ApiResponse(code = 404, message = "Usuario não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção")
    })
    @PostMapping("/reset-pass")
    public HttpStatus sendResetPassEmail(@RequestBody RequestPassResetTO dto) {
        logger.info("Requisitando link para trocar de senha" + dto.getEmail());
        service.sendResetPasswordEmail(dto.getEmail(), dto.getUrl());
        logger.info("Link gerado e email enviado para " + dto.getEmail());
        return HttpStatus.OK;
    }

    @ApiOperation(value = "Trocar senha")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Senha alterada, retorna o usuário"),
            @ApiResponse(code = 409, message = "Senha já foi alterada"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção")
    })
    @PutMapping("/reset-pass")
    public UserTO resetPass(@RequestBody ResetPassTO dto) {
        logger.info("Requisitando troca de senha");
        UserTO userTO = service.resetPass(dto);
        logger.info("Senha trocada para o usuario " + userTO.getId());
        return userTO;
    }

    @ApiOperation(value = "Retorna o usuário pelo token, usado para trocar a senha")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Token encontrado, retorna o usuario"),
            @ApiResponse(code = 404, message = "Usuario/Token não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma excecao")
    })
    @GetMapping("/reset-pass/{token}")
    public UserTO getByToken(@PathVariable String token) {
        logger.info("Requisitando usuario pelo token");
        UserTO userTO = service.getByToken(token);
        logger.info("Usuario encontrado: " + userTO);
        return userTO;
    }
}