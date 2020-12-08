package br.edu.ifsp.spo.bulls.users.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Objeto de trânsito: Mudar senha por token")
public class ResetPassTO {
    @ApiModelProperty(value = "Token")
    private String token;
    @ApiModelProperty(value = "Senha")
    private String password;
}
