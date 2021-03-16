package br.edu.ifsp.spo.bulls.feed.api.service;

import br.edu.ifsp.spo.bulls.common.api.dto.SurveyOptionsTO;
import br.edu.ifsp.spo.bulls.common.api.dto.VoteTO;
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
        surveysOptionsRepository.deleteAllBySurvey(post.getSurvey());
    }

    public VoteTO vote(int profileId, VoteTO voteTO){
        // TODO: Implementar
        return null;
    }

    public VoteTO updateVote(int profileId, VoteTO voteTO, UUID voteId){
        // TODO: Implementar
        return null;
    }

    public void deleteVote(UUID voteId) {
        // TODO: Implementar
    }

    public int getVotesBySurvey(UUID surveyId) {
        // TODO: Implementar
        return 0;
    }

    public VoteTO getVoteByProfileAndOption(int profileId, UUID optionId){
        // TODO: Implementar
        return null;
    }
}
