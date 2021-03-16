package br.edu.ifsp.spo.bulls.users.api.service;

import br.edu.ifsp.spo.bulls.users.api.bean.UserBooksBeanUtil;
import br.edu.ifsp.spo.bulls.users.api.domain.ReadingTracking;
import br.edu.ifsp.spo.bulls.users.api.domain.Tracking;
import br.edu.ifsp.spo.bulls.users.api.domain.UserBooks;
import br.edu.ifsp.spo.bulls.users.api.dto.ReadingTrackingTO;
import br.edu.ifsp.spo.bulls.users.api.dto.TrackingTO;
import br.edu.ifsp.spo.bulls.users.api.dto.UserBooksTO;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.users.api.enums.Status;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.users.api.repository.TrackingRepository;
import br.edu.ifsp.spo.bulls.users.api.repository.UserBooksRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TrackingServiceTest {

    @MockBean
    private UserBooksRepository mockUserBooksRepository;

    @MockBean
    private UserBooksService mockUserBooksService;

    @MockBean
    private TrackingRepository mockTrackingRepository;

    @MockBean
    private ReadingTrackingService mockBeadingTrackingService;

    @Autowired
    private TrackingService trackingService;

    @Autowired
    private UserBooksBeanUtil userBooksBeanUtil;

    private UserBooks userBooksQueroLer = new UserBooks();
    private UserBooksTO userBooksQueroLerTO = new UserBooksTO();
    private UserBooks userBooksLido = new UserBooks();
    private UserBooksTO userBooksLidoTO = new UserBooksTO();
    private TrackingTO trackingTO = new TrackingTO();
    private TrackingTO trackingTOLido = new TrackingTO();
    private Tracking trackingQueroLer = new Tracking();
    private Tracking trackingOpen = new Tracking();
    private Tracking trackingLido = new Tracking();
    private List<TrackingTO> trackingTOList = new ArrayList<>();
    private List<Tracking> trackingList = new ArrayList<>();
    private List<Tracking> trackingListOpen = new ArrayList<>();
    private List<ReadingTracking> trackingsFilho = new ArrayList<>();
    private List<ReadingTrackingTO> readingsTrackingsTO = new ArrayList<>();

    @BeforeEach
    void setUp() {
        userBooksQueroLer.setId(1L);
        userBooksQueroLer.setStatus(Status.QUERO_LER);

        userBooksLido.setId(2L);
        userBooksLido.setStatus(Status.LIDO);

        trackingTO.setId(UUID.randomUUID());
        trackingTO.setUserBookId(userBooksQueroLer.getId());
        trackingTO.setTrackings(new ArrayList<>());
        trackingTO.setFinishedDate(LocalDateTime.now());
        trackingTO.setVelocidadeLeitura(null);

        trackingTOLido.setId(UUID.randomUUID());
        trackingTOLido.setUserBookId(userBooksLido.getId());
        trackingTOLido.setTrackings(new ArrayList<>());
        trackingTOLido.setFinishedDate(LocalDateTime.now());
        trackingTOLido.setVelocidadeLeitura(null);

        trackingQueroLer.setId(trackingTO.getId());
        trackingQueroLer.setUserBook(userBooksQueroLer);
        trackingQueroLer.setReadingTrackings(new ArrayList<>());
        trackingQueroLer.setFinishedDate(LocalDateTime.now());

        trackingLido.setId(trackingTOLido.getId());
        trackingLido.setUserBook(userBooksLido);
        trackingLido.setReadingTrackings(new ArrayList<>());
        trackingLido.setFinishedDate(LocalDateTime.now());

        trackingOpen.setId(trackingTO.getId());
        trackingOpen.setUserBook(userBooksQueroLer);
        trackingOpen.setReadingTrackings(new ArrayList<>());
        trackingOpen.setFinishedDate(null);

        trackingTOList.add(trackingTO);
        trackingTOList.add(trackingTOLido);

        trackingListOpen.add(trackingQueroLer);
        trackingListOpen.add(trackingOpen);

        trackingList.add(trackingQueroLer);
        trackingList.add(trackingLido);


        trackingsFilho.add(new ReadingTracking());
        readingsTrackingsTO.add(new ReadingTrackingTO());
    }

    @Test
    void shouldGetAllTrackingsByUserbookId() {
        Mockito.when(mockTrackingRepository.findAllByUserBookOrderByCreationDate(userBooksQueroLer)).thenReturn(trackingList);
        Mockito.when(mockUserBooksRepository.findById(userBooksQueroLer.getId())).thenReturn(Optional.ofNullable(userBooksQueroLer));
        List<TrackingTO> resultado = trackingService.getAllByBook(userBooksQueroLer.getId());

        assertNotNull(resultado);
    }

    @Test
    void shouldntGetAllTrackingsByUserbookWhenUserbooksNotFund() {
        Mockito.when(mockUserBooksRepository.findById(userBooksQueroLer.getId())).thenThrow(new ResourceNotFoundException(CodeException.UB001.getText(), CodeException.UB001));
        assertThrows(ResourceNotFoundException.class, () -> trackingService.getAllByBook(userBooksQueroLer.getId()));
    }

    @Test
    void shouldGetOneTrackingGroupById() {
        Mockito.when(mockTrackingRepository.findById(trackingTO.getId())).thenReturn(Optional.ofNullable(trackingQueroLer));
        TrackingTO resultado = trackingService.getById(trackingTO.getId());
        assertNotNull(resultado);
    }

    @Test
    void shouldntGetOneTrackingByIdWhenTrackingNotFound() {
        Mockito.when(mockTrackingRepository.findById(trackingQueroLer.getId())).thenThrow(new ResourceNotFoundException(CodeException.TA002.getText(), CodeException.TA002));
        assertThrows(ResourceNotFoundException.class, () -> trackingService.getOne(trackingTO.getId()));
    }

    @Test
    void shouldSaveTrackingUserbooksStatusQueroLer() {
        Mockito.when(mockTrackingRepository.save(trackingQueroLer)).thenReturn(trackingQueroLer);
        Mockito.when(mockUserBooksRepository.findById(trackingTO.getUserBookId())).thenReturn(Optional.ofNullable(userBooksQueroLer));
        Mockito.when(mockTrackingRepository.findAllByUserBookOrderByCreationDate(userBooksQueroLer)).thenReturn(trackingList);
        Mockito.when(mockUserBooksService.updateStatus(userBooksBeanUtil.toDTOUpdate(userBooksQueroLer))).thenReturn(userBooksQueroLerTO);

        TrackingTO resultado = trackingService.save(trackingTO);
        assertEquals(trackingTO.getComentario(), resultado.getComentario());
        assertEquals(trackingTO.getVelocidadeLeitura(), resultado.getVelocidadeLeitura());
    }

    @Test
    void shouldSaveTrackingUserbooksStatusLido() {
        Mockito.when(mockTrackingRepository.save(trackingLido)).thenReturn(trackingLido);
        Mockito.when(mockUserBooksRepository.findById(trackingTOLido.getUserBookId())).thenReturn(Optional.ofNullable(userBooksLido));
        Mockito.when(mockTrackingRepository.findAllByUserBookOrderByCreationDate(userBooksLido)).thenReturn(trackingList);
        Mockito.when(mockUserBooksService.updateStatus(userBooksBeanUtil.toDTOUpdate(userBooksLido))).thenReturn(userBooksLidoTO);

        TrackingTO resultado = trackingService.save(trackingTOLido);
        assertEquals(trackingTOLido.getComentario(), resultado.getComentario());
        assertEquals(trackingTOLido.getVelocidadeLeitura(), resultado.getVelocidadeLeitura());
    }

    @Test
    void shouldntSaveTrackingWhenOtherIsOpen() {
        Mockito.when(mockTrackingRepository.save(trackingQueroLer)).thenReturn(trackingQueroLer);
        Mockito.when(mockUserBooksRepository.findById(trackingTO.getUserBookId())).thenReturn(Optional.ofNullable(userBooksQueroLer));
        Mockito.when(mockTrackingRepository.findAllByUserBookOrderByCreationDate(userBooksQueroLer)).thenReturn(trackingListOpen);

        assertThrows(ResourceConflictException.class, () -> trackingService.save(trackingTO));
    }

    @Test
    void shouldntSaveTrackingWhenUserbooksNotFound() {
        Mockito.when(mockTrackingRepository.save(trackingQueroLer)).thenThrow(new ResourceNotFoundException(CodeException.UB001.getText(), CodeException.UB001));
        assertThrows(ResourceNotFoundException.class, () -> trackingService.getOne(trackingQueroLer.getId()));
    }

    @Test
    void shouldDeleteTracking() {
        Mockito.when(mockTrackingRepository.findById(trackingQueroLer.getId())).thenReturn(Optional.ofNullable(trackingQueroLer));
        Mockito.doNothing().when(mockTrackingRepository).deleteById(trackingTO.getId());
        Mockito.when(mockBeadingTrackingService.getByTrackingGroup(trackingTO.getId())).thenReturn(trackingsFilho);

        trackingService.deleteById(trackingTO.getId());
    }

    @Test
    void shouldntDeleteTrackingWwhenTtrackingNotFound() {
        Mockito.doThrow(new ResourceNotFoundException(CodeException.TA002.getText(), CodeException.TA002)).when(mockTrackingRepository).deleteById(trackingTO.getId());
        assertThrows(ResourceNotFoundException.class, () -> trackingService.deleteById(trackingTO.getId()));
    }

    @Test
    void shouldUpdateTrackingReturnDTO() {
        Mockito.when(mockTrackingRepository.save(trackingQueroLer)).thenReturn(trackingQueroLer);
        Mockito.when(mockTrackingRepository.findById(trackingTO.getId())).thenReturn(Optional.ofNullable(trackingQueroLer));
        TrackingTO resultado = trackingService.update(trackingTO.getId(), trackingTO);

        assertEquals(trackingTO.getTrackings(), resultado.getTrackings());
        assertEquals(trackingTO.getComentario(), resultado.getComentario());
        assertEquals(trackingTO.getUserBookId(), resultado.getUserBookId());
        assertEquals(trackingTO.getVelocidadeLeitura(), resultado.getVelocidadeLeitura());
    }

    @Test
    void shouldntUpdateTrackingWhenTrackingNotFoundDTO() {
        Mockito.when(mockTrackingRepository.findById(trackingQueroLer.getId())).thenThrow(new ResourceNotFoundException(CodeException.TA002.getText(), CodeException.TA002));
        assertThrows(ResourceNotFoundException.class, () -> trackingService.update(trackingQueroLer));
    }

    @Test
    void shouldUpdateTracking() {
        Mockito.when(mockTrackingRepository.save(trackingQueroLer)).thenReturn(trackingQueroLer);
        Mockito.when(mockTrackingRepository.findById(trackingQueroLer.getId())).thenReturn(Optional.ofNullable(trackingQueroLer));
        Tracking resultado = trackingService.update(trackingQueroLer);
        assertEquals(trackingQueroLer, resultado);
    }

    @Test
    void shouldntUpdateTrackingWhenTrackingNotFound() {
        Mockito.when(mockTrackingRepository.findById(trackingQueroLer.getId())).thenThrow(new ResourceNotFoundException(CodeException.TA002.getText(), CodeException.TA002));
        assertThrows(ResourceNotFoundException.class, () -> trackingService.update(trackingQueroLer));
    }

    @Test
    void shouldCalcularVelocidadeLeituraWithOneTracking() {
        trackingTO.setTrackings(readingsTrackingsTO);
        double speedy = trackingTO.getTrackings().get(0).getNumPag()/1;
        double result = trackingService.calcularVelocidadeLeitura(trackingTO);
        assertEquals(speedy, result);
    }

    @Test
    void shouldCalcularVelocidadeLeituraWithManyEqualsDateTracking() {
        Clock clock = Clock.fixed(Instant.parse("2021-01-05T10:15:30.00Z"), ZoneId.of("UTC"));
        LocalDateTime creationDate1 = LocalDateTime.now(clock);
        LocalDateTime creationDate2 = LocalDateTime.now(clock);

        ReadingTrackingTO tracking1 = new ReadingTrackingTO();
        tracking1.setCreationDate(creationDate2);
        tracking1.setNumPag(40);

        readingsTrackingsTO.add(tracking1);
        trackingTO.setTrackings(readingsTrackingsTO);

        trackingTO.getTrackings().get(0).setCreationDate(creationDate1);
        trackingTO.getTrackings().get(0).setNumPag(20);
        double speedy = trackingTO.getTrackings().get(1).getNumPag();
        double result = trackingService.calcularVelocidadeLeitura(trackingTO);
        assertEquals(speedy, result);
    }

    @Test
    void shouldCalcularVelocidadeLeituraWithManyDifferentDateTracking() {
        Clock clock = Clock.fixed(Instant.parse("2021-01-04T10:15:30.00Z"), ZoneId.of("UTC"));
        LocalDateTime creationDate1 = LocalDateTime.now(clock);
        clock = Clock.fixed(Instant.parse("2021-01-05T10:15:30.00Z"), ZoneId.of("UTC"));
        LocalDateTime creationDate2 = LocalDateTime.now(clock);

        ReadingTrackingTO tracking1 = new ReadingTrackingTO();
        tracking1.setCreationDate(creationDate2);
        tracking1.setNumPag(40);

        readingsTrackingsTO.add(tracking1);
        trackingTO.setTrackings(readingsTrackingsTO);

        trackingTO.getTrackings().get(0).setCreationDate(creationDate1);
        trackingTO.getTrackings().get(0).setNumPag(20);
        double speedy = trackingTO.getTrackings().get(1).getNumPag()/2;
        double result = trackingService.calcularVelocidadeLeitura(trackingTO);
        assertEquals(speedy, result);
    }
}