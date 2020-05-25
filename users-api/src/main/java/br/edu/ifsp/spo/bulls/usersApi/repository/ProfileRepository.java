package br.edu.ifsp.spo.bulls.usersApi.repository;

import java.util.HashSet;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import br.edu.ifsp.spo.bulls.usersApi.domain.Profile;
import br.edu.ifsp.spo.bulls.usersApi.domain.User;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, Integer>{

	boolean existsByUser(User user);
	HashSet<Profile> findAll();
	Profile getByUser(User user);
}
