package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.domain.User;
import br.edu.ifsp.spo.bulls.usersApi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserBooksServiceTest {
    @Autowired
    UserRepository repository;

    @Autowired
    UserBooksService service;

    private User user = repository.save(new User("teste", "teste@email.com", "PASS"));

    @Test
    public void user_books_service_should_save() {
        service.save()
    }

}
