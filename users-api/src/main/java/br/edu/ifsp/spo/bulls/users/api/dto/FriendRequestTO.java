package br.edu.ifsp.spo.bulls.users.api.dto;

import br.edu.ifsp.spo.bulls.common.api.domain.Friendship;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequestTO {
    private Long id;
    private Friendship.FriendshipStatus status;
    private LocalDateTime addDate;
    private ProfileTO profileTO;
}
