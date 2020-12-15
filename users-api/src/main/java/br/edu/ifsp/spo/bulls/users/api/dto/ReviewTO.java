package br.edu.ifsp.spo.bulls.users.api.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import javax.persistence.Entity;
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
}
