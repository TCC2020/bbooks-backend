package br.edu.ifsp.spo.bulls.competition.api.bean;

import br.edu.ifsp.spo.bulls.competition.api.domain.CompetitionMember;
import br.edu.ifsp.spo.bulls.competition.api.dto.CompetitionMemberTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static org.springframework.beans.BeanUtils.copyProperties;

@Component
public class CompetitionMemberBeanUtil {

    private final Logger logger = LoggerFactory.getLogger(CompetitionMemberBeanUtil.class);

    public CompetitionMember toDomain(CompetitionMemberTO memberTO){
        CompetitionMember member = new CompetitionMember();
        try {
            copyProperties(memberTO, member);
        }   catch(Exception e) {
            logger.error("Exception coverting to dto: " + e);
        }

        return member;
    }

    public CompetitionMemberTO toDto(CompetitionMember member){
        CompetitionMemberTO memberTO = new CompetitionMemberTO();
        try {
            copyProperties(member, memberTO);
            memberTO.setCompetitionId(member.getCompetition().getId());
        }   catch(Exception e) {
            logger.error("Exception coverting to dto: " + e);
        }
        return memberTO;
    }
}
