package br.edu.ifsp.spo.bulls.competition.api.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@ApiModel(value = "Objeto de domínio: Competição")
public class Competition {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String rules;

    @Column(nullable = false)
    private LocalDateTime finalDate;

    @Column(nullable = false)
    private LocalDateTime subscriptionDate;

    @Column(nullable = false)
    private LocalDateTime subscriptionFinalDate;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    private UUID winnerProfile;

    @PrePersist
    public void prePersist() {
        creationDate = LocalDateTime.now();
    }


}
