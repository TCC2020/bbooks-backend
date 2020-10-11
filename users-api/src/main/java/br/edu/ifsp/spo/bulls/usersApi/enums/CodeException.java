package br.edu.ifsp.spo.bulls.usersApi.enums;

public enum CodeException {

    AU001("Author not found", "AU001"),
    AU002("Author already exists", "AU002"),
    AT001("Error, wrong password or user not found", "AT001"),
    AT002("Error, token not found", "AT002"),
    AT003("Password already changed", "AT003"),
    US001("User not found", "US001"),
    US002("Email already used", "US002"),
    US003("Password can't be null", "US003"),
    US004("Token not found", "US004"),
    US005("Username already used", "US005"),
    BK001("IBNB already exists", "BK001"),
    BK002("Book not found", "BK002"),
    BK003("Book should have at least 1 author", "BK003"),
    RT001("Reading Tracking not found", "RT001"),
    RT002("Page number greater than the total pages of the book", "RT002"),
    RT003("Book already completed", "RT002"),
    TG001("Tag not found", "TG001"),
    TG002("Tag sent does not match ID", "TG002"),
    PF001("Profile not found", "PF001"),
    UB001("UserBook not found", "UB001");



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
