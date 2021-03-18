package br.edu.ifsp.spo.bulls.common.api.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class BookMonthTO {

    private UUID id;

    private UUID groupId;

    private LocalDate monthYear;

    private int bookId;

    private String bookGoogleId;
}
