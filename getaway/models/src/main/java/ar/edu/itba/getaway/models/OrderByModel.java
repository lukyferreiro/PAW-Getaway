package ar.edu.itba.getaway.models;

public enum OrderByModel {
    OrderByRankAsc("ORDER BY AVG(COALESCE(SCORE,0)) ASC"),
    OrderByRankDesc("ORDER BY AVG(COALESCE(SCORE,0)) DESC"),
    OrderByAZ("ORDER BY experienceName ASC"),
    OrderByZA("ORDER BY experienceName DESC"),
    OrderByLowPrice("ORDER BY price ASC"),
    OrderByHighPrice("ORDER BY price DESC");

    final String SqlQuery;

    OrderByModel(String SqlQuery){
        this.SqlQuery = SqlQuery;
    }

    public String getSqlQuery() {
        return SqlQuery;
    }
}
