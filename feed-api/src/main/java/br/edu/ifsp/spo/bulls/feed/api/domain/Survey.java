package br.edu.ifsp.spo.bulls.feed.api.domain;

import lombok.Data;

import javax.persistence.*;
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
    @OneToOne(orphanRemoval=true)
    private Post post;
}
