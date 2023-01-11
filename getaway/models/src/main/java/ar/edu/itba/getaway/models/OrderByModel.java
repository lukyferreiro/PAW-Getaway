package ar.edu.itba.getaway.models;

import java.util.Comparator;

public enum OrderByModel {
    OrderByRankAsc("ORDER BY exp.averageScore ASC"),
    OrderByRankDesc("ORDER BY exp.averageScore DESC"),
    OrderByAZ("ORDER BY exp.experienceName ASC"),
    OrderByZA("ORDER BY exp.experienceName DESC"),
    OrderByLowPrice("ORDER BY exp.price ASC"),
    OrderByHighPrice("ORDER BY exp.price DESC"),
    OrderByViewAsc("ORDER BY exp.views ASC"),
    OrderByViewDesc("ORDER BY exp.views DESC");

    final String SqlQuery;

    OrderByModel(String SqlQuery) {
        this.SqlQuery = SqlQuery;
    }

    public String getSqlQuery() {
        return SqlQuery;
    }

    public static OrderByModel[] getUserOrderByModel() {
        return new OrderByModel[]{OrderByRankAsc, OrderByRankDesc, OrderByAZ, OrderByZA, OrderByLowPrice, OrderByHighPrice};
    }

    public static OrderByModel[] getProviderOrderByModel() {
        return OrderByModel.values();
    }

}
