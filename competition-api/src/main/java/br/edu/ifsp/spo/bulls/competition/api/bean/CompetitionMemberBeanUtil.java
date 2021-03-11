package br.edu.ifsp.spo.bulls.competition.api.bean;

import br.edu.ifsp.spo.bulls.common.api.dto.CompetitionMemberTO;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.competition.api.domain.CompetitionMember;
import br.edu.ifsp.spo.bulls.common.api.dto.CompetitionMemberSaveTO;
import br.edu.ifsp.spo.bulls.competition.api.feign.UserCommonFeign;
import br.edu.ifsp.spo.bulls.competition.api.repository.CompetitionRepository;
import br.edu.ifsp.spo.bulls.competition.api.service.CompetitionService;
import br.edu.ifsp.spo.bulls.competition.api.service.CompetitionVoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.beans.BeanUtils.copyProperties;

@Component
public class CompetitionMemberBeanUtil {

    private final Logger logger = LoggerFactory.getLogger(CompetitionMemberBeanUtil.class);

    @Autowired
    private CompetitionRepository competitionRepository;

    @Autowired
    private CompetitionService competitionService;

    @Autowired
    private CompetitionVoteService voteService;

    @Autowired
    private UserCommonFeign feign;

    public CompetitionMember toDomain(CompetitionMemberSaveTO memberTO){
        CompetitionMember member = new CompetitionMember();
        try {
            copyProperties(memberTO, member);
            member.setCompetition(competitionRepository.findById(memberTO.getCompetitionId())
                    .orElseThrow( () -> new ResourceNotFoundException(CodeException.CP001.getText(), CodeException.CP001)));
        }   catch(Exception e) {
            logger.error("Exception coverting to dto: " + e);
        }

        return member;
    }

    public CompetitionMemberSaveTO toSaveTO(CompetitionMember member){
        CompetitionMemberSaveTO saveTO = new CompetitionMemberSaveTO();
        try {
            copyProperties(member, saveTO);
            saveTO.setCompetitionId(member.getCompetition().getId());
                    }   catch(Exception e) {
            logger.error("Exception coverting to dto: " + e);
        }

        return saveTO;
    }

    public CompetitionMemberTO toReturnDTO(CompetitionMember member){
        CompetitionMemberTO memberTO = new CompetitionMemberTO();
        try {
            copyProperties(member, memberTO);
            memberTO.setCompetitionTO(competitionService.getById(member.getCompetition().getId()));
            memberTO.setProfile(feign.getProfile(member.getProfileId()));
            memberTO.setMeanVote(voteService.average(member));
        }   catch(Exception e) {
            logger.error("Exception coverting to dto: " + e);
        }
        return memberTO;
    }

    public List<CompetitionMemberTO> toDtoList(List<CompetitionMember> list) {
        return list.stream().parallel().map(this::toReturnDTO).collect(Collectors.toList());
    }
}
