package br.edu.ifsp.spo.bulls.usersApi.repository;

import br.edu.ifsp.spo.bulls.usersApi.domain.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashSet;

@Repository
public interface TagRepository extends CrudRepository <Tag, Long> {

    LinkedHashSet<Tag> findByProfileId(int profileId);
}
