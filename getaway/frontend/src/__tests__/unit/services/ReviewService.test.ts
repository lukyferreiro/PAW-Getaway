/**
 * @jest-environment jsdom
 */

import {experienceService, reviewService} from "../../../services";
import {
    successfullyMockResponse,
    reviewModel1,
} from "../../Mocks";

describe('Review Service Test', () => {
    test("Should get review with id 1", async () => {
        successfullyMockResponse(200, reviewModel1);

        return reviewService.getReviewById(1)
            .then((response) => {
                expect(response.hasFailed()).toBeFalsy();
                expect(response.getData()).toBe(reviewModel1);
            });
    });

    test("Should create a new review", async () => {
        const headers = new window.Headers();
        headers.set("Location", "http://localhost:8080/api/reviews/3" );

        successfullyMockResponse(201, [], headers);

        return reviewService.postNewReview(
            1,
            "Review title",
            "Description title",
            "5",
        )
            .then((response) => {
                expect(response.hasFailed()).toBeFalsy();
                expect(response.getStatusCode()).toBe(201);
            });
    });
});