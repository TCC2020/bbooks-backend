package br.edu.ifsp.spo.bulls.users.api.dto;

public class UserBooksDataStatusTO {

    public String googleId;
    public int bookId;
    public Long queroLer;
    public Long lendo;
    public Long lido;
    public Long emprestado;
    public Long relendo;
    public Long interrompido;

    public UserBooksDataStatusTO(String googleId, Long queroLer, Long lendo, Long lido, Long emprestado, Long relendo, Long interrompido) {
        this.googleId = googleId;
        this.queroLer = queroLer;
        this.lendo = lendo;
        this.lido = lido;
        this.emprestado = emprestado;
        this.relendo = relendo;
        this.interrompido = interrompido;
    }

    public UserBooksDataStatusTO(int bookId, Long queroLer, Long lendo, Long lido, Long emprestado, Long relendo, Long interrompido) {
        this.bookId = bookId;
        this.queroLer = queroLer;
        this.lendo = lendo;
        this.lido = lido;
        this.emprestado = emprestado;
        this.relendo = relendo;
        this.interrompido = interrompido;
    }
}
