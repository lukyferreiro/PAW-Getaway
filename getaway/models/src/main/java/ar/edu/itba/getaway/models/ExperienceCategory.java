package ar.edu.itba.getaway.models;

import java.util.Arrays;

public enum ExperienceCategory {
    adventures("Aventura", "adventures"),
    gastronomy("Gastronomia", "gastronomy"),
    hotels("Hoteleria", "hotels"),
    relax("Relax", "relax"),
    night("Vida nocturna", "night"),
    historic("Historico", "historic");

    private final String name;
    private final String databaseName;

    ExperienceCategory(String name, String databaseName){
        this.name = name;
        this.databaseName = databaseName;
    }

    public static boolean contains(String value){
        return Arrays.stream(values()).map(Enum::name).anyMatch(code -> code.equals(value));
    }

    public String getName() {
        return name;
    }

    public String getDatabaseName() {
        return databaseName;
    }
}
