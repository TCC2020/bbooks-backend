package br.edu.ifsp.spo.bulls.feed.api.repository;

import br.edu.ifsp.spo.bulls.common.api.enums.Role;
import br.edu.ifsp.spo.bulls.feed.api.domain.GroupMemberId;
import br.edu.ifsp.spo.bulls.feed.api.domain.GroupMembers;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface GroupMemberRepository extends CrudRepository<GroupMembers, GroupMemberId>  {

    List<GroupMembers> findByIdUser(UUID userId);

    List<GroupMembers> findByIdGroup(UUID groupId);

    @Query(value =
            "SELECT g.id.user " +
                    "FROM GroupMembers g " +
                    "WHERE g.id.group = :groupId " +
                    "AND g.role = :cargo"
    )
    UUID findGroupOwner(@Param("groupId") UUID groupId,
                        @Param("cargo") Role role);

}
