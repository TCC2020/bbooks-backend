package br.edu.ifsp.spo.bulls.users.api.bean;

import br.edu.ifsp.spo.bulls.users.api.domain.ReadingTarget;
import br.edu.ifsp.spo.bulls.users.api.dto.ReadingTargetProgressTO;
import br.edu.ifsp.spo.bulls.users.api.dto.ReadingTargetTO;
import br.edu.ifsp.spo.bulls.users.api.enums.Status;
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
            setProgress(dto);
        }
        catch (Exception e) {
            logger.error("Error while converting Profile to ProfileTO: " +  e);
        }
        return dto;
    }

    public List<ReadingTargetTO> toDtoList(List<ReadingTarget> domains) {
        return domains.parallelStream().map(this::toDto).collect(Collectors.toList());
    }

    private void setProgress(ReadingTargetTO dto) {
        ReadingTargetProgressTO progress = new ReadingTargetProgressTO();
        progress.setDone(dto.getTargets()
                .stream().parallel().filter(userBooksTO -> userBooksTO.getStatus() == Status.LIDO)
                .collect(Collectors.toList()).size());

        progress.setRemaining(dto.getTargets().size() - progress.getDone());
        progress.setProgress((double)progress.getDone()/(double)dto.getTargets().size() * 100D);
        dto.setProgress(progress);
    }

}
