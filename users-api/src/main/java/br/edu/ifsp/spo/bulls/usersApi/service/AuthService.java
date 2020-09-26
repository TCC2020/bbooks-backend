package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.bean.UserBeanUtil;
import br.edu.ifsp.spo.bulls.usersApi.domain.User;
import br.edu.ifsp.spo.bulls.usersApi.dto.LoginTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.ResetPassTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserTO;
import br.edu.ifsp.spo.bulls.usersApi.enums.EmailSubject;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceUnauthorizedException;
import br.edu.ifsp.spo.bulls.usersApi.repository.UserRepository;
import br.edu.ifsp.spo.bulls.usersApi.service.impl.EmailServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private UserBeanUtil utils;
    @Autowired
    private EmailServiceImpl emailService;

    private Logger logger = LoggerFactory.getLogger(AuthService.class);
    
    public UserTO authLogin(LoginTO loginTO){
        Optional<User> optionalUser = repository.findByEmail(loginTO.getEmail());

        if(optionalUser.isPresent()){
            if(optionalUser.get().getPassword().equals(loginTO.getPassword())){
                if(optionalUser.get().getToken() == null){
                    optionalUser.get().setToken(UUID.randomUUID().toString());
                    repository.save(optionalUser.get());
                }
                return utils.toUserTO(optionalUser.get());
            }
            else {
                logger.warn("Tentativa de acesso com senha errada. Usuario: "
                        + optionalUser.get().getId() + "  Hora: " + LocalDateTime.now());
                //log de senha errada
                throw new ResourceUnauthorizedException("Aconteceu um erro. Senha errada e/ou usuário não existe.");
            }
        }
        else{
            logger.warn("Tentativa de acesso mas usuário não existe. Usuario: "
                    + loginTO.getEmail() + "  Hora: " + LocalDateTime.now());
            throw new ResourceNotFoundException("Aconteceu um erro. Senha errada e/ou usuário não existe.");
        }
    }

    public void sendResetPasswordEmail(String email, String url) {
        User user = repository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found."));

        emailService.
                getInstance()
                .withUrls(url + user.getToken())
                .withTo(user.getEmail())
                .withContent(" Recuperar senha " + user.getUserName())
                .withSubject(EmailSubject.RECUPERAR_SENHA.name())
                .send();
     }

    public UserTO verified(LoginTO loginTO) throws Exception {
    	Optional<User> optionalUser = repository.findByEmail(loginTO.getEmail());

        if(optionalUser.isPresent()){
            if(optionalUser.get().getPassword().equals(loginTO.getPassword())){
                if(optionalUser.get().getToken() == null) {
                	optionalUser.get().setToken(UUID.randomUUID().toString());
                }
                  
                optionalUser.get().setVerified(true);
                repository.save(optionalUser.get());
                return utils.toUserTO(optionalUser.get());
            }
            else {
                logger.warn("Tentativa de acesso com senha errada. Usuario: "
                        + optionalUser.get().getId() + "  Hora: " + LocalDateTime.now());
                throw new ResourceUnauthorizedException("Aconteceu um erro. Senha errada e/ou usuário não existe.");
            }
        }
        else{
            logger.warn("Tentativa de acesso mas usuário não existe. Usuario: "
                    + loginTO.getEmail() + "  Hora: " + LocalDateTime.now());
            throw new ResourceNotFoundException("Aconteceu um erro. Senha errada e/ou usuário não existe.");
        }
	}

    public UserTO resetPass(ResetPassTO dto) {
        User user = repository.findByToken(dto.getToken()).orElseThrow(() -> new ResourceConflictException("Password already changed"));
        user.setPassword(dto.getPassword());
        user.setToken(UUID.randomUUID().toString());
        logger.info("Reset password sucess. User: " + user.getId());
        return utils.toUserTO(repository.save(user));
    }

    public UserTO getByToken(String token) {
        return  utils.toUserTO(repository.findByToken(token).orElseThrow(() -> new ResourceNotFoundException("User not found.")));
    }

}
