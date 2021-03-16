package br.edu.ifsp.spo.bulls.feed.api.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
public class BookMonth {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    private GroupRead group;

    private LocalDate monthYear;

    private int bookId;

    private String bookGoogleId;

    @PrePersist
    public void prePersist() {
        monthYear = LocalDate.now();
    }
}
