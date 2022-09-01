package Interfaces.Necessary.City;


import Models.Necessary.CityModel;

import java.util.List;
import java.util.Optional;

public interface CityService {
    CityModel create (CityModel cityModel);
    boolean update (long cityId, CityModel cityModel);
    boolean delete (long cityId);
    List<CityModel> list();
    Optional<CityModel> getByID (long cityId);
}
