package br.edu.ifsp.spo.bulls.users.api.repository;

import br.edu.ifsp.spo.bulls.users.api.domain.Friendship;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipRepository extends CrudRepository<Friendship, Long> {
    @Query(value = "SELECT * FROM friendships WHERE PROFILE1 = :id OR PROFILE2 = :id AND STATUS = 'added'", nativeQuery = true)
    HashSet<Friendship> findFriendships(@Param("id") int id);

    @Query(value = "SELECT * FROM friendships WHERE PROFILE1 = :id AND PROFILE2 = :id2 OR PROFILE1 = :id2 AND PROFILE2 = :id", nativeQuery = true)
    Optional<Friendship> hasFriendship(@Param("id") int id, @Param("id2") int id2);

    @Query(value = "SELECT * FROM friendships WHERE PROFILE1 = :id OR PROFILE2 = :id AND STATUS = 'pending'", nativeQuery = true)
    List<Friendship> findPendingRequests(@Param("id") int id);
}
