package br.edu.ifsp.spo.bulls.feed.api.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "surveys_vote")
public class Vote {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;
    private int profileId;
}
