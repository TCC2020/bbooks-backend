package br.edu.ifsp.spo.bulls.competition.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompetitionTO {
        private UUID id;

        private String title;

        private String rules;

        private LocalDateTime finalDate;

        private LocalDateTime subscriptionDate;

        private LocalDateTime subscriptionFinalDate;

        private LocalDateTime creationDate;

        private UUID winnerProfile;

        private int creatorProfile;
}
