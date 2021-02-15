package br.edu.ifsp.spo.bulls.competition.api.bean;

import br.edu.ifsp.spo.bulls.competition.api.domain.Competition;
import br.edu.ifsp.spo.bulls.competition.api.dto.CompetitionTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CompetitionBeanUtill {

    private final Logger logger = LoggerFactory.getLogger(CompetitionBeanUtill.class);

    public Competition toDomain(CompetitionTO competitionTO){
        Competition competition = new Competition();
        //  TODO: implementar
        return competition;
    }

    public CompetitionTO toDto(Competition competition){
        CompetitionTO competitionTO = new CompetitionTO();
        //  TODO: implementar
        return competitionTO;
    }
}
