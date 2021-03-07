package br.edu.ifsp.spo.bulls.common.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompetitionTO {
        private UUID id;

        private String title;

        private String rules;

        private LocalDate finalDate;

        private LocalDateTime subscriptionDate;

        private LocalDateTime subscriptionFinalDate;

        private LocalDateTime creationDate;

        private CompetitionMemberSaveTO winnerProfile;

        private int creatorProfile;
}
