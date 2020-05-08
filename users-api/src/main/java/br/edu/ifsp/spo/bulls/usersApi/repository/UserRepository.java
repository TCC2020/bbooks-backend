package br.edu.ifsp.spo.bulls.usersApi.repository;

import br.edu.ifsp.spo.bulls.usersApi.domain.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long>, JpaSpecificationExecutor {
    Optional<User> findByToken(String token);
}
