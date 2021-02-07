package br.edu.ifsp.spo.bulls.common.api.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class BookAdTO {
    private UUID id;
    private String description;
    private List<String> images;
    private String isbn;
    private UUID userId;
    private String idBookGoogle;
    private int bookId;
    private BookTO bookTO;
    private AdReviewTO review;
}
