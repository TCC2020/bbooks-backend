package br.edu.ifsp.spo.bulls.feed.api.repository;

import br.edu.ifsp.spo.bulls.common.api.enums.Role;
import br.edu.ifsp.spo.bulls.feed.api.domain.GroupMemberId;
import br.edu.ifsp.spo.bulls.feed.api.domain.GroupMembers;
import br.edu.ifsp.spo.bulls.feed.api.domain.GroupRead;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface GroupMemberRepository extends CrudRepository<GroupMembers, GroupMemberId>  {

    @Query(value =
            "SELECT m.id.groupRead " +
                    "FROM GroupMembers m " +
                    "INNER JOIN m.id.groupRead " +
                    "WHERE m.id.user = :userId"
    )
    List<GroupRead> findByIdUser(@Param("userId") UUID userId);

    List<GroupMembers> findByIdGroupRead(GroupRead groupRead);

    @Query(value =
            "SELECT g.id.user " +
                    "FROM GroupMembers g " +
                    "WHERE g.id.groupRead.id = :groupId " +
                    "AND g.role = :cargo"
    )
    UUID findGroupOwner(@Param("groupId") UUID groupId,
                        @Param("cargo") Role role);

}
