package br.edu.ifsp.spo.bulls.users.api.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "public_profiles")
public class PublicProfile {
    @Id
    @ApiModelProperty(value = "Identificador")
    @GeneratedValue(strategy= GenerationType.AUTO)
    public UUID id;
    public String name;
    public String description;
    @OneToOne
    public User user;
    public LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(name = "public_profiles_followers",
            joinColumns = @JoinColumn(name = "public_profiles_id"),
            inverseJoinColumns = @JoinColumn(name = "followers_id"))
    public List<Profile> followers;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}