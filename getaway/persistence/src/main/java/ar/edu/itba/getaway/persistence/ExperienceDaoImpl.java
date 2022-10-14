package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.interfaces.persistence.ExperienceDao;
import ar.edu.itba.getaway.interfaces.persistence.ImageDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;
import java.util.*;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ExperienceDaoImpl implements ExperienceDao {

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceDaoImpl.class);

//    private final RowMapper<ExperienceModel> EXPERIENCE_MODEL_ROW_MAPPER = (rs, rowNum) ->
//            new ExperienceModel(rs.getLong("experienceid"),
//                    rs.getString("experienceName"),
//                    rs.getString("address"),
//                    rs.getString("description"),
//                    rs.getString("email"),
//                    rs.getString("siteUrl"),
//                    (rs.getObject("price") == null) ? null : rs.getBigDecimal("price").doubleValue(),
//                    rs.getLong("cityId"),
//                    rs.getLong("categoryId"),
//                    rs.getLong("userId"),
//                    getImageIdByExperienceId(rs.getLong("experienceid")),
//                    getHasImage(rs.getLong("experienceid")));
//
//    //TODO ver si se pueden sacar estos metodos de aca
//    private Long getImageIdByExperienceId(Long experienceId){
//        return imageDao.getImgByExperienceId(experienceId).get().getImageId();
//    }
//    private boolean getHasImage(Long experienceId){
//        return imageDao.getImgByExperienceId(experienceId).get().getImage() != null;
//    }

    @Override
    public ExperienceModel createExperience(String name, String address, String description, String email, String url,
                                            Double price, CityModel city, CategoryModel category, UserModel user) {

        final ExperienceModel experience = new ExperienceModel(name, address, description, email, url, price, city, category, user);
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
        final TypedQuery<Double> query = em.createQuery("SELECT Max(exp.price) FROM ExperienceModel exp WHERE exp.category = :category", Double.class);
        query.setParameter("category", category);
        return Optional.ofNullable(query.getSingleResult());
    }

//    @Override
//    public List<ExperienceModel> listExperiencesByFilter(Long categoryId, Double max, Long score, Long city, Optional<OrderByModel> order, Integer page, Integer page_size) {
//        String orderQuery;
//        if (order.isPresent()){
//            orderQuery = order.get().getSqlQuery();
//        }
//        else {
//            orderQuery = " ";
//        }
//
//        String query;
//        if (city > 0){
//            query = "SELECT experiences.experienceId, experienceName, address, experiences.description, email, siteUrl, price, cityId, categoryId, experiences.userId FROM experiences LEFT JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE categoryid = ? AND COALESCE(price,0) <=? AND cityId = ? " +
//                    "GROUP BY experiences.experienceid HAVING AVG(COALESCE(score,0))>=? " + orderQuery
//                    + " LIMIT ? OFFSET ?";
//            LOGGER.debug("Executing query: {}", query);
//            return jdbcTemplate.query(query, new Object[]{categoryId, max, city, score, page_size, (page-1)*page_size}, EXPERIENCE_MODEL_ROW_MAPPER);
//        }
//        else {
//            query = "SELECT experiences.experienceId, experienceName, address, experiences.description, email, siteUrl, price, cityId, categoryId, experiences.userId FROM experiences LEFT JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE categoryid = ? AND COALESCE(price,0) <=? " +
//                    "GROUP BY experiences.experienceid HAVING AVG(COALESCE(score,0))>=?" + orderQuery
//                    + " LIMIT ? OFFSET ?";
//            LOGGER.debug("Executing query: {}", query);
//            return jdbcTemplate.query(query, new Object[]{categoryId, max, score, page_size, (page-1)*page_size}, EXPERIENCE_MODEL_ROW_MAPPER);
//        }
//    }
//
//    @Override
//    public Integer countListByFilter(Long categoryId, Double max, Long score, Long city) {
//        String query;
//        if (city > 0){
//            query = "SELECT COALESCE(COUNT (experienceName), 0) FROM experiences LEFT JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE categoryid = ? AND COALESCE(price,0) <=? AND cityId = ?" +
//                    " HAVING AVG(COALESCE(score,0))>=?";
//            LOGGER.debug("Executing query: {}", query);
//            return jdbcTemplate.queryForObject(query, new Object[]{categoryId, max, city, score}, Integer.class);
//        }
//        else {
//            query = "SELECT COALESCE(COUNT (experienceName), 0) FROM experiences LEFT JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE categoryid = ? AND COALESCE(price,0) <=? " +
//                    " HAVING AVG(COALESCE(score,0))>=?";
//            LOGGER.debug("Executing query: {}", query);
//            return jdbcTemplate.queryForObject(query, new Object[]{categoryId, max, score}, Integer.class);
//        }
//    }
//
//    @Override
//    public List<ExperienceModel> listExperiencesByBestRanked(Long categoryId){
//        final String query = "SELECT experiences.experienceId, experienceName, address, experiences.description, email, siteUrl, price, cityId, categoryId, experiences.userId FROM experiences LEFT JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE categoryid = ? GROUP BY experiences.experienceid ORDER BY AVG(COALESCE(score,0)) DESC LIMIT 12 ";
//        LOGGER.debug("Executing query: {}", query);
//        return jdbcTemplate.query(query, new Object[]{categoryId}, EXPERIENCE_MODEL_ROW_MAPPER);
//    }
//
//    @Override
//    public List<ExperienceModel> listExperiencesFavsByUserId(Long userId, Optional<OrderByModel> order, Integer page, Integer page_size) {
//        String orderQuery;
//        if (order.isPresent()){
//            orderQuery = order.get().getSqlQuery();
//        }
//        else {
//            orderQuery = " ";
//        }
//
//        final String query = "SELECT experiences.experienceId, experienceName, address, experiences.description, email, siteUrl, price, cityId, categoryId, experiences.userId FROM experiences LEFT JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE experiences.experienceId IN ( SELECT favuserexperience.experienceId FROM favuserexperience WHERE userId = ? ) GROUP BY experiences.experienceid HAVING AVG(COALESCE(score,0))>=0 " + orderQuery
//                + " LIMIT ? OFFSET ?";
//        LOGGER.debug("Executing query: {}", query);
//        return jdbcTemplate.query(query, new Object[]{userId, page_size, (page-1)*page_size}, EXPERIENCE_MODEL_ROW_MAPPER);
//    }
//
//    @Override
//    public Integer getCountExperiencesFavsByUserId(Long userId){
//        final String query = "SELECT COALESCE(COUNT (experienceId), 1) FROM favuserexperience WHERE userId = ? ";
//        LOGGER.debug("Executing query: {}", query);
//        return jdbcTemplate.queryForObject(query, new Object[]{userId}, Integer.class);
//    }
//
//
//    @Override
//    public List<ExperienceModel> listExperiencesByName(String name, Optional<OrderByModel> order, Integer page, Integer page_size) {
//        String orderQuery;
//        if (order.isPresent()){
//            orderQuery = order.get().getSqlQuery();
//        }
//        else {
//            orderQuery = " ";
//        }
//        final String query = "SELECT experiences.experienceId, experienceName, address, experiences.description, email, siteUrl, price, cityId, categoryId, experiences.userId FROM experiences LEFT JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE LOWER(experienceName) LIKE LOWER(?) GROUP BY experiences.experienceid HAVING AVG(COALESCE(score,0))>=0 " + orderQuery + " LIMIT ? OFFSET ?";
//        LOGGER.debug("Executing query: {}", query);
//        return jdbcTemplate.query(query, new Object[]{'%'+name+'%', page_size, (page-1)*page_size}, EXPERIENCE_MODEL_ROW_MAPPER);
//    }
//
//    @Override
//    public Integer getCountByName(String name) {
//        final String query = "SELECT COALESCE(COUNT (experienceName), 1) FROM experiences WHERE LOWER(experienceName) LIKE LOWER(?) ";
//        LOGGER.debug("Executing query: {}", query);
//        return jdbcTemplate.queryForObject(query, new Object[]{'%'+name+'%'}, Integer.class);
//    }
//
//    @Override
//    public boolean hasExperiencesByUserId(Long userId){
//        final String query = "SELECT COUNT(*) FROM experiences WHERE userId = ? ";
//        LOGGER.debug("Executing query: {}", query);
//        return jdbcTemplate.queryForObject(query, new Object[]{userId}, Integer.class) == 0;
//    }
//
//    @Override
//    public boolean experiencesBelongsToId(Long userId, Long experienceId){
//        final String query = "SELECT COUNT(*) FROM experiences WHERE userId = ? AND experienceid = ? ";
//        LOGGER.debug("Executing query: {}", query);
//        return jdbcTemplate.queryForObject(query, new Object[]{userId, experienceId}, Integer.class) == 1;
//    }
}