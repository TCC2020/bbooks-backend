package br.edu.ifsp.spo.bulls.users.api.bean;

import br.edu.ifsp.spo.bulls.users.api.domain.Tag;
import br.edu.ifsp.spo.bulls.users.api.dto.TagTO;
import br.edu.ifsp.spo.bulls.users.api.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TagBeanUtil {
    private Logger logger = LoggerFactory.getLogger(UserBooksBeanUtil.class);

    public TagTO toDto(Tag tag) {
        TagTO dto = new TagTO();
        try{
            BeanUtils.copyProperties(tag, dto);
        }catch(Exception e) {
            logger.error("Error while converting UserBooks to UserBooksTO: " +  e);
        }
        return dto;
    }

    public Tag toDomain(TagTO dto) {
        Tag domain = new Tag();
        try{
            BeanUtils.copyProperties(dto, domain);
        }catch(Exception e) {
            logger.error("Error while converting UserBooks to UserBooksTO: " +  e);
        }
        return domain;
    }

    public List<TagTO> toDtoList(List<Tag> tags) {
        return tags.parallelStream().map(tag -> toDto(tag)).collect(Collectors.toList());
    }

    public List<Tag> toDomainList(List<TagTO> tags) {
        return tags.stream().map(tag -> toDomain(tag)).collect(Collectors.toList());
    }
}
