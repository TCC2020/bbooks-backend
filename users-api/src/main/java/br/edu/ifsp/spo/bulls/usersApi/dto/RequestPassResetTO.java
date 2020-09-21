package br.edu.ifsp.spo.bulls.usersApi.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Objeto de trânsito: Mudar senha via email ")
public class RequestPassResetTO {
    String email;
    String url;
}
