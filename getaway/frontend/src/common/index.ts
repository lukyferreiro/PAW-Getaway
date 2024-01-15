import Aventura from "../images/Aventura.svg";
import Gastronomia from "../images/Gastronomia.svg";
import Historico from "../images/Historico.svg";
import Hoteleria from "../images/Hoteleria.svg";
import Relax from "../images/Relax.svg";
import Vida_nocturna from "../images/Vida_nocturna.svg";

export const paths = {
    // LOCAL_BASE_URL : '/',
    // BASE_URL: 'http://localhost:8080/webapp_war/api',
    LOCAL_BASE_URL : '/paw-2022b-1/',
    BASE_URL: 'http://pawserver.it.itba.edu.ar/paw-2022b-1/api',
    EXPERIENCES: '/experiences',
    LOCATION: '/countries',
    REVIEWS: '/reviews',
    USERS: '/users',
};

export const APPLICATION_JSON_TYPE = 'application/json';

export const ERROR_V1 = "application/vnd.getaway.error.v1+json";
export const USER_V1 = "application/vnd.getaway.user.v1+json";
export const USER_INFO_V1 = "application/vnd.getaway.userInfo.v1+json";
export const USER_PASSWORD_V1 = "application/vnd.getaway.patch.password.v1+json";
export const USER_PASSWORD_EMAIL_V1 = "application/vnd.getaway.passwordEmail.v1+json";
export const EXPERIENCE_V1 = "application/vnd.getaway.experience.v1+json";
export const EXPERIENCE_VISIBILITY_V1 = "application/vnd.getaway.experience.patch.visibility.v1+json";
export const EXPERIENCE_LIST_V1 = "application/vnd.getaway.experienceList.v1+json";
export const REVIEW_V1 = "application/vnd.getaway.review.v1+json";
export const REVIEW_LIST_V1 = "application/vnd.getaway.reviewList.v1+json";
export const COUNTRY_V1 = "application/vnd.getaway.country.v1+json";
export const COUNTRY_LIST_V1 = "application/vnd.getaway.countryList.v1+json";
export const CITY_V1 = "application/vnd.getaway.city.v1+json";
export const CITY_LIST_V1 = "application/vnd.getaway.cityList.v1+json";
export const CATEGORY_V1 = "application/vnd.getaway.category.v1+json";
export const CATEGORY_LIST_V1 = "application/vnd.getaway.categoryList.v1+json";
export const ORDER_LIST_V1 = "application/vnd.getaway.orderList.v1+json";

export type CategoryName = "Aventura" | "Gastronomia" | "Historico" | "Hoteleria" | "Relax" | "Vida_nocturna";

const categoryImages: Record<CategoryName, any> = {
    Aventura,
    Gastronomia,
    Historico,
    Hoteleria,
    Relax,
    Vida_nocturna
};

export default categoryImages;