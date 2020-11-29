package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.bean.UserBeanUtil;
import br.edu.ifsp.spo.bulls.usersApi.repository.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AuthServiceTest {

    @Autowired
    private UserRepository repository;
    @Autowired
    private UserBeanUtil utils;
    @Autowired
    private AuthService authservice;


}
