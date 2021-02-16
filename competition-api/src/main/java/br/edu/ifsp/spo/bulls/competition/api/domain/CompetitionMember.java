package br.edu.ifsp.spo.bulls.competition.api.domain;

import br.edu.ifsp.spo.bulls.common.api.enums.Role;
import br.edu.ifsp.spo.bulls.common.api.enums.Status;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@ApiModel(value = "Objeto de domínio: Membros da competição")
public class CompetitionMember {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID memberId;

    private String title;

    private String story;

    @Column(nullable = false)
    private int profileId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    private Competition competition;

    @PrePersist
    public void prePersist() {
        status = Status.pending;
    }
}
