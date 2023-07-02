import { paths, APPLICATION_JSON_TYPE } from "../common";
import {
    ExperienceModel,
    PagedContent,
    PostResponse,
    PutResponse,
    Result,
    ReviewModel,
    AnonymousLandingPageModel,
    UserLandingPageModel,
    MaxPriceModel, OrderByModel
} from "../types";
import {resultFetch} from "../scripts/resultFetch";
import {getPagedFetch} from "../scripts/getPagedFetch";
import {authedFetch} from "../scripts/authedFetch";

export class  ExperienceService {
    private readonly basePath = paths.BASE_URL + paths.EXPERIENCES;

    public async createExperience(
        name: string,
        category: number,
        country: number,
        city: number,
        address: string,
        mail: string,
        price?: number,
        url?: string,
        description?: string
    ): Promise<Result<PostResponse>> {
        const newExperience = JSON.stringify({
            name: name,
            category: category,
            country: country,
            city: city,
            address: address,
            mail: mail,
            price: price ? price : null,
            url: url ? url : "",
            description: description ? description : ""
        });

        return resultFetch<PostResponse>(this.basePath, {
            method: "POST",
            headers: {
                "Content-Type": APPLICATION_JSON_TYPE,
            },
            body: newExperience,
        });
    }

    public async getAnonymousLandingPage(): Promise<Result<AnonymousLandingPageModel>> {
        return resultFetch<AnonymousLandingPageModel>(this.basePath + "/landingPage", {
            method: "GET"
        })
    }

    public async getUserLandingPage(): Promise<Result<UserLandingPageModel>> {
        return resultFetch<UserLandingPageModel>(this.basePath + "/landingPage", {
            method: "GET"
        })
    }

    public async getExperiencesByFilter(
        category?: string,
        name?: string,
        order?: string,
        price?: number,
        score?: number,
        city?: number,
        page?: number
    ): Promise<Result<PagedContent<ExperienceModel[]>>> {
        const url = new URL(this.basePath + "/filter");
        if (typeof category === "string") {
            url.searchParams.append("category", category);
        }
        if (typeof name === "string") {
            url.searchParams.append("name", name);
        }
        if (typeof order === "string") {
            url.searchParams.append("order", order);
        }
        if (price || price === 0) {
            url.searchParams.append("price", price.toString());
        }
        if (score) {
            url.searchParams.append("score", score.toString());
        }
        if (city) {
            url.searchParams.append("city", city.toString());
        }
        return getPagedFetch<ExperienceModel[]>(url.toString(), page);
    }

    public async getFilterMaxPrice(
        category?: string,
        name?: string,
    ): Promise<Result<MaxPriceModel>> {
        const url = new URL(this.basePath + "/filter/maxPrice");

        if (typeof name === "string") {
            url.searchParams.append("name", name);
        }
        if (typeof category === "string") {
            url.searchParams.append("category", category);
        }

        return resultFetch<MaxPriceModel>(url.toString(), {
            method: "GET",
        });
    }

    public async getUserOrderByModels(
    ): Promise<Result<OrderByModel[]>> {
        const url = new URL(this.basePath + "/filter/orderByModels?user=true");

        return resultFetch<OrderByModel[]>(url.toString(), {
            method: "GET",
        });
    }

    public async getProviderOrderByModels(
    ): Promise<Result<OrderByModel[]>> {
        const url = new URL(this.basePath + "/filter/orderByModels?provider=true");

        return resultFetch<OrderByModel[]>(url.toString(), {
            method: "GET",
        });
    }

    public async getExperienceById(
        experienceId: number,
        view?: boolean
    ): Promise<Result<ExperienceModel>> {
        const url = new URL(this.basePath + `/experience/${experienceId}`);
        if (typeof view === "boolean") {
            url.searchParams.append("view", view.toString());
        }

        return resultFetch(url.toString(), {
            method: "GET"
        });
    }

    public async getExperienceNameById(experienceId: number): Promise<Result<ExperienceModel>> {
        return resultFetch<ExperienceModel>(this.basePath + `/experience/${experienceId}/name`, {
            method: "GET",
        });
    }

    public async updateExperienceById(
        experienceId: number,
        name: string,
        category: number,
        country: number,
        city: number,
        address: string,
        mail: string,
        price?: number,
        url?: string,
        description?: string
    ): Promise<Result<PutResponse>> {
        const experienceToUpdate = JSON.stringify({
            name: name,
            category: category,
            country: country,
            city: city,
            address: address,
            mail: mail,
            price: price ? price : null,
            url: url ? url : "",
            description: description ? description : ""
        });
        return resultFetch(this.basePath + `/experience/${experienceId}`, {
            method: "PUT",
            headers: {
                "Content-Type": APPLICATION_JSON_TYPE,
            },
            body: experienceToUpdate,
        });
    }

    public async deleteExperienceById(experienceId: number)  {
        return authedFetch(this.basePath + `/experience/${experienceId}`, {
            method: "DELETE",
        });
    }

    public async updateExperienceImage(
        experienceId: number,
        file: File
    ): Promise<Result<PutResponse>> {
        const formData = new FormData();
        formData.append("experienceImage", file, file.name);
        return resultFetch<PutResponse>(this.basePath + `/experience/${experienceId}/experienceImage`, {
            method: "PUT",
            headers: {},
            body: formData,
        });
    }

    public async getExperienceReviews(
        experienceId: number,
        page?: number
    ): Promise<Result<PagedContent<ReviewModel[]>>> {
        return getPagedFetch<ReviewModel[]>(this.basePath + `/experience/${experienceId}/reviews`, page);
    }

    public async postNewReview(
        experienceId: number,
        title: string,
        description: string,
        score: string
    ): Promise<Result<PostResponse>> {
        const reviewToUpdate = JSON.stringify({
            title:title,
            description:description,
            score:score
        });
        return resultFetch<PostResponse>(this.basePath + `/experience/${experienceId}/reviews`, {
            method: "POST",
            headers: {
                "Content-Type": APPLICATION_JSON_TYPE,
            },
            body: reviewToUpdate,
        });
    }

    public async setExperienceFav(
        experienceId: number,
        set?: boolean
    ) {
        const url = new URL(this.basePath + `/experience/${experienceId}/fav`);
        if (typeof set === "boolean") {
            url.searchParams.append("set", set.toString());
        }

        return resultFetch(url.toString(), {
            method: "PUT",
            headers: {},
            body: {}
        });
    }

    public async setExperienceObservable(
        experienceId: number,
        set?: boolean
    ) {
        const url = new URL(this.basePath + `/experience/${experienceId}/observable`);
        if (typeof set === "boolean") {
            url.searchParams.append("set", set.toString());
        }

        return resultFetch(url.toString(), {
            method: "PUT",
            headers: {},
            body: {}
        });
    }
}