package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.CityModel;
import ar.edu.itba.getaway.models.CountryModel;
import ar.edu.itba.getaway.interfaces.persistence.LocationDao;
import ar.edu.itba.getaway.models.OrderByModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class LocationDaoImpl implements LocationDao {
    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationDaoImpl.class);

    @Override
    public Optional<CityModel> getCityById(long cityId) {
        LOGGER.debug("Get city with id {}", cityId);
        return Optional.ofNullable(em.find(CityModel.class, cityId));
    }

    @Override
    public List<CityModel> getCitiesByCountry(CountryModel country) {
        LOGGER.debug("Get cities from country with countryId {}", country.getCountryId());
        final TypedQuery<CityModel> query = em.createQuery("FROM CityModel WHERE country = :country ORDER BY cityName ASC" , CityModel.class);
        query.setParameter("country", country);
        return query.getResultList();
    }

    @Override
    public Optional<CityModel> getCityByName(String cityName) {
        LOGGER.debug("Get city with name {}", cityName);
        final TypedQuery<CityModel> query = em.createQuery("FROM CityModel WHERE cityName = :cityName", CityModel.class);
        query.setParameter("cityName", cityName);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public List<CountryModel> listAllCountries() {
        LOGGER.debug("List all countries");
        return em.createQuery("FROM CountryModel ORDER BY countryname ASC", CountryModel.class).getResultList();
    }

    @Override
    public Optional<CountryModel> getCountryById(long countryId) {
        LOGGER.debug("Get country with id {}", countryId);
        return Optional.ofNullable(em.find(CountryModel.class, countryId));
    }

    @Override
    public Optional<CountryModel> getCountryByName(String countryName) {
        LOGGER.debug("Get country with name {}", countryName);
        final TypedQuery<CountryModel> query = em.createQuery("FROM CountryModel WHERE countryName = :countryName", CountryModel.class);
        query.setParameter("countryName", countryName);
        return query.getResultList().stream().findFirst();
    }
}
