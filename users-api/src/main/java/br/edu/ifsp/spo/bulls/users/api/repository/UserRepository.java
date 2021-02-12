package br.edu.ifsp.spo.bulls.users.api.repository;

import br.edu.ifsp.spo.bulls.users.api.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
    Optional<User> findByToken(String token);

    Optional<User> findByEmail(String email);

	boolean existsByUserName(String userName);

	HashSet<User> findAll();

	User findByUserName(String username);

	@Query(value = "SELECT u.* FROM users u LEFT JOIN profiles p ON u.id = p.user_id WHERE p.id = :profileId ;", nativeQuery = true)
	Optional<User> findByProfileId(@Param("profileId") int profileId);
}
