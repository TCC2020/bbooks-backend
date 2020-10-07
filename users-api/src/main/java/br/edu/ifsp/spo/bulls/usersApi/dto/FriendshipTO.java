package br.edu.ifsp.spo.bulls.usersApi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class FriendshipTO {
    private int profileId;
    private HashSet<UserTO> friends;
}
