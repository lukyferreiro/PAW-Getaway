package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.interfaces.persistence.ExperienceDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ExperienceDaoImpl implements ExperienceDao {

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceDaoImpl.class);

    @Override
    public ExperienceModel createExperience(String name, String address, String description, String email, String url, Double price, CityModel city, CategoryModel category, UserModel user, ImageModel experienceImage) {

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
    public Optional<ExperienceModel> getVisibleExperienceById(long experienceId) {
        LOGGER.debug("Get experience with id {}", experienceId);
        final TypedQuery<ExperienceModel> query = em.createQuery("SELECT exp FROM ExperienceModel exp WHERE exp.experienceId = :experienceId AND exp.observable = true", ExperienceModel.class);
        query.setParameter("experienceId", experienceId);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public Optional<Double> getMaxPriceByCategory(CategoryModel category) {
        LOGGER.debug("Get maxprice of category {}", category.getCategoryName());
        final TypedQuery<Double> query = em.createQuery("SELECT Max(exp.price) FROM ExperienceModel exp WHERE exp.category = :category", Double.class);
        query.setParameter("category", category);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public List<ExperienceModel> listExperiencesByFilter(CategoryModel category, Double max, Long score, CityModel city, Optional<OrderByModel> order, int page, int pageSize) {
        String orderQuery;
        if (order.isPresent()) {
            orderQuery = order.get().getSqlQuery();
        } else {
            orderQuery = OrderByModel.OrderByRankDesc.getSqlQuery();
        }

        TypedQuery<ExperienceModel> query = em.createQuery("SELECT exp FROM ExperienceModel exp WHERE exp.category=:category AND COALESCE(exp.price,0)<=:max AND exp.averageScore>=:score AND exp.observable=true " + orderQuery, ExperienceModel.class);

        if (city != null) {
            query = em.createQuery("SELECT exp FROM ExperienceModel exp WHERE exp.category=:category AND exp.city=:city AND COALESCE(exp.price,0)<=:max AND exp.averageScore>=:score AND exp.observable=true " + orderQuery, ExperienceModel.class);
            query.setParameter("city", city);
        }

        query.setParameter("category", category);
        query.setParameter("max", max);
        query.setParameter("score", score);
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public long countListByFilter(CategoryModel category, Double max, Long score, CityModel city) {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(exp) FROM ExperienceModel exp WHERE exp.category=:category AND COALESCE(exp.price,0)<=:max  AND exp.averageScore>=:score AND exp.observable=true", Long.class);

        if (city != null) {
            query = em.createQuery("SELECT COUNT(exp) FROM ExperienceModel exp WHERE exp.category=:category AND exp.city=:city AND COALESCE(exp.price,0)<=:max AND exp.averageScore>=:score AND exp.observable=true", Long.class);
            query.setParameter("city", city);
        }
        query.setParameter("category", category);
        query.setParameter("max", max);
        query.setParameter("score", score);
        return query.getSingleResult();
    }

    @Override
    public List<ExperienceModel> listExperiencesByBestRanked(CategoryModel category) {
        final TypedQuery<ExperienceModel> query = em.createQuery("SELECT exp FROM ExperienceModel exp WHERE exp.category = :category AND exp.observable=true ORDER BY exp.averageScore DESC", ExperienceModel.class);
        query.setMaxResults(9);
        query.setParameter("category", category);
        return query.getResultList();
    }

    @Override
    public List<ExperienceModel> listExperiencesByName(String name, Optional<OrderByModel> order, int page, int pageSize) {
        String orderQuery;
        if (order.isPresent()) {
            orderQuery = order.get().getSqlQuery();
        } else {
            orderQuery = OrderByModel.OrderByRankDesc.getSqlQuery();
        }
        final TypedQuery<ExperienceModel> query = em.createQuery("SELECT exp FROM ExperienceModel exp WHERE (LOWER(exp.experienceName) LIKE LOWER(CONCAT('%', :name,'%')) OR LOWER(exp.description) LIKE LOWER(CONCAT('%', :name,'%')) OR LOWER(exp.address) LIKE LOWER(CONCAT('%', :name,'%')) OR LOWER(exp.city.cityName) LIKE LOWER(CONCAT('%', :name,'%')) OR LOWER(exp.city.country.countryName) LIKE LOWER(CONCAT('%', :name,'%'))) AND exp.observable=true " + orderQuery, ExperienceModel.class);
        query.setParameter("name", name);
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public long getCountByName(String name) {
        final TypedQuery<Long> query = em.createQuery("SELECT COUNT(exp) FROM ExperienceModel exp WHERE (LOWER(exp.experienceName) LIKE LOWER(CONCAT('%', :name,'%')) OR LOWER(exp.description) LIKE LOWER(CONCAT('%', :name,'%')) OR LOWER(exp.address) LIKE LOWER(CONCAT('%', :name,'%')) OR LOWER(exp.city.cityName) LIKE LOWER(CONCAT('%', :name,'%')) OR LOWER(exp.city.country.countryName) LIKE LOWER(CONCAT('%', :name,'%'))) AND exp.observable=true", Long.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public List<ExperienceModel> getExperiencesListByUser(String name, UserModel user, Optional<OrderByModel> order, int page, int pageSize) {
        LOGGER.debug("Get experiences of user with id {}", user.getUserId());
        String orderQuery;
        if (order.isPresent()) {
            orderQuery = order.get().getSqlQuery();
        } else {
            orderQuery = OrderByModel.OrderByAZ.getSqlQuery();
        }
        final TypedQuery<ExperienceModel> query = em.createQuery("SELECT exp FROM ExperienceModel exp WHERE LOWER(exp.experienceName) LIKE LOWER(CONCAT('%', :name,'%')) AND exp.user =:user " + orderQuery, ExperienceModel.class);
        query.setParameter("name", name);
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);
        query.setParameter("user", user);
        return query.getResultList();
    }

    @Override
    public long getCountExperiencesByUser(String name, UserModel user) {
        final TypedQuery<Long> query = em.createQuery("SELECT COUNT(exp) FROM ExperienceModel exp WHERE LOWER(exp.experienceName) LIKE LOWER(CONCAT('%', :name,'%')) AND exp.user =:user", Long.class);
        query.setParameter("name", name);
        query.setParameter("user", user);
        return query.getSingleResult();
    }

    @Override
    public List<ExperienceModel> getRecommendedByFavs(UserModel user){
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
                 "    )\n" +
                 "GROUP BY experienceid\n" +
                 "ORDER BY COUNT(experienceid) DESC");

        queryForIds.setParameter("userid", user.getUserId());
         List<Number> resultingIds = (List<Number>) queryForIds.getResultList();

        final TypedQuery<ExperienceModel> queryForExperiences = em.createQuery("SELECT exp FROM ExperienceModel exp WHERE exp.experienceId IN :idList", ExperienceModel.class);
        queryForExperiences.setParameter("idList",  resultingIds.stream().map(Number::longValue).collect(Collectors.toList()) );
        queryForExperiences.setMaxResults(9);

        return queryForExperiences.getResultList();
    }

    @Override
    public List<ExperienceModel> getRecommendedByViews(UserModel user) {
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
                        "    )\n" +
                        "GROUP BY experienceid\n" +
                        "ORDER BY COUNT(experienceid) DESC");

        queryForIds.setParameter("userid", user.getUserId());
        List<Number> resultingIds = (List<Number>) queryForIds.getResultList();

        final TypedQuery<ExperienceModel> queryForExperiences = em.createQuery("SELECT exp FROM ExperienceModel exp WHERE exp.experienceId IN :idList", ExperienceModel.class);
        queryForExperiences.setParameter("idList",  resultingIds.stream().map(Number::longValue).collect(Collectors.toList()) );
        queryForExperiences.setMaxResults(9);

        return queryForExperiences.getResultList();
    }

}