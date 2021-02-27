package br.edu.ifsp.spo.bulls.common.api.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class VoteTO {
    private UUID id;
    private UUID userId;
}
