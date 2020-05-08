package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.repository.UserRepository;
import br.edu.ifsp.spo.bulls.usersApi.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public Optional<org.springframework.security.core.userdetails.User> findByToken(String token) {
        Optional<User> optionalUser = repository.findByToken(token);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            org.springframework.security.core.userdetails.User userFromSpringSecurity = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), true, true, true, true,
                    AuthorityUtils.createAuthorityList("USER"));
            return Optional.of(userFromSpringSecurity);
        }
        return  Optional.empty();
    }
}
