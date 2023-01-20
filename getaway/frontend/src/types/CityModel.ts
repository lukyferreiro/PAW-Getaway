import CountryModel from "./CountryModel";

export default interface CityModel {
    cityId: number;
    name: string;
    country: CountryModel;
}