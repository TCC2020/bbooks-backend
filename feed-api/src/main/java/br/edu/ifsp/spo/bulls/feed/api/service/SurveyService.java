package br.edu.ifsp.spo.bulls.feed.api.service;

import br.edu.ifsp.spo.bulls.common.api.dto.SurveyOptionsTO;
import br.edu.ifsp.spo.bulls.feed.api.bean.SurveyBeanUtil;
import br.edu.ifsp.spo.bulls.feed.api.domain.Post;
import br.edu.ifsp.spo.bulls.feed.api.domain.Survey;
import br.edu.ifsp.spo.bulls.feed.api.repository.PostRepository;
import br.edu.ifsp.spo.bulls.feed.api.repository.SurveyOptionsRepository;
import br.edu.ifsp.spo.bulls.feed.api.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SurveyService {

    @Autowired
    private SurveyOptionsRepository surveysOptionsRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private SurveyBeanUtil beanUtil;

    public void save(Survey survey, List<SurveyOptionsTO> optionsTO) {
        optionsTO.stream().forEach( x -> surveysOptionsRepository.save(beanUtil.toSurveyOptionDomain(x, survey)));
    }

    public void deleteByPost(UUID idPost) {
        Post post = postRepository.findById(idPost).get();
        System.out.println(post.getSurvey());
        surveysOptionsRepository.deleteAllBySurvey(post.getSurvey());

    }
}
