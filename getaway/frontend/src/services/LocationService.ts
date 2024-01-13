import { paths } from "../common";
import {CityModel, CountryModel, Result} from "../types";
import {resultFetch} from "../scripts/resultFetch";

export class LocationService {
    private readonly basePath = paths.BASE_URL + paths.LOCATION;

    public async getCountries(): Promise<Result<CountryModel[]>> {
        return resultFetch<CountryModel[]>(this.basePath, {
            method: "GET",
        });
    }

    public async getCountryById(countryId: number): Promise<Result<CountryModel>> {
        return resultFetch<CountryModel>(this.basePath + `/${countryId}`, {
            method: "GET",
        });
    }

    public async getCountryByLink(url: string): Promise<Result<CountryModel>> {
        return resultFetch<CountryModel>(url, {
            method: "GET",
        });
    }

    public async getCitiesByCountry(countryId: number): Promise<Result<CityModel[]>> {
        return resultFetch<CityModel[]>(this.basePath + `/${countryId}/cities`, {
            method: "GET",
        });
    }

    public async getCityById(countryId: number, cityId: number): Promise<Result<CityModel>> {
        return resultFetch<CityModel>(this.basePath + `/${countryId}/cities/${cityId}`, {
            method: "GET",
        });
    }

    public async getCityByLink(url: string): Promise<Result<CityModel>> {
        return resultFetch<CityModel>(url, {
            method: "GET",
        });
    }
}