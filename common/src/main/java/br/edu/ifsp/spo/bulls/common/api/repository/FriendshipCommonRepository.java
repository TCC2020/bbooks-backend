package br.edu.ifsp.spo.bulls.common.api.repository;

import br.edu.ifsp.spo.bulls.common.api.domain.Friendship;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendshipCommonRepository extends CrudRepository<Friendship, Long> {
    @Query(value = "SELECT * FROM friendships WHERE PROFILE1 = :id AND PROFILE2 = :id2 OR PROFILE1 = :id2 AND PROFILE2 = :id", nativeQuery = true)
    Optional<Friendship> hasFriendship(@Param("id") int id, @Param("id2") int id2);
}
