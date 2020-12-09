package br.edu.ifsp.spo.bulls.users.api.service;

import br.edu.ifsp.spo.bulls.users.api.domain.Book;
import br.edu.ifsp.spo.bulls.users.api.domain.BookRecommendation;
import br.edu.ifsp.spo.bulls.users.api.domain.Profile;
import br.edu.ifsp.spo.bulls.users.api.dto.BookRecommendationTO;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BookRecommendationServiceTest {
    private Profile profileSubmitter;
    private Profile profileReceived;
    private Book book;
    private BookRecommendation bookRecommendation;
    private BookRecommendationTO bookRecommendationTO;
    private List<BookRecommendation> bookRecommendationList;
    private List<BookRecommendationTO> bookRecommendationTOList;
}
