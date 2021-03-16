package br.edu.ifsp.spo.bulls.feed.api.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "surveys")
public class Survey {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;
    private boolean bookMonth;
    private boolean open;

    @PrePersist
    public void prePersist() {
        open = true;
    }
}
