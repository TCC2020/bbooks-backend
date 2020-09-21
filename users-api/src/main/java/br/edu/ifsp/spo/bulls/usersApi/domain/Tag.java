package br.edu.ifsp.spo.bulls.usersApi.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Data
@Entity
@ApiModel(value = "Objeto de domínio: TAG")
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToOne
    private Profile profile;
    private String color;
    @ManyToMany(cascade = CascadeType.MERGE)
    private List<UserBooks> books;
}
