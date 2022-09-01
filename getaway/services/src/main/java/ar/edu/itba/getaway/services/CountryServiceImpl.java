package Interfaces.Necessary.Country;

import Models.Necessary.CountryModel;

import java.util.List;
import java.util.Optional;

public class CountryServiceImpl implements CountryService{
    @Autowired
    private CountryDao countryDao;

    @Override
    public CountryModel create (CountryModel countryModel){
        return countryDao.create(countryModel);
    }

    @Override
    public boolean update(long countryId, CountryModel countryModel){
        return countryDao.update(countryId, countryModel);
    }

    @Override
    public boolean delete (long countryId){
        return countryDao.delete(countryId);
    }

    @Override
    public List<CountryModel> list() {
        return countryDao.list();
    }

    @Override
    public Optional<CountryModel> getById (long countryId){
        return countryDao.getById(countryId);
    }
}

