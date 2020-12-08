package br.edu.ifsp.spo.bulls.users.api.service;

import br.edu.ifsp.spo.bulls.users.api.bean.UserBeanUtil;
import br.edu.ifsp.spo.bulls.users.api.domain.User;
import br.edu.ifsp.spo.bulls.users.api.dto.LoginTO;
import br.edu.ifsp.spo.bulls.users.api.dto.ResetPassTO;
import br.edu.ifsp.spo.bulls.users.api.dto.UserTO;
import br.edu.ifsp.spo.bulls.users.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.users.api.enums.EmailSubject;
import br.edu.ifsp.spo.bulls.users.api.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.users.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.users.api.exception.ResourceUnauthorizedException;
import br.edu.ifsp.spo.bulls.users.api.repository.UserRepository;
import br.edu.ifsp.spo.bulls.users.api.service.impl.EmailServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class AuthService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private UserBeanUtil utils;
    @Autowired
    private EmailServiceImpl emailService;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    private Logger logger = LoggerFactory.getLogger(AuthService.class);
    
    public UserTO authLogin(LoginTO loginTO){
        Optional<User> optionalUser = repository.findByEmail(loginTO.getEmail());

        if(optionalUser.isPresent()){
            if(bCryptPasswordEncoder.matches(loginTO.getPassword(), optionalUser.get().getPassword())){
                if(optionalUser.get().getToken() == null){
                    optionalUser.get().setToken(UUID.randomUUID().toString());
                    repository.save(optionalUser.get());
                }
                return utils.toUserTO(optionalUser.get());
            }
            else {
                logger.warn("Tentativa de acesso com senha errada. Usuario: "
                        + optionalUser.get().getEmail());
                throw new ResourceUnauthorizedException(CodeException.AT001.getText(), CodeException.AT001);
            }
        }
        else{
            logger.warn("Tentativa de acesso mas usuário não existe. Usuario: "
                    + loginTO.getEmail());
            throw new ResourceUnauthorizedException(CodeException.AT001.getText(), CodeException.AT001);
        }
    }

    public UserTO authLoginToken(LoginTO loginTO){
        Optional<User> optionalUser = repository.findByEmail(loginTO.getEmail());

        if(optionalUser.isPresent()){
                if(optionalUser.get().getToken().equals(loginTO.getToken())){
                    optionalUser.get().setToken(UUID.randomUUID().toString());
                    repository.save(optionalUser.get());
                }else{
                    logger.warn("Tentativa de acesso com token inexistente. Usuario: "
                            + loginTO.getEmail());
                    throw new ResourceNotFoundException(CodeException.AT002.getText(), CodeException.AT002);
                }
                return utils.toUserTO(optionalUser.get());
        }
        else{
            logger.warn("Tentativa de acesso mas usuário não existe. Usuario: "
                    + loginTO.getEmail());
            throw new ResourceUnauthorizedException(CodeException.AT001.getText(), CodeException.AT001);
        }
    }

    public void sendResetPasswordEmail(String email, String url) {
        User user = repository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(CodeException.US001.getText(), CodeException.US001));

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
            if(bCryptPasswordEncoder.matches(loginTO.getPassword(), optionalUser.get().getPassword())){
                if(optionalUser.get().getToken() == null) {
                	optionalUser.get().setToken(UUID.randomUUID().toString());
                }
                optionalUser.get().setVerified(true);
                repository.save(optionalUser.get());
                return utils.toUserTO(optionalUser.get());
            }
            else {
                logger.warn("Tentativa de acesso com senha errada. Usuario: "
                        + optionalUser.get().getEmail());
                throw new ResourceUnauthorizedException(CodeException.AT001.getText(), CodeException.AT001);
            }
        }
        else{
            logger.warn("Tentativa de acesso mas usuário não existe. Usuario: "
                    + loginTO.getEmail());
            throw new ResourceUnauthorizedException(CodeException.AT001.getText(), CodeException.AT001);
        }
	}

    public UserTO resetPass(ResetPassTO dto) {
        User user = repository.findByToken(dto.getToken()).orElseThrow(() -> new ResourceConflictException(CodeException.AT003.getText(), CodeException.AT003));
        user.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        user.setToken(UUID.randomUUID().toString());
        logger.info("Reset password sucess. User: " + user.getId());
        return utils.toUserTO(repository.save(user));
    }

    public UserTO getByToken(String token) {
        return  utils.toUserTO(repository.findByToken(token).orElseThrow(() -> new ResourceNotFoundException(CodeException.US001.getText(), CodeException.US001)));
    }

}
