package ar.edu.itba.getaway.models;

public enum OrderByModel {
    OrderByRankAsc("ORDER BY exp.averageScore ASC",1),
    OrderByRankDesc("ORDER BY exp.averageScore DESC",2),
    OrderByAZ("ORDER BY exp.experienceName ASC",3),
    OrderByZA("ORDER BY exp.experienceName DESC",4),
    OrderByLowPrice("ORDER BY exp.price ASC",5),
    OrderByHighPrice("ORDER BY exp.price DESC",6);

    final String SqlQuery;
    final Integer id;

    OrderByModel(String SqlQuery, Integer id){
        this.SqlQuery = SqlQuery;
        this.id = id;
    }

    public String getSqlQuery() {
        return SqlQuery;
    }

    public Integer getId() {
        return id;
    }
}
