package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.bean.TrackingBeanUtil;
import br.edu.ifsp.spo.bulls.usersApi.bean.UserBooksBeanUtil;
import br.edu.ifsp.spo.bulls.usersApi.domain.ReadingTracking;
import br.edu.ifsp.spo.bulls.usersApi.domain.Tracking;
import br.edu.ifsp.spo.bulls.usersApi.domain.UserBooks;
import br.edu.ifsp.spo.bulls.usersApi.dto.ReadingTrackingTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.TrackingTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserBooksTO;
import br.edu.ifsp.spo.bulls.usersApi.enums.CodeException;
import br.edu.ifsp.spo.bulls.usersApi.enums.Status;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.usersApi.repository.TrackingRepository;
import br.edu.ifsp.spo.bulls.usersApi.repository.UserBooksRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TrackingServiceTest {

    @MockBean
    UserBooksRepository mockUserBooksRepository;

    @MockBean
    UserBooksService mockUserBooksService;

    @MockBean
    TrackingRepository mockTrackingRepository;

    @MockBean
    ReadingTrackingService mockBeadingTrackingService;


    @Autowired
    TrackingBeanUtil beanUtil;

    @Autowired
    TrackingService trackingService;

    @Autowired
    UserBooksBeanUtil userBooksBeanUtil;

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

    @BeforeEach
    void setUp() {
        userBooksQueroLer.setId(1L);
        userBooksQueroLer.setStatus(Status.QUERO_LER);

        userBooksLido.setId(2L);
        userBooksLido.setStatus(Status.LIDO);

        trackingTO.setId(UUID.randomUUID());
        trackingTO.setUserBookId(userBooksQueroLer.getId());
        trackingTO.setTrackings(new ArrayList<ReadingTrackingTO>());
        trackingTO.setFinishedDate(LocalDateTime.now());

        trackingTOLido.setId(UUID.randomUUID());
        trackingTOLido.setUserBookId(userBooksLido.getId());
        trackingTOLido.setTrackings(new ArrayList<ReadingTrackingTO>());
        trackingTOLido.setFinishedDate(LocalDateTime.now());

        trackingQueroLer.setId(trackingTO.getId());
        trackingQueroLer.setUserBook(userBooksQueroLer);
        trackingQueroLer.setReadingTrackings(new ArrayList<ReadingTracking>());
        trackingQueroLer.setFinishedDate(LocalDateTime.now());

        trackingLido.setId(trackingTOLido.getId());
        trackingLido.setUserBook(userBooksLido);
        trackingLido.setReadingTrackings(new ArrayList<ReadingTracking>());
        trackingLido.setFinishedDate(LocalDateTime.now());

        trackingOpen.setId(trackingTO.getId());
        trackingOpen.setUserBook(userBooksQueroLer);
        trackingOpen.setReadingTrackings(new ArrayList<ReadingTracking>());
        trackingOpen.setFinishedDate(null);

        trackingTOList.add(trackingTO);
        trackingTOList.add(trackingTOLido);

        trackingListOpen.add(trackingQueroLer);
        trackingListOpen.add(trackingOpen);

        trackingList.add(trackingQueroLer);
        trackingList.add(trackingLido);
    }

    @Test
    void should_get_all_trackings_by_userbook() {
        Mockito.when(mockTrackingRepository.findAllByUserBookOrderByCreationDate(userBooksQueroLer)).thenReturn(trackingList);
        Mockito.when(mockUserBooksRepository.findById(userBooksQueroLer.getId())).thenReturn(Optional.ofNullable(userBooksQueroLer));
        List<TrackingTO> resultado = trackingService.getAllByBook(userBooksQueroLer.getId());

        assertEquals(trackingTOList, resultado);
    }

    @Test
    void shouldnt_get_all_trackings_by_userbook_when_userbooks_not_found() {
        Mockito.when(mockUserBooksRepository.findById(userBooksQueroLer.getId())).thenThrow(new ResourceNotFoundException(CodeException.UB001.getText(), CodeException.UB001));
        assertThrows(ResourceNotFoundException.class, () -> trackingService.getAllByBook(userBooksQueroLer.getId()));
    }

    @Test
    void should_get_one_tracking_by_id() {
        Mockito.when(mockTrackingRepository.findById(trackingTO.getId())).thenReturn(Optional.ofNullable(trackingQueroLer));
        TrackingTO resultado = trackingService.findById(trackingTO.getId());
        assertEquals(trackingTO, resultado);
    }

    @Test
    void shouldnt_get_one_tracking_by_id_when_tracking_not_found() {
        Mockito.when(mockTrackingRepository.findById(trackingQueroLer.getId())).thenThrow(new ResourceNotFoundException(CodeException.TA002.getText(), CodeException.TA002));
        assertThrows(ResourceNotFoundException.class, () -> trackingService.getOne(trackingTO.getId()));
    }

    @Test
    void should_save_tracking_userbooks_quero_ler() {
        Mockito.when(mockTrackingRepository.save(trackingQueroLer)).thenReturn(trackingQueroLer);
        Mockito.when(mockUserBooksRepository.findById(trackingTO.getUserBookId())).thenReturn(Optional.ofNullable(userBooksQueroLer));
        Mockito.when(mockTrackingRepository.findAllByUserBookOrderByCreationDate(userBooksQueroLer)).thenReturn(trackingList);
        Mockito.when(mockUserBooksService.updateStatus(userBooksBeanUtil.toDTOUpdate(userBooksQueroLer))).thenReturn(userBooksQueroLerTO);

        TrackingTO resultado = trackingService.save(trackingTO);
        assertEquals(trackingTO, resultado);
    }

    @Test
    void should_save_tracking_userbooks_lido() {
        Mockito.when(mockTrackingRepository.save(trackingLido)).thenReturn(trackingLido);
        Mockito.when(mockUserBooksRepository.findById(trackingTOLido.getUserBookId())).thenReturn(Optional.ofNullable(userBooksLido));
        Mockito.when(mockTrackingRepository.findAllByUserBookOrderByCreationDate(userBooksLido)).thenReturn(trackingList);
        Mockito.when(mockUserBooksService.updateStatus(userBooksBeanUtil.toDTOUpdate(userBooksLido))).thenReturn(userBooksLidoTO);

        TrackingTO resultado = trackingService.save(trackingTOLido);
        assertEquals(trackingTOLido, resultado);
    }

    @Test
    void shouldnt_save_tracking_when_other_is_open() {
        Mockito.when(mockTrackingRepository.save(trackingQueroLer)).thenReturn(trackingQueroLer);
        Mockito.when(mockUserBooksRepository.findById(trackingTO.getUserBookId())).thenReturn(Optional.ofNullable(userBooksQueroLer));
        Mockito.when(mockTrackingRepository.findAllByUserBookOrderByCreationDate(userBooksQueroLer)).thenReturn(trackingListOpen);

        assertThrows(ResourceConflictException.class, () -> trackingService.save(trackingTO));
    }

    @Test
    void shouldnt_save_tracking_when_userbooks_not_found() {
        Mockito.when(mockTrackingRepository.save(trackingQueroLer)).thenThrow(new ResourceNotFoundException(CodeException.UB001.getText(), CodeException.UB001));
        assertThrows(ResourceNotFoundException.class, () -> trackingService.getOne(trackingQueroLer.getId()));
    }

    @Test
    void should_delete_tracking() {
    }

    @Test
    void shouldnt_delete_tracking_when_tracking_not_found() {
        Mockito.doThrow(new ResourceNotFoundException(CodeException.TA002.getText(), CodeException.TA002)).when(mockTrackingRepository).deleteById(trackingTO.getId());
        assertThrows(ResourceNotFoundException.class, () -> trackingService.update(trackingTO.getId(), trackingTO));
    }

    @Test
    void should_update_tracking() {
    }

    @Test
    void shouldnt_update_tracking_when_tracking_not_found() {
        Mockito.when(mockTrackingRepository.findById(trackingTO.getId())).thenThrow(new ResourceNotFoundException(CodeException.TA002.getText(), CodeException.TA002));
        assertThrows(ResourceNotFoundException.class, () -> trackingService.update(trackingTO.getId(), trackingTO));
    }
}