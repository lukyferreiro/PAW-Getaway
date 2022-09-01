package Interfaces.Necessary.City;

import Models.Necessary.CityModel;

import java.util.List;
import java.util.Optional;

public interface CityDao {
    CityModel create(CityModel cityModel);
    boolean update(int cityId, CityModel cityModel);
    boolean delete(int cityId);
    Optional<CityModel> getById(long cityId);
    List<CityModel> list();
}
