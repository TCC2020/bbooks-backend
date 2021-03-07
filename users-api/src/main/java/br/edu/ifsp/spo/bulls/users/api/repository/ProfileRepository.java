package br.edu.ifsp.spo.bulls.users.api.repository;

import java.util.HashSet;

import br.edu.ifsp.spo.bulls.common.api.dto.profile.BaseProfileTO;
import br.edu.ifsp.spo.bulls.users.api.domain.Profile;
import br.edu.ifsp.spo.bulls.users.api.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, Integer>{

	HashSet<Profile> findAll();
	Profile findByUser(User user);
	HashSet<Profile> findAllById(Iterable<Integer> ids);

	@Query("SELECT new br.edu.ifsp.spo.bulls.common.api.dto.profile.BaseProfileTO(p.id, p.name, p.lastName, p.profileImage, p.user.userName) " +
			"FROM Profile p WHERE p.id = :profileId")
//	@Query(value = "SELECT p.id, p.name, p.last_name, p.profile_image " +
//			"FROM profiles p WHERE p.id = :profileId", nativeQuery = true)
	BaseProfileTO findBaseProfileById(@Param("profileId") int profileId);
}
