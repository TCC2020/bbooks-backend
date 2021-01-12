package br.edu.ifsp.spo.bulls.users.api.service;

import br.edu.ifsp.spo.bulls.users.api.bean.ReadingTargetBeanUtil;
import br.edu.ifsp.spo.bulls.users.api.bean.UserBooksBeanUtil;
import br.edu.ifsp.spo.bulls.users.api.domain.Profile;
import br.edu.ifsp.spo.bulls.users.api.domain.ReadingTarget;
import br.edu.ifsp.spo.bulls.users.api.domain.UserBooks;
import br.edu.ifsp.spo.bulls.users.api.dto.ProfileTO;
import br.edu.ifsp.spo.bulls.users.api.dto.ReadingTargetTO;
import br.edu.ifsp.spo.bulls.users.api.dto.UserBooksTO;
import br.edu.ifsp.spo.bulls.users.api.enums.Status;
import br.edu.ifsp.spo.bulls.users.api.repository.ReadingTargetRepository;
import br.edu.ifsp.spo.bulls.users.api.repository.UserBooksRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ReadingTargetServiceTest {
    @MockBean
    private ReadingTargetRepository repository;

    @MockBean
    private UserBooksRepository userBooksRepository;

    @MockBean
    private UserBooksBeanUtil userBooksBeanUtil;

    @Autowired
    private ReadingTargetService service;

    @Autowired
    private ReadingTargetBeanUtil util;

    private ReadingTarget readingTarget = new ReadingTarget();

    private ReadingTargetTO readingTargetTO = new ReadingTargetTO();

    private UserBooks userBooks = new UserBooks();

    private UserBooks userBooks2 = new UserBooks();

    private UserBooksTO userBooksTO = new UserBooksTO();

    private UserBooksTO userBooksTO2 = new UserBooksTO();

    private Profile profile = new Profile();

    private ProfileTO profileTO = new ProfileTO();

    @BeforeEach
    public void setup() {
        profile.setId(1);
        profileTO.setId(1);

        userBooks.setId(1L);
        userBooks.setProfile(profile);
        userBooks.setIdBookGoogle("32");
        userBooks.setStatus(Status.LENDO);

        userBooks2.setId(2L);
        userBooks2.setProfile(profile);
        userBooks2.setIdBookGoogle("32");
        userBooks2.setStatus(Status.LENDO);

        userBooksTO.setId(1L);
        userBooksTO.setProfileId(profileTO.getId());
        userBooksTO.setIdBookGoogle("33");
        userBooksTO.setStatus(Status.LENDO);

        userBooksTO2.setId(2L);
        userBooksTO2.setProfileId(profileTO.getId());
        userBooksTO2.setIdBookGoogle("33");
        userBooksTO2.setStatus(Status.LENDO);

        readingTarget = new ReadingTarget();
        readingTarget.setId(UUID.randomUUID());
        readingTarget.setProfileId(1);
        readingTarget.setTargets(new ArrayList<UserBooks>());
        readingTarget.getTargets().add(userBooks);
        readingTarget.setYear(LocalDateTime.now().getYear());

        readingTargetTO = new ReadingTargetTO();
        readingTargetTO.setId(UUID.randomUUID());
        readingTargetTO.setProfileId(1);
        readingTargetTO.setTargets(new ArrayList<UserBooksTO>());
        readingTargetTO.getTargets().add(userBooksTO);
        readingTargetTO.setYear(LocalDateTime.now().getYear());
    }

    @Test
    public void reading_target_should_add_when_already_has_reading_target() {
        readingTarget.getTargets().add(userBooks2);
        Mockito.when(repository.save(readingTarget)).thenReturn(readingTarget);
        Mockito.when(repository.findByProfileIdAndYear(profile.getId(), readingTarget.getYear()))
                .thenReturn(Optional.ofNullable(readingTarget));
        Mockito.when(userBooksRepository.findById(userBooks2.getId())).thenReturn(Optional.ofNullable(userBooks2));

        ReadingTargetTO saved = service.addTarget(profile.getId(), userBooks2.getId());

        Mockito.when(userBooksBeanUtil.toDomainList(saved.getTargets())).thenReturn(readingTarget.getTargets());

        assertEquals(readingTarget, util.toDomain(saved));
    }
}
