import ExperienceModel from "./ExperienceModel";
import UserModel from "./UserModel";

export default interface ReviewModel {
    id: number;
    title: string;
    description: string;
    score: number;
    date: string;
    experience: ExperienceModel;
    user: UserModel;
}
