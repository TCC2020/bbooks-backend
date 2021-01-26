package br.edu.ifsp.spo.bulls.users.api.repository;

import br.edu.ifsp.spo.bulls.common.api.domain.Profile;
import br.edu.ifsp.spo.bulls.users.api.domain.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TagRepository extends CrudRepository <Tag, Long> {

    List<Tag> findByProfile(Profile profile);

    List<Tag> findAll();

}
