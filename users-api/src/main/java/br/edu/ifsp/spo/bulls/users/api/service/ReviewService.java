package br.edu.ifsp.spo.bulls.users.api.service;

import br.edu.ifsp.spo.bulls.users.api.bean.ReviewBeanUtil;
import br.edu.ifsp.spo.bulls.users.api.domain.Book;
import br.edu.ifsp.spo.bulls.users.api.domain.Review;
import br.edu.ifsp.spo.bulls.users.api.dto.ReviewTO;
import br.edu.ifsp.spo.bulls.users.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.users.api.exception.ResourceBadRequestException;
import br.edu.ifsp.spo.bulls.users.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.users.api.repository.BookRepository;
import br.edu.ifsp.spo.bulls.users.api.repository.ReviewRepository;
import com.google.rpc.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository repository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ReviewBeanUtil beanUtil;

    public ReviewTO getOneById(UUID reviewId, String token){
        return beanUtil.toDto(repository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException(CodeException.RE001.getText(),CodeException.RE001)));
    }

    public List<ReviewTO> getAllByBook(int bookId, String token){
        Book book = bookRepository.findById(bookId).orElseThrow( () -> new ResourceNotFoundException(CodeException.BK002.getText(), CodeException.BK002));
        List<Review> listBook = repository.findAllByBookOrderByCreationDate(book);
        return beanUtil.toDto(listBook);
    }

    public List<ReviewTO> getAllByGoogleBook(String googleBookId, String token){
        List<Review> listBook = repository.findAllByIdGoogleBookOrderByCreationDate(googleBookId);
        return beanUtil.toDto(listBook);
    }

    public ReviewTO save(ReviewTO reviewTO, String token){
        Review review  = beanUtil.toDomain(reviewTO);
        return beanUtil.toDto(repository.save(review));
    }

    public ReviewTO updateReview(UUID reviewId, ReviewTO reviewTO, String token){
        if(!reviewTO.getId().equals(reviewId))
            throw new ResourceBadRequestException(CodeException.RE002.getText(), CodeException.RE002);

        return beanUtil.toDto( repository.findById(reviewId).map( review -> {
            review.setTitle(reviewTO.getTitle());
            review.setBody(reviewTO.getBody());
            return repository.save(review);
        }).orElseThrow( () -> new ResourceNotFoundException(CodeException.RE001.getText(), CodeException.RE001)));
    }

    public void deleteById(UUID reviewId, String token){
         repository.deleteById(reviewId);
    }
}
