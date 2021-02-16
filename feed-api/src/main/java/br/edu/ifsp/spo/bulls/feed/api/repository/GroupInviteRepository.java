package br.edu.ifsp.spo.bulls.feed.api.repository;

import br.edu.ifsp.spo.bulls.feed.api.domain.GroupInvite;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GroupInviteRepository extends CrudRepository<GroupInvite, UUID> {
    List<GroupInvite> findByUserId(UUID id);
}
