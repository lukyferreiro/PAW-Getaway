package ar.edu.itba.getaway.webapp.forms;

public class FavExperienceForm {

    private boolean favExp;
    private long experienceId;

    public long getExperienceId() {
        return experienceId;
    }

    public boolean getFavExp(){
        return favExp;
    }

    public void setExperienceId(long experienceId) {
        this.experienceId = experienceId;
    }

    public void setFavExp(boolean favExp) {
        this.favExp = favExp;
    }
}
