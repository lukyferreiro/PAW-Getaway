package ar.edu.itba.getaway.webapp.forms;

public class FilterForm {

    private String activityCity;
    private Double activityPriceMax;
    private Boolean enablePrice;
    private String score;

    public Double getActivityPriceMax() {
        return activityPriceMax;
    }
    public String getActivityCity() {
        return activityCity;
    }
    public String getScore() {
        return score;
    }
    public Long getScoreVal(){
        if(score == null || score.equals(""))
            return (long) -1;
        return Long.parseLong(score);
    }
    public void setActivityPriceMax(Double activityPriceMax) {
        this.activityPriceMax = activityPriceMax;
    }
    public void setActivityCity(String activityCity) {
        this.activityCity = activityCity;
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
