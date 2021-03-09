package br.edu.ifsp.spo.bulls.feed.api.domain;

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
    private int profileId;
//    @ManyToOne
//    @JoinColumn(name = "survey_option_id")
//    private SurveysOptions surveysOptions;
//    @ManyToOne
//    @JoinColumn(name = "survey_id")
//    private Survey survey;
}
