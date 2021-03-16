package br.edu.ifsp.spo.bulls.feed.api.bean;

import br.edu.ifsp.spo.bulls.common.api.dto.SurveyOptionsTO;
import br.edu.ifsp.spo.bulls.common.api.dto.SurveyTO;
import br.edu.ifsp.spo.bulls.common.api.dto.VoteTO;
import br.edu.ifsp.spo.bulls.feed.api.domain.Survey;
import br.edu.ifsp.spo.bulls.feed.api.domain.SurveysOptions;
import br.edu.ifsp.spo.bulls.feed.api.domain.Vote;
import br.edu.ifsp.spo.bulls.feed.api.repository.SurveyOptionsRepository;
import br.edu.ifsp.spo.bulls.feed.api.repository.VoteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SurveyBeanUtil {

    @Autowired
    private SurveyOptionsRepository optionsRepository;

    @Autowired
    private VoteRepository voteRepository;

    public SurveyTO toDto(Survey survey) {
        SurveyTO surveyTO = new  SurveyTO();
        BeanUtils.copyProperties(survey, surveyTO);
        List<SurveysOptions> options = optionsRepository.findBySurvey(survey);

        surveyTO.setOptions(toSurveyOptionsDtoList(options));

        return surveyTO;
    }

    public Survey toDomain(SurveyTO dto) {
        Survey domain = new Survey();
        BeanUtils.copyProperties(dto, domain);
        // TODO: Ajustar
        //if(dto.getOptions() != null)
        //    domain.setOptions(toSurveyOptionsDomainList(dto.getOptions()));
        return domain;
    }

//    private List<SurveysOptions> toSurveyOptionsDomainList(List<SurveyOptionsTO> options) {
//        return options.stream().parallel().map(this::toSurveyOptionDomain).collect(Collectors.toList());
//    }
//
    public List<SurveyOptionsTO> toSurveyOptionsDtoList(List<SurveysOptions> options) {
        return options.parallelStream().map(this::toSurveyOptionDto).collect(Collectors.toList());
    }
//
//    public SurveysOptions toSurveyOptionDomain(SurveyOptionsTO dto) {
//        SurveysOptions domain = new SurveysOptions();
//        BeanUtils.copyProperties(dto, domain);
//        return domain;
//    }

    public SurveysOptions toSurveyOptionDomain(SurveyOptionsTO dto, Survey survey) {
        SurveysOptions domain = new SurveysOptions();
        BeanUtils.copyProperties(dto, domain);
        domain.setSurvey(survey);
        return domain;
    }

//    private List<Vote> toVoteDomainList(List<VoteTO> dtos) {
//        return dtos.stream().parallel().map(this::toVoteDomain).collect(Collectors.toList());
//    }

//    public Vote toVoteDomain(VoteTO dto) {
//        Vote domain = new Vote();
//        BeanUtils.copyProperties(dto, domain);
//        return domain;
//    }
//
    public SurveyOptionsTO toSurveyOptionDto(SurveysOptions surveyOptions) {
        SurveyOptionsTO dto = new SurveyOptionsTO();
        BeanUtils.copyProperties(surveyOptions, dto);
        List<Vote> votes = voteRepository.findByOption(surveyOptions);
        dto.setVotes(this.toVoteDtoList(votes));
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
