package br.edu.ifsp.spo.bulls.common.api.dto.user;

import lombok.Data;

import java.util.UUID;

@Data
public class UserPublicProfileUpdateTO {
    private UUID id;
    public String name;
    public String Description;
}
