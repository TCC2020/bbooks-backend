package br.edu.ifsp.spo.bulls.competition.api.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
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
    private BookExchangeStatus status;
    private LocalDateTime exchangeDate;
    private LocalDateTime creationDate;
    @OneToMany
    private List<BookAd> requesterAds;
    @OneToMany
    private List<BookAd> receiverAds;

    public enum BookExchangeStatus {
        pending("pending"),
        refused("refused"),
        accepted("accepted");

        private final String text;
        BookExchangeStatus(final String text) {
            this.text = text;
        }

        public static BookExchangeStatus getByString(String value) {
            for (BookExchangeStatus exchange : BookExchangeStatus.values()) {
                if (exchange.text.equalsIgnoreCase(value))
                    return exchange;
            }
            return null;
        }
    }

    @PrePersist
    public void prePersist() {
        creationDate = LocalDateTime.now();
        status = BookExchangeStatus.pending;
    }

}
