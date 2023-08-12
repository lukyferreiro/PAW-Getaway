package ar.edu.itba.getaway.webapp.controller.queryParamsValidators;

import ar.edu.itba.getaway.models.CategoryModel;
import ar.edu.itba.getaway.models.CityModel;
import ar.edu.itba.getaway.models.OrderByModel;
import ar.edu.itba.getaway.models.UserModel;

public class GetExperiencesParams {

    private final CategoryModel category;
    private final String name;
    private final OrderByModel order;
    private final Double maxPrice;
    private final Long maxScore;
    private final CityModel city;
    private final UserModel user;

    public GetExperiencesParams(CategoryModel category, String name, OrderByModel order, Double maxPrice, Long maxScore, CityModel city, UserModel user){
        this.category = category;
        this.name = name;
        this.order = order;
        this.maxPrice = maxPrice;
        this.maxScore = maxScore;
        this.city = city;
        this.user = user;
    }

    public CategoryModel getCategory() {
        return category;
    }
    public String getName() {
        return name;
    }
    public OrderByModel getOrder() {
        return order;
    }
    public Double getMaxPrice() {
        return maxPrice;
    }
    public Long getMaxScore() {
        return maxScore;
    }
    public CityModel getCity() {
        return city;
    }
    public UserModel getUser() {
        return user;
    }
}
