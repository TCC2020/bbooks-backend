package br.edu.ifsp.spo.bulls.users.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Objeto de trânsito: Mudar senha via email ")
public class RequestPassResetTO {
    @ApiModelProperty(value = "Email do usuário")
    public String email;
    @ApiModelProperty(value = "Link para trocar de senha")
    public String url;
}
