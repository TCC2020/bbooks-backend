package br.edu.ifsp.spo.bulls.competition.api.bean;

import br.edu.ifsp.spo.bulls.common.api.dto.CompetitionVoteReturnTO;
import br.edu.ifsp.spo.bulls.common.api.dto.CompetitionVotesSaveTO;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.competition.api.domain.CompetitionVotes;
import br.edu.ifsp.spo.bulls.competition.api.repository.CompetitionMemberRepository;
import br.edu.ifsp.spo.bulls.competition.api.service.CompetitionMemberService;
import br.edu.ifsp.spo.bulls.competition.api.service.CompetitionVoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.beans.BeanUtils.copyProperties;

@Component
public class CompetitionVotesBeanUtil {

    private final Logger logger = LoggerFactory.getLogger(CompetitionVotesBeanUtil.class);

    @Autowired
    private CompetitionMemberRepository memberRepository;

    @Autowired
    private CompetitionMemberService memberService;

    public CompetitionVotes toDomain(CompetitionVotesSaveTO save){
        CompetitionVotes competitionVotes = new CompetitionVotes();

        try {
            copyProperties(save, competitionVotes);
            competitionVotes.setMember(memberRepository.findById(save.getMemberId())
                    .orElseThrow( () -> new ResourceNotFoundException(CodeException.CM001.getText(), CodeException.CM001)));
        }   catch(Exception e) {
            logger.error("Exception coverting to dto: " + e);
        }

        return competitionVotes;
    }

    public CompetitionVoteReturnTO toReturnTO(CompetitionVotes votes){
        CompetitionVoteReturnTO returnTO = new CompetitionVoteReturnTO();

        try {
            copyProperties(votes, returnTO);
            returnTO.setMember(memberService.getById(votes.getMember().getMemberId()));
        }   catch(Exception e) {
            logger.error("Exception coverting to dto: " + e);
        }

        return  returnTO;
    }

    public List<CompetitionVoteReturnTO> toReturnTO(List<CompetitionVotes> votes){
        return  votes.stream().parallel().map(this::toReturnTO).collect(Collectors.toList());
    }
}
