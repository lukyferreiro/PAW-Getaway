import { paths, APPLICATION_JSON_TYPE } from "../common";
import {ErrorResponse, ExperienceModel, PagedContent, PostResponse, PutResponse, Result, ReviewModel, AnonymousLandingPageModel, UserLandingPageModel} from "../types";
import {resultFetch} from "../scripts/resultFetch";
import {getPagedFetch} from "../scripts/getPagedFetch";
import {authedFetch} from "../scripts/authedFetch";
import {getImageFetch} from "../scripts/getImageFetch";

export class ExperienceService {
    private readonly basePath = paths.BASE_URL + paths.EXPERIENCES;

    public async createExperience(
        name: string,
        category: number,
        country: string,
        city: string,
        address: string,
        price: string,
        url: string,
        mail: string,
        description: string
    ): Promise<Result<PostResponse>> {
        const newExperience = JSON.stringify({
            name: name,
            category: category,
            country: country,
            city: city,
            address: address,
            price: price,
            url: url,
            mail: mail,
            description: description
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

    public async getExperiencesByCategory(
        category: string,
        order?: string,
        price?: number,
        score?: number,
        city?: number,
        page?: number
    ): Promise<Result<PagedContent<ExperienceModel[]>>> {
        const url = new URL(this.basePath + "/category/" + category);
        if (typeof order === "string") {
            url.searchParams.append("order", order);
        }
        if (price) {
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

    public async getExperiencesByName(
        name: string,
        order?: string,
        page?: number
    ): Promise<Result<PagedContent<ExperienceModel[]>>> {
        const url = new URL(this.basePath + "/name/" + name);
        if (typeof order === "string") {
            url.searchParams.append("order", order);
        }
        return getPagedFetch<ExperienceModel[]>(url.toString(), page);
    }

    public async getExperienceById(experienceId: number): Promise<Result<ExperienceModel>> {
        return resultFetch<ExperienceModel>(this.basePath + "/experience/" + experienceId, {
            method: "GET",
        });
    }

    public async getExperienceNameById(experienceId: number): Promise<Result<ExperienceModel>> {
        return resultFetch<ExperienceModel>(this.basePath + "/experience/" + experienceId + "/name", {
            method: "GET",
        });
    }

    public async updateExperienceById(
        experienceId: number,
        name: string,
        category: number,
        country: string,
        city: string,
        address: string,
        price: string,
        url: string,
        mail: string,
        description: string
    ): Promise<Result<PutResponse>> {
        const experienceToUpdate = JSON.stringify({
            name: name,
            category: category,
            country: country,
            city: city,
            address: address,
            price: price,
            url: url,
            mail: mail,
            description: description
        });
        return resultFetch(this.basePath + "/experience/" + experienceId, {
            method: "PUT",
            headers: {
                "Content-Type": APPLICATION_JSON_TYPE,
            },
            body: experienceToUpdate,
        });
    }

    public async deleteExperienceById(experienceId: number)  {
        return authedFetch(this.basePath + "/experience/" + experienceId, {
            method: "DELETE",
        });
    }

    public async getExperienceImage(experienceId: number): Promise<Result<Blob>> {
        return getImageFetch(this.basePath + "/experience" + experienceId + "/experienceImage");
    }

    public async updateExperienceImage(
        experienceId: number
    ): Promise<Result<PutResponse>> {
        const formData = new FormData();
        return resultFetch<PutResponse>(this.basePath + "/experience" + experienceId + "/experienceImage", {
            method: "PUT",
            headers: {},
            body: formData,
        });
    }

    public async getExperienceReviews(
        experienceId: number,
        page?: number
    ): Promise<Result<PagedContent<ReviewModel[]>>> {
        return getPagedFetch<ReviewModel[]>(this.basePath + "/experience/" + experienceId + "/reviews", page);
    }

    public async postNewReview(
        experienceId: number,
        title?: string,
        description?: string,
        score?: string
    ) {
        const reviewToUpdate = JSON.stringify({
            title:title,
            description:description,
            score:score
        });
        return resultFetch(this.basePath + "/experience/" + experienceId + "/reviews", {
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
        const url = new URL(this.basePath + "/experience/" + experienceId + "/fav");
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
        const url = new URL(this.basePath + "/experience/" + experienceId + "/observable");
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