package br.edu.ifsp.spo.bulls.usersApi.bean;

import br.edu.ifsp.spo.bulls.usersApi.domain.BookRecommendation;
import br.edu.ifsp.spo.bulls.usersApi.dto.BookRecommendationTO;
import br.edu.ifsp.spo.bulls.usersApi.enums.CodeException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.usersApi.repository.BookRepository;
import br.edu.ifsp.spo.bulls.usersApi.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookRecommendationBeanUtil {

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    BookRepository bookRepository;

    public BookRecommendation toDomain(BookRecommendationTO bookRecommendationTO){
        BookRecommendation bookRecommendation =  new BookRecommendation();
        bookRecommendation.setIdBookGoogle(bookRecommendationTO.getIdBookGoogle());
        bookRecommendation.setId(bookRecommendationTO.getId());
        bookRecommendation.setBook(bookRepository.findById(bookRecommendationTO.getIdBook())
                            .orElseThrow(() -> new ResourceNotFoundException(CodeException.BK002.getText(), CodeException.BK002)));
        bookRecommendation.setProfileReceived(profileRepository.findById(bookRecommendationTO.getProfileIdReceived())
                            .orElseThrow(() -> new ResourceNotFoundException(CodeException.PF001.getText(), CodeException.PF001)));
        bookRecommendation.setProfileSubmitter(profileRepository.findById(bookRecommendationTO.getProfileIdSubmitter())
                .orElseThrow(() -> new ResourceNotFoundException(CodeException.PF001.getText(), CodeException.PF001)));

        return bookRecommendation;
    }

    public BookRecommendationTO toDto(BookRecommendation bookRecommendation){
        BookRecommendationTO bookRecommendationTO =  new BookRecommendationTO();
        bookRecommendationTO.setIdBookGoogle(bookRecommendationTO.getIdBookGoogle());
        bookRecommendationTO.setId(bookRecommendationTO.getId());
        bookRecommendationTO.setIdBook(bookRecommendation.getBook().getId());
        bookRecommendationTO.setProfileIdReceived(bookRecommendation.getProfileReceived().getId());
        bookRecommendationTO.setProfileIdSubmitter(bookRecommendation.getProfileSubmitter().getId()s);

        return  bookRecommendationTO;
    }
}
