package ar.edu.itba.getaway.webapp.forms;

public class FilterForm {

    private String activityCity;
    private Double activityPriceMax;

    public Double getActivityPriceMax() {
        return activityPriceMax;
    }

    public String getActivityCity() {
        return activityCity;
    }

    public void setActivityPriceMax(Double activityPriceMax) {
        this.activityPriceMax = activityPriceMax;
    }

    public void setActivityCity(String activityCity) {
        this.activityCity = activityCity;
    }
}
