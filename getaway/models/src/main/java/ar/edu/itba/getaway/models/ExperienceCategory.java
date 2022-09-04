package ar.edu.itba.getaway.models;

import java.util.Arrays;

public enum ExperienceCategory {
    adventures,
    gastronomy,
    hotels,
    relax,
    night,
    historic;

    public static boolean contains(String value){
        return Arrays.stream(values()).map(Enum::name).anyMatch(code -> code.equals(value));
    }


}
