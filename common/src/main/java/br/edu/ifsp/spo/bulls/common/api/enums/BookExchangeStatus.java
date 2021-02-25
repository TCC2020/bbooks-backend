package br.edu.ifsp.spo.bulls.common.api.enums;

public enum BookExchangeStatus {
    pending("pending"),
    refused("refused"),
    accepted("accepted"),
    canceled("canceled");

    private final String text;
    BookExchangeStatus(final String text) {
        this.text = text;
    }

    public static BookExchangeStatus getByString(String value) {
        for (BookExchangeStatus exchange : BookExchangeStatus.values()) {
            if (exchange.text.equalsIgnoreCase(value))
                return exchange;
        }
        return null;
    }
}
