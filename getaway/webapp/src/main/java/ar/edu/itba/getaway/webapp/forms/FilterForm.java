package ar.edu.itba.getaway.webapp.forms;

public class FilterForm {

    private String experienceCity;
    private Double experiencePriceMax;
    private Boolean enablePrice;
    private String score;

    public Double getExperiencePriceMax() {
        return experiencePriceMax;
    }
    public String getExperienceCity() {
        return experienceCity;
    }
    public String getScore() {
        return score;
    }
    public Long getScoreVal(){
        if(score == null || score.equals(""))
            return (long) -1;
        return Long.parseLong(score);
    }
    public void setExperiencePriceMax(Double experiencePriceMax) {
        this.experiencePriceMax = experiencePriceMax;
    }
    public void setExperienceCity(String experienceCity) {
        this.experienceCity = experienceCity;
    }
    public Boolean getEnablePrice() {
        return enablePrice;
    }
    public void setEnablePrice(Boolean enablePrice) {
        this.enablePrice = enablePrice;
    }
    public void setScore(String score) {
        this.score = score;
    }

}
