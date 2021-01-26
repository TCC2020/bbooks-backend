package br.edu.ifsp.spo.bulls.users.api.service;

import br.edu.ifsp.spo.bulls.users.api.bean.ReadingTrackingBeanUtil;
import br.edu.ifsp.spo.bulls.users.api.bean.UserBooksBeanUtil;
import br.edu.ifsp.spo.bulls.common.api.domain.Profile;
import br.edu.ifsp.spo.bulls.users.api.domain.ReadingTracking;
import br.edu.ifsp.spo.bulls.users.api.domain.Tracking;
import br.edu.ifsp.spo.bulls.users.api.domain.UserBooks;
import br.edu.ifsp.spo.bulls.users.api.domain.Book;
import br.edu.ifsp.spo.bulls.users.api.dto.ReadingTrackingTO;
import br.edu.ifsp.spo.bulls.users.api.dto.TrackingTO;
import br.edu.ifsp.spo.bulls.users.api.dto.UserBookUpdateStatusTO;
import br.edu.ifsp.spo.bulls.users.api.dto.UserBooksTO;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.users.api.enums.Status;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.users.api.repository.ReadingTrackingRepository;
import br.edu.ifsp.spo.bulls.users.api.repository.TrackingRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ReadingTrackingServiceTest {

    @Autowired
    public ReadingTrackingService readingTrackingService;

    @Autowired
    public UserBooksBeanUtil userBooksBeanUtil;

    @MockBean
    private ReadingTrackingRepository mockReadingTrackingRepository;

    @MockBean
    private TrackingRepository mockTrackingRepository;

    @MockBean
    private TrackingService mockTrackingService;

    @MockBean
    private UserBooksService mockUserBooksService;

    @Autowired
    private ReadingTrackingBeanUtil readingTrackingBeanUtil;

    private ReadingTrackingTO readingTrackingTO;
    private ReadingTrackingTO readingTrackingTO2;
    private ReadingTracking readingTracking;
    private ReadingTracking readingTracking2;
    private Tracking tracking;
    private Tracking tracking2;
    private List<ReadingTracking> readingTrackingList;
    private UserBooks userBooks;

    @BeforeEach
    void setUp(){
        Profile profile = new Profile();
        profile.setId(1);

        userBooks = new UserBooks();
        userBooks.setIdBookGoogle("googlebook");
        userBooks.setPage(100);
        userBooks.setStatus(Status.QUERO_LER);
        userBooks.setProfile(profile);

        TrackingTO trackingTO = new TrackingTO();
        trackingTO.setId(UUID.randomUUID());
        trackingTO.setCreationDate(LocalDateTime.now());
        trackingTO.setUserBookId(userBooks.getId());

        tracking = new Tracking();
        tracking.setId(trackingTO.getId());
        tracking.setCreationDate(LocalDateTime.now());
        tracking.setUserBook(userBooks);

        readingTrackingTO = new ReadingTrackingTO();
        readingTrackingTO.setId(UUID.randomUUID());
        readingTrackingTO.setNumPag(10);
        readingTrackingTO.setTrackingUpId(trackingTO.getId());
        readingTrackingTO.setPercentage(10.0F);

        Mockito.when(mockTrackingRepository.findById(readingTrackingTO.getTrackingUpId())).thenReturn(Optional.ofNullable(tracking));
        readingTracking = readingTrackingBeanUtil.toDomain(readingTrackingTO);
        readingTracking.setId(readingTrackingTO.getId());


        Book book = new Book();
        book.setId(1);
        book.setNumberPage(100);
        UserBooks userBooks2 = new UserBooks();
        userBooks2.setBook(book);
        userBooks2.setStatus(Status.QUERO_LER);

        tracking2 = new Tracking();
        tracking2.setId(UUID.randomUUID());
        tracking2.setId(trackingTO.getId());
        tracking2.setCreationDate(LocalDateTime.now());
        tracking2.setUserBook(userBooks2);

        readingTracking2 = new ReadingTracking();
        readingTracking2.setId(tracking2.getId());
        readingTracking2.setTrackingGroup(tracking2);
        readingTracking2.setNumPag(20);

        readingTrackingTO2 = readingTrackingBeanUtil.toDTO(readingTracking2);
        readingTrackingTO2.setPercentage(20.0F);
        readingTrackingList = new ArrayList<>();
        readingTrackingList.add(readingTracking2);



    }

    @Test
    void shouldSave(){
        readingTracking2.setNumPag(6);
        readingTracking.setPercentage(10.0F);
        Mockito.when(mockTrackingRepository.findById(readingTrackingTO.getTrackingUpId())).thenReturn(Optional.ofNullable(tracking));
        Mockito.when(mockReadingTrackingRepository.findByTrackingGroup(tracking)).thenReturn(readingTrackingList);
        Mockito.when(mockReadingTrackingRepository.save(readingTracking)).thenReturn(readingTracking);

        ReadingTrackingTO resultado = readingTrackingService.save(readingTrackingTO);

        assertEquals(readingTrackingTO, resultado);
    }

    @Test
    void shouldntSaveWhenPaginasLidasMenorQueAnteriores(){
        Mockito.when(mockTrackingRepository.findById(readingTrackingTO.getTrackingUpId())).thenReturn(Optional.ofNullable(tracking));
        Mockito.when(mockReadingTrackingRepository.findByTrackingGroup(tracking)).thenReturn(readingTrackingList);

        ResourceConflictException e = assertThrows(ResourceConflictException.class, () -> readingTrackingService.save(readingTrackingTO));
        assertEquals(CodeException.RT005.getText(), e.getMessage());
    }

    @Test
    void shouldntSaveWhenPageIsZero(){
        readingTrackingTO.setNumPag(0);
        ResourceConflictException e = assertThrows(ResourceConflictException.class, () -> readingTrackingService.save(readingTrackingTO));
        assertEquals(CodeException.RT006.getText(), e.getMessage());
    }

    @Test
    void shouldSaveAndStatusShouldBeLendo()  {
        readingTracking2.setPercentage(20.0F);
        Mockito.when(mockTrackingRepository.findById(readingTrackingTO2.getTrackingUpId())).thenReturn(Optional.ofNullable(tracking2));
        Mockito.when(mockReadingTrackingRepository.findByTrackingGroup(tracking)).thenReturn(readingTrackingList);
        Mockito.when(mockReadingTrackingRepository.save(readingTracking2)).thenReturn(readingTracking2);

        ReadingTrackingTO resultado = readingTrackingService.save(readingTrackingTO2);

        assertEquals(readingTrackingTO2, resultado);
    }

    @Test
    void shouldSaveAndStatusShouldBeLido() {
        readingTrackingTO2.setNumPag(100);
        readingTracking2.setNumPag(100);
        readingTrackingTO2.setPercentage(100.0F);
        readingTracking2.setPercentage(100.0F);
        tracking2.setFinishedDate(LocalDateTime.now());
        readingTracking2.getTrackingGroup().getUserBook().setStatus(Status.LIDO);
        Mockito.when(mockTrackingRepository.findById(readingTrackingTO2.getTrackingUpId())).thenReturn(Optional.ofNullable(tracking2));
        Mockito.when(mockReadingTrackingRepository.findByTrackingGroup(tracking2)).thenReturn(readingTrackingList);
        Mockito.when(mockReadingTrackingRepository.save(readingTracking2)).thenReturn(readingTracking2);

        System.out.println(readingTracking2);
        ReadingTrackingTO resultado = readingTrackingService.save(readingTrackingTO2);

        assertEquals(readingTrackingTO2, resultado);
    }

    @Test
    void shouldGetByTrackingGroup() {
        List<ReadingTracking> readingTrackingList = new ArrayList<>();
        readingTrackingList.add(readingTracking);
        readingTrackingList.add(readingTracking2);

        Mockito.when(mockTrackingService.getOne(readingTrackingTO.getTrackingUpId())).thenReturn(tracking);
        Mockito.when(mockReadingTrackingRepository.findByTrackingGroup(readingTracking.getTrackingGroup())).thenReturn(readingTrackingList);

        List<ReadingTracking> resultado = readingTrackingService.getByTrackingGroup(readingTrackingTO.getTrackingUpId());

        assertEquals(readingTrackingList, resultado);
    }

    @Test
    void shouldNotSaveIfPageIsMoreThanBook() {
        tracking.getUserBook().setPage(50);
        readingTrackingTO.setNumPag(51);
        Mockito.when(mockTrackingRepository.findById(readingTrackingTO.getTrackingUpId())).thenReturn(Optional.ofNullable(tracking));

        ResourceConflictException e = assertThrows(ResourceConflictException.class, () -> readingTrackingService.save(readingTrackingTO));
        assertEquals(CodeException.RT002.getText(), e.getMessage());
    }

    @Test
    void update() {
        Mockito.when(mockTrackingRepository.findById(readingTrackingTO.getTrackingUpId())).thenReturn(Optional.ofNullable(tracking));
        Mockito.when(mockReadingTrackingRepository.save(readingTracking)).thenReturn(readingTracking);
        Mockito.when(mockReadingTrackingRepository.findById(readingTrackingTO.getId())).thenReturn(Optional.ofNullable(readingTracking));
        ReadingTrackingTO resultado = readingTrackingService.update(readingTrackingTO, readingTrackingTO.getId());

        assertEquals(readingTrackingTO, resultado);
    }

    @Test
    void shouldntUpdateWhenIdNotMatch() {
        assertThrows(ResourceConflictException.class, () -> readingTrackingService.update(readingTrackingTO, UUID.randomUUID()));
    }

    @Test
    void updateTrackingNotFound(){
        Mockito.when(mockTrackingRepository.findById(readingTrackingTO.getTrackingUpId())).thenReturn(Optional.ofNullable(tracking));
        Mockito.when(mockReadingTrackingRepository.findById(readingTrackingTO.getId())).thenReturn(Optional.ofNullable(null));

        ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class,
                () -> readingTrackingService.update(readingTrackingTO, readingTrackingTO.getId()));
        assertEquals(CodeException.RT001.getText(), e.getMessage());
    }

    @Test
    void getOneTrackingNotFound(){
        Mockito.when(mockReadingTrackingRepository.findById(readingTrackingTO.getId())).thenReturn(Optional.ofNullable(null));
        ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class,
                () -> readingTrackingService.get(readingTrackingTO.getId()));
        assertEquals(CodeException.RT001.getText(), e.getMessage());
    }

    @Test
    void getOneTrackingOk(){
        Mockito.when(mockReadingTrackingRepository.findById(readingTrackingTO.getId())).thenReturn(Optional.ofNullable(readingTracking));

        ReadingTrackingTO resultado = readingTrackingService.get(readingTrackingTO.getId());

        assertEquals(readingTrackingTO, resultado);
    }

    @Test
    void delete(){
        Mockito.when(mockReadingTrackingRepository.findById(readingTrackingTO.getId())).thenReturn(Optional.ofNullable(readingTracking));
        Mockito.doNothing().when(mockReadingTrackingRepository).deleteById(readingTrackingTO.getId());
        readingTrackingService.delete(readingTrackingTO.getId());
    }

    @Test
    void delete100percetage(){
        readingTracking.setPercentage(100.00F);
        UserBookUpdateStatusTO userBookUpdateStatusTO = userBooksBeanUtil.toDTOUpdate(userBooks);
        UserBooksTO userBooksTO = new UserBooksTO();
        userBooksTO.setId(userBooks.getId());
        userBooksTO.setProfileId(userBooks.getProfile().getId());
        userBooksTO.setIdBookGoogle(userBooks.getIdBookGoogle());
        userBooksTO.setStatus(userBooks.getStatus());

        Mockito.when(mockTrackingService.update(tracking)).thenReturn(tracking);
        Mockito.when(mockUserBooksService.updateStatus(userBookUpdateStatusTO)).thenReturn(userBooksTO);
        Mockito.when(mockReadingTrackingRepository.findById(readingTrackingTO.getId())).thenReturn(Optional.ofNullable(readingTracking));
        Mockito.doNothing().when(mockReadingTrackingRepository).deleteById(readingTrackingTO.getId());
        readingTrackingService.delete(readingTrackingTO.getId());
    }

    @Test
    void deleteTrackingNotFound() {
        Mockito.when(mockReadingTrackingRepository.findById(readingTrackingTO.getId())).thenReturn(Optional.ofNullable(null));
        ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class,
                () -> readingTrackingService.delete(readingTrackingTO.getId()));
        assertEquals(CodeException.RT001.getText(), e.getMessage());
    }

}