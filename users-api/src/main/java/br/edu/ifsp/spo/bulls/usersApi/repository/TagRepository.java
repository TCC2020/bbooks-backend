package br.edu.ifsp.spo.bulls.usersApi.repository;

import br.edu.ifsp.spo.bulls.usersApi.domain.Profile;
import br.edu.ifsp.spo.bulls.usersApi.domain.Tag;
import br.edu.ifsp.spo.bulls.usersApi.domain.UserBooks;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Repository
public interface TagRepository extends CrudRepository <Tag, Long> {

    List<Tag> findByProfile(Profile profile);

    List<Tag> findAll();
}
