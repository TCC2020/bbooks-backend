package br.edu.ifsp.spo.bulls.usersApi.repository;

import br.edu.ifsp.spo.bulls.usersApi.domain.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String>, JpaSpecificationExecutor {
    Optional<User> findByToken(String token);

    Optional<User> findByEmail(String email);

	boolean existsByEmail(String email);
	boolean existsByUserName(String userName);
	HashSet<User> findAll();
}
