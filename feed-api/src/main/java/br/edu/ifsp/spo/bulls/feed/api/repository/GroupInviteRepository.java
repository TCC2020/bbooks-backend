package br.edu.ifsp.spo.bulls.feed.api.repository;

import br.edu.ifsp.spo.bulls.feed.api.domain.GroupInvite;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GroupInviteRepository extends CrudRepository<GroupInvite, UUID> {
    List<GroupInvite> findByUserId(UUID id);

    @Query(value = "SELECT * FROM group_invites WHERE user_id = :userId AND group_id = :groupId ;", nativeQuery = true)
    GroupInvite findByUserIdAndGroup(@Param("userId") UUID userId, @Param("groupId") UUID groupId);
}
