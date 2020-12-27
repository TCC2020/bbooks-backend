package br.edu.ifsp.spo.bulls.users.api.bean;

import br.edu.ifsp.spo.bulls.users.api.domain.ReadingTarget;
import br.edu.ifsp.spo.bulls.users.api.dto.ReadingTargetTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReadingTargetBeanUtil {
    @Autowired
    private UserBooksBeanUtil userBooksBeanUtil;

    private Logger logger = LoggerFactory.getLogger(ReadingTargetBeanUtil.class);

    public ReadingTarget toDomain(ReadingTargetTO dto) {
        ReadingTarget domain = new ReadingTarget();
        try {
            BeanUtils.copyProperties(dto, domain);
            domain.setTargets(userBooksBeanUtil.toDomainList(dto.getTargets()));
        }
        catch (Exception e) {
            logger.error("Error while converting Profile to ProfileTO: " +  e);
        }
        return domain;
    }

    public ReadingTargetTO toDto(ReadingTarget domain) {
        ReadingTargetTO dto = new ReadingTargetTO();
        try {
            BeanUtils.copyProperties(domain, dto);
            dto.setTargets(userBooksBeanUtil.toDtoList(domain.getTargets()));
        }
        catch (Exception e) {
            logger.error("Error while converting Profile to ProfileTO: " +  e);
        }
        return dto;
    }

    public List<ReadingTargetTO> toDtoList(List<ReadingTarget> domains) {
        return domains.parallelStream().map(this::toDto).collect(Collectors.toList());
    }

}
