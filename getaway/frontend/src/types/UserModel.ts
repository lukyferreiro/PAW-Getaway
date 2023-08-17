export default interface CurrentUserModel {
    id: number;
    name: string;
    surname: string;
    email: string;
    isVerified: boolean;
    isProvider: boolean;
    hasImage: boolean;
    self: string;
    profileImageUrl: string;
    experiencesUrl: string;
    reviewsUrl: string;
    favsUrl: string;
    viewedUrl: string;
    recommendationsByFavsUrl: string;
    recommendationsByReviewsUrl: string;
}