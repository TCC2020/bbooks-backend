package br.edu.ifsp.spo.bulls.usersApi.enums;

public enum CodeException {

    AU001("Author not found", "AU001"),
    AU002("Author already exists", "AU002"),
    AT001("Error, wrong password or user not found", "AT001"),
    AT002("Error, token not found", "AT002"),
    AT003("Password already changed", "AT003"),
    US001("User not found", "US001"),
    BK001("IBNB already exists", "BK001"),
    BK002("Book not found", "BK002"),
    BK003("Book should have at least 1 author", "BK003");



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
