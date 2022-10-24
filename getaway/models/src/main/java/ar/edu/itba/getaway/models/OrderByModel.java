package ar.edu.itba.getaway.models;

public enum OrderByModel {
    OrderByRankAsc("ORDER BY exp.averageScore ASC"),
    OrderByRankDesc("ORDER BY exp.averageScore DESC"),
    OrderByAZ("ORDER BY exp.experienceName ASC"),
    OrderByZA("ORDER BY exp.experienceName DESC"),
    OrderByLowPrice("ORDER BY exp.price ASC"),
    OrderByHighPrice("ORDER BY exp.price DESC");

    final String SqlQuery;

    OrderByModel(String SqlQuery){
        this.SqlQuery = SqlQuery;
    }

    public String getSqlQuery() {
        return SqlQuery;
    }
}
