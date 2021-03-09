package br.edu.ifsp.spo.bulls.common.api.enums;

public enum ReactionType {
    like("like"),
    dislike("dislike"),
    loved("loved"),
    hilarius("hilarius"),
    surprised("surprised"),
    sad("sad"),
    hated("hated");

    private final String text;

    ReactionType (final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public static ReactionType getByString(String value) {
        for(ReactionType objectType : ReactionType.values()){
            if(objectType.text.equalsIgnoreCase(value))
                return objectType;
        }
        return null;
    }
}