package br.edu.ifsp.spo.bulls.users.api.service;

import br.edu.ifsp.spo.bulls.users.api.dto.ReviewTO;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {

    public ReviewTO getOneById(UUID reviewId, String token){
        return new ReviewTO();
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

    }
}
