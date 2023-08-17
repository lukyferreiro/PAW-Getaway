import ExperienceNameModel from "./ExperienceNameModel";
import {UserInfoModel} from "./index";

export default interface ReviewModel {
    id: number;
    title: string;
    description: string;
    score: number;
    date: string;
    self: string;
    userUrl: string;
    experienceUrl: string;
    userImage: string;
    user: UserInfoModel;
    experience: ExperienceNameModel;
}
