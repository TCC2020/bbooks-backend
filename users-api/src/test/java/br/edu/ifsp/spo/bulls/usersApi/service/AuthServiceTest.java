package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.bean.UserBeanUtil;
import br.edu.ifsp.spo.bulls.usersApi.domain.Author;
import br.edu.ifsp.spo.bulls.usersApi.domain.User;
import br.edu.ifsp.spo.bulls.usersApi.dto.AuthorTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserTO;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.usersApi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AuthServiceTest {

    @Autowired
    private UserRepository repository;
    @Autowired
    private UserBeanUtil utils;
    @Autowired
    private AuthService authservice;

    @Test
    void testSaveByGoogleWithAccount() throws Exception{

//        UserTO userTo = new UserTO("teesteS", "testegooglep@login", "1010101012291", "nome", "sobrenome");
//        userTo.setUserName("1010101012291x");
//        userTo.setIdToken("ewrdsfqwefdsfqwefdsfwefwqefweerw");
//        userTo.setToken("sdfoiwhefiodsfoiweahfoidsajfoiewajfoidsajfoiewajfoidsjfa");
//        userTo.setIdSocial("1010101012291x");
//        authservice.saveGoogle(userTo);
//        Optional user =  repository.findByIdSocial( userTo.getIdSocial());
//        UserTO user1;
//        if(user.isPresent()){
//            user1 = utils.toUserTO((User) user.get());
//            //assertEquals(userTo.getUserName(), user1.getUserName());
//            assertEquals(userTo.getEmail(), user1.getEmail());
//            assertEquals(userTo.getPassword(), user1.getPassword());
//            assertEquals(userTo.getIdSocial(), user1.getIdSocial());
//            assertEquals(userTo.getToken(), user1.getToken());
//        }
    }
}
