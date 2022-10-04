package ar.edu.itba.getaway.models;

public class FavExperienceModel {
    private final Long experienceId;
    private final Long userId;

    public FavExperienceModel(Long userId, Long experienceId) {
        this.experienceId = experienceId;
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
    public Long getExperienceId() {
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
        return this.experienceId.equals(other.experienceId) && this.userId.equals(other.userId);
    }
}
