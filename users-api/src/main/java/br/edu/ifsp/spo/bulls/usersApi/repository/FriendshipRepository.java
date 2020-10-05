package br.edu.ifsp.spo.bulls.usersApi.repository;

import br.edu.ifsp.spo.bulls.usersApi.domain.Friendship;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;

@Repository
public interface FriendshipRepository extends CrudRepository<Friendship, Long> {
    @Query(value = "SELECT * FROM friendships WHERE PROFILE1 = :id OR PROFILE2 = :id AND STATUS = 'added'", nativeQuery = true)
    HashSet<Friendship> findFriendships(@Param("id") int id);

    @Query(value = "SELECT * FROM friendships WHERE PROFILE1 = :id OR PROFILE2 = :id AND STATUS = 'pending'", nativeQuery = true)
    List<Friendship> findPendingRequests(@Param("id") int id);
}
