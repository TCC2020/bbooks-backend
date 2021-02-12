package br.edu.ifsp.spo.bulls.competition.api.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import java.util.UUID;

@Data
@Entity
@Table(name = "ad_reviews")
public class AdReview {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;
    private String description;
}
