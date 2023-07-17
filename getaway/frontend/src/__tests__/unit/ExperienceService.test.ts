/**
 * @jest-environment jsdom
 */

import {experienceService} from "../../services";
import {
    successfullyMockResponse,
    experienceModelFav,
    experienceModelNoFav,
    experienceModelCategory,
    maxPriceModel,
    orderByModel,
    reviewModel1,
    reviewModel2,
} from "../Mocks";

test("Should create a new experience", async () => {
    successfullyMockResponse(204, []);

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
            expect(response.hasFailed()).toBeTruthy();
            expect(response.getError().getStatus()).toBe(204);
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
        });
});

test("Should get max price of all categories", async () => {
    successfullyMockResponse(200, maxPriceModel);
    return experienceService.getFilterMaxPrice()
        .then((response) => {
            expect(response.hasFailed()).toBeFalsy();
            expect(response.getData()).toBe(maxPriceModel);
        });
});

test("Should get order by models", async () => {
    successfullyMockResponse(200, [orderByModel]);
    return experienceService.getUserOrderByModels()
        .then((response) => {
            expect(response.hasFailed()).toBeFalsy();
            expect(response.getData()[0]).toBe(orderByModel);
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
    successfullyMockResponse(200, experienceModelFav);

    return experienceService.getExperienceNameById(1)
        .then((response) => {
            expect(response.hasFailed()).toBeFalsy();
            expect(response.getData()).toBe(experienceModelFav);
        });
});

test("Should update experience image", async () => {
    successfullyMockResponse(204, {});

    return experienceService.updateExperienceImage(1, new window.File([], "image"))
        .then((response) => {
            expect(response.hasFailed()).toBeTruthy();
            expect(response.getError().getStatus()).toBe(204);
        });
});

test("Should get all reviews of experience with id 1", async () => {
    const headers = new window.Headers();
    headers.set("X-Total-Pages", "1");

    successfullyMockResponse(200, [reviewModel1, reviewModel2], headers);

    return experienceService.getExperienceReviews(1)
        .then((ans) => {
            expect(ans.hasFailed()).toBeFalsy();
            expect(ans.getData().getContent()[0]).toBe(reviewModel1);
            expect(ans.getData().getContent()[1]).toBe(reviewModel2);
        });
});

test("Should create a new review in experience with id 1", async () => {
    successfullyMockResponse(204, []);

    return experienceService.postNewReview(
        1,
        "Titulo de reseña nueva",
        "Mi descripcion",
        "3",
    )
        .then((response) => {
            expect(response.hasFailed()).toBeTruthy();
            expect(response.getError().getStatus()).toBe(204);
        });
});