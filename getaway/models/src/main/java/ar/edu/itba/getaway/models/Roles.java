package ar.edu.itba.getaway.models;

import java.util.Arrays;

public enum Roles {
    PROVIDER,
    USER,
    NOT_VERIFIED,
    VERIFIED;

    public static boolean contains(String value){
        return Arrays.stream(values()).map(Enum::name).anyMatch(code -> code.equals(value));
    }
}
