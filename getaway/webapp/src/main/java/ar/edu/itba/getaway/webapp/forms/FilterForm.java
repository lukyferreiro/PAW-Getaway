package ar.edu.itba.getaway.webapp.forms;

public class FilterForm {

    private String activityCity;
    private Double activityPriceMax;
    private Boolean enablePrice;
    private String activityReview;

    public Double getActivityPriceMax() {
        return activityPriceMax;
    }
    public String getActivityCity() {
        return activityCity;
    }
    public String getActivityReview() {
        return activityReview;
    }
    public Long getScore(){
        return Long.valueOf(activityReview);
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
    public void setActivityReview(String activityReview) {
        this.activityReview = activityReview;
    }
}
