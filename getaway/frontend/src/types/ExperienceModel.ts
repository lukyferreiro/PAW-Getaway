import CityModel from "./CityModel";
import CategoryModel from "./CategoryModel";
import CountryModel from "./CountryModel";
import {UserInfoModel} from "./index";

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
    user: UserInfoModel;
    observable: boolean;
    views: number;
    score: number;
    reviewCount: number;
    fav: boolean;
    hasImage: boolean;
    imageUrl: string;
}