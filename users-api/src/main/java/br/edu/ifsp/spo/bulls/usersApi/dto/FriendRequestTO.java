package br.edu.ifsp.spo.bulls.usersApi.dto;

import br.edu.ifsp.spo.bulls.usersApi.domain.Friendship;

import java.time.LocalDateTime;

public class FriendRequestTO {
    private Long id;
    private Friendship.FriendshipStatus status;
    private LocalDateTime addDate;
    private ProfileTO profileTO;

}
