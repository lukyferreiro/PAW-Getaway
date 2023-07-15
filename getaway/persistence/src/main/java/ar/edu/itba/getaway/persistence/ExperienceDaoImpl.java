package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.interfaces.exceptions.DuplicateExperienceException;
import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.interfaces.persistence.ExperienceDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ExperienceDaoImpl implements ExperienceDao {

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceDaoImpl.class);

    private boolean checkDuplicateExperience(String name, String address, CityModel city){
        final TypedQuery<ExperienceModel> query = em.createQuery("FROM ExperienceModel WHERE experienceName = :experienceName AND address = :address AND cityid = :cityId ", ExperienceModel.class);
        query.setParameter("experienceName", name);
        query.setParameter("address", address);
        query.setParameter("cityId", city.getCityId());
        return query.getResultList().size() != 0;
    }

    @Override
    public ExperienceModel createExperience(String name, String address, String description, String email, String url, Double price, CityModel city, CategoryModel category, UserModel user, ImageModel experienceImage) throws DuplicateExperienceException {

        if(checkDuplicateExperience(name, address, city)){
            throw new DuplicateExperienceException();
        }

        final ExperienceModel experience = new ExperienceModel(name, address, description, email, url, price, city, category, user, experienceImage, true, 0);
        em.persist(experience);
        LOGGER.debug("Create experience with id: {}", experience.getExperienceId());
        return experience;
    }

    @Override
    public void updateExperience(ExperienceModel experienceModel) {
        LOGGER.debug("Update experience with id: {}", experienceModel.getExperienceId());
        em.merge(experienceModel);
    }

    @Override
    public void deleteExperience(ExperienceModel experience) {
        LOGGER.debug("Delete experience with id {}", experience.getExperienceId());
        em.remove(experience);
    }

    @Override
    public Optional<ExperienceModel> getExperienceById(long experienceId) {
        LOGGER.debug("Get experience with id {}", experienceId);
        return Optional.ofNullable(em.find(ExperienceModel.class, experienceId));
    }

    @Override
    public Optional<ExperienceModel> getVisibleExperienceById(long experienceId, UserModel user) {
        LOGGER.debug("Get experience with id {}", experienceId);
        final TypedQuery<ExperienceModel> query = em.createQuery("SELECT exp FROM ExperienceModel exp WHERE exp.experienceId = :experienceId AND (exp.observable = true OR exp.user = :user)", ExperienceModel.class);
        query.setParameter("experienceId", experienceId);
        query.setParameter("user", user);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public List<ExperienceModel> listExperiencesByFilter(CategoryModel category, String name, Double max, Long score, CityModel city, Optional<OrderByModel> order, int page, int pageSize) {
        LOGGER.debug("Getting experiences by current filter");

        String orderQuery = getOrderQuery(order);
        if (name.equals("%") || name.equals("_")) {
            name = '/' + name;
        }

        StringBuilder finalQuery = new StringBuilder();
        StringBuilder baseQuery = new StringBuilder("SELECT experiences.experienceid \n" + "FROM (experiences NATURAL JOIN (cities NATURAL JOIN countries)) LEFT JOIN reviews ON experiences.experienceid = reviews.experienceid \n" + "WHERE (\n" + "(LOWER(experienceName) LIKE LOWER(CONCAT('%', :name ,'%')))\n" + "OR (LOWER(experiences.description) LIKE LOWER(CONCAT('%',  :name,'%')))\n" + "OR (LOWER(address) LIKE LOWER(CONCAT('%',  :name,'%')) )\n" + "OR (LOWER(cityName) LIKE LOWER(CONCAT('%',  :name,'%')) )\n" + "OR (LOWER(countryName) LIKE LOWER(CONCAT('%',  :name,'%')))\n" + ") \n" + "AND COALESCE(price,0) <= :max \n" + "AND observable = true \n");

        String groupByClause = "GROUP BY experiences.experienceId HAVING AVG(COALESCE(score,0))>= :score\n";
        String categorySearch = "AND categoryId = :categoryId ";
        String citySearch = "AND cityid = :cityId ";

        Query queryForIds = em.createNativeQuery(baseQuery + groupByClause);

        if (city != null) {
            if (category != null) {
                finalQuery.append(baseQuery).append(citySearch).append(categorySearch).append(groupByClause);
                queryForIds = em.createNativeQuery(finalQuery.toString());
                queryForIds.setParameter("cityId", city.getCityId());
                queryForIds.setParameter("categoryId", category.getCategoryId());
            } else {
                finalQuery.append(baseQuery).append(citySearch).append(groupByClause);
                queryForIds = em.createNativeQuery(finalQuery.toString());
                queryForIds.setParameter("cityId", city.getCityId());
            }
        } else if (category != null) {
            finalQuery.append(baseQuery).append(categorySearch).append(groupByClause);
            queryForIds = em.createNativeQuery(finalQuery.toString());
            queryForIds.setParameter("categoryId", category.getCategoryId());
        }

        queryForIds.setParameter("max", max);
        queryForIds.setParameter("score", score);
        queryForIds.setParameter("name", name);

        List<Number> resultingIds = (List<Number>) queryForIds.getResultList();

        List<Long> idList = resultingIds.stream().map(Number::longValue).collect(Collectors.toList());
        final TypedQuery<ExperienceModel> queryForExperiences;
        if (idList.size() > 0) {
            LOGGER.debug("Selecting experiences contained in ");
            queryForExperiences = em.createQuery("SELECT exp FROM ExperienceModel exp WHERE exp.experienceId IN (:idList) " + orderQuery, ExperienceModel.class);
            queryForExperiences.setParameter("idList", idList);
            queryForExperiences.setMaxResults(pageSize);
            queryForExperiences.setFirstResult((page - 1) * pageSize);
            return queryForExperiences.getResultList();
        }

        LOGGER.debug("No results available for these filters");
        return new ArrayList<>();
    }

    @Override
    public long countListByFilter(CategoryModel category, String name, Double max, Long score, CityModel city) {
        LOGGER.debug("Getting count of experiences by current filter");
        if (name.equals("%") || name.equals("_")) {
            name = '/' + name;
        }

        StringBuilder finalQuery = new StringBuilder();
        StringBuilder baseQuery = new StringBuilder("SELECT experiences.experienceid \n" + "FROM (experiences NATURAL JOIN (cities NATURAL JOIN countries)) LEFT JOIN reviews ON experiences.experienceid = reviews.experienceid \n" + "WHERE (\n" + "(LOWER(experienceName) LIKE LOWER(CONCAT('%', :name ,'%')))\n" + "OR (LOWER(experiences.description) LIKE LOWER(CONCAT('%',  :name,'%')))\n" + "OR (LOWER(address) LIKE LOWER(CONCAT('%',  :name,'%')) )\n" + "OR (LOWER(cityName) LIKE LOWER(CONCAT('%',  :name,'%')) )\n" + "OR (LOWER(countryName) LIKE LOWER(CONCAT('%',  :name,'%')))\n" + ") \n" + "AND COALESCE(price,0) <= :max \n" + "AND observable = true \n");

        String groupByClause = "GROUP BY experiences.experienceId HAVING AVG(COALESCE(score,0))>= :score\n";
        String categorySearch = "AND categoryId = :categoryId ";
        String citySearch = "AND cityid = :cityId ";

        Query queryForIds = em.createNativeQuery(baseQuery + groupByClause);

        if (city != null) {
            if (category != null) {
                finalQuery.append(baseQuery).append(citySearch).append(categorySearch).append(groupByClause);
                queryForIds = em.createNativeQuery(finalQuery.toString());
                queryForIds.setParameter("cityId", city.getCityId());
                queryForIds.setParameter("categoryId", category.getCategoryId());
            } else {
                finalQuery.append(baseQuery).append(citySearch).append(groupByClause);
                queryForIds = em.createNativeQuery(finalQuery.toString());
                queryForIds.setParameter("cityId", city.getCityId());
            }
        } else if (category != null) {
            finalQuery.append(baseQuery).append(categorySearch).append(groupByClause);
            queryForIds = em.createNativeQuery(finalQuery.toString());
            queryForIds.setParameter("categoryId", category.getCategoryId());
        }

        queryForIds.setParameter("max", max);
        queryForIds.setParameter("score", score);
        queryForIds.setParameter("name", name);

        try {
            return queryForIds.getResultList().size();
        } catch (NoResultException e) {
            return 0;
        }
    }

    @Override
    public Optional<Double> getMaxPriceByCategoryAndName(CategoryModel category, String name) {
        LOGGER.debug("Get maxprice of name search {}", name);
        TypedQuery<Double> query = em.createQuery("SELECT Max(exp.price) FROM ExperienceModel exp WHERE (LOWER(exp.experienceName) LIKE LOWER(CONCAT('%', :name,'%')) OR LOWER(exp.description) LIKE LOWER(CONCAT('%', :name,'%')) OR LOWER(exp.address) LIKE LOWER(CONCAT('%', :name,'%')) OR LOWER(exp.city.cityName) LIKE LOWER(CONCAT('%', :name,'%')) OR LOWER(exp.city.country.countryName) LIKE LOWER(CONCAT('%', :name,'%'))) AND exp.observable=true ", Double.class);

        if (category != null) {
            query = em.createQuery("SELECT Max(exp.price) FROM ExperienceModel exp WHERE (LOWER(exp.experienceName) LIKE LOWER(CONCAT('%', :name,'%')) OR LOWER(exp.description) LIKE LOWER(CONCAT('%', :name,'%')) OR LOWER(exp.address) LIKE LOWER(CONCAT('%', :name,'%')) OR LOWER(exp.city.cityName) LIKE LOWER(CONCAT('%', :name,'%')) OR LOWER(exp.city.country.countryName) LIKE LOWER(CONCAT('%', :name,'%'))) AND exp.category = :category AND exp.observable=true", Double.class);
            query.setParameter("category", category);
        }

        query.setParameter("name", name);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public List<ExperienceModel> listExperiencesByBestRanked(CategoryModel category, int size) {
        final TypedQuery<ExperienceModel> query = em.createQuery("SELECT exp FROM ExperienceModel exp WHERE exp.category = :category AND exp.observable=true ORDER BY exp.averageScore DESC", ExperienceModel.class);
        query.setMaxResults(size);
        query.setParameter("category", category);
        return query.getResultList();
    }

    @Override
    public List<ExperienceModel> listExperiencesSearchByUser(String name, UserModel user, Optional<OrderByModel> order, int page, int pageSize) {
        LOGGER.debug("List experiences of user with id {}", user.getUserId());
        String orderQuery = getOrderQuery(order);

        Query queryForIds = em.createNativeQuery(
                "SELECT experienceId \n" +
                        "FROM experiences \n" +
                        "WHERE (LOWER(experienceName) LIKE LOWER(CONCAT('%', :name,'%'))) AND userid = :userId");

        queryForIds.setParameter("name", name);
        queryForIds.setParameter("userId", user.getUserId());


        List<Number> resultingIds = (List<Number>) queryForIds.getResultList();

        List<Long> idList = resultingIds.stream().map(Number::longValue).collect(Collectors.toList());
        final TypedQuery<ExperienceModel> queryForExperiences;
        if (idList.size() > 0) {
            LOGGER.debug("Selecting experiences contained in ");
            queryForExperiences = em.createQuery("SELECT exp FROM ExperienceModel exp WHERE exp.experienceId IN (:idList) " + orderQuery, ExperienceModel.class);
            queryForExperiences.setParameter("idList", idList);
            queryForExperiences.setMaxResults(pageSize);
            queryForExperiences.setFirstResult((page - 1) * pageSize);
            return queryForExperiences.getResultList();
        }

        LOGGER.debug("No results available for this name search for user with id{}", user.getUserId());
        return new ArrayList<>();
    }

    @Override
    public long getCountExperiencesByUser(String name, UserModel user) {
        Query query = em.createNativeQuery(
                "SELECT count(experienceId) \n" +
                        "FROM experiences \n" +
                        "WHERE (LOWER(experienceName) LIKE LOWER(CONCAT('%', :name,'%')) ) AND userid = :userId");
        query.setParameter("name", name);
        query.setParameter("userId", user.getUserId());

        try {
            return ((BigInteger) query.getSingleResult()).intValue();
        } catch (NoResultException e) {
            return 0;
        }
    }

    private String getOrderQuery(Optional<OrderByModel> order) {
        if (order.isPresent()) {
            return order.get().getSqlQuery();
        }
        return OrderByModel.OrderByAZ.getSqlQuery();
    }

    @Override
    public List<ExperienceModel> listExperiencesFavsByUser(UserModel user, Optional<OrderByModel> order, int page, int pageSize) {
        LOGGER.debug("List experiences of user with id {}", user.getUserId());
        String orderQuery = getOrderQuery(order);

        Query queryForIds = em.createNativeQuery(
                "SELECT favuserexperience.experienceid \n" +
                        "FROM favuserexperience JOIN experiences ON favuserexperience.experienceId = experiences.experienceid \n" +
                        "WHERE observable = true AND favuserexperience.userid = :userId");

        queryForIds.setParameter("userId", user.getUserId());

        List<Number> resultingIds = (List<Number>) queryForIds.getResultList();

        List<Long> idList = resultingIds.stream().map(Number::longValue).collect(Collectors.toList());
        final TypedQuery<ExperienceModel> queryForExperiences;
        if (idList.size() > 0) {
            LOGGER.debug("Selecting experiences contained in ");
            queryForExperiences = em.createQuery("SELECT exp FROM ExperienceModel exp WHERE exp.experienceId IN (:idList) " + orderQuery, ExperienceModel.class);
            queryForExperiences.setParameter("idList", idList);
            queryForExperiences.setMaxResults(pageSize);
            queryForExperiences.setFirstResult((page - 1) * pageSize);
            return queryForExperiences.getResultList();
        }

        LOGGER.debug("User with id {} has no observable faved experiences", user.getUserId());
        return new ArrayList<>();
    }

    @Override
    public long getCountListExperiencesFavsByUser(UserModel user) {
        Query query = em.createNativeQuery(
                "SELECT COUNT(favuserexperience.experienceid) \n" +
                        "FROM favuserexperience JOIN experiences ON favuserexperience.experienceId = experiences.experienceid \n" +
                        "WHERE observable = true AND favuserexperience.userid = :userId");

        query.setParameter("userId", user.getUserId());
        try {
            return ((BigInteger) query.getSingleResult()).intValue();
        } catch (NoResultException e) {
            return 0;
        }
    }

    @Override
    public List<ExperienceModel> getRecommendedByFavs(UserModel user, int maxResults) {
        LOGGER.debug("Getting possible ids of experiences to recommend based on user {} favourites", user.getUserId());
        final Query queryForIds = em.createNativeQuery(
                "SELECT experienceid\n" +
                        "FROM favuserexperience\n" +
                        "WHERE userid IN (\n" +
                        "    SELECT userid\n" +
                        "    FROM favuserexperience\n" +
                        "    WHERE userid != :userid AND experienceid IN (\n" +
                        "        SELECT experienceid\n" +
                        "        FROM favuserexperience\n" +
                        "        WHERE userid = :userid\n" +
                        "    )\n" +
                        ")\n" +
                        "  AND experienceid NOT IN (\n" +
                        "        SELECT experienceid\n" +
                        "        FROM favuserexperience\n" +
                        "        WHERE userid = :userid\n" +
                        "    )");

        queryForIds.setParameter("userid", user.getUserId());
        List<Number> resultingIds = (List<Number>) queryForIds.getResultList();

        List<Long> idList = resultingIds.stream().map(Number::longValue).collect(Collectors.toList());
        final TypedQuery<ExperienceModel> queryForExperiences;
        if (idList.size() > 0) {
            LOGGER.debug("Selecting experiences contained in ");
            queryForExperiences = em.createQuery("SELECT exp FROM ExperienceModel exp WHERE exp.observable=true AND exp.experienceId IN (:idList) AND exp.user != :user " + OrderByModel.OrderByRankDesc.getSqlQuery(), ExperienceModel.class);
            queryForExperiences.setParameter("idList", idList);
            queryForExperiences.setParameter("user", user);
            queryForExperiences.setMaxResults(maxResults);
            return queryForExperiences.getResultList();
        }

        LOGGER.debug("Not possible to make a recommendation by favourites alone");
        return new ArrayList<>();
    }

    @Override
    public List<ExperienceModel> getRecommendedByViews(UserModel user, int maxResults, List<Long> alreadyAdded) {
        final Query queryForIds = em.createNativeQuery(
                "SELECT experienceid\n" +
                        "FROM viewed\n" +
                        "WHERE userid IN (\n" +
                        "    SELECT userid\n" +
                        "    FROM viewed\n" +
                        "    WHERE userid != :userid AND experienceid IN (\n" +
                        "        SELECT experienceid\n" +
                        "        FROM viewed\n" +
                        "        WHERE userid = :userid\n" +
                        "    )\n" +
                        ")\n" +
                        "  AND experienceid NOT IN (\n" +
                        "        SELECT experienceid\n" +
                        "        FROM viewed\n" +
                        "        WHERE userid = :userid\n" +
                        "    )\n");

        queryForIds.setParameter("userid", user.getUserId());
        List<Number> resultingIds = (List<Number>) queryForIds.getResultList();

        if (alreadyAdded.size() == 0) {
            alreadyAdded.add(-1L);
        }

        List<Long> idList = resultingIds.stream().map(Number::longValue).collect(Collectors.toList());
        final TypedQuery<ExperienceModel> queryForExperiences;
        if (idList.size() > 0) {
            queryForExperiences = em.createQuery("SELECT exp FROM ExperienceModel exp WHERE exp.observable=true AND exp.experienceId IN (:idList) AND exp.experienceId NOT IN (:alreadyAdded) AND exp.user != :user " + OrderByModel.OrderByRankDesc.getSqlQuery(), ExperienceModel.class);
            queryForExperiences.setParameter("idList", idList);
            queryForExperiences.setParameter("user", user);
            queryForExperiences.setParameter("alreadyAdded", alreadyAdded);
            queryForExperiences.setMaxResults(maxResults);
            return queryForExperiences.getResultList();
        }

        LOGGER.debug("Not possible to make a recommendation by views alone");
        return new ArrayList<>();
    }

    public List<ExperienceModel> getRecommendedBestRanked(int maxResults, List<Long> alreadyAdded) {
        if (alreadyAdded.size() == 0) {
            alreadyAdded.add(-1L);
        }

        final TypedQuery<ExperienceModel> query = em.createQuery("SELECT exp FROM ExperienceModel exp WHERE exp.observable=true AND exp.id NOT IN (:alreadyAdded) ORDER BY exp.averageScore DESC", ExperienceModel.class);
        query.setParameter("alreadyAdded", alreadyAdded);
        query.setMaxResults(maxResults);
        return query.getResultList();
    }

    @Override
    public List<Long> reviewedExperiencesId(UserModel user) {
        final Query queryForReviewedExperiencesId = em.createNativeQuery("SELECT experienceid FROM reviews WHERE userid=:userid");
        queryForReviewedExperiencesId.setParameter("userid", user.getUserId());
        List<Number> resultingIds = (List<Number>) queryForReviewedExperiencesId.getResultList();
        return resultingIds.stream().map(Number::longValue).collect(Collectors.toList());
    }

    @Override
    public List<ExperienceModel> getRecommendedByReviewsCity(UserModel user, int maxResults, List<Long> alreadyAdded, List<Long> reviewedIds) {
        final Query queryForCityIds = em.createNativeQuery(
                "SELECT cityid\n" +
                        "FROM (\n" +
                        "    SELECT cityid\n" +
                        "    FROM experiences\n" +
                        "    where experienceid IN (\n" +
                        "        SELECT experienceid\n" +
                        "        FROM reviews\n" +
                        "        WHERE userid=:userid AND score>=3\n" +
                        "        )\n" +
                        "    )  AS aux\n" +
                        "GROUP BY cityid\n" +
                        "HAVING COUNT(cityid) >= ALL (\n" +
                        "    SELECT COUNT(cityid)\n" +
                        "    FROM (\n" +
                        "        SELECT cityid\n" +
                        "        FROM experiences\n" +
                        "        where experienceid IN (\n" +
                        "            SELECT experienceid\n" +
                        "            FROM reviews\n" +
                        "            WHERE userid=:userid AND score>=3\n" +
                        "        )\n" +
                        "    ) AS aux2\n" +
                        "    GROUP BY cityid\n" +
                        "    )");

        queryForCityIds.setParameter("userid", user.getUserId());
        List<Number> resultingIds = (List<Number>) queryForCityIds.getResultList();
        List<Long> idList = resultingIds.stream().map(Number::longValue).collect(Collectors.toList());

        if (alreadyAdded.size() == 0) {
            alreadyAdded.add(-1L);
        }

        final TypedQuery<ExperienceModel> queryForExperiences;
        if (idList.size() > 0) {
            queryForExperiences = em.createQuery("SELECT exp FROM ExperienceModel exp WHERE exp.observable=true AND exp.city.cityId IN (:idList) AND exp.experienceId NOT IN (:reviewedIds) AND exp.experienceId NOT IN (:alreadyAdded) AND exp.user != :user " + OrderByModel.OrderByRankDesc.getSqlQuery(), ExperienceModel.class);
            queryForExperiences.setParameter("idList", idList);
            queryForExperiences.setParameter("user", user);
            queryForExperiences.setParameter("reviewedIds", reviewedIds);
            queryForExperiences.setParameter("alreadyAdded", alreadyAdded);
            queryForExperiences.setMaxResults(maxResults);
            return queryForExperiences.getResultList();
        }

        return new ArrayList<>();
    }

    public List<ExperienceModel> getRecommendedByReviewsProvider(UserModel user, int maxResults, List<Long> alreadyAdded, List<Long> reviewedIds) {
        final Query queryForProviderIds = em.createNativeQuery(
                "SELECT userid\n" +
                        "FROM (\n" +
                        "    SELECT userid\n" +
                        "    FROM experiences\n" +
                        "    where experienceid IN (\n" +
                        "        SELECT experienceid\n" +
                        "        FROM reviews\n" +
                        "        WHERE userid=:userid AND score>=3\n" +
                        "        )\n" +
                        "    )  AS aux\n" +
                        "GROUP BY userid\n" +
                        "HAVING COUNT(userid) >= ALL (\n" +
                        "    SELECT COUNT(userid)\n" +
                        "    FROM (\n" +
                        "        SELECT userid\n" +
                        "        FROM experiences\n" +
                        "        where experienceid IN (\n" +
                        "            SELECT experienceid\n" +
                        "            FROM reviews\n" +
                        "            WHERE userid=:userid AND score>=3\n" +
                        "        )\n" +
                        "    ) AS aux\n" +
                        "    GROUP BY userid\n" +
                        "    )");

        queryForProviderIds.setParameter("userid", user.getUserId());
        List<Number> resultingIds = (List<Number>) queryForProviderIds.getResultList();

        List<Long> idList = resultingIds.stream().map(Number::longValue).collect(Collectors.toList());
        final TypedQuery<ExperienceModel> queryForExperiences;

        if (alreadyAdded.size() == 0) {
            alreadyAdded.add(-1L);
        }

        if (idList.size() > 0) {
            queryForExperiences = em.createQuery("SELECT exp FROM ExperienceModel exp WHERE exp.observable=true AND exp.user.userId IN (:idList) AND exp.experienceId NOT IN (:alreadyAdded) AND exp.experienceId NOT IN (:reviewedIds) AND exp.user != :user " + OrderByModel.OrderByRankDesc.getSqlQuery(), ExperienceModel.class);
            queryForExperiences.setParameter("idList", idList);
            queryForExperiences.setParameter("user", user);
            queryForExperiences.setParameter("reviewedIds", reviewedIds);
            queryForExperiences.setParameter("alreadyAdded", alreadyAdded);
            queryForExperiences.setMaxResults(maxResults);
            return queryForExperiences.getResultList();
        }

        return new ArrayList<>();

    }

    public List<ExperienceModel> getRecommendedByReviewsCategory(UserModel user, int maxResults, List<Long> alreadyAdded, List<Long> reviewedIds) {
        final Query queryForCategoryIds = em.createNativeQuery(
                "SELECT categoryid\n" +
                "FROM (\n" +
                "    SELECT categoryid\n" +
                "    FROM experiences\n" +
                "    where experienceid IN (\n" +
                "        SELECT experienceid\n" +
                "        FROM reviews\n" +
                "        WHERE userid=:userid AND score>=3\n" +
                "        )\n" +
                "    )  AS aux\n" +
                "GROUP BY categoryid\n" +
                "HAVING COUNT(categoryid) >= ALL (\n" +
                "    SELECT COUNT(categoryid)\n" +
                "    FROM (\n" +
                "        SELECT categoryid\n" +
                "        FROM experiences\n" +
                "        where experienceid IN (\n" +
                "            SELECT experienceid\n" +
                "            FROM reviews\n" +
                "            WHERE userid=:userid AND score>=3\n" +
                "        )\n" +
                "    ) AS aux\n" +
                "    GROUP BY categoryid\n" +
                "    )\n");

        queryForCategoryIds.setParameter("userid", user.getUserId());
        List<Number> resultingIds = (List<Number>) queryForCategoryIds.getResultList();

        if (alreadyAdded.size() == 0) {
            alreadyAdded.add(-1L);
        }

        List<Long> idList = resultingIds.stream().map(Number::longValue).collect(Collectors.toList());
        final TypedQuery<ExperienceModel> queryForExperiences;
        if (idList.size() > 0) {
            queryForExperiences = em.createQuery("SELECT exp FROM ExperienceModel exp WHERE exp.observable=true AND exp.category.categoryId IN (:idList) AND exp.experienceId NOT IN (:alreadyAdded) AND exp.experienceId NOT IN (:reviewedIds) AND exp.user != :user " + OrderByModel.OrderByRankDesc.getSqlQuery(), ExperienceModel.class);
            queryForExperiences.setParameter("idList", idList);
            queryForExperiences.setParameter("user", user);
            queryForExperiences.setParameter("reviewedIds", reviewedIds);
            queryForExperiences.setParameter("alreadyAdded", alreadyAdded);
            queryForExperiences.setMaxResults(maxResults);
            return queryForExperiences.getResultList();
        }

        return new ArrayList<>();
    }


}