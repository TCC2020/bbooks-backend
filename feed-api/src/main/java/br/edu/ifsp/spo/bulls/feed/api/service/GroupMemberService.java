package br.edu.ifsp.spo.bulls.feed.api.service;

import br.edu.ifsp.spo.bulls.feed.api.domain.GroupMembers;
import br.edu.ifsp.spo.bulls.feed.api.repository.GroupMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupMemberService {

    @Autowired
    private GroupMemberRepository repository;

    public void putMember(GroupMembers membro){
        repository.save(membro);
    }

    public void exitMember(GroupMembers membro){
        repository.deleteById(membro.getId());
    }
}
