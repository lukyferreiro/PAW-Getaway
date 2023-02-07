import CountryModel from "./CountryModel";

export default interface CityModel {
    id: number;
    name: string;
    country: CountryModel;
}