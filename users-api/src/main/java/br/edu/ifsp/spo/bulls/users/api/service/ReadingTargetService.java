package br.edu.ifsp.spo.bulls.users.api.service;

import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.users.api.bean.ReadingTargetBeanUtil;
import br.edu.ifsp.spo.bulls.users.api.domain.ReadingTarget;
import br.edu.ifsp.spo.bulls.users.api.dto.ReadingTargetTO;
import br.edu.ifsp.spo.bulls.users.api.repository.ReadingTargetRepository;
import br.edu.ifsp.spo.bulls.users.api.repository.UserBooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ReadingTargetService {
    @Autowired
    private ReadingTargetRepository repository;

    @Autowired
    private ReadingTargetBeanUtil beanUtil;

    @Autowired
    private UserBooksRepository userBooksRepository;

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

    public ReadingTargetTO addTarget(int id, Long userBookId) {
        ReadingTarget target = repository.findByProfileIdAndYear(id, LocalDateTime.now().getYear())
                .orElseThrow(() -> new ResourceNotFoundException(CodeException.RTG001.getText(), CodeException.RTG001));
        target.getTargets().add(userBooksRepository.findById(userBookId)
                .orElseThrow(() -> new ResourceNotFoundException(CodeException.UB001.getText(), CodeException.UB001)));

        return beanUtil.toDto(repository.save(target));
    }
}
