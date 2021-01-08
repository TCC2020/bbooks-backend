package br.edu.ifsp.spo.bulls.users.api.bean;

import br.edu.ifsp.spo.bulls.users.api.controller.BookController;
import br.edu.ifsp.spo.bulls.users.api.domain.Review;
import br.edu.ifsp.spo.bulls.users.api.dto.ReviewTO;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.users.api.repository.BookRepository;
import br.edu.ifsp.spo.bulls.users.api.repository.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReviewBeanUtil {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ProfileRepository profileRepository;

    private Logger logger = LoggerFactory.getLogger(BookController.class);

    public Review toDomain(ReviewTO reviewTO){
        System.out.println(reviewTO);
        Review review =  new Review();
        try{
            BeanUtils.copyProperties(reviewTO, review);
        }catch(Exception e) {
            logger.error("Error while converting BookRecommendationTO to BookRecommendation: " +  e);
        }
        if(reviewTO.getBookId() != 0)
            review.setBook(bookRepository.findById(reviewTO.getBookId()).orElseThrow(() -> new ResourceNotFoundException(CodeException.BK002.getText(), CodeException.BK002)));

        review.setProfile(profileRepository.findById(reviewTO.getProfileId())
                .orElseThrow(() -> new ResourceNotFoundException(CodeException.PF001.getText(), CodeException.PF001)));
        return review;
    }

    public ReviewTO toDto(Review review){
        ReviewTO reviewTO =  new ReviewTO();
        try{
            BeanUtils.copyProperties(review, reviewTO);
            reviewTO.setProfileId(review.getProfile().getId());
            if(review.getBook() != null)
                reviewTO.setBookId(review.getBook().getId());
        }catch(Exception e) {
            logger.error("Error while converting BookRecommendation to BookRecommendationTO: " +  e);
        }
        return  reviewTO;
    }

    public List<ReviewTO> toDto(List<Review> reviews) {
        List<ReviewTO> reviewTOList = new ArrayList<>();
        for (Review review: reviews ) {
            reviewTOList.add(this.toDto(review));
        }
        return reviewTOList;
    }
}
