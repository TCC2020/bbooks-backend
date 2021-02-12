package br.edu.ifsp.spo.bulls.competition.api.domain;

import br.edu.ifsp.spo.bulls.common.api.enums.BookExchangeStatus;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "book_exchanges")
@ApiModel(value = "Objeto de domínio: anúncio de livro")
@Data
public class Exchange {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;
    @Enumerated(EnumType.STRING)
    private BookExchangeStatus status;
    private LocalDateTime exchangeDate;
    private LocalDateTime creationDate;
    private UUID requesterId;
    private UUID receiverId;
    @OneToMany
    private List<BookAd> requesterAds;
    @OneToMany
    private List<BookAd> receiverAds;


    @PrePersist
    public void prePersist() {
        creationDate = LocalDateTime.now();
        status = BookExchangeStatus.pending;
    }

}
