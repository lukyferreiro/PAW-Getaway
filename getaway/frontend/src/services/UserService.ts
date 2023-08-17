import {APPLICATION_JSON_TYPE, paths} from "../common";
import {
    ErrorResponse, ExperienceModel,
    PagedContent, PostResponse,
    PutResponse, Result,
    ReviewModel, UserModel,
} from "../types";
import {getPagedFetch} from "../scripts/getPagedFetch";
import {resultFetch} from "../scripts/resultFetch";
import UserRecommendationsModel from "../types/UserRecommendationsModel";

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
                new ErrorResponse(409, "Conflict","Confirm password must match with password")
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
        return resultFetch<UserModel>(this.basePath + `/${userId}`, {
            method: "GET",
        });
    }

    public async updateUserInfoById(
        userId?: number,
        name?: string,
        surname?: string
    ) : Promise<Result<PutResponse>>{
        const userToUpdate = JSON.stringify({
            name: name,
            surname: surname
        });
        return resultFetch<PutResponse> (this.basePath + `/${userId}`, {
            method: "PUT",
            headers: {
                "Content-Type": APPLICATION_JSON_TYPE,
            },
            body: userToUpdate,
        });
    }

    public async updateUserProfileImage(
        userId: number,
        file: File
    ): Promise<Result<PutResponse>> {
        const formData = new FormData();
        formData.append("profileImage", file, file.name);
        return resultFetch<PutResponse>(this.basePath + `/${userId}/profileImage`, {
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
        const url = new URL(this.basePath + `/${userId}/experiences`);
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
            this.basePath + `/${userId}/reviews`,
            page
        );
    }

    public async getUserFavExperiences(
        userId: number,
        order?: string,
        page?: number
    ): Promise<Result<PagedContent<ExperienceModel[]>>> {
        const url = new URL(this.basePath + `/${userId}/favExperiences`);
        if (typeof order === "string") {
            url.searchParams.append("order", order);
        }
        return getPagedFetch<ExperienceModel[]>(url.toString(), page);
    }

    public async getUserRecommendations(userId: number | undefined): Promise<Result<UserRecommendationsModel>> {
        return resultFetch<UserRecommendationsModel>(this.basePath + `/${userId}/recommendations`, {
            method: "GET"
        })
    }

    public async verifyUser(
        token?: string
    ) : Promise<Result<PutResponse>> {
        const url = new URL(this.basePath + "/emailToken" );
        if (typeof token === "string") {
            url.searchParams.append("token", token);
        }
        return resultFetch<PutResponse>(url.toString(), {
            method: "PUT",
            headers: {},
            body: {},
        });
    }

    public async sendNewVerifyUserEmail() : Promise<Result<PostResponse>> {
        const url = new URL(this.basePath + "/emailToken" );
        return resultFetch<PostResponse>(url.toString(), {
            method: "POST",
            headers: {},
            body: {},
        });
    }

    public async resetPassword(
        token?: string,
        password?: string
    ) : Promise<Result<PutResponse>> {
        const url = new URL(this.basePath + "/passwordToken" );
        const newPassword = JSON.stringify({
            password: password,
            token: token
        });
        // if (typeof token === "string") {
        //     url.searchParams.append("token", token);
        // }
        return resultFetch<PutResponse>(url.toString(), {
            method: "PUT",
            headers: {
                "Content-Type": APPLICATION_JSON_TYPE,
            },
            body: newPassword,
        });
    }

    public async sendPasswordResetEmail(
        email?: string
    ) : Promise<Result<PostResponse>> {
        const url = new URL(this.basePath + "/passwordToken" );
        const emailToSend = JSON.stringify({
            email: email
        });
        return resultFetch<PostResponse>(url.toString(), {
            method: "POST",
            headers: {
                "Content-Type": APPLICATION_JSON_TYPE,
            },
            body: emailToSend,
        });
    }
}
