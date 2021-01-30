package br.edu.ifsp.spo.bulls.feed.api.repository;

import br.edu.ifsp.spo.bulls.common.api.enums.Cargo;
import br.edu.ifsp.spo.bulls.feed.api.domain.GroupMemberId;
import br.edu.ifsp.spo.bulls.feed.api.domain.GroupMembers;
import br.edu.ifsp.spo.bulls.feed.api.enums.TypePost;
import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GroupMemberRepository extends CrudRepository<GroupMembers, GroupMemberId>  {

    GroupMembers findByIdGroupAndIdUser(UUID groupId, UUID userId);

    List<GroupMembers> findByIdUser(UUID userId);

    List<GroupMembers> findByIdGroup(UUID groupId);

    @Query(value =
            "SELECT g.id.user " +
                    "FROM GroupMembers g " +
                    "WHERE g.id.group = :groupId " +
                    "AND g.cargo = :cargo"
    )
    UUID findGroupOwner(@Param("groupId") UUID groupId,
                        @Param("cargo") Cargo cargo);

}
