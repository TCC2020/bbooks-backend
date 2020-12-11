package br.edu.ifsp.spo.bulls.users.api.repository;

import java.util.HashSet;

import br.edu.ifsp.spo.bulls.users.api.domain.Profile;
import br.edu.ifsp.spo.bulls.users.api.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, Integer>{

	HashSet<Profile> findAll();
	Profile findByUser(User user);
	HashSet<Profile> findAllById(Iterable<Integer> ids);
}
