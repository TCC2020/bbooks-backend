package br.edu.ifsp.spo.bulls.feed.api.repository;

import br.edu.ifsp.spo.bulls.feed.api.domain.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GroupRepository extends CrudRepository<Group, UUID> {
    boolean existsByName(String name);
}
