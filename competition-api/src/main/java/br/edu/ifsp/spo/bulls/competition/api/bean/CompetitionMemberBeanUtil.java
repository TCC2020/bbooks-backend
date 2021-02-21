package br.edu.ifsp.spo.bulls.competition.api.bean;

import br.edu.ifsp.spo.bulls.common.api.dto.CompetitionMemberTO;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.competition.api.domain.Competition;
import br.edu.ifsp.spo.bulls.competition.api.domain.CompetitionMember;
import br.edu.ifsp.spo.bulls.common.api.dto.CompetitionMemberSaveTO;
import br.edu.ifsp.spo.bulls.competition.api.feign.UserCommonFeign;
import br.edu.ifsp.spo.bulls.competition.api.repository.CompetitionRepository;
import br.edu.ifsp.spo.bulls.competition.api.service.CompetitionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.springframework.beans.BeanUtils.copyProperties;

@Component
public class CompetitionMemberBeanUtil {

    private final Logger logger = LoggerFactory.getLogger(CompetitionMemberBeanUtil.class);

    @Autowired
    private CompetitionRepository competitionRepository;

    @Autowired
    private CompetitionService competitionService;

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

    public CompetitionMemberSaveTO toSaveDto(CompetitionMember member){
        CompetitionMemberSaveTO memberTO = new CompetitionMemberSaveTO();
        try {
            copyProperties(member, memberTO);
            memberTO.setCompetitionId(member.getCompetition().getId());
            // TODO: calcular a média de votações
        }   catch(Exception e) {
            logger.error("Exception coverting to dto: " + e);
        }
        return memberTO;
    }

    public CompetitionMemberTO toReturnDTO(CompetitionMember member){
        CompetitionMemberTO memberTO = new CompetitionMemberTO();
        try {
            copyProperties(member, memberTO);
            memberTO.setCompetitionTO(competitionService.getById(member.getCompetition().getId()));
            memberTO.setProfile(feign.getProfile(member.getProfileId()));
            // TODO: calcular a média de votações
        }   catch(Exception e) {
            logger.error("Exception coverting to dto: " + e);
        }
        return memberTO;
    }
}
