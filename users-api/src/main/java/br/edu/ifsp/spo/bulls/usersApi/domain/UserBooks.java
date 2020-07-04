package br.edu.ifsp.spo.bulls.usersApi.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data

@Entity
@Table(name = "userBooks")
public class UserBooks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String isbn10;
    private String isbn13;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime addDate;
    @OneToOne
    private Book book;
    private int profileId;

    public enum Status {
        QUERO_LER("Quero ler"),
        LENDO("Lendo"),
        LIDO("Lido");

        private final String text;
        Status(final String text) {
            this.text = text;
        }
        @Override
        public String toString() {
            return text;
        }
    }

    @PrePersist
    public void prePersist() {
        addDate = LocalDateTime.now();
    }

}
