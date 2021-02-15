package br.edu.ifsp.spo.bulls.competition.api.bean;

import br.edu.ifsp.spo.bulls.competition.api.domain.CompetitionMember;
import br.edu.ifsp.spo.bulls.competition.api.dto.CompetitionMemberTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CompetitionMemberBeanUtil {

    private final Logger logger = LoggerFactory.getLogger(CompetitionMemberBeanUtil.class);

    public CompetitionMember toDomain(CompetitionMemberTO memberTO){
        CompetitionMember member = new CompetitionMember();
        //  TODO: implementar
        return member;
    }

    public CompetitionMemberTO toDto(CompetitionMember member){
        CompetitionMemberTO memberTO = new CompetitionMemberTO();
        //  TODO: implementar
        return memberTO;
    }
}
