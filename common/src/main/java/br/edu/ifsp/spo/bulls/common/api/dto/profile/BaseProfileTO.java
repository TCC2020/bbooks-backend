package br.edu.ifsp.spo.bulls.common.api.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseProfileTO {
    private int id;
    private String name;
    private String lastName;
    private String profileImage;
    private String username;
}
