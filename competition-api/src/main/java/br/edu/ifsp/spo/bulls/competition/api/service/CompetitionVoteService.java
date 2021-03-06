package br.edu.ifsp.spo.bulls.competition.api.service;

import br.edu.ifsp.spo.bulls.common.api.dto.CompetitionVoteReturnTO;
import br.edu.ifsp.spo.bulls.common.api.dto.CompetitionVotesSaveTO;
import br.edu.ifsp.spo.bulls.common.api.dto.ProfileTO;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.enums.Status;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceForbiddenException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceUnauthorizedException;
import br.edu.ifsp.spo.bulls.competition.api.bean.CompetitionVotesBeanUtil;
import br.edu.ifsp.spo.bulls.competition.api.domain.CompetitionMember;
import br.edu.ifsp.spo.bulls.competition.api.domain.CompetitionVotes;
import br.edu.ifsp.spo.bulls.competition.api.feign.UserCommonFeign;
import br.edu.ifsp.spo.bulls.competition.api.repository.CompetitionMemberRepository;
import br.edu.ifsp.spo.bulls.competition.api.repository.CompetitionVotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CompetitionVoteService {
    @Autowired
    private CompetitionVotesRepository repository;

    @Autowired
    private CompetitionVotesBeanUtil beanUtil;

    @Autowired
    private CompetitionMemberRepository memberRepository;

    @Autowired
    private UserCommonFeign feign;

    public void deleteVote(UUID voteId, String tokenValue) {
        CompetitionVotes competitionVotes = repository.findById(voteId)
                .orElseThrow( () -> new ResourceNotFoundException(CodeException.CV004.getText()));

        verifyProfile(competitionVotes.getProfileId(), tokenValue);

        repository.delete(competitionVotes);
    }

    public List<CompetitionVoteReturnTO> getVotesByMember(UUID memberId) {
        CompetitionMember member = memberRepository.findById(memberId).orElseThrow( () -> new ResourceNotFoundException(CodeException.CM001.getText()));

        if(member.getStatus() == Status.accept){
            return beanUtil.toReturnTO(repository.findByMember(member));

        }else{
            throw new ResourceForbiddenException(CodeException.CV002.getText());
        }
    }

    public CompetitionVoteReturnTO vote(CompetitionVotesSaveTO voteSaveTO, String tokenValue) {
        verifyProfile(voteSaveTO.getProfileId(), tokenValue);

        CompetitionMember member = memberRepository.findById(voteSaveTO.getMemberId()).orElseThrow( () -> new ResourceNotFoundException(CodeException.CM001.getText()));

        verifyDate(member);
        verifyIfProfilePreviouslyVoted(voteSaveTO, member);

        if(member.getStatus() == Status.accept){
            CompetitionVotes vote = beanUtil.toDomain(voteSaveTO);
            return beanUtil.toReturnTO(vote);

        }else{
            throw new ResourceForbiddenException(CodeException.CV002.getText());
        }
    }

    private void verifyDate(CompetitionMember member) {
        if(LocalDateTime.now().isBefore(member.getCompetition().getSubscriptionFinalDate())){
            throw new ResourceConflictException(CodeException.DA006.getText());
        }
    }

    private void verifyIfProfilePreviouslyVoted(CompetitionVotesSaveTO voteSaveTO, CompetitionMember member) {
        if(repository.existsByProfileIdAndMember(voteSaveTO.getProfileId(), member)){
            throw new ResourceConflictException(CodeException.CV003.getText());
        }
    }

    private void verifyProfile(int profielId, String tokenValue) {
        ProfileTO voter = feign.getProfile(profielId);
        ProfileTO requester = feign.getProfileByToken(tokenValue);
        if(voter.getId() != requester.getId()){
            throw new ResourceUnauthorizedException(CodeException.CV001.getText());
        }
    }

    public CompetitionVoteReturnTO updateVote(CompetitionVotesSaveTO vote, UUID voteId, String tokenValue) {
        verifyProfile(vote.getProfileId(), tokenValue);

        return beanUtil.toReturnTO(repository.findById(voteId).map(vote1 -> {
            vote1.setValue(vote.getValue());
            return repository.save(vote1);
        }).orElseThrow( () -> new ResourceNotFoundException(CodeException.CV004.getText(), CodeException.CV004)));
    }

    public float mean(UUID competitionMemberId){
        CompetitionMember member = memberRepository.findById(competitionMemberId).orElseThrow( () -> new ResourceNotFoundException(CodeException.CM001.getText()));

        List<CompetitionVotes> votes = repository.findByMember(member);

        float total = 0.0F;

        for(int x = 0;  x < votes.size(); x ++){
            total = total + votes.get(x).getValue();
        }

        return total / votes.size();
    }
}
