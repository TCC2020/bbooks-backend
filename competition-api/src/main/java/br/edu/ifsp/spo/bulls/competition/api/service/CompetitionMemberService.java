package br.edu.ifsp.spo.bulls.competition.api.service;

import br.edu.ifsp.spo.bulls.common.api.dto.ProfileTO;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceUnauthorizedException;
import br.edu.ifsp.spo.bulls.competition.api.bean.CompetitionMemberBeanUtil;
import br.edu.ifsp.spo.bulls.competition.api.domain.CompetitionMember;
import br.edu.ifsp.spo.bulls.competition.api.dto.CompetitionMemberTO;
import br.edu.ifsp.spo.bulls.competition.api.dto.CompetitionTO;
import br.edu.ifsp.spo.bulls.competition.api.feign.UserCommonFeign;
import br.edu.ifsp.spo.bulls.competition.api.repository.CompetitionMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class CompetitionMemberService {

    @Autowired
    private CompetitionMemberRepository repository;

    @Autowired
    private CompetitionMemberBeanUtil beanUtil;

    @Autowired
    private UserCommonFeign feign;

    public Page<CompetitionMemberTO> getMembers(UUID id, int page, int size) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "id");

        //TODO: implementar
        return null;
    }

    public Page<CompetitionTO> getCompetitionsByProfile(UUID id, int page, int size) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "id");
        //TODO: implementar
        return null;
    }

    public void exitMember(String token, UUID id) {
        CompetitionMember competitionMember = repository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException(CodeException.CM001.getText(), CodeException.CM001));

        ProfileTO profileTO = feign.getProfileByToken(token);

        if(profileTO.getId() != competitionMember.getProfileId() ){
            throw new ResourceUnauthorizedException(CodeException.CM002.getText(), CodeException.CM002);
        }
        repository.deleteById(id);
    }

    public CompetitionMemberTO getById(UUID id) {
        return beanUtil.toDto(repository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException(CodeException.CM001.getText(), CodeException.CM001)));
    }

    public CompetitionMemberTO updateMember(String token, CompetitionMemberTO memberTO, UUID id) {
        CompetitionMember member = beanUtil.toDomain(memberTO);
        ProfileTO profileTO = feign.getProfileByToken(token);

        if(profileTO.getId() != memberTO.getProfileId() ){
            return updateByAdmin(member, id);
        }else{
            return updateByProfile(member, id);
        }
    }

    private CompetitionMemberTO updateByProfile(CompetitionMember member, UUID id) {
        return beanUtil.toDto(repository.findById(id).map( member1 -> {
            member1.setStory(member.getStory());
            member1.setTitle(member.getTitle());
            return repository.save(member1);
        }).orElseThrow( () -> new ResourceNotFoundException(CodeException.CM001.getText(), CodeException.CM001)));
    }

    private CompetitionMemberTO updateByAdmin(CompetitionMember member, UUID id) {
        return beanUtil.toDto(repository.findById(id).map( member1 -> {
            member1.setStatus(member.getStatus());
            return repository.save(member1);
        }).orElseThrow( () -> new ResourceNotFoundException(CodeException.CM001.getText(), CodeException.CM001)));
    }

    public CompetitionMemberTO saveMember(String token,CompetitionMemberTO memberTO) {
        // TODO: implementar requisições

        ProfileTO profileTO = feign.getProfileByToken(token);

        if(profileTO.getId() != memberTO.getProfileId() ){
            throw new ResourceUnauthorizedException(CodeException.CM002.getText(), CodeException.CM002);
        }

        return beanUtil.toDto(repository.save(beanUtil.toDomain(memberTO)));
    }
}
