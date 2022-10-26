package ar.edu.itba.getaway.models;

import java.util.Comparator;

public enum OrderByModel {
    OrderByRankAsc("ORDER BY exp.averageScore ASC",1, Comparator.comparing(ExperienceModel::getAverageScore)),
    OrderByRankDesc("ORDER BY exp.averageScore DESC",2,Comparator.comparing(ExperienceModel::getAverageScore).reversed()),
    OrderByAZ("ORDER BY exp.experienceName ASC",3, Comparator.comparing(ExperienceModel::getExperienceName)),
    OrderByZA("ORDER BY exp.experienceName DESC",4, Comparator.comparing(ExperienceModel::getExperienceName).reversed()),
    OrderByLowPrice("ORDER BY exp.price ASC",5, (o1,o2) -> {
        Double price1 = o1.getPrice() != null ? o1.getPrice() : 0;
        Double price2 = o2.getPrice() != null ? o2.getPrice() : 0;
        return price2.compareTo(price1);
    }
    ),
    OrderByHighPrice("ORDER BY exp.price DESC",6,(o1,o2) -> {
        Double price1 = o1.getPrice() != null ? o1.getPrice() : 0;
        Double price2 = o2.getPrice() != null ? o2.getPrice() : 0;
        return price1.compareTo(price2);
    });

    final String SqlQuery;
    final Integer id;
    final Comparator<? super ExperienceModel> comparator;
    OrderByModel(String SqlQuery, Integer id, Comparator<? super ExperienceModel> comparator){
        this.SqlQuery = SqlQuery;
        this.id = id;
        this.comparator = comparator;
    }

    public String getSqlQuery() {
        return SqlQuery;
    }

    public Integer getId() {
        return id;
    }
}
