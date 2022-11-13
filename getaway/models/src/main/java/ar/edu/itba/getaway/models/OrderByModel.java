package ar.edu.itba.getaway.models;

import java.util.Comparator;

public enum OrderByModel {
    OrderByRankAsc("ORDER BY exp.averageScore ASC", Comparator.comparing(ExperienceModel::getAverageScore)),
    OrderByRankDesc("ORDER BY exp.averageScore DESC", Comparator.comparing(ExperienceModel::getAverageScore).reversed()),
    OrderByAZ("ORDER BY exp.experienceName ASC", Comparator.comparing(ExperienceModel::getExperienceName)),
    OrderByZA("ORDER BY exp.experienceName DESC", Comparator.comparing(ExperienceModel::getExperienceName).reversed()),
    OrderByLowPrice("ORDER BY exp.price ASC", (o1, o2) -> {
        Double price1 = o1.getPrice() != null ? o1.getPrice() : 0;
        Double price2 = o2.getPrice() != null ? o2.getPrice() : 0;
        return price2.compareTo(price1);
    }
    ),
    OrderByHighPrice("ORDER BY exp.price DESC", (o1, o2) -> {
        Double price1 = o1.getPrice() != null ? o1.getPrice() : 0;
        Double price2 = o2.getPrice() != null ? o2.getPrice() : 0;
        return price1.compareTo(price2);
    }),
    OrderByViewAsc("ORDER BY exp.views ASC", null),
    OrderByViewDesc("ORDER BY exp.views DESC", null);

    final String SqlQuery;
    final Comparator<? super ExperienceModel> comparator;

    OrderByModel(String SqlQuery, Comparator<? super ExperienceModel> comparator) {
        this.SqlQuery = SqlQuery;
        this.comparator = comparator;
    }

    public Comparator<? super ExperienceModel> getComparator() {
        return comparator;
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
