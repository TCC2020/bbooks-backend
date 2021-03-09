package br.edu.ifsp.spo.bulls.feed.api.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "surveys_options")
public class SurveysOptions {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;
    private String option;
    @ManyToOne
    private Survey survey;

    @OneToMany(orphanRemoval=true)
    private List<Vote> votes;
}
