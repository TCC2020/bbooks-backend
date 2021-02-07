package br.edu.ifsp.spo.bulls.common.api.dto;

import lombok.Data;

@Data
public class GetFriendStatusTO {
    private int profileId;
    private int profileFriendId;
}
