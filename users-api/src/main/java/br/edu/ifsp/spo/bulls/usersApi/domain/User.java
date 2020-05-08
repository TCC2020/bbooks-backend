package br.edu.ifsp.spo.bulls.usersApi.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User implements Serializable {
    //Uuid
    @Id
    private String id;
    private String email;
    private String userName;
    private String password;
    private String token;
    private LocalDateTime creationDate;

    @PrePersist
    private void prePersist() {
        creationDate = LocalDateTime.now();
    }
}
