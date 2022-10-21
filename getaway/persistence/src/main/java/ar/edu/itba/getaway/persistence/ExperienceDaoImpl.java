package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.interfaces.persistence.ExperienceDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

@Repository
public class ExperienceDaoImpl implements ExperienceDao {

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceDaoImpl.class);

    @Override
    public ExperienceModel createExperience(String name, String address, String description, String email, String url,
                                            Double price, CityModel city, CategoryModel category, UserModel user, ImageModel experienceImage) {

        final ExperienceModel experience = new ExperienceModel(name, address, description, email, url, price, city, category, user, experienceImage);
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
    public Optional<ExperienceModel> getExperienceById(Long experienceId) {
        LOGGER.debug("Get experience with id {}", experienceId);
        return Optional.ofNullable(em.find(ExperienceModel.class, experienceId));
    }

    @Override
    public List<ExperienceModel> listExperiencesByUser(UserModel user, CategoryModel category) {
        LOGGER.debug("Get experiences of category {} of user with id {}", category.getCategoryName(),user.getUserId());
        final TypedQuery<ExperienceModel> query = em.createQuery("FROM ExperienceModel WHERE user = :user AND category = :category", ExperienceModel.class);
        query.setParameter("user", user);
        query.setParameter("category", category);
        return query.getResultList();
    }

    @Override
    public Optional<Double> getMaxPriceByCategory (CategoryModel category){
        LOGGER.debug("Get maxprice of category {}", category.getCategoryName());
//        final TypedQuery<Double> query = em.createQuery("SELECT Max(exp.price) FROM ExperienceModel exp WHERE exp.category = :category", Double.class);
//        query.setParameter("category", category);
//        return Optional.ofNullable(query.getSingleResult());
        return Optional.ofNullable(0D);
    }

    @Override
    public List<ExperienceModel> listExperiencesByFilter(CategoryModel category, Double max, Long score, CityModel city, Optional<OrderByModel> order, Integer page, Integer page_size) {
        String orderQuery;
        if (order.isPresent()){
            orderQuery = order.get().getSqlQuery();
        }
        else {
            orderQuery = " ";
        }

        if (city != null){
            final TypedQuery<ExperienceModel> query = em.createQuery("SELECT exp FROM ExperienceModel exp WHERE exp.category=:category AND exp.city=:city ", ExperienceModel.class);
//                    query = "SELECT COALESCE(COUNT (experienceName), 0) FROM experiences LEFT JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE categoryid = ? AND COALESCE(price,0) <=? AND cityId = ?" +
//                    " HAVING AVG(COALESCE(score,0))>=?";
//            LOGGER.debug("Executing query: {}", query);
//            return jdbcTemplate.queryForObject(query, new Object[]{categoryId, max, city, score}, Integer.class);
            query.setParameter("category", category);
            query.setParameter("city", city);
            query.setFirstResult((page - 1) * page_size);
            query.setMaxResults(page_size);
            return query.getResultList();
        }
        else {
//            query = "SELECT COALESCE(COUNT (experienceName), 0) FROM experiences LEFT JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE categoryid = ? AND COALESCE(price,0) <=? " +
//                    " HAVING AVG(COALESCE(score,0))>=?";
//            LOGGER.debug("Executing query: {}", query);
//            return jdbcTemplate.queryForObject(query, new Object[]{categoryId, max, score}, Integer.class);
            final TypedQuery<ExperienceModel> query = em.createQuery("SELECT exp FROM ExperienceModel exp WHERE exp.category=:category", ExperienceModel.class);
            query.setParameter("category", category);
//            query.setParameter("price", max);
            query.setFirstResult((page - 1) * page_size);
            query.setMaxResults(page_size);
            return query.getResultList();
        }
    }

    @Override
    public Long countListByFilter(CategoryModel category, Double max, Long score, CityModel city) {
        if (city != null){
            //Add
            final TypedQuery<Long> query = em.createQuery("SELECT COUNT(exp) FROM ExperienceModel exp WHERE exp.category=:category AND exp.city=:city ", Long.class);
//                    query = "SELECT COALESCE(COUNT (experienceName), 0) FROM experiences LEFT JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE categoryid = ? AND COALESCE(price,0) <=? AND cityId = ?" +
//                    " HAVING AVG(COALESCE(score,0))>=?";
//            LOGGER.debug("Executing query: {}", query);
//            return jdbcTemplate.queryForObject(query, new Object[]{categoryId, max, city, score}, Integer.class);
            query.setParameter("category", category);
//            query.setParameter("price", max);
            query.setParameter("city", city);
            return query.getSingleResult();
        }
        else {
//            query = "SELECT COALESCE(COUNT (experienceName), 0) FROM experiences LEFT JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE categoryid = ? AND COALESCE(price,0) <=? " +
//                    " HAVING AVG(COALESCE(score,0))>=?";
//            LOGGER.debug("Executing query: {}", query);
//            return jdbcTemplate.queryForObject(query, new Object[]{categoryId, max, score}, Integer.class);
            final TypedQuery<Long> query = em.createQuery("SELECT COUNT(exp) FROM ExperienceModel exp WHERE exp.category=:category", Long.class);
            query.setParameter("category", category);
//            query.setParameter("price", max);
            return query.getSingleResult();
        }
    }

    @Override
    public List<ExperienceModel> listExperiencesByBestRanked(CategoryModel category){
//        final String query = "SELECT experiences.experienceId, experienceName, address, experiences.description, email, siteUrl, price, cityId, categoryId, experiences.userId FROM experiences LEFT JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE categoryid = ? GROUP BY experiences.experienceid ORDER BY AVG(COALESCE(score,0)) DESC LIMIT 12 ";
//        LOGGER.debug("Executing query: {}", query);
//        return jdbcTemplate.query(query, new Object[]{categoryId}, EXPERIENCE_MODEL_ROW_MAPPER);
        final TypedQuery<ExperienceModel> query = em.createQuery("SELECT exp FROM ExperienceModel exp WHERE exp.category = :category", ExperienceModel.class);
        query.setMaxResults(6);
        query.setParameter("category", category);
        return query.getResultList();
    }

    @Override
    public List<ExperienceModel> listExperiencesFavsByUser(UserModel user, Optional<OrderByModel> order, Integer page, Integer page_size) {
        String orderQuery;
        if (order.isPresent()){
            orderQuery = order.get().getSqlQuery();
        }
        else {
            orderQuery = " ";
        }

//        final String query = "SELECT experiences.experienceId, experienceName, address, experiences.description, email, siteUrl, price, cityId, categoryId, experiences.userId FROM experiences LEFT JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE experiences.experienceId IN ( SELECT favuserexperience.experienceId FROM favuserexperience WHERE userId = ? ) GROUP BY experiences.experienceid HAVING AVG(COALESCE(score,0))>=0 " + orderQuery
//                + " LIMIT ? OFFSET ?";
//        LOGGER.debug("Executing query: {}", query);
//        return jdbcTemplate.query(query, new Object[]{userId, page_size, (page-1)*page_size}, EXPERIENCE_MODEL_ROW_MAPPER);
        return new ArrayList<>(user.getFavExperiences());
    }

    @Override
    public Integer getCountExperiencesFavsByUser(UserModel user){
//        final String query = "SELECT COALESCE(COUNT (experienceId), 1) FROM favuserexperience WHERE userId = ? ";
//        LOGGER.debug("Executing query: {}", query);
//        return jdbcTemplate.queryForObject(query, new Object[]{userId}, Integer.class);
        return user.getFavExperiences().size();
    }


    @Override
    public List<ExperienceModel> listExperiencesByName(String name, Optional<OrderByModel> order, Integer page, Integer page_size) {
//        String orderQuery;
//        if (order.isPresent()){
//            orderQuery = order.get().getSqlQuery();
//        }
//        else {
//            orderQuery = " ";
//        }
//
//        final String query = "SELECT experiences.experienceId, experienceName, address, experiences.description, email, siteUrl, price, cityId, categoryId, experiences.userId FROM experiences LEFT JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE LOWER(experienceName) LIKE LOWER(?) GROUP BY experiences.experienceid HAVING AVG(COALESCE(score,0))>=0 " + orderQuery + " LIMIT ? OFFSET ?";
//        LOGGER.debug("Executing query: {}", query);
//        return jdbcTemplate.query(query, new Object[]{'%'+name+'%', page_size, (page-1)*page_size}, EXPERIENCE_MODEL_ROW_MAPPER);
        final TypedQuery<ExperienceModel> query = em.createQuery("SELECT exp FROM ExperienceModel exp WHERE LOWER(exp.experienceName) LIKE LOWER(CONCAT('%', :name,'%'))", ExperienceModel.class);
        query.setParameter("name", name);
        query.setFirstResult((page - 1) * page_size);
        query.setMaxResults(page_size);
        return query.getResultList();
    }

    @Override
    public Long getCountByName(String name) {
//        final String query = "SELECT COALESCE(COUNT (experienceName), 1) FROM experiences WHERE LOWER(experienceName) LIKE LOWER(?) ";
//        LOGGER.debug("Executing query: {}", query);
//        return jdbcTemplate.queryForObject(query, new Object[]{'%'+name+'%'}, Integer.class);
        final TypedQuery<Long> query = em.createQuery("SELECT COUNT(exp) FROM ExperienceModel exp WHERE LOWER(exp.experienceName) LIKE LOWER(CONCAT('%', :name,'%'))", Long.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public boolean hasExperiencesByUser(UserModel user){
//        final String query = "SELECT COUNT(*) FROM experiences WHERE userId = ? ";
//        LOGGER.debug("Executing query: {}", query);
//        return jdbcTemplate.queryForObject(query, new Object[]{userId}, Integer.class) == 0;
        final TypedQuery<ExperienceModel> query = em.createQuery("SELECT exp FROM ExperienceModel exp WHERE exp.user = :user ", ExperienceModel.class);
        query.setParameter("user", user);
        return query.getResultList().size() != 0;
    }

    @Override
    public boolean experienceBelongsToUser(UserModel user, ExperienceModel experience){
//        final String query = "SELECT COUNT(*) FROM experiences WHERE userId = ? AND experienceid = ? ";
//        LOGGER.debug("Executing query: {}", query);
//        return jdbcTemplate.queryForObject(query, new Object[]{userId, experienceId}, Integer.class) == 1;
        return experience.getUser().equals(user);
    }
}