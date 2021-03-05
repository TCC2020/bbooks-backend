package br.edu.ifsp.spo.bulls.users.api.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "public_profiles_followers")
public class PublicProfileFollowers implements Serializable {
    @Id
    @ApiModelProperty(value = "Identificador")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;
    @Column(name = "public_profiles_id")
    private UUID publicProfileId;
    @Column(name = "followers_id")
    private int followerId;

    public PublicProfileFollowers(UUID publicProfileId, int followerId){
        this.publicProfileId = publicProfileId;
        this.followerId = followerId;
    }
}
