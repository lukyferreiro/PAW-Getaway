import { paths } from "../common";
import {CityModel, CountryModel, ErrorResponse, Result} from "../types";
import {resultFetch} from "../scripts/resultFetch";

export class LocationService {
    private readonly basePath = paths.BASE_URL + paths.LOCATION;

    public async getCountries(): Promise<Result<CountryModel[]>> {
        return resultFetch<CountryModel[]>(this.basePath, {
            method: "GET",
        });
    }

    public async getCitiesByCountry(countryId:number): Promise<Result<CityModel[]>> {
        return resultFetch<CityModel[]>(this.basePath + "/" + countryId + "/cities", {
            method: "GET",
        });
    }

    public async getCityById(cityId:number): Promise<Result<CityModel>> {
        return resultFetch<CityModel>(this.basePath + "/cities" + cityId, {
            method: "GET",
        });
    }
}