package ar.edu.itba.getaway.webapp.forms;

public class FilterForm {

    private String activityCity;
    private long activityPriceMax;

    public long getActivityPriceMax() {
        return activityPriceMax;
    }

    public String getActivityCity() {
        return activityCity;
    }

    public void setActivityPriceMax(long activityPriceMax) {
        this.activityPriceMax = activityPriceMax;
    }

    public void setActivityCity(String activityCity) {
        this.activityCity = activityCity;
    }
}
