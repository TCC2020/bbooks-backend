package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.bean.UserBeanUtil;
import br.edu.ifsp.spo.bulls.usersApi.domain.User;
import br.edu.ifsp.spo.bulls.usersApi.dto.LoginTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.ResetPassTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserTO;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceUnauthorizedException;
import br.edu.ifsp.spo.bulls.usersApi.repository.UserRepository;
import br.edu.ifsp.spo.bulls.usersApi.service.impl.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
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
    @Autowired
    private UserService userService;
    
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
        String link = url + user.getToken();
        emailService.sendEmailTo(user.getEmail(), "Recuperação de senha", "<h1>Acesse o link para redefinir a senha: "+link+"</h1>");
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

    public UserTO saveGoogle(UserTO userTO) throws Exception {
        Optional<User> u = repository.findByIdSocial( userTO.getIdSocial());
        if(u.isPresent()){
            return utils.toUserTO(u.get());
        }else{
            return  this.userService.saveGoogle(userTO);
        }
    }
}
