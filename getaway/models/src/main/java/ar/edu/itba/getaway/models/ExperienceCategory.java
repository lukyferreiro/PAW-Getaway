package ar.edu.itba.getaway.models;

import java.util.Arrays;

public enum ExperienceCategory {
    Aventura("Aventura"),
    Gastronomia("Gastronomia"),
    Hoteleria("Hoteleria"),
    Relax("Relax"),
    Vida_nocturna("Vida nocturna"),
    Historico("Historico");

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
