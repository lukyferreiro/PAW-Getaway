package ar.edu.itba.getaway.webapp.controller.queryParamsValidators;

import ar.edu.itba.getaway.interfaces.exceptions.CategoryNotFoundException;
import ar.edu.itba.getaway.interfaces.exceptions.CityNotFoundException;
import ar.edu.itba.getaway.interfaces.services.CategoryService;
import ar.edu.itba.getaway.interfaces.services.ExperienceService;
import ar.edu.itba.getaway.interfaces.services.LocationService;
import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.models.pagination.Page;
import ar.edu.itba.getaway.webapp.security.services.AuthContext;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.Objects;

public enum GetExperiencesFilter {

    SEARCH() {
        @Override
        public GetExperiencesParams validateParams(
                AuthContext authContext, CategoryService categoryService, ExperienceService experienceService,
                LocationService locationService, String category, String name, OrderByModel order, Double maxPrice,
                Long maxScore, Long cityId, Long userId
        ) {
            CategoryModel categoryModel = null;
            CityModel cityModel = null;
            if (!category.equals("")){
                categoryModel = categoryService.getCategoryByName(category).orElseThrow(CategoryNotFoundException::new);
            }
            if (maxPrice == -1) {
                maxPrice = experienceService.getMaxPriceByCategoryAndName(categoryModel, name).orElse(0.0);
            }
            if (cityId != -1) {
                cityModel = locationService.getCityById(cityId).orElseThrow(CityNotFoundException::new);
            }
            final UserModel user = authContext.getCurrentUser();
            return new GetExperiencesParams(categoryModel, name, order, maxPrice, maxScore, cityModel, user);
        }

        @Override
        public Page<ExperienceModel> getExperiences(ExperienceService experienceService, GetExperiencesParams params, int page) {
            return experienceService.listExperiencesByFilter(
                    params.getCategory(), params.getName(), params.getMaxPrice(), params.getMaxScore(),
                    params.getCity(), params.getOrder(), page, params.getUser()
            );
        }

        @Override
        public UriBuilder getUriBuilder(UriInfo uriInfo, String category, String name, OrderByModel order, Double maxPrice, Long maxScore, CityModel city, Long userId, int page) {
            final UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
            if(!Objects.equals(category, "")){
                uriBuilder.queryParam("category", category);
            }
            if(!Objects.equals(name, "")){
                uriBuilder.queryParam("name", name);
            }
            if(maxPrice != -1){
                uriBuilder.queryParam("price", maxPrice);
            }
            if(maxScore != 0){
                uriBuilder.queryParam("score", maxScore);
            }
            if (city != null) {
                uriBuilder.queryParam("city", city.getCityId());
            }
            return uriBuilder
                    .queryParam("order", order)
                    .queryParam("page", page)
                    .queryParam("filter", this.toString());
        }

    },

    BEST_CATEGORY() {

        @Override
        public GetExperiencesParams validateParams(
                AuthContext authContext, CategoryService categoryService, ExperienceService experienceService,
                LocationService locationService, String category, String name, OrderByModel order, Double maxPrice,
                Long maxScore, Long cityId, Long userId
        ) {
            CategoryModel categoryModel = categoryService.getCategoryByName(category).orElseThrow(CategoryNotFoundException::new);;
            final UserModel userModel = validateUserId(authContext, userId);
            return new GetExperiencesParams(categoryModel, null, null, null, null, null, userModel);
        }

        @Override
        public Page<ExperienceModel> getExperiences(ExperienceService experienceService, GetExperiencesParams params, int page) {
            return experienceService.listExperiencesByBestRanked(params.getCategory(), params.getUser());
        }

        @Override
        public UriBuilder getUriBuilder(UriInfo uriInfo, String category, String name, OrderByModel order, Double maxPrice, Long maxScore, CityModel city, Long userId, int page) {
            return uriInfo.getAbsolutePathBuilder()
                    .queryParam("category", category)
                    .queryParam("page", page)
                    .queryParam("filter", this.toString());
        }
    },

    PROVIDER() {
        @Override
        public GetExperiencesParams validateParams(
                AuthContext authContext, CategoryService categoryService, ExperienceService experienceService,
                LocationService locationService, String category, String name, OrderByModel order, Double maxPrice,
                Long maxScore, Long cityId, Long userId
        ) {
            final UserModel userModel = validateUserId(authContext, userId);
            return new GetExperiencesParams(null, name, order, null, null, null, userModel);
        }

        @Override
        public Page<ExperienceModel> getExperiences(ExperienceService experienceService, GetExperiencesParams params, int page) {
            return experienceService.listExperiencesSearchByUser(params.getName(), params.getUser(), params.getOrder(), page);
        }

        @Override
        public UriBuilder getUriBuilder(UriInfo uriInfo, String category, String name, OrderByModel order, Double maxPrice, Long maxScore, CityModel city, Long userId, int page) {
            return uriInfo.getAbsolutePathBuilder()
                    .queryParam("name", name)
                    .queryParam("order", order)
                    .queryParam("page", page)
                    .queryParam("filter", this.toString());
        }
    },

    FAVS() {
        @Override
        public GetExperiencesParams validateParams(
                AuthContext authContext, CategoryService categoryService, ExperienceService experienceService,
                LocationService locationService, String category, String name, OrderByModel order, Double maxPrice,
                Long maxScore, Long cityId, Long userId
        ) {
            final UserModel userModel = validateUserId(authContext, userId);
            return new GetExperiencesParams(null, null, order, null, null, null, userModel);
        }

        @Override
        public Page<ExperienceModel> getExperiences(ExperienceService experienceService, GetExperiencesParams params, int page) {
            return experienceService.listExperiencesFavsByUser(params.getUser(), params.getOrder(), page);
        }

        @Override
        public UriBuilder getUriBuilder(UriInfo uriInfo, String category, String name, OrderByModel order, Double maxPrice, Long maxScore, CityModel city, Long userId, int page) {
            return uriInfo.getAbsolutePathBuilder()
                    .queryParam("order", order)
                    .queryParam("page", page)
                    .queryParam("filter", this.toString());
        }
    },

    VIEWED() {
        @Override
        public GetExperiencesParams validateParams(
                AuthContext authContext, CategoryService categoryService, ExperienceService experienceService,
                LocationService locationService, String category, String name, OrderByModel order, Double maxPrice,
                Long maxScore, Long cityId, Long userId
        ) {
            final UserModel userModel = validateUserId(authContext, userId);
            return new GetExperiencesParams(null, null, null, null, null, null, userModel);
        }

        @Override
        public Page<ExperienceModel> getExperiences(ExperienceService experienceService, GetExperiencesParams params, int page) {
            return experienceService.getViewedExperiences(params.getUser());
        }

        @Override
        public UriBuilder getUriBuilder(UriInfo uriInfo, String category, String name, OrderByModel order, Double maxPrice, Long maxScore, CityModel city, Long userId, int page) {
            return uriInfo.getAbsolutePathBuilder()
                    .queryParam("page", page)
                    .queryParam("filter", this.toString());
        }
    },

    RECOMMENDED_BY_FAVS() {
        @Override
        public GetExperiencesParams validateParams(
                AuthContext authContext, CategoryService categoryService, ExperienceService experienceService,
                LocationService locationService, String category, String name, OrderByModel order, Double maxPrice,
                Long maxScore, Long cityId, Long userId
        ) {
            final UserModel userModel = validateUserId(authContext, userId);
            return new GetExperiencesParams(null, null, null, null, null, null, userModel);
        }

        @Override
        public Page<ExperienceModel> getExperiences(ExperienceService experienceService, GetExperiencesParams params, int page) {
            return experienceService.getRecommendedByFavs(params.getUser());
        }

        @Override
        public UriBuilder getUriBuilder(UriInfo uriInfo, String category, String name, OrderByModel order, Double maxPrice, Long maxScore, CityModel city, Long userId, int page) {
            return uriInfo.getAbsolutePathBuilder()
                    .queryParam("page", page)
                    .queryParam("filter", this.toString());
        }
    },

    RECOMMENDED_BY_REVIEWS() {
        @Override
        public GetExperiencesParams validateParams(
                AuthContext authContext, CategoryService categoryService, ExperienceService experienceService,
                LocationService locationService, String category, String name, OrderByModel order, Double maxPrice,
                Long maxScore, Long cityId, Long userId
        ) {
            final UserModel userModel = validateUserId(authContext, userId);
            return new GetExperiencesParams(null, null, null, null, null, null, userModel);
        }

        @Override
        public Page<ExperienceModel> getExperiences(ExperienceService experienceService, GetExperiencesParams params, int page) {
            return experienceService.getRecommendedByReviews(params.getUser());
        }

        @Override
        public UriBuilder getUriBuilder(UriInfo uriInfo, String category, String name, OrderByModel order, Double maxPrice, Long maxScore, CityModel city, Long userId, int page) {
            return uriInfo.getAbsolutePathBuilder()
                    .queryParam("page", page)
                    .queryParam("filter", this.toString());
        }
    };

    public abstract GetExperiencesParams validateParams(
            AuthContext authContext, CategoryService categoryService, ExperienceService experienceService,
            LocationService locationService, String category, String name, OrderByModel order,
            Double maxPrice, Long maxScore, Long cityId, Long userId
    );

    public abstract Page<ExperienceModel> getExperiences(ExperienceService experienceService, GetExperiencesParams params, int page);
    public abstract UriBuilder getUriBuilder(UriInfo uriInfo, String category, String name, OrderByModel order, Double maxPrice, Long maxScore, CityModel city, Long userId, int page);

    private static UserModel validateUserId(AuthContext authContext, Long userId) {
        if(userId == null){
            throw new InvalidRequestParamsException("errors.invalidParam.getExperiences.userIdNull");
        }
        return authContext.getCurrentUser();
    }

    public static GetExperiencesFilter fromString(String filter) {
        try {
            return valueOf(filter.toUpperCase());
        } catch (Exception e) {
            throw new InvalidGetExperiencesFilterException("errors.invalidExperienceFilter");
        }
    }

    GetExperiencesFilter() {

    }

}
