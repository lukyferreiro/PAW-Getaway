package ar.edu.itba.getaway.models;

import java.util.Arrays;

public enum ExperienceCategory {
    adventures("Aventura"),
    gastronomy("Gastronomia"),
    hotels("Hoteleria"),
    relax("Relax"),
    night("Vida nocturna"),
    historic("Historico");

    private final String name;

    ExperienceCategory(String name){
        this.name = name;
    }

    public static boolean contains(String value){
        return Arrays.stream(values()).map(Enum::name).anyMatch(code -> code.equals(value));
    }

    public String getName() {
        return name;
    }
}
