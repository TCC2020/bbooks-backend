package br.edu.ifsp.spo.bulls.users.api.service;

import br.edu.ifsp.spo.bulls.users.api.bean.ReviewBeanUtil;
import br.edu.ifsp.spo.bulls.users.api.dto.ReviewTO;
import br.edu.ifsp.spo.bulls.users.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.users.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.users.api.repository.ReviewRepository;
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
    private ReviewBeanUtil beanUtil;

    public ReviewTO getOneById(UUID reviewId, String token){
        return beanUtil.toDto(repository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException(CodeException.RE001.getText(),CodeException.RE001)));
    }

    public List<ReviewTO> getAllByBook(String bookId, String token){
        return new ArrayList<>();
    }

    public ReviewTO postReview(ReviewTO reviewTO, String token){
        return new ReviewTO();
    }

    public ReviewTO putReview(UUID reviewId, ReviewTO reviewTO, String token){
        return new ReviewTO();
    }

    public void deleteById(UUID reviewId, String token){
         repository.deleteById(reviewId);
    }
}
