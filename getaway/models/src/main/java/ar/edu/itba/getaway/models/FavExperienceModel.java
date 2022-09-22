package ar.edu.itba.getaway.models;

public class FavExperienceModel {
    private final long experienceId;
    private final long userId;

    public FavExperienceModel(long userId, long experienceId) {
        this.experienceId = experienceId;
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public long getExperienceId() {
        return experienceId;
    }
}
