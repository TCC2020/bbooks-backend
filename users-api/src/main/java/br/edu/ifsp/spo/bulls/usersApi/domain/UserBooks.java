package br.edu.ifsp.spo.bulls.usersApi.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Data
@Entity
@ApiModel(value = "Objeto de domínio: Livros do usuario")
@Table(name = "userBooks")
public class UserBooks {
    @Id
    @ApiModelProperty(value = "Identificador")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "Número identificador do livro da Api do google")
    private String idBook;

    @ApiModelProperty(value = "Qauntidade de páginas do livro da Api do google")
    private int page;

    @ApiModelProperty(value = "Status do livro")
    @Enumerated(EnumType.STRING)
    private Status status;

    @ApiModelProperty(value = "Data que o livro foi adicionado na estante")
    private LocalDateTime addDate;

    @ApiModelProperty(value = "Livro")
    @ManyToOne
    private Book book;

    @ApiModelProperty(value = "Usuario dono da estante virtual")
    @OneToOne
    private Profile profile;

    @OneToMany
    private List<Tracking> trackings = new ArrayList<>();

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
