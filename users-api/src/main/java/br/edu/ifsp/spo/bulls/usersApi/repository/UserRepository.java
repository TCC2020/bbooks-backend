package br.edu.ifsp.spo.bulls.usersApi.repository;

import br.edu.ifsp.spo.bulls.usersApi.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
    Optional<User> findByToken(String token);

    Optional<User> findByEmail(String email);

    Optional<User> findByIdSocial(String idSocial);

	boolean existsByEmail(String email);
	boolean existsByUserName(String userName);
	HashSet<User> findAll();

	User findByUserName(String username);
}
