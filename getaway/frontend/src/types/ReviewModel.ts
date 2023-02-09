import UserModel from "./UserModel";
import ExperienceNameModel from "./ExperienceNameModel";

export default interface ReviewModel {
    id: number;
    title: string;
    description: string;
    score: number;
    date: string;
    experience: ExperienceNameModel;
    user: UserModel;
}
