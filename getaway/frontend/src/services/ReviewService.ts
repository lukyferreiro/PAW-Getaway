import {APPLICATION_JSON_TYPE, paths} from "../common";
import { Result, ReviewModel} from "../types";
import {resultFetch} from "../scripts/resultFetch";
import {authedFetch} from "../scripts/authedFetch";

export class ReviewService {
    private readonly basePath = paths.BASE_URL + paths.REVIEWS;

    public async getReviewById(reviewId: number): Promise<Result<ReviewModel>> {
        return resultFetch<ReviewModel>(this.basePath + "/" + reviewId, {
            method: "GET",
        });
    }

    public async updateReviewById(
        reviewId: number,
        title: string,
        description: string,
        score: string
    ) {
        const reviewToUpdate = JSON.stringify({
            title:title,
            description:description,
            score:score
        });
        return resultFetch(this.basePath + "/" + reviewId, {
            method: "PUT",
            headers: {
                "Content-Type": APPLICATION_JSON_TYPE,
            },
            body: reviewToUpdate,
        });
    }

    public async deleteReviewById(reviewId: number)  {
        return authedFetch(this.basePath + "/" + reviewId, {
            method: "DELETE",
        });
    }
}