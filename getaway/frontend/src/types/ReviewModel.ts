import ExperienceNameModel from "./ExperienceNameModel";
import {UserInfoModel} from "./index";

export default interface ReviewModel {
    id: number;
    title: string;
    description: string;
    score: number;
    date: string;
    experience: ExperienceNameModel;
    user: UserInfoModel;
}
