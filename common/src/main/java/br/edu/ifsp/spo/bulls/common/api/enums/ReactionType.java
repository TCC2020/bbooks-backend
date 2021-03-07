package br.edu.ifsp.spo.bulls.common.api.enums;

public enum ReactionType {
    like("LIKE"),
    dislike("DISLIKED"),
    loved("LOVED"),
    hilarius("HILARIUS"),
    surprised("SURPRISED"),
    sad("SAD"),
    hated("HATED");

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