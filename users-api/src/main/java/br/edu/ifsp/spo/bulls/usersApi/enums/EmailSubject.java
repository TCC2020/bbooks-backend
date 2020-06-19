package br.edu.ifsp.spo.bulls.usersApi.enums;

public enum EmailSubject {
    VERIFY_EMAIL("VerifyEmail");

    @SuppressWarnings("unused")
	private String text;

    EmailSubject(String text){
        this.text =text;
    }
}
