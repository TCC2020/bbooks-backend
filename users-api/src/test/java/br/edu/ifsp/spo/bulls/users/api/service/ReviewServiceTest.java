package br.edu.ifsp.spo.bulls.users.api.service;

import br.edu.ifsp.spo.bulls.users.api.bean.ReviewBeanUtil;
import br.edu.ifsp.spo.bulls.users.api.domain.Book;
import br.edu.ifsp.spo.bulls.common.api.domain.Profile;
import br.edu.ifsp.spo.bulls.users.api.domain.Review;
import br.edu.ifsp.spo.bulls.common.api.domain.User;
import br.edu.ifsp.spo.bulls.users.api.dto.ReviewTO;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceBadRequestException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.users.api.repository.BookRepository;
import br.edu.ifsp.spo.bulls.users.api.repository.ProfileRepository;
import br.edu.ifsp.spo.bulls.users.api.repository.ReviewRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.Optional;
import java.util.function.Function;
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
    private BookRepository mockBookRepository;

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
    private Page<ReviewTO> reviewTOPage;
    private PageRequest pageRequest;

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

        reviewTOPage = new Page<ReviewTO>() {
            @Override
            public int getTotalPages() {
                return 0;
            }

            @Override
            public long getTotalElements() {
                return 0;
            }

            @Override
            public <U> Page<U> map(Function<? super ReviewTO, ? extends U> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return 0;
            }

            @Override
            public int getSize() {
                return 0;
            }

            @Override
            public int getNumberOfElements() {
                return 0;
            }

            @Override
            public List<ReviewTO> getContent() {
                return reviewTOList;
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @NotNull
            @Override
            public Iterator<ReviewTO> iterator() {
                return null;
            }
        };

        pageRequest = PageRequest.of(
                0,
                1,
                Sort.Direction.ASC,
                "id");
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
        Mockito.when(mockBookRepository.findById(book.getId())).thenReturn(Optional.ofNullable(book));
        Mockito.when(mockBookRepository.existsById(book.getId())).thenReturn(true);
        Mockito.when(mockReviewRepository.searchByBookId(book.getId(), pageRequest)).thenReturn(reviewTOPage);
        Page<ReviewTO> reviews = service.getAllByBook(book.getId(), user.getToken(), 0 , 1);
        assertEquals(reviewTOList, reviews.getContent());
    }

    @Test
    void shouldntGetAllByBookWhenBookNotFound() {
        Mockito.when(mockBookRepository.existsById(book.getId())).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.getAllByBook(book.getId(), user.getToken(), 0, 1));
    }

    @Test
    void getAllByGoogleBook() {
        Mockito.when(mockReviewRepository.searchByGoogleBook(review.getIdGoogleBook(), pageRequest)).thenReturn(reviewTOPage);
        Page<ReviewTO> reviews = service.getAllByGoogleBook(review.getIdGoogleBook(), user.getToken(), 0 , 1);
        assertEquals(reviewTOList, reviews.getContent());
    }

    @Test
    void shouldSaveReview() {
        Mockito.when(mockBookRepository.findById(book.getId())).thenReturn(Optional.ofNullable(book));
        Mockito.when(mockProfileRepository.findById(reviewBook.getProfile().getId())).thenReturn(Optional.ofNullable(profile));
        Mockito.when(mockReviewRepository.save(reviewBook)).thenReturn(reviewBook);

        ReviewTO result = service.save(reviewTOBook, user.getToken());
        assertEquals(reviewTOBook, result);
    }

    @Test
    void shouldntSaveReviewWhenBookNotFound() {
        Mockito.when(mockBookRepository.findById(book.getId())).thenReturn(Optional.ofNullable(null));
        assertThrows(ResourceNotFoundException.class, () -> service.save(reviewTOBook, user.getToken()));
    }

    @Test
    void updateReview() {
        Mockito.when(mockReviewRepository.findById(review.getId())).thenReturn(Optional.ofNullable(review));
        Mockito.when(mockReviewRepository.save(review)).thenReturn(review);
        ReviewTO result = service.updateReview(reviewTO.getId(), reviewTO, user.getToken());

        assertEquals(reviewTO, result);
    }

    @Test
    void shouldntUpdateReviewWhenReviewNotFound() {
        Mockito.when(mockReviewRepository.findById(review.getId())).thenThrow(new ResourceNotFoundException(CodeException.RE001.getText(),CodeException.RE001));
        assertThrows(ResourceNotFoundException.class, () -> service.updateReview(reviewTOBook.getId(), reviewTOBook, user.getToken()));
    }

    @Test
    void shouldntUpdateReviewWhenIdNotMatch() {
        assertThrows(ResourceBadRequestException.class, () -> service.updateReview(UUID.randomUUID(), reviewTOBook, user.getToken()));
    }

    @Test
    void shouldDeleteReviewById() {
        Mockito.doNothing().when(mockReviewRepository).deleteById(review.getId());
        service.deleteById(review.getId(), user.getToken());
        Mockito.verify(mockReviewRepository).deleteById(review.getId());
    }
}