/**
 * @jest-environment jsdom
 */

import {experienceService, reviewService} from "../../../services";
import {
    successfullyMockResponse,
    experienceModelFav,
    experienceModelNoFav,
    experienceModelCategory,
    orderByModel,
    reviewModel1,
    reviewModel2,
    categoryModel1,
    categoryModel2, experienceModelMaxPrice,
} from "../../Mocks";

describe('Experience Service Test', () => {

    test("Should create a new experience", async () => {
        const headers = new window.Headers();
        headers.set("Location", "http://localhost:8080/api/experiences/5" );

        successfullyMockResponse(201, [], headers);

        return experienceService.createExperience(
            "Test experiencia",
            1,
            14,
            18,
            "Madero 123",
            "lferreiro@itba.edu.ar",
            500,
            "https://campus.com",
            "Esta es la descripcion de mi test"
        )
            .then((response) => {
                expect(response.hasFailed()).toBeFalsy();
                expect(response.getStatusCode()).toBe(201);
            });
    });

    test("Should get all experiences", async () => {
        const headers = new window.Headers();
        headers.set("X-Total-Pages", "1");

        successfullyMockResponse(200, [experienceModelFav, experienceModelNoFav, experienceModelCategory], headers);

        return experienceService.getExperiences()
            .then((response) => {
                expect(response.hasFailed()).toBeFalsy();
                expect(response.getData().getContent()[0]).toBe(experienceModelFav);
                expect(response.getData().getContent()[1]).toBe(experienceModelNoFav);
                expect(response.getData().getContent()[2]).toBe(experienceModelCategory);
                expect(response.getData().getContent().length).toBe(3);
            });
    });

    test("Should get experiences of category with id 2", async () => {
        const headers = new window.Headers();
        headers.set("X-Total-Pages", "1");

        successfullyMockResponse(200, [experienceModelCategory], headers);

        return experienceService.getExperiences("Gastronomia")
            .then((response) => {
                expect(response.hasFailed()).toBeFalsy();
                expect(response.getData().getContent()[0]).toBe(experienceModelCategory);
                expect(response.getData().getContent().length).toBe(1);
            });
    });

    test("Should get experiences with name", async () => {
        const headers = new window.Headers();
        headers.set("X-Total-Pages", "1");

        successfullyMockResponse(200, [experienceModelNoFav], headers);

        return experienceService.getExperiences(undefined, "Experiencia comun")
            .then((response) => {
                expect(response.hasFailed()).toBeFalsy();
                expect(response.getData().getContent()[0]).toBe(experienceModelNoFav);
                expect(response.getData().getContent().length).toBe(1);
            });
    });

    test("Should get max price of all categories", async () => {
        const headers = new window.Headers();
        headers.set("X-Total-Pages", "1");

        successfullyMockResponse(200, [experienceModelMaxPrice, experienceModelFav, experienceModelCategory, experienceModelNoFav], headers);
        return experienceService.getFilterMaxPrice()
            .then((response) => {
                expect(response.hasFailed()).toBeFalsy();
                expect(response.getData().getContent()[0]).toBe(experienceModelMaxPrice);
                expect(response.getData().getContent()[1]).toBe(experienceModelFav);
                expect(response.getData().getContent()[2]).toBe(experienceModelCategory);
                expect(response.getData().getContent()[3]).toBe(experienceModelNoFav);
                expect(response.getData().getContent().length).toBe(4);
            });
    });

    test("Should get order by models", async () => {
        successfullyMockResponse(200, orderByModel);
        return experienceService.getUserOrderByModels()
            .then((response) => {
                expect(response.hasFailed()).toBeFalsy();
                expect(response.getData()).toBe(orderByModel);
            });
    });

    test("Should get experience with id 1", async () => {
        successfullyMockResponse(200, experienceModelFav);

        return experienceService.getExperienceById(1)
            .then((response) => {
                expect(response.hasFailed()).toBeFalsy();
                expect(response.getData()).toBe(experienceModelFav);
            });
    });

    test("Should get experience name with id 1", async () => {
        successfullyMockResponse(200, experienceModelNoFav);

        return experienceService.getExperienceNameById(1)
            .then((response) => {
                expect(response.hasFailed()).toBeFalsy();
                expect(response.getData()).toBe(experienceModelNoFav);
            });
    });

    test("Should update experience image", async () => {
        successfullyMockResponse(204, {});

        return experienceService.updateExperienceImage(1, new window.File([], "image"))
            .then((response) => {
                expect(response.hasFailed()).toBeFalsy();
                expect(response.getStatusCode()).toBe(204);
            });
    });

    test("Should get all reviews of experience with id 1", async () => {
        const headers = new window.Headers();
        headers.set("X-Total-Pages", "1");

        successfullyMockResponse(200, [reviewModel1, reviewModel2], headers);

        return experienceService.getExperienceReviews(1)
            .then((response) => {
                expect(response.hasFailed()).toBeFalsy();
                expect(response.getData().getContent()[0]).toBe(reviewModel1);
                expect(response.getData().getContent()[1]).toBe(reviewModel2);
                expect(response.getData().getContent().length).toBe(2);
            });
    });

    test("Should create a new review in experience with id 1", async () => {
        const headers = new window.Headers();
        headers.set("Location", "http://localhost:8080/api/reviews/3" );

        successfullyMockResponse(201, [], headers);

        return reviewService.postNewReview(
            1,
            "Titulo de reseña nueva",
            "Mi descripcion",
            "3",
        )
            .then((response) => {
                expect(response.hasFailed()).toBeFalsy();
                expect(response.getStatusCode()).toBe(201);
            });
    });

    test("Should get all categories", async () => {
        successfullyMockResponse(200, [categoryModel1, categoryModel2]);
        return experienceService.getCategories()
            .then((response) => {
                expect(response.hasFailed()).toBeFalsy();
                expect(response.getData()[0]).toBe(categoryModel1);
                expect(response.getData()[1]).toBe(categoryModel2);
                expect(response.getData().length).toBe(2);
            });
    });
});