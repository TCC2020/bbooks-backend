package br.edu.ifsp.spo.bulls.competition.api.bean;

import br.edu.ifsp.spo.bulls.common.api.dto.ExchangeTO;
import br.edu.ifsp.spo.bulls.competition.api.controller.ExchangeController;
import br.edu.ifsp.spo.bulls.competition.api.domain.Exchange;
import br.edu.ifsp.spo.bulls.competition.api.feign.UserCommonFeign;
import br.edu.ifsp.spo.bulls.competition.api.util.BookAdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.beans.BeanUtils.copyProperties;

@Component
public class ExchangeBeanUtil {
    private final Logger logger = LoggerFactory.getLogger(ExchangeController.class);

    @Autowired
    private BookAdUtil bookAdUtil;

    @Autowired
    private UserCommonFeign feign;

    public ExchangeTO toDto(Exchange domain) {
        ExchangeTO dto = new ExchangeTO();
        try {
            copyProperties(domain, dto);
            dto.setRequesterAds(bookAdUtil.toDtoList(domain.getRequesterAds()));
            dto.setReceiverAds(bookAdUtil.toDtoList(domain.getReceiverAds()));
            dto.setRequester(feign.getUserById(domain.getRequesterId()));
            dto.setReceiver(feign.getUserById(domain.getReceiverId()));
        }   catch(Exception e) {
            logger.error("Exception coverting to dto: " + e);
        }
        return dto;
    }

    public Exchange toDomain(ExchangeTO dto) {
        Exchange domain = new Exchange();
        try {
            copyProperties(dto, domain);
            domain.setRequesterAds(bookAdUtil.toDomainList(dto.getRequesterAds()));
            domain.setReceiverAds(bookAdUtil.toDomainList(dto.getReceiverAds()));
        }   catch(Exception e) {
                logger.error("Exception coverting to domain: " + e);
        }
        return domain;
    }

    public List<ExchangeTO> toDtoList(List<Exchange> list) {
        return list.stream().parallel().map(this::toDto).collect(Collectors.toList());
    }
}
