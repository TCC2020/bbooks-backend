package br.edu.ifsp.spo.bulls.users.api.dto;

import br.edu.ifsp.spo.bulls.common.api.dto.UserTO;
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
