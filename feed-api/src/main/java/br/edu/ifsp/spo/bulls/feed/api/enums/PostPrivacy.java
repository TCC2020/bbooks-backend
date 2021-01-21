package br.edu.ifsp.spo.bulls.feed.api.enums;

public enum PostPrivacy {
    public_all("public"),
    friends_only("friend");

    private final String text;

    PostPrivacy(String text) {
        this.text = text;
    }
}
