import ExperienceModel from "./ExperienceModel";

export default interface userLandingPageModel {
    viewed: ExperienceModel[];
    recommendedByFavs: ExperienceModel[];
    recommendedByReviews: ExperienceModel[];
}
