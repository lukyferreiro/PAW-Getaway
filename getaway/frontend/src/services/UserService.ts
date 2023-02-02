import {APPLICATION_JSON_TYPE, paths} from "../common";
import {getImageFetch} from "../scripts/getImageFetch";
import {
    ErrorResponse, ExperienceModel,
    PagedContent, PostResponse,
    PutResponse, Result,
    ReviewModel, UserModel,
} from "../types";
import {getPagedFetch} from "../scripts/getPagedFetch";
import {resultFetch} from "../scripts/resultFetch";

export class UserService {
    private readonly basePath = paths.BASE_URL + paths.USERS;

    public async createUser(
        name: string,
        surname: string,
        email: string,
        password: string,
        confirmPassword: string
    ): Promise<Result<PostResponse>> {
        const newUser = JSON.stringify({
            name: name,
            surname: surname,
            email: email,
            password: password,
            confirmPassword: confirmPassword,
        });

        if (confirmPassword !== password) {
            return Result.failed(
                new ErrorResponse(409, "Confirm password must match with password")
            );
        }

        return resultFetch<PostResponse>(this.basePath, {
            method: "POST",
            headers: {
                "Content-Type": APPLICATION_JSON_TYPE,
            },
            body: newUser,
        });
    }


    public async getUserById(userId: number): Promise<Result<UserModel>> {
        return resultFetch<UserModel>(this.basePath + "/" + userId, {
            method: "GET",
        });
    }

    public async getUserCurrentUser(): Promise<Result<UserModel>> {
        return resultFetch<UserModel>(this.basePath + "/currentUser", {
            method: "GET",
        });
    }

    public async getUserProfileImage(userId: number): Promise<Result<Blob>> {
        return getImageFetch(this.basePath + "/" + userId + "/profileImage");
    }

    public async updateUserProfileImage(
        userId: number
    ): Promise<Result<PutResponse>> {
        const formData = new FormData();
        return resultFetch<PutResponse>(this.basePath + "/" + userId + "/profileImage", {
            method: "PUT",
            headers: {},
            body: formData,
        });
    }

    public async getUserExperiences(
        userId: number,
        name?: string,
        order?: string,
        page?: number,
    ): Promise<Result<PagedContent<ExperienceModel[]>>> {
        const url = new URL(this.basePath + "/" + userId + "/experiences");
        if (typeof name === "string") {
            url.searchParams.append("name", name);
        }
        if (typeof order === "string") {
            url.searchParams.append("order", order);
        }
        return getPagedFetch<ExperienceModel[]>(url.toString(), page);
    }

    public async getUserReviews(
        userId: number,
        page?: number
    ): Promise<Result<PagedContent<ReviewModel[]>>> {
        return getPagedFetch<ReviewModel[]>(
            this.basePath + "/" + userId + "/reviews",
            page
        );
    }

    public async getUserFavExperiences(
        userId: number,
        order?: string,
        page?: number
    ): Promise<Result<PagedContent<ExperienceModel[]>>> {
        const url = new URL(this.basePath + "/" + userId + "/favExperiences");
        if (typeof order === "string") {
            url.searchParams.append("order", order);
        }
        return getPagedFetch<ExperienceModel[]>(url.toString(), page);

    }


    //  TODO
    // PUT /userId
    // PUT y POST de /emailVerification
    // PUT y POST de /passwordReset

}
