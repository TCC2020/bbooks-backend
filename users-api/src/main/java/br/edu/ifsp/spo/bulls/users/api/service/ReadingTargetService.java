package br.edu.ifsp.spo.bulls.users.api.service;

import br.edu.ifsp.spo.bulls.users.api.bean.ReadingTargetBeanUtil;
import br.edu.ifsp.spo.bulls.users.api.dto.ReadingTargetTO;
import br.edu.ifsp.spo.bulls.users.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.users.api.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.users.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.users.api.repository.ReadingTargetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReadingTargetService {
    @Autowired
    private ReadingTargetRepository repository;

    @Autowired
    private ReadingTargetBeanUtil beanUtil;

    public ReadingTargetTO save(ReadingTargetTO dto) {
        if(repository.findByProfileIdAndYear(dto.getProfileId(), dto.getYear()).isPresent())
            throw new ResourceConflictException(CodeException.RTG002.getText(), CodeException.RTG002);
        return beanUtil.toDto(repository.save(beanUtil.toDomain(dto)));
    }

    public ReadingTargetTO getById(UUID id) {
        return beanUtil.toDto(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CodeException.RTG001.getText(), CodeException.RTG001)));
    }

    public List<ReadingTargetTO> getAllByProfileId(Long profileId) {
        return beanUtil.toDtoList(repository.findByProfileId(profileId));
    }

    public ReadingTargetTO update(ReadingTargetTO dto) {
        if (!repository.findById(dto.getId()).isPresent())
            throw new ResourceNotFoundException(CodeException.RTG001.getText(), CodeException.RTG001);
        return beanUtil.toDto(repository.save(beanUtil.toDomain(dto)));
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
