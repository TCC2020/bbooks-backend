package br.edu.ifsp.spo.bulls.common.api.dto;

import br.edu.ifsp.spo.bulls.common.api.enums.Role;
import br.edu.ifsp.spo.bulls.common.api.enums.Status;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Data
public class CompetitionMemberTO {

    private UUID memberId;

    private String title;

    private String story;

    private ProfileTO profile;

    private Role role;

    private Status status;

    private CompetitionTO competitionTO;

    private float meanVote;
}
