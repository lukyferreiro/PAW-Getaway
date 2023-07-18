import {
    CategoryModel,
    CityModel,
    CountryModel,
    ExperienceModel,
    ExperienceNameModel,
    MaxPriceModel,
    OrderByModel,
    ReviewModel,
    UserModel,
} from "../../types"

import {MockLocalStorage} from "./MockLocalStorage";

export const categoryModel1: CategoryModel = {
    id: 1,
    name: "Aventura",
};

export const categoryModel2: CategoryModel = {
    id: 2,
    name: "Gastronomia",
};

export const cityModel: CityModel = {
    id: 1,
    name: "Buenos Aires",
};

export const countryModel: CountryModel = {
    id: 14,
    name: "Argentina",
};

export const userModel: UserModel = {
    userId: 1,
    name: "Lucas",
    surname: "Ferreiro",
    sub: "lferreiro@itba.edu.ar",
    isVerified: true,
    isProvider: true,
    hasImage: false,
    profileImageUrl: ""
};

export const orderByModel: OrderByModel = {
    order: "OrderByAZ",
};

export const maxPriceModel: MaxPriceModel = {
    maxPrice: 1000,
};

export const experienceNameModel: ExperienceNameModel = {
    id: 1,
    name: "Experiencia comun",
};

export const experienceModelNoFav: ExperienceModel = {
    id: 1,
    name: "Experiencia comun",
    price: 100,
    address: "Av 9 de Julio",
    email: "example@example.com",
    description: "Esto es una descripcion",
    siteUrl: "https://google.com",
    city: cityModel,
    country: countryModel,
    category: categoryModel1,
    user: userModel,
    observable: true,
    views: 100,
    score: 4,
    reviewCount: 8,
    fav: false,
    hasImage: false,
    imageUrl: ""

};

export const experienceModelFav: ExperienceModel = {
    id: 2,
    name: "Experiencia fav",
    price: 1000,
    address: "Av 9 de Julio",
    email: "example@example.com",
    description: "Esto es una descripcion",
    siteUrl: "https://google.com",
    city: cityModel,
    country: countryModel,
    category: categoryModel1,
    user: userModel,
    observable: true,
    views: 100,
    score: 4,
    reviewCount: 8,
    fav: true,
    hasImage: false,
    imageUrl: ""

};

export const experienceModelCategory: ExperienceModel = {
    id: 3,
    name: "Experiencia de categoria 2",
    price: 500,
    address: "Av 9 de Julio",
    email: "example@example.com",
    description: "Esto es una descripcion",
    siteUrl: "https://google.com",
    city: cityModel,
    country: countryModel,
    category: categoryModel2,
    user: userModel,
    observable: true,
    views: 100,
    score: 4,
    reviewCount: 8,
    fav: true,
    hasImage: false,
    imageUrl: ""
};

export const reviewModel1: ReviewModel = {
    id: 1,
    title: "Titulo de reseña",
    description: "Esta es mi descripcion de reseña",
    score: 4,
    date: "2023-02-02",
    experience: experienceNameModel,
    user: userModel,
};

export const reviewModel2: ReviewModel = {
    id: 2,
    title: "Titulo de reseña 2",
    description: "Esta es mi descripcion de reseña",
    score: 2,
    date: "2023-02-02",
    experience: experienceNameModel,
    user: userModel,
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