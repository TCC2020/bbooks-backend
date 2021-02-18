package br.edu.ifsp.spo.bulls.common.api.enums;

public enum BookCondition {
    used("used"),
    not_used("not_used");

    private final String text;
    BookCondition(final String text) {
        this.text = text;
    }

    public static BookCondition getByString(String value) {
        for (BookCondition condition : BookCondition.values()) {
            if (condition.text.equalsIgnoreCase(value))
                return condition;
        }
        return null;
    }
}
