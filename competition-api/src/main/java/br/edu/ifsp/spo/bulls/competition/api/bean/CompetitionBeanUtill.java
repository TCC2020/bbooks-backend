package br.edu.ifsp.spo.bulls.competition.api.bean;

import br.edu.ifsp.spo.bulls.common.api.enums.Role;
import br.edu.ifsp.spo.bulls.competition.api.domain.Competition;
import br.edu.ifsp.spo.bulls.competition.api.dto.CompetitionTO;
import br.edu.ifsp.spo.bulls.competition.api.repository.CompetitionMemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.springframework.beans.BeanUtils.copyProperties;

@Component
public class CompetitionBeanUtill {

    private final Logger logger = LoggerFactory.getLogger(CompetitionBeanUtill.class);

    @Autowired
    private CompetitionMemberRepository memberRepository;

    public Competition toDomain(CompetitionTO competitionTO){
        Competition competition = new Competition();
        try {
            copyProperties(competitionTO, competition);
        }   catch(Exception e) {
            logger.error("Exception coverting to dto: " + e);
        }

        return competition;
    }

    public CompetitionTO toDto(Competition competition){
        CompetitionTO competitionTO = new CompetitionTO();
        try {
            copyProperties(competition, competitionTO);
            competitionTO.setCreatorProfile(memberRepository.getCreatorOfCompetition(competition.getId(), Role.owner).getProfileId());
            //TODO: Colocar o array de membros
        }   catch(Exception e) {
            logger.error("Exception coverting to dto: " + e);
        }
        return competitionTO;
    }
}
