export default interface ExperienceModel {
    id: number;
    name: string;
    description?: string;
    address: string;
    email: string;
    price?: number;
    score: number;
    views: number;
    siteUrl?: string;
    isFav: boolean;
    observable: boolean;
    hasImage: boolean;
    reviewCount: number;
    self: string;
    imageUrl: string;
    reviewsUrl: string;
    cityUrl: string;
    userUrl: string;
    categoryUrl: string;
}