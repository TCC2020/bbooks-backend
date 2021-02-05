package br.edu.ifsp.spo.bulls.feed.api.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.UUID;
import java.io.Serializable;

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
