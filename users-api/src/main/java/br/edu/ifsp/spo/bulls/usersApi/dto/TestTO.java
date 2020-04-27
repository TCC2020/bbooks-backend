package br.edu.ifsp.spo.bulls.usersApi.dto;

import lombok.Data;

@Data
public class TestTO {
    public TestTO(){

    }
    private String teamName;
    private String university;
    private Long year;
}
