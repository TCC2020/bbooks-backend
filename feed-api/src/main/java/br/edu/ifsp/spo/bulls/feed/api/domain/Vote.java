package br.edu.ifsp.spo.bulls.feed.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "surveys")
@NoArgsConstructor
public class Vote {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;
    @Column(unique = true)
    private int profileId;
    @ManyToOne
    @JsonIgnoreProperties(value = "votes", allowSetters = true)
    @JoinColumn(name = "survey_options_id")
    private SurveysOptions surveyOptions;
    @ManyToOne
    @JsonIgnoreProperties(value = "options", allowSetters = true)
    private Survey survey;
    public Vote(int profileId) {
        this.profileId = profileId;
    }

    public Vote(int profileId, Survey survey) {
        this.profileId = profileId;
        this.survey = survey;
    }

    public Vote(int profileId, Survey survey, SurveysOptions surveyOptions) {
        this.profileId = profileId;
        this.survey = survey;
        this.surveyOptions = surveyOptions;
    }
}
