package br.edu.ifsp.spo.bulls.users.api.enums;

public enum Status {

    QUERO_LER("Quero ler"),
    LENDO("Lendo"),
    LIDO("Lido"),
    EMPRESTADO("Emprestado"),
    RELENDO("Relendo"),
    INTERROMPIDO("INTERROMPIDO");

    private final String text;
    Status(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public static Status getByString(String value) {
        for (Status status : Status.values()) {
            if (status.text.equalsIgnoreCase(value))
                return status;
        }
        return null;
    }
}
