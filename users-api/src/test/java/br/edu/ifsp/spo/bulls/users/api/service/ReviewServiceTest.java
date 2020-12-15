package br.edu.ifsp.spo.bulls.users.api.service;

import br.edu.ifsp.spo.bulls.users.api.bean.ReviewBeanUtil;
import br.edu.ifsp.spo.bulls.users.api.domain.Book;
import br.edu.ifsp.spo.bulls.users.api.domain.Profile;
import br.edu.ifsp.spo.bulls.users.api.domain.Review;
import br.edu.ifsp.spo.bulls.users.api.domain.User;
import br.edu.ifsp.spo.bulls.users.api.dto.ReviewTO;
import br.edu.ifsp.spo.bulls.users.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.users.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.users.api.repository.ProfileRepository;
import br.edu.ifsp.spo.bulls.users.api.repository.ReviewRepository;
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
public class ReviewServiceTest {

    @Autowired
    private ReviewBeanUtil beanUtil;

    @Autowired
    private ReviewService service;

    @MockBean
    private ReviewRepository mockReviewRepository;

    @MockBean
    private ProfileRepository mockProfileRepository;

    private Review review;
    private ReviewTO reviewTO;
    private Book book;
    private Review reviewBook;
    private ReviewTO reviewTOBook;
    private User user;
    private Profile profile;
    private List<Review> reviewList;
    private List<ReviewTO> reviewTOList;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("email");
        user.setToken("token");
        user.setUserName("username");

        profile = new Profile();
        profile.setUser(user);
        profile.setId(1);

        review = new Review();
        review.setBody("resenha");
        review.setCreationDate(LocalDateTime.now());
        review.setId(UUID.randomUUID());
        review.setTitle("titulo da resenha");
        review.setIdGoogleBook("idGoogleBook");
        review.setProfile(profile);

        book = new Book();
        book.setId(1);
        book.setTitle("LIVRO");

        reviewBook = new Review();
        reviewBook.setBody("resenha");
        reviewBook.setCreationDate(LocalDateTime.now());
        reviewBook.setId(UUID.randomUUID());
        reviewBook.setTitle("titulo da resenha");
        reviewBook.setBook(book);
        reviewBook.setProfile(profile);

        reviewTOBook = beanUtil.toDto(reviewBook);

        reviewList = new ArrayList<>();
        reviewList.add(review);

        reviewTO = beanUtil.toDto(review);
        reviewTOList = beanUtil.toDto(reviewList);
    }

    @Test
    void shouldGetOneReviewById() {
        Mockito.when(mockReviewRepository.findById(review.getId())).thenReturn(Optional.ofNullable(review));
        Mockito.when(mockProfileRepository.findById(review.getProfile().getId())).thenReturn(Optional.ofNullable(profile));

        ReviewTO result = service.getOneById(review.getId(), user.getToken());

        assertEquals(reviewTO, result);
    }

    @Test
    void shouldntGetOneReviewByIdWhenReviewNotFound() {
        Mockito.when(mockReviewRepository.findById(review.getId())).thenThrow(new ResourceNotFoundException(CodeException.RE001.getText(),CodeException.RE001));

        assertThrows(ResourceNotFoundException.class, () ->  service.getOneById(review.getId(), user.getToken()));
    }

    @Test
    void getAllByBook() {
        //fail("Não implementado");
    }

    @Test
    void postReview() {
        //fail("Não implementado");
    }

    @Test
    void putReview() {
        //fail("Não implementado");
    }

    @Test
    void shouldDeleteReviewById() {
        Mockito.doNothing().when(mockReviewRepository).deleteById(review.getId());
        service.deleteById(review.getId(), user.getToken());
        Mockito.verify(mockReviewRepository).deleteById(review.getId());
    }
}