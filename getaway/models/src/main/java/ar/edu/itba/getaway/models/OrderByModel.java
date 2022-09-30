package ar.edu.itba.getaway.models;

public enum OrderByModel {
    OrderByRankAsc("AVG(SCORE)","ASC"),
    OrderByRankDesc("AVG(SCORE)", "DESC"),
    OrderByAZ("experienceName", "ASC"),
    OrderByZA("experienceName", "DESC"),
    OrderByLowPrice("price", "ASC"),
    OrderByHighPrice("price", "DESC");

    String criteria;
    String direction;

    OrderByModel(String criteria, String direction){
        this.criteria = criteria;
        this.direction = direction;
    }

    public String getCriteria(){
        return this.criteria;
    }

    public String getDirection(){
        return this.direction;
    }

    public String getFullUrl() {
        return String.format("orderBy=%s&direction=%s", this.criteria,this.direction);
    }
}
