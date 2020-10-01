package br.edu.ifsp.spo.bulls.usersApi.domain;

public enum CDNEnum {
    profile_image("PROFILE_IMAGE");

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
