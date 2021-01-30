package br.edu.ifsp.spo.bulls.feed.api.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
public class GroupMemberId  implements Serializable {

    @ApiModelProperty(value = "Id do grupo")
    @Column(name = "group_id")
    private UUID group;

    @ApiModelProperty(value = "Id do usuário")
    @Column(name = "user_id")
    private UUID user;

}
