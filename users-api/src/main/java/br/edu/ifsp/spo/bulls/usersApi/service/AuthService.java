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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
                throw new ResourceUnauthorizedException("Wrong password.");
            }
        }
        else{
            throw new ResourceNotFoundException("User doesn't exist.");
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
                throw new ResourceUnauthorizedException("Wrong password.");
            }
        }
        else{
            throw new ResourceNotFoundException("User doesn't exist.");
        }
	}

    public UserTO resetPass(ResetPassTO dto) {
        User user = repository.findByToken(dto.getToken()).orElseThrow(() -> new ResourceConflictException("Password already changed"));
        user.setPassword(dto.getPassword());
        user.setToken(UUID.randomUUID().toString());
        return utils.toUserTO(repository.save(user));
    }

    public UserTO getByToken(String token) {
        return  utils.toUserTO(repository.findByToken(token).orElseThrow(() -> new ResourceNotFoundException("User not found.")));
    }

}
