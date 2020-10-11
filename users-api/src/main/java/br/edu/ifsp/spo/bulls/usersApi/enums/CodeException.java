package br.edu.ifsp.spo.bulls.usersApi.enums;

public enum CodeException {

    AU001("Author not found", "AU001"),
    AU002("Author already exists", "AU002"),
    AT001("Error, wrong password or user not found", "AT001"),
    AT002("Error, token not found", "AT002"),
    AT003("Password already changed", "AT003"),
    US001("User not found", "US001");



    private final String text;
    private final String number;

    CodeException(final String text, String number) {
        this.text = text;
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public String getNumber() {
        return number;
    }
}
