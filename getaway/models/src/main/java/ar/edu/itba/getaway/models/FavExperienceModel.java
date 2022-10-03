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

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if (!(o instanceof FavExperienceModel)){
            return false;
        }
        FavExperienceModel other = (FavExperienceModel) o;
        return this.experienceId == other.experienceId && this.userId == other.userId;
    }
}
