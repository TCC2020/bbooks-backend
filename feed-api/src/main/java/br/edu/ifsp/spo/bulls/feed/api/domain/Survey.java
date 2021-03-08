package br.edu.ifsp.spo.bulls.feed.api.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "surveys")
public class Survey {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;
    private String description;
    @OneToMany(cascade = CascadeType.ALL , mappedBy="survey", orphanRemoval = true)
    private List<SurveysOptions> options;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
