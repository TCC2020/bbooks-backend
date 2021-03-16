package br.edu.ifsp.spo.bulls.users.api.bean;

import br.edu.ifsp.spo.bulls.users.api.controller.BookController;
import br.edu.ifsp.spo.bulls.users.api.domain.BookRecommendation;
import br.edu.ifsp.spo.bulls.users.api.dto.BookRecommendationTO;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.users.api.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class BookRecommendationBeanUtil {

    @Autowired
    private BookRepository bookRepository;

    private final Logger logger = LoggerFactory.getLogger(BookController.class);

    public BookRecommendation toDomain(BookRecommendationTO bookRecommendationTO){
        BookRecommendation bookRecommendation =  new BookRecommendation();
        try{
            BeanUtils.copyProperties(bookRecommendationTO, bookRecommendation);
        }catch(Exception e) {
            logger.error("Error while converting BookRecommendationTO to BookRecommendation: " +  e);
        }
        if(bookRecommendationTO.getIdBook() != 0)
            bookRecommendation.setBook(bookRepository.findById(bookRecommendationTO.getIdBook())
                    .orElseThrow(() -> new ResourceNotFoundException(CodeException.BK002.getText(), CodeException.BK002)));
        return bookRecommendation;
    }

    public BookRecommendationTO toDto(BookRecommendation bookRecommendation){
        BookRecommendationTO bookRecommendationTO =  new BookRecommendationTO();
        try{
            BeanUtils.copyProperties(bookRecommendation, bookRecommendationTO);
            if(bookRecommendation.getBook() != null)
                bookRecommendationTO.setIdBook(bookRecommendation.getBook().getId());
        }catch(Exception e) {
            logger.error("Error while converting BookRecommendation to BookRecommendationTO: " +  e);
        }
        return  bookRecommendationTO;
    }

    public List<BookRecommendationTO> toDto(List<BookRecommendation> recommendations) {
        List<BookRecommendationTO> recommendationsToList = new ArrayList<>();
        for (BookRecommendation recommendation: recommendations ) {
            recommendationsToList.add(this.toDto(recommendation));
        }
        return recommendationsToList;
    }
}
