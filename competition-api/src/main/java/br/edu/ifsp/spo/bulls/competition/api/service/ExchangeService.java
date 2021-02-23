package br.edu.ifsp.spo.bulls.competition.api.service;

import br.edu.ifsp.spo.bulls.common.api.dto.ExchangeTO;
import br.edu.ifsp.spo.bulls.common.api.dto.UserTO;
import br.edu.ifsp.spo.bulls.common.api.enums.BookExchangeStatus;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceBadRequestException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceUnauthorizedException;
import br.edu.ifsp.spo.bulls.competition.api.bean.ExchangeBeanUtil;
import br.edu.ifsp.spo.bulls.competition.api.domain.Exchange;
import br.edu.ifsp.spo.bulls.competition.api.feign.UserCommonFeign;
import br.edu.ifsp.spo.bulls.competition.api.repository.ExchangeRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ExchangeService {
    @Autowired
    private ExchangeRepository repository;

    @Autowired
    private UserCommonFeign feign;

    @Autowired
    private ExchangeBeanUtil util;

    public ExchangeTO create(String token, ExchangeTO dto) {
        UserTO user = feign.getUserInfo(StringUtils.removeStart(token, "Bearer").trim());
        if(user != null && user.getId().equals(dto.getRequesterId()) && checkExchangeIntegrity(dto))
            return util.toDto(repository.save(util.toDomain(dto)));
        throw new ResourceBadRequestException(CodeException.EXC001.getText(), CodeException.EXC001);
    }

    private boolean checkExchangeIntegrity(ExchangeTO dto) {
        return dto.getRequesterAds().stream().parallel()
                .anyMatch(ad -> ad.getUserId().equals(dto.getRequesterId()))
                && dto.getReceiverAds().stream().parallel()
                .anyMatch(ad -> ad.getUserId().equals(dto.getReceiverId()));
    }

    public ExchangeTO accept(String token, UUID id) {
        UserTO user = feign.getUserInfo(StringUtils.removeStart(token, "Bearer").trim());
        Exchange exchange = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CodeException.EXC002));
        if(user.getId().equals(exchange.getReceiverId())) {
            exchange.setStatus(BookExchangeStatus.accepted);
            exchange.getReceiverAds().stream().parallel().forEach(ad -> ad.setIsOpen(false));
            return util.toDto(repository.save(exchange));
        }
        throw new ResourceUnauthorizedException(CodeException.EXC003);
    }

    public ExchangeTO refuse(String token, UUID id) {
        UserTO user = feign.getUserInfo(StringUtils.removeStart(token, "Bearer").trim());
        Exchange exchange = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CodeException.EXC002));
        if(user.getId().equals(exchange.getReceiverId())) {
            exchange.setStatus(BookExchangeStatus.refused);
            return util.toDto(repository.save(exchange));
        }
        throw new ResourceUnauthorizedException(CodeException.EXC003);
    }

    public void delete(String token, UUID id) {
        UserTO user = feign.getUserInfo(StringUtils.removeStart(token, "Bearer").trim());
        Exchange exchange = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CodeException.EXC002));
        if(user.getId().equals(exchange.getRequesterId()))
            repository.deleteById(id);
        throw new ResourceUnauthorizedException(CodeException.EXC004);
    }

    public List<ExchangeTO> getByUser(UUID id) {
        return util.toDtoList(repository.findByReceiverId(id));
    }

    public List<ExchangeTO> getByUserSent(UUID id) {
        return util.toDtoList(repository.findByRequesterId(id));
    }
}
