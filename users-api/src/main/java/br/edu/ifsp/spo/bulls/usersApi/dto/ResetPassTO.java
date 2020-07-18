package br.edu.ifsp.spo.bulls.usersApi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPassTO {
    private String token;
    private String password;
}
