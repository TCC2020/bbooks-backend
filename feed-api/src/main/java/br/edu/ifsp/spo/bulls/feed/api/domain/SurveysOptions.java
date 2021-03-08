package br.edu.ifsp.spo.bulls.feed.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private String googleBookId;
    @ManyToOne
    @JsonIgnoreProperties(value = "options", allowSetters = true)
    @JoinColumn(name = "survey_id",updatable = false)
    private Survey survey;
    private int bootId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="surveyOptions", orphanRemoval = true)
    private List<Vote> votes;
}
