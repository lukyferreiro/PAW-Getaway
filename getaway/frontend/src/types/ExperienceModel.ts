import CityModel from "./CityModel";
import UserModel from "./UserModel";
import CategoryModel from "./CategoryModel";

export default interface ExperienceModel {
    experienceId: number;
    name: string;
    price: number;
    address: string;
    email: string;
    description: string;
    siteUrl: string;
    city: CityModel;
    category: CategoryModel;
    user: UserModel;
    observable: boolean;
    views: number;
    score: number;
    reviewsCount: number;
}