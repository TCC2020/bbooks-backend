package br.edu.ifsp.spo.bulls.feed.api.dto;

import br.edu.ifsp.spo.bulls.common.api.dto.UserTO;
import br.edu.ifsp.spo.bulls.common.api.enums.Role;
import br.edu.ifsp.spo.bulls.feed.api.enums.MemberStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class GroupMemberFull {
    @ApiModelProperty(value = "Usuário")
    private UserTO user;

    @ApiModelProperty(value = "Id do groupo")
    private UUID groupId;

    @ApiModelProperty(value = "Data que o membro entrou no grupo")
    private LocalDateTime date;

    @ApiModelProperty(value = "Função do membro")
    private Role role;

    @ApiModelProperty(value = "Status do membro")
    private MemberStatus status;

}
