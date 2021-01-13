package br.edu.ifsp.spo.bulls.users.api.service;

import br.edu.ifsp.spo.bulls.users.api.bean.ReadingTargetBeanUtil;
import br.edu.ifsp.spo.bulls.users.api.bean.UserBooksBeanUtil;
import br.edu.ifsp.spo.bulls.users.api.domain.Profile;
import br.edu.ifsp.spo.bulls.users.api.domain.ReadingTarget;
import br.edu.ifsp.spo.bulls.users.api.domain.UserBooks;
import br.edu.ifsp.spo.bulls.users.api.dto.ProfileTO;
import br.edu.ifsp.spo.bulls.users.api.dto.ReadingTargetTO;
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
import static org.mockito.Mockito.verify;

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

    private UserBooks userBooks = new UserBooks();

    private UserBooks userBooks2 = new UserBooks();

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

        readingTarget = new ReadingTarget();
        readingTarget.setId(UUID.randomUUID());
        readingTarget.setProfileId(1);
        readingTarget.setTargets(new ArrayList<UserBooks>());
        readingTarget.getTargets().add(userBooks);
        readingTarget.setYear(LocalDateTime.now().getYear());
    }

    @Test
    public void readingTargetShouldAddWhenAlreadyHasReadingTarget() {
        readingTarget.getTargets().add(userBooks2);
        Mockito.when(repository.save(readingTarget)).thenReturn(readingTarget);
        Mockito.when(repository.findByProfileIdAndYear(profile.getId(), readingTarget.getYear()))
                .thenReturn(Optional.ofNullable(readingTarget));
        Mockito.when(userBooksRepository.findById(userBooks2.getId())).thenReturn(Optional.ofNullable(userBooks2));

        ReadingTargetTO saved = service.addTarget(profile.getId(), userBooks2.getId());

        Mockito.when(userBooksBeanUtil.toDomainList(saved.getTargets())).thenReturn(readingTarget.getTargets());

        assertEquals(readingTarget, util.toDomain(saved));
    }

    @Test
    public void readingTargetShouldAddWhenHasNotReadingTarget() {
        readingTarget.getTargets().add(userBooks2);
        Mockito.when(repository.save(readingTarget)).thenReturn(readingTarget);
        Mockito.when(repository.findByProfileIdAndYear(profile.getId(), readingTarget.getYear()))
                .thenReturn(Optional.ofNullable(readingTarget));
        Mockito.when(userBooksRepository.findById(userBooks2.getId())).thenReturn(Optional.ofNullable(userBooks2));

        ReadingTargetTO saved = service.addTarget(profile.getId(), userBooks2.getId());

        Mockito.when(userBooksBeanUtil.toDomainList(saved.getTargets())).thenReturn(readingTarget.getTargets());

        assertEquals(readingTarget, util.toDomain(saved));
    }

    @Test
    public void shouldGetById() {
        Mockito.when(repository.findById(readingTarget.getId())).thenReturn(Optional.ofNullable(readingTarget));
        assertEquals(util.toDto(readingTarget), service.getById(readingTarget.getId()));
    }

    @Test
    public void shouldGetAllByProfileId() {
        ArrayList<ReadingTarget> targets = new ArrayList<>();
        targets.add(readingTarget);
        Mockito.when(repository.findByProfileId(profile.getId())).thenReturn(targets);
        assertEquals(service.getAllByProfileId(profile.getId()), util.toDtoList(targets));
    }

    @Test
    public void shouldDelete() {
        Mockito.doNothing().when(repository).deleteById(readingTarget.getId());
        service.delete(readingTarget.getId());
        verify(repository).deleteById(readingTarget.getId());
    }

    @Test
    public void shouldRemoveTarget() {
        ReadingTarget readingTarget2 = readingTarget;
        readingTarget.getTargets().add(userBooks2);
        Mockito.when(repository.findByProfileIdAndYear(profile.getId(), readingTarget.getYear()))
                .thenReturn(Optional.ofNullable(readingTarget));
        Mockito.when(userBooksRepository.findById(userBooks2.getId())).thenReturn(Optional.ofNullable(userBooks2));
        Mockito.when(repository.save(readingTarget)).thenReturn(readingTarget2);

        assertEquals(util.toDto(readingTarget), service.removeTarget(profile.getId(), userBooks2.getId()));
    }

    @Test
    public void shouldGetByUserBookId() {
        Mockito.when(repository.findByProfileIdAndYear(profile.getId(), readingTarget.getYear()))
                .thenReturn(Optional.ofNullable(readingTarget));
        Mockito.when(userBooksRepository.findById(userBooks.getId())).thenReturn(Optional.ofNullable(userBooks));

        ReadingTargetTO dto = service.getByUserBookId(profile.getId(), userBooks.getId());
        assertEquals(dto, util.toDto(readingTarget));
    }

    @Test
    public void shouldNotGetByUserBookId() {
        Mockito.when(repository.findByProfileIdAndYear(profile.getId(), readingTarget.getYear()))
                .thenReturn(Optional.ofNullable(readingTarget));
        Mockito.when(userBooksRepository.findById(userBooks2.getId())).thenReturn(Optional.ofNullable(userBooks2));


        assertEquals(new ReadingTargetTO(), service.getByUserBookId(profile.getId(), userBooks2.getId()));
    }
}
