package br.edu.ifsp.spo.bulls.usersApi.enums;

public enum EmailSubject {
    VERIFY_EMAIL("Verificar email"),
    RECUPERAR_SENHA("Recuperar senha");
    @SuppressWarnings("unused")
	private String text;

    EmailSubject(String text){
        this.text =text;
    }
}
