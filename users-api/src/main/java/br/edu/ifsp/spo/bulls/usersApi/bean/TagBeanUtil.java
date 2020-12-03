package br.edu.ifsp.spo.bulls.usersApi.bean;

import br.edu.ifsp.spo.bulls.usersApi.domain.Tag;
import br.edu.ifsp.spo.bulls.usersApi.dto.TagTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

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

    public List<TagTO> toDtoList(List<Tag> tags) {
        return tags.parallelStream().map(this::toDto).collect(Collectors.toList());
    }
}
