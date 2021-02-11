package br.edu.ifsp.spo.bulls.common.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdReviewTO {
    private UUID id;
    private String description;

}
