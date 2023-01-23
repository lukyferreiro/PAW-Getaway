import ExperienceModel from "./ExperienceModel";
import UserModel from "./UserModel";

export default interface ReviewModel {
    reviewId: number;
    title: string;
    description: string;
    score: number;
    date: string;
    experience: ExperienceModel;
    user: UserModel;

}
