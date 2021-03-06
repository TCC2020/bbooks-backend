package br.edu.ifsp.spo.bulls.common.api.dto.user;

import br.edu.ifsp.spo.bulls.common.api.dto.ProfileTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
public class BaseUserTO {
    private UUID id;

    @ApiModelProperty(value = "Nome de usuário")
    @NotBlank(message = "UserName is mandatory")
    private String userName;

    @ApiModelProperty(value = "Usuario dono da estante virtual")
    private ProfileTO profile;
}
