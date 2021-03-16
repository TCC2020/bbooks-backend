package br.edu.ifsp.spo.bulls.common.api.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class BookMonthTO {

    private UUID id;

    private UUID groupId;

    private String month;

    private int bookId;

    private String bookGoogleId;
}
