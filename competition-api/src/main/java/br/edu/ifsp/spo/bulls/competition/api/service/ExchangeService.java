package br.edu.ifsp.spo.bulls.competition.api.service;

import br.edu.ifsp.spo.bulls.common.api.dto.ChatIdTO;
import br.edu.ifsp.spo.bulls.common.api.dto.ExchangeTO;
import br.edu.ifsp.spo.bulls.common.api.dto.UserTO;
import br.edu.ifsp.spo.bulls.common.api.enums.BookExchangeStatus;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceBadRequestException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceUnauthorizedException;
import br.edu.ifsp.spo.bulls.common.api.service.EmailService;
import br.edu.ifsp.spo.bulls.competition.api.bean.ExchangeBeanUtil;
import br.edu.ifsp.spo.bulls.competition.api.domain.Exchange;
import br.edu.ifsp.spo.bulls.common.api.dto.ExchangeTokenTO;
import br.edu.ifsp.spo.bulls.competition.api.feign.UserCommonFeign;
import br.edu.ifsp.spo.bulls.competition.api.repository.ExchangeRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public class ExchangeService {
    @Autowired
    private ExchangeRepository repository;

    @Autowired
    private UserCommonFeign feign;

    @Value("${app.front}")
    private String frontUrl;

    @Autowired
    private ExchangeBeanUtil util;

    @Autowired
    private EmailService email;

    public ExchangeTO getById(UUID id) {
        return util.toDto(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(CodeException.EXC002)));
    }

    public ExchangeTO create(String token, ExchangeTO dto) {
        UserTO user = feign.getUserInfo(StringUtils.removeStart(token, "Bearer").trim());
        if(user != null && user.getId().equals(dto.getRequesterId()) && checkExchangeIntegrity(dto)) {
            ExchangeTO exchangeReturn = util.toDto(repository.save(util.toDomain(dto)));
            // TODO: Variável front
            this.sendEmailStatusExchange(
                    exchangeReturn,
                    exchangeReturn.getReceiver().getEmail(),
                    "Proposta de troca",
                    "Visualizar proposta",
                    frontUrl,
                    "Você recebeu uma proposta para trocar seus livros",
                    "Proposta de troca"
            );
            return exchangeReturn;
        }
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
            exchange.getRequesterAds().stream().parallel().forEach(ad -> ad.setIsOpen(false));
            ExchangeTO exchangeReturn = util. toDto(repository.save(exchange));
            // TODO: Variável front
            this.sendEmailStatusExchange(
                    exchangeReturn,
                    exchangeReturn.getRequester().getEmail(),
                    "Proposta de troca aceita",
                    "Visualizar proposta",
                    frontUrl,
                    exchangeReturn.getReceiver().getUserName() + " aceitou sua proposta para trocar livros.",
                    "Proposta de troca aceita"
            );
            return exchangeReturn;
        }
        throw new ResourceUnauthorizedException(CodeException.EXC003);
    }

    public ExchangeTokenTO generateExchangeToken(String token, UUID id) {
        UserTO user = feign.getUserInfo(StringUtils.removeStart(token, "Bearer").trim());
        Exchange exchange = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CodeException.EXC002));
        if(user.getId().equals(exchange.getReceiverId())) {
            if(!exchange.getStatus().equals(BookExchangeStatus.accepted))
                throw new ResourceConflictException(CodeException.EXC008);
            exchange.setToken(UUID.randomUUID());
            exchange.setExpiryTime(LocalDateTime.now().plusMinutes(5));
            Exchange ex = repository.save(exchange);
            return new ExchangeTokenTO(ex.getToken(), ex.getExpiryTime());
        }
        throw new ResourceUnauthorizedException(CodeException.EXC003);
    }

    public ExchangeTO exchangeByToken(String token, UUID exchangeToken) {
        UserTO user = feign.getUserInfo(StringUtils.removeStart(token, "Bearer").trim());
        Exchange exchange = repository.findByToken(exchangeToken)
                .orElseThrow(() -> new ResourceNotFoundException(CodeException.EXC002));
        if(user.getId().equals(exchange.getRequesterId())) {
            if(exchange.getExpiryTime().isBefore(LocalDateTime.now()))
                throw new ResourceUnauthorizedException(CodeException.EXC006);
            exchange.setStatus(BookExchangeStatus.exchanged);
            return util.toDto(repository.save(exchange));
        }
        throw new ResourceConflictException(CodeException.EXC005);
    }


        public ExchangeTO refuse(String token, UUID id) {
        UserTO user = feign.getUserInfo(StringUtils.removeStart(token, "Bearer").trim());
        Exchange exchange = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CodeException.EXC002));
        if(user.getId().equals(exchange.getReceiverId())) {
            exchange.setStatus(BookExchangeStatus.refused);
            exchange.getReceiverAds().stream().parallel().forEach(ad -> ad.setIsOpen(true));
            exchange.getRequesterAds().stream().parallel().forEach(ad -> ad.setIsOpen(true));
            ExchangeTO exchangeReturn = util. toDto(repository.save(exchange));
            // TODO: Variável front
            this.sendEmailStatusExchange(
                    exchangeReturn,
                    exchangeReturn.getRequester().getEmail(),
                    "Proposta de troca recusada",
                    "Visualizar proposta",
                    frontUrl,
                    exchangeReturn.getReceiver().getUserName() + " recusou sua proposta para trocar livros.",
                    "Proposta de troca recusada"
            );
            return exchangeReturn;
        }
        throw new ResourceUnauthorizedException(CodeException.EXC003);
    }

    public Boolean hasExchangesById(UUID bookAdId) {
        return repository.hasExchanges(bookAdId);
    }

    public ExchangeTO cancel(String token, UUID id) {
        UserTO user = feign.getUserInfo(StringUtils.removeStart(token, "Bearer").trim());
        Exchange exchange = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CodeException.EXC002));
        if(user.getId().equals(exchange.getRequesterId()) || user.getId().equals(exchange.getReceiverId())) {
            exchange.getReceiverAds().stream().parallel().forEach(ad -> ad.setIsOpen(true));
            exchange.getRequesterAds().stream().parallel().forEach(ad -> ad.setIsOpen(true));
            exchange.setStatus(BookExchangeStatus.canceled);
            ExchangeTO exchangeReturn = util. toDto(repository.save(exchange));
            // TODO: Variável front
            this.sendEmailStatusExchange(
                    exchangeReturn,
                    exchangeReturn.getReceiver().getEmail(),
                    "Proposta de troca cancelada",
                    "Visualizar proposta",
                    frontUrl,
                    exchangeReturn.getRequester().getUserName() + " cancelou a proposta para trocar livros enviada anteriormente.",
                    "Proposta de troca cancelada"
            );
            return exchangeReturn;
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

    private void sendEmailStatusExchange(ExchangeTO exchangeTO, String emailSend, String title, String action, String link, String content, String subject) {
        new Thread(()-> {
            email.
                    getInstance()
                    .withTo(emailSend)
                    .withExchangeTo(exchangeTO)
                    .withTitle(title)
                    .withAction(action)
                    .withLink(link)
                    .withContent(content)
                    .withSubject(subject)
                    .send();
        }).start();
    }

    public ExchangeTO putChatId(UUID exchangeId, String chatId) {
        Exchange exchange = repository.findById(exchangeId)
                .orElseThrow(() -> new ResourceNotFoundException(CodeException.EXC002));
        exchange.setChatId(chatId);
        return util.toDto(repository.save(exchange));
    }

    public ChatIdTO getChatId(String token, UUID exchangeId) {
        Exchange exchange = repository.findById(exchangeId)
                .orElseThrow(() -> new ResourceNotFoundException(CodeException.EXC002));
        UserTO user = feign.getUserInfo(StringUtils.removeStart(token, "Bearer").trim());
        if(user.getId().equals(exchange.getRequesterId()) || user.getId().equals(exchange.getReceiverId()))
            return new ChatIdTO(exchange.getChatId());
        throw new ResourceUnauthorizedException(CodeException.EXC007);
    }
}
