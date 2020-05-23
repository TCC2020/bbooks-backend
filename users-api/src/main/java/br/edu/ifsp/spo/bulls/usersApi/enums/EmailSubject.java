package br.edu.ifsp.spo.bulls.usersApi.enums;

import br.edu.ifsp.spo.bulls.usersApi.service.EmailService;

public enum EmailSubject {
    VERIFY_EMAIL("VerifyEmail");

    private String text;

    EmailSubject(String text){
        this.text = text;
    }


}
