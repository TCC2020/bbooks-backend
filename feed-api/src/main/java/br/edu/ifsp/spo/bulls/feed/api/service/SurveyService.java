package br.edu.ifsp.spo.bulls.feed.api.service;

import br.edu.ifsp.spo.bulls.common.api.dto.ProfileTO;
import br.edu.ifsp.spo.bulls.common.api.dto.survey.SurveyTO;
import br.edu.ifsp.spo.bulls.common.api.dto.survey.SurveyVoteTO;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.feed.api.bean.SurveyBeanUtil;
import br.edu.ifsp.spo.bulls.feed.api.domain.Survey;
import br.edu.ifsp.spo.bulls.feed.api.domain.SurveysOptions;
import br.edu.ifsp.spo.bulls.feed.api.domain.Vote;
import br.edu.ifsp.spo.bulls.feed.api.feign.UserCommonFeign;
import br.edu.ifsp.spo.bulls.feed.api.repository.PostRepository;
import br.edu.ifsp.spo.bulls.feed.api.repository.SurveyRepository;
import br.edu.ifsp.spo.bulls.feed.api.repository.SurveysOptionsRepository;
import br.edu.ifsp.spo.bulls.feed.api.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SurveyService {
    @Autowired
    private SurveyRepository repository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private SurveysOptionsRepository optionsRepository;

    @Autowired
    private UserCommonFeign userCommonFeign;

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private SurveyBeanUtil beanUtil;

    @Autowired
    private VoteRepository voteRepository;

    public SurveyTO vote(String token, SurveyVoteTO dto) {
        ProfileTO profile = userCommonFeign.getProfileByToken(token);
        Survey survey = surveyRepository.findById(dto.getSurveyId()).orElseThrow(() -> new ResourceNotFoundException(CodeException.SUR001));
        Vote vote = voteRepository.findByProfileIdAndSurvey(profile.getId(), survey).orElse(null);
        if(vote != null) {
            SurveysOptions options = optionsRepository.findById(dto.getSurveyOptionId()).orElseThrow(() -> new ResourceNotFoundException(CodeException.SUR001));
            if(vote.getSurveyOptions().getId().equals(options.getId())) {
                options.getVotes().remove(vote);
                optionsRepository.save(options);
                return beanUtil.toDto(surveyRepository.findById(dto.getSurveyId()).get());
            }
            vote.setSurveyOptions(options);
            voteRepository.save(vote);
            return beanUtil.toDto(surveyRepository.findById(dto.getSurveyId()).get());
        }
        vote = new Vote(profile.getId(), survey, optionsRepository.findById(dto.getSurveyOptionId()).orElseThrow(() -> new ResourceNotFoundException(CodeException.SUR001)));
        voteRepository.save(vote);
        return beanUtil.toDto(surveyRepository.findById(dto.getSurveyId()).get());

//        ProfileTO profile = userCommonFeign.getProfileByToken(token);
//        SurveysOptions options = optionsRepository.findById(dto.getSurveyOptionId())
//                .orElseThrow(() -> new ResourceNotFoundException(CodeException.SUR002));
//        Vote vote = options.getVotes().stream().parallel().filter(v -> v.getProfileId() == profile.getId())
//                .findFirst().orElse(null);
//        if(vote == null)
//            options.getVotes().add(new Vote(profile.getId()));
//        else
//            options.getVotes().remove(vote);
//
//        optionsRepository.save(options);
//        return beanUtil.toDto(repository.findById(options.getSurvey().getId()).orElse(null));
    }
}
