package br.edu.ifsp.spo.bulls.common.api.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class GroupInviteTO {
    private UUID id;
    private UUID groupId;
    private Object group;
    private UUID userId;
    private UUID inviter;
}
