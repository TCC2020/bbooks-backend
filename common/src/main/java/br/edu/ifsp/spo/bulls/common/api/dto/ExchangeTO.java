package br.edu.ifsp.spo.bulls.common.api.dto;

import br.edu.ifsp.spo.bulls.common.api.enums.BookExchangeStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class ExchangeTO {
    private UUID id;
    private BookExchangeStatus status;
    private LocalDateTime exchangeDate;
    private LocalDateTime creationDate;
    private UUID requesterId;
    private UUID receiverId;
    private List<BookAdTO> requesterAds;
    private List<BookAdTO> receiverAds;
}
