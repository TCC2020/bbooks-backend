package br.edu.ifsp.spo.bulls.users.api.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@ApiModel(value = "Objeto de transito: Resenhas ")
public class ReviewTO {
    public UUID id;
    public String title;
    public String body;
    public int bookId;
    public String idGoogleBook;
    public int profileId;
    public LocalDateTime creationDate;

    public ReviewTO(UUID id, String title, String body, String idGoogleBook, int profileId, LocalDateTime creationDate) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.idGoogleBook = idGoogleBook;
        this.profileId = profileId;
        this.creationDate = creationDate;
    }

    public ReviewTO(UUID id, String title, String body, int bookId, String idGoogleBook, int profileId, LocalDateTime creationDate) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.bookId = bookId;
        this.idGoogleBook = idGoogleBook;
        this.profileId = profileId;
        this.creationDate = creationDate;
    }

    public ReviewTO() {
    }
}
