package br.edu.ifsp.spo.bulls.usersApi.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Data

@Entity
@Table(name = "userBooks")
public class UserBooks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String isbn10;
    private String isbn13;

    private String idBook;

    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime addDate;
    @OneToOne
    private Book book;
    private int profileId;

    public enum Status {
        QUERO_LER("Quero ler"),
        LENDO("Lendo"),
        LIDO("Lido"),
        EMPRESTADO("Emprestado"),
        RELENDO("Relendo"),
        INTERROMPIDO("INTERROMPIDO");

        private final String text;
        Status(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }

        public static Status getByString(String value) {
            for(Status status : Status.values()){
                if(status.text.equalsIgnoreCase(value))
                    return status;
            }
            return null;
        }
    }

    @PrePersist
    public void prePersist() {
        addDate = LocalDateTime.now();
    }

}
