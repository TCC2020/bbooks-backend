package br.edu.ifsp.spo.bulls.feed.api.enums;

public enum TypePost {
    post("post"),
    comentario("comment");

    private final String text;

    TypePost(String text) {
        this.text = text;
    }
}
