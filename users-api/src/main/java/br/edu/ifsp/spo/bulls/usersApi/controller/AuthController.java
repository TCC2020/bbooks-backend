package br.edu.ifsp.spo.bulls.usersApi.controller;

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
@RequestMapping(value = "/auth", produces="application/json", consumes="application/json")
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
    public void login(@RequestBody LoginTO loginTO){
        try{
            service.authLogin(loginTO);
        }catch (Exception ex){
            logger.error("Error while login." +
                    "Message: " + ex.getMessage() +
                    "Cause: " + ex.getCause() +
                    "Trace: " + ex.getStackTrace());
        }
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
        return service.verified(loginTO);
    }

    @ApiOperation(value = "Enviar email para trocar senha")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Email enviado"),
            @ApiResponse(code = 404, message = "Usuario não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção")
    })
    @PostMapping("/reset-pass")
    public HttpStatus sendResetPassEmail(@RequestBody RequestPassResetTO dto) {
        service.sendResetPasswordEmail(dto.getEmail(), dto.getUrl());
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
        return service.resetPass(dto);
    }

    @ApiOperation(value = "Trocar senha utilizando o token")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Senha alterada, retorna o usuário"),
            @ApiResponse(code = 404, message = "Usuario não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção")
    })
    @GetMapping("/reset-pass/{token}")
    public UserTO getByToken(@PathVariable String token) {
        return service.getByToken(token);
    }
}