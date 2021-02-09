package br.edu.ifsp.spo.bulls.feed.api.service;

import br.edu.ifsp.spo.bulls.feed.api.domain.GroupMembers;
import br.edu.ifsp.spo.bulls.feed.api.domain.GroupRead;
import br.edu.ifsp.spo.bulls.feed.api.feign.UserCommonFeign;
import br.edu.ifsp.spo.bulls.feed.api.repository.GroupMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GroupMemberService {

    @Autowired
    private GroupMemberRepository repository;

    @Autowired
    private UserCommonFeign feign;

    public void putMember(GroupMembers membro){
        feign.getUserById(membro.getId().getUser());
        repository.save(membro);
    }

    public void exitMember(GroupMembers membro){
        repository.deleteById(membro.getId());
    }

    public List<GroupRead> getGroupByUser(UUID id) {
        feign.getUserById(id);
        return repository.findByIdUser(id);
    }

    public List<GroupMembers> getGroupMembers(UUID id) {
        return repository.findByIdGroupRead(id);
    }
}
