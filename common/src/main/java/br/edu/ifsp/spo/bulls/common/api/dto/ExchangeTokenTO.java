package br.edu.ifsp.spo.bulls.common.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ExchangeTokenTO {
    private UUID token;
    private LocalDateTime expiryTime;
}
