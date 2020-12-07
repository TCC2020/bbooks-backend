package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.bean.BookRecommendationBeanUtil;
import br.edu.ifsp.spo.bulls.usersApi.domain.BookRecommendation;
import br.edu.ifsp.spo.bulls.usersApi.dto.BookRecommendationTO;
import br.edu.ifsp.spo.bulls.usersApi.repository.BookRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookRecommendationService {

    @Autowired
    BookRecommendationRepository repository;

    @Autowired
    BookRecommendationBeanUtil beanUtil;

    public BookRecommendationTO save(BookRecommendationTO bookRecommendationTO){
        BookRecommendation bookRecommendation = beanUtil.toDomain(bookRecommendationTO);
        return beanUtil.toDto(repository.save(bookRecommendation));
    }
}
