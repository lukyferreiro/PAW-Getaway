import {
 CategoryModel,
 CityModel,
 CountryModel,
 ExperienceModel,
 OrderByModel,
 ReviewModel,
 UserModel,
} from "../../types"

import {MockLocalStorage} from "./MockLocalStorage";

export const categoryModel1: CategoryModel = {
    id: 1,
    name: "Aventura",
    self: "http://localhost:8080/api/experiences/categories/1"
};

export const categoryModel2: CategoryModel = {
    id: 2,
    name: "Gastronomia",
    self: "http://localhost:8080/api/experiences/categories/2"
};

export const cityModel: CityModel = {
    id: 1,
    name: "Buenos Aires",
    self: "http://localhost:8080/api/countries/14/cities/1",
    countryUrl: "http://plocalhost:8080/api/countries/14"
};

export const countryModel: CountryModel = {
    id: 14,
    name: "Argentina",
    self: "http://localhost:8080/api/countries/14",
    citiesUrl: "http://localhost:8080/api/countries/14/cities"
};

export const userModel: UserModel = {
    id: 1,
    name: "Lucas",
    surname: "Ferreiro",
    email: "lferreiro@itba.edu.ar",
    isVerified: true,
    isProvider: true,
    hasImage: false,
    self: "http://localhost:8080/api/users/1",
    profileImageUrl: "http://localhost:8080/api/users/1/profileImage",
    experiencesUrl: "http://localhost:8080/api/experiences?userId=1&filter=PROVIDER",
    reviewsUrl: "http://localhost:8080/api/reviews?userId=1",
    favsUrl: "http://localhost:8080/api/experiences?userId=1&filter=FAVS",
    viewedUrl: "http://localhost:8080/api/experiences?userId=1&filter=VIEWED",
    recommendationsByFavsUrl: "http://localhost:8080/api/experiences?userId=1&filter=RECOMMENDED_BY_FAVS",
    recommendationsByReviewsUrl: "http://localhost:8080/api/experiences?userId=1&filter=RECOMMENDED_BY_REVIEWS",
};

export const orderByModel: OrderByModel = {
    orders: ["OrderByRankAsc", "OrderByRankDesc", "OrderByAZ", "OrderByZA", "OrderByLowPrice", "OrderByHighPrice"],
    self: "http://localhost:8080/api/experiences/orders"
};

export const experienceModelNoFav: ExperienceModel = {
    id: 1,
    name: "Experiencia comun",
    description: "Esto es una descripcion",
    address: "Av 9 de Julio",
    email: "example@example.com",
    price: 100,
    score: 4,
    views: 100,
    siteUrl: "https://google.com",
    observable: true,
    hasImage: false,
    reviewCount: 8,
    self: "http://localhost:8080/api/experiences/1",
    imageUrl: "http://localhost:8080/api/experiences/1/experienceImage",
    reviewsUrl: "http://localhost:8080/api/reviews?experienceId=1",
    cityUrl: "http://localhost:8080/api/countries/14/cities/1",
    userUrl: "http://localhost:8080/api/users/1",
    categoryUrl: "http://localhost:8080/api/experiences/categories/1",
};

export const experienceModelFav: ExperienceModel = {
    id: 2,
    name: "Experiencia fav",
    description: "Esto es una descripcion",
    address: "Av 9 de Julio",
    email: "example@example.com",
    price: 1000,
    score: 4,
    views: 100,
    siteUrl: "https://google.com",
    observable: true,
    hasImage: false,
    reviewCount: 8,
    self: "http://localhost:8080/api/experiences/2",
    imageUrl: "http://localhost:8080/api/experiences/2/experienceImage",
    reviewsUrl: "http://localhost:8080/api/reviews?experienceId=2",
    cityUrl: "http://localhost:8080/api/countries/14/cities/1",
    userUrl: "http://localhost:8080/api/users/1",
    categoryUrl: "http://localhost:8080/api/experiences/categories/1",
};

export const experienceModelCategory: ExperienceModel = {
    id: 3,
    name: "Experiencia de categoria 2",
    description: "Esto es una descripcion",
    address: "Av 9 de Julio",
    email: "example@example.com",
    price: 500,
    score: 4,
    views: 100,
    siteUrl: "https://google.com",
    observable: true,
    hasImage: false,
    reviewCount: 8,
    self: "http://localhost:8080/api/experiences/3",
    imageUrl: "http://localhost:8080/api/experiences/3/experienceImage",
    reviewsUrl: "http://localhost:8080/api/reviews?experienceId=3",
    cityUrl: "http://localhost:8080/api/countries/14/cities/1",
    userUrl: "http://localhost:8080/api/users/1",
    categoryUrl: "http://localhost:8080/api/experiences/categories/1",
};

export const experienceModelMaxPrice: ExperienceModel = {
    id: 4,
    name: "Experiencia de mayor precio",
    description: "Esto es la descripcion",
    address: "Av 9 de Julio",
    email: "example@example.com",
    price: 100000,
    score: 3,
    views: 100,
    siteUrl: "https://google.com",
    observable: true,
    hasImage: false,
    reviewCount: 8,
    self: "http://localhost:8080/api/experiences/4",
    imageUrl: "http://localhost:8080/api/experiences/4/experienceImage",
    reviewsUrl: "http://localhost:8080/api/reviews?experienceId=4",
    cityUrl: "http://localhost:8080/api/countries/14/cities/1",
    userUrl: "http://localhost:8080/api/users/1",
    categoryUrl: "http://localhost:8080/api/experiences/categories/1",
};

export const reviewModel1: ReviewModel = {
    id: 1,
    title: "Titulo de reseña",
    description: "Esta es mi descripcion de reseña",
    score: 4,
    date: "2023-02-02",
    self: "http://localhost:8080/api/reviews/1",
    userUrl: "http://localhost:8080/api/users/1",
    experienceUrl: "http://localhost:8080/api/experiences/1",
};

export const reviewModel2: ReviewModel = {
    id: 2,
    title: "Titulo de reseña 2",
    description: "Esta es mi descripcion de reseña",
    score: 2,
    date: "2023-02-02",
    self: "http://localhost:8080/api/reviews/1",
    userUrl: "http://localhost:8080/api/users/1",
    experienceUrl: "http://localhost:8080/api/experiences/1",
};


export function successfullyMockResponse(
    code: number,
    returnBody: any,
    headers?: Headers
) {
    global.localStorage = new MockLocalStorage();
    return (global.fetch = jest.fn().mockImplementationOnce(() => {
        return new Promise((resolve, reject) => {
            resolve({
                ok: true,
                status: code,
                headers: headers,
                json: () => {
                    return returnBody;
                },
            });
        });
    }));
}

test("", () => {
});