package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.domain.Profile;
import br.edu.ifsp.spo.bulls.usersApi.domain.User;
import br.edu.ifsp.spo.bulls.usersApi.dto.BookCaseTO;
import br.edu.ifsp.spo.bulls.usersApi.repository.ProfileRepository;
import br.edu.ifsp.spo.bulls.usersApi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserBooksServiceTest {
    @Autowired
    UserRepository repository;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    UserBooksService service;

    private User user = repository.save(new User("teste", "teste@email.com", "PASS"));
    Profile profile = profileRepository.save(new Profile("nome", "sobrenome", "pais", "sao paulo", "SP", "10/10/1998",user ));

    //    @Test
//    public void user_books_service_should_save() {
//        service.save()
//    }

    @Test
    public void getByProfileId(){
        BookCaseTO result = service.getByProfileId(profile.getId());

        assertTrue(true);
    }

}
