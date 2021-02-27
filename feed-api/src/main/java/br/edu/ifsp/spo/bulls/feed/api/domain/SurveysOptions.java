package br.edu.ifsp.spo.bulls.feed.api.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
    @OneToMany(orphanRemoval=true)
    private List<Vote> votes;
}
