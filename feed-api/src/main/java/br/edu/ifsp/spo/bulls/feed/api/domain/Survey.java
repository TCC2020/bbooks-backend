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
    private LocalDateTime createdAt;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "survey_id")
    private List<SurveysOptions> options;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
