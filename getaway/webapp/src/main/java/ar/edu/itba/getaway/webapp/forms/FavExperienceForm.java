package ar.edu.itba.getaway.webapp.forms;

public class FavExperienceForm {

    private String favExp;
    private long experienceId;

    public long getExperienceId() {
        return experienceId;
    }

    public boolean getFavExp(){
        return favExp.equals("true");
    }

    public void setExperienceId(long experienceId) {
        this.experienceId = experienceId;
    }

    public void setFavExp(String favExp) {
        this.favExp = favExp;
    }

}
