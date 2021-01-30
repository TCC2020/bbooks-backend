package br.edu.ifsp.spo.bulls.feed.api.service;

import br.edu.ifsp.spo.bulls.feed.api.domain.Group;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GroupService {

    public Group save(Group group) {
        //TODO: Chamar o repository aqui
        return new Group();
    }

    public Group update(Group group, UUID groupId) {
        //TODO: Chamar o repository aqui
        return new Group();
    }

    public Group getById(UUID groupId) {
        //TODO: Chamar o repository aqui
        return new Group();
    }

    public void delete(UUID groupId) {
        //TODO: Chamar o repository aqui
    }
}
