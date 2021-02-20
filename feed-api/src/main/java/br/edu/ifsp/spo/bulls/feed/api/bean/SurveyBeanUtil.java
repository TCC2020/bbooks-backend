package br.edu.ifsp.spo.bulls.feed.api.bean;

import br.edu.ifsp.spo.bulls.common.api.dto.SurveyOptionsTO;
import br.edu.ifsp.spo.bulls.common.api.dto.SurveyTO;
import br.edu.ifsp.spo.bulls.common.api.dto.VoteTO;
import br.edu.ifsp.spo.bulls.feed.api.domain.Survey;
import br.edu.ifsp.spo.bulls.feed.api.domain.SurveysOptions;
import br.edu.ifsp.spo.bulls.feed.api.domain.Vote;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SurveyBeanUtil {

    public SurveyTO toDto(Survey survey) {
        SurveyTO surveyTO = new  SurveyTO();
        BeanUtils.copyProperties(survey, surveyTO);
        if(survey.getOptions() != null)
            surveyTO.setOptions(toSurveyOptionsDtoList(survey.getOptions()));
        return surveyTO;
    }

    public List<SurveyOptionsTO> toSurveyOptionsDtoList(List<SurveysOptions> options) {
        return options.parallelStream().map(this::toSurveyOptionDto).collect(Collectors.toList());
    }

    public SurveyOptionsTO toSurveyOptionDto(SurveysOptions surveyOptions) {
        SurveyOptionsTO dto = new SurveyOptionsTO();
        BeanUtils.copyProperties(surveyOptions, dto);
        if(surveyOptions.getVotes() != null)
            dto.setVotes(toVoteDtoList(surveyOptions.getVotes()));
        return dto;
    }

    public VoteTO toVoteDto(Vote vote) {
        VoteTO dto = new VoteTO();
        BeanUtils.copyProperties(vote, dto);
        return dto;
    }

    public List<VoteTO> toVoteDtoList(List<Vote> votes) {
        return votes.stream().parallel().map(this::toVoteDto).collect(Collectors.toList());
    }
}
