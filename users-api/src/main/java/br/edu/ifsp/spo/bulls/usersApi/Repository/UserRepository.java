package br.edu.ifsp.spo.bulls.usersApi.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifsp.spo.bulls.usersApi.dto.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

	boolean existsByEmail(String email);
}
