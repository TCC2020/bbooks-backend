package br.edu.ifsp.spo.bulls.users.api.enums;

public enum CDNEnum {
    profile_image("PROFILE_IMAGE"),
    book_ad_id("BOOK_AD_ID"),
    book_image("BOOK_IMAGE");

    private final String text;
    CDNEnum(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public static CDNEnum getByString(String value) {
        for(CDNEnum objectType : CDNEnum.values()){
            if(objectType.text.equalsIgnoreCase(value))
                return objectType;
        }
        return null;
    }
}
