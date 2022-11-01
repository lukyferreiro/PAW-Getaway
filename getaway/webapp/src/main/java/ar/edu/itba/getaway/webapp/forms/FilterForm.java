package ar.edu.itba.getaway.webapp.forms;

public class FilterForm {

    private String cityId;
    private Double maxPrice;
    private Boolean enablePrice;
    private String score;

    public Double getMaxPrice() {
        return maxPrice;
    }
    public String getCityId() {
        return cityId;
    }
    public String getScore() {
        return score;
    }
    public Long getScoreVal(){
        if(score == null || score.equals(""))
            return (long) -1;
        return Long.parseLong(score);
    }
    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }
    public void setCityId(String cityId) {
        this.cityId = cityId;
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
