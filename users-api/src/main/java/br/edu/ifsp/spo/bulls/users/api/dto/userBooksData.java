package br.edu.ifsp.spo.bulls.users.api.dto;

import br.edu.ifsp.spo.bulls.users.api.enums.Status;
import lombok.Data;

@Data
public class userBooksData {
    private Status status;
    private Long statusCount;

    public userBooksData(Status status, Long getStatusCount) {
        this.status = status;
        this.statusCount = getStatusCount;
    }

}
