import CityModel from "./CityModel";
import UserModel from "./UserModel";
import CategoryModel from "./CategoryModel";
import CountryModel from "./CountryModel";

export default interface ExperienceModel {
    id: number;
    name: string;
    price?: number;
    address: string;
    email: string;
    description?: string;
    siteUrl?: string;
    city: CityModel;
    country: CountryModel;
    category: CategoryModel;
    user: UserModel;
    observable: boolean;
    views: number;
    score: number;
    reviewCount: number;
    fav?: boolean;
}