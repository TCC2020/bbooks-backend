package br.edu.ifsp.spo.bulls.users.api.service;

import br.edu.ifsp.spo.bulls.users.api.bean.BookRecommendationBeanUtil;
import br.edu.ifsp.spo.bulls.users.api.domain.BookRecommendation;
import br.edu.ifsp.spo.bulls.users.api.dto.BookRecommendationTO;
import br.edu.ifsp.spo.bulls.users.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.users.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.users.api.repository.BookRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class BookRecommendationService {

    @Autowired
    private BookRecommendationRepository repository;

    @Autowired
    private BookRecommendationBeanUtil beanUtil;

    public BookRecommendationTO save(BookRecommendationTO bookRecommendationTO){
        BookRecommendation bookRecommendation = beanUtil.toDomain(bookRecommendationTO);
        return beanUtil.toDto(repository.save(bookRecommendation));
    }

    public BookRecommendationTO update(BookRecommendationTO bookRecommendationTO, UUID recommendationId){
        BookRecommendation retorno = repository.findById(recommendationId).map(recommendation -> {
            recommendation.setComentario(bookRecommendationTO.getComentario());
            return repository.save(recommendation);
        }).orElseThrow( () -> new ResourceNotFoundException(CodeException.BR001.getText(), CodeException.BR001));

        return beanUtil.toDto(retorno);
    }

    public void delete(UUID recommendationId){
        repository.deleteById(recommendationId);
    }

    public List<BookRecommendationTO> getRecommentionsSent(int profileSubmitter){
        return beanUtil.toDto(repository.findByProfileSubmitter(profileSubmitter));
    }

    public BookRecommendationTO getRecommentionById(UUID recommendationId){
        return beanUtil.toDto(repository.findById(recommendationId)
                .orElseThrow(() -> new ResourceNotFoundException(CodeException.BR001.getText(), CodeException.BR001)));
    }

    public List<BookRecommendationTO> getRecommentionsReceived(int profileReceived){
        return beanUtil.toDto(repository.findByProfileReceived(profileReceived));
    }
}
