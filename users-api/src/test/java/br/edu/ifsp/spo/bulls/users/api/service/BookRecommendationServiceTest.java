package br.edu.ifsp.spo.bulls.users.api.service;

import br.edu.ifsp.spo.bulls.users.api.bean.BookRecommendationBeanUtil;
import br.edu.ifsp.spo.bulls.users.api.domain.Book;
import br.edu.ifsp.spo.bulls.users.api.domain.BookRecommendation;
import br.edu.ifsp.spo.bulls.users.api.domain.Profile;
import br.edu.ifsp.spo.bulls.users.api.dto.BookRecommendationTO;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.users.api.repository.BookRecommendationRepository;
import br.edu.ifsp.spo.bulls.users.api.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BookRecommendationServiceTest {

    private Profile profileSubmitter;
    private Profile profileReceived;
    private Book book;
    private BookRecommendation bookRecommendation;
    private BookRecommendationTO bookRecommendationTO;
    private BookRecommendation bookRecommendationBook;
    private BookRecommendationTO bookRecommendationBookTO;
    private List<BookRecommendation> bookRecommendationList;
    private List<BookRecommendationTO> bookRecommendationTOList;

    @Autowired
    private BookRecommendationService service;

    @MockBean
    private BookRecommendationRepository mockRecommendationRepository;

    @MockBean
    private BookRepository mockBookRepository;

    @Autowired
    private BookRecommendationBeanUtil beanUtil;

    @BeforeEach
    void setUp(){
        profileReceived = new Profile();
        profileReceived.setId(1);

        profileSubmitter = new Profile();
        profileSubmitter.setId(2);

        bookRecommendationTO = new BookRecommendationTO();
        bookRecommendationTO.setId(UUID.randomUUID());
        bookRecommendationTO.setProfileReceived(profileReceived.getId());
        bookRecommendationTO.setProfileSubmitter(profileSubmitter.getId());
        bookRecommendationTO.setIdBookGoogle("idbook");

        bookRecommendation = new BookRecommendation();
        bookRecommendation = beanUtil.toDomain(bookRecommendationTO);

        bookRecommendationList = new ArrayList<>();
        bookRecommendationList.add(bookRecommendation);

        bookRecommendationTOList = new ArrayList<>();
        bookRecommendationTOList.add(bookRecommendationTO);

        book = new Book();
        book.setId(1);

        bookRecommendationBook = new BookRecommendation();
        bookRecommendationBook.setId(UUID.randomUUID());
        bookRecommendationBook.setProfileReceived(profileReceived.getId());
        bookRecommendationBook.setProfileSubmitter(profileSubmitter.getId());
        bookRecommendationBook.setBook(book);

        bookRecommendationBookTO = new BookRecommendationTO();
        bookRecommendationBookTO = beanUtil.toDto(bookRecommendationBook);
    }

    @Test
    void shouldSaveRecommendation(){
        Mockito.when(mockRecommendationRepository.save(bookRecommendation))
                .thenReturn(bookRecommendation);

        BookRecommendationTO resultado = service.save(bookRecommendationTO);

        assertEquals(bookRecommendationTO, resultado);
    }

    @Test
    void shouldSaveRecommendationWithBook(){
        Mockito.when(mockRecommendationRepository.save(bookRecommendationBook))
                .thenReturn(bookRecommendationBook);
        Mockito.when(mockBookRepository.findById(book.getId())).thenReturn(Optional.ofNullable(book));

        BookRecommendationTO resultado = service.save(bookRecommendationBookTO);

        assertEquals(bookRecommendationBookTO, resultado);

    }

    @Test
    void shouldNotSaveRecommendationWhenBookNotFound(){

        Mockito.when(mockRecommendationRepository.save(bookRecommendationBook))
                .thenReturn(bookRecommendationBook);
        Mockito.when(mockBookRepository.findById(book.getId())).thenThrow(new ResourceNotFoundException(CodeException.BK002.getText(), CodeException.BK002));
        assertThrows(ResourceNotFoundException.class, () -> service.save(bookRecommendationBookTO));
    }

    @Test
    void shouldDeleteRecommendation(){
        Mockito.doNothing().when(mockRecommendationRepository).deleteById(bookRecommendationTO.getId());
        service.delete(bookRecommendationTO.getId());
    }

    @Test
    void shouldUpdateRecommendation(){
        Mockito.when(mockRecommendationRepository.findById(bookRecommendationTO.getId()))
                .thenReturn(Optional.ofNullable(bookRecommendation));
        Mockito.when(mockRecommendationRepository.save(bookRecommendation)).thenReturn(bookRecommendation);

        BookRecommendationTO resultado = service.update(bookRecommendationTO, bookRecommendationTO.getId());

        assertEquals(bookRecommendationTO, resultado);
    }

    @Test
    void shouldntUpdateRecommendationWhenRecommendationNotFound(){
        Mockito.when(mockRecommendationRepository.findById(bookRecommendationTO.getId()))
                .thenThrow( new ResourceNotFoundException(CodeException.BR001.getText(), CodeException.BR001));

        assertThrows(ResourceNotFoundException.class, () -> service.update(bookRecommendationTO, bookRecommendationTO.getId()));
    }

    @Test
    void shouldGetRecommendationsSent(){
        Mockito.when(mockRecommendationRepository.findByProfileSubmitter(profileSubmitter.getId()))
                .thenReturn(bookRecommendationList);

        List<BookRecommendationTO> resultado = service.getRecommentionsSent(profileSubmitter.getId());

        assertEquals(bookRecommendationTOList, resultado);
    }

    @Test
    void shouldGetRecommentionsReceived(){
        Mockito.when(mockRecommendationRepository.findByProfileReceived(profileReceived.getId()))
                .thenReturn(bookRecommendationList);

        List<BookRecommendationTO> resultado = service.getRecommentionsReceived(profileReceived.getId());

        assertEquals(bookRecommendationTOList, resultado);
    }

    @Test
    void shouldGetRecommendation(){
        Mockito.when(mockRecommendationRepository.findById(bookRecommendationTO.getId())).thenReturn(Optional.ofNullable(bookRecommendation));

        BookRecommendationTO resultado = service.getRecommentionById(bookRecommendationTO.getId());

        assertEquals(bookRecommendationTO, resultado);

    }

    @Test
    void shouldntGetRecommendationWhenRecommendationNotFound(){
        Mockito.when(mockRecommendationRepository.findById(bookRecommendationTO.getId()))
                .thenThrow( new ResourceNotFoundException(CodeException.BR001.getText(), CodeException.BR001));

        assertThrows(ResourceNotFoundException.class, () -> service.getRecommentionById(bookRecommendationTO.getId()));
    }
}
