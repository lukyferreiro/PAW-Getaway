import {APPLICATION_JSON_TYPE, paths, USER_INFO_V1, USER_V1, USER_PASSWORD_V1, USER_PASSWORD_EMAIL_V1} from "../common";
import {
    ErrorResponse, ExperienceModel,
    PagedContent, PostResponse,
    PutResponse, Result,
    ReviewModel, UserModel,
} from "../types";
import {getPagedFetch} from "../scripts/getPagedFetch";
import {resultFetch} from "../scripts/resultFetch";
import {checkValidJWT} from "../scripts/checkError";

export class UserService {
    private readonly userBasePath = paths.BASE_URL + paths.USERS;
    private readonly experienceBasePath = paths.BASE_URL + paths.EXPERIENCES;
    private readonly reviewsBasePath = paths.BASE_URL + paths.REVIEWS;

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

        return resultFetch<PostResponse>(this.userBasePath, {
            method: "POST",
            headers: {
                "Content-Type": USER_V1,
            },
            body: newUser,
        });
    }


    public async getUserById(userId: number): Promise<Result<UserModel>> {
        return resultFetch<UserModel>(this.userBasePath + `/${userId}`, {
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
        return resultFetch<PutResponse> (this.userBasePath + `/${userId}`, {
            method: "PUT",
            headers: {
                "Content-Type": USER_INFO_V1,
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
        return resultFetch<PutResponse>(this.userBasePath + `/${userId}/profileImage`, {
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
        const url = new URL(this.experienceBasePath);
        url.searchParams.append("filter", 'PROVIDER');
        url.searchParams.append("userId", userId.toString());
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
        const url = new URL(this.reviewsBasePath);
        url.searchParams.append("userId", userId.toString());
        return getPagedFetch<ReviewModel[]>(url.toString(), page);
    }

    public async getUserFavExperiences(
        userId: number,
        order?: string,
        page?: number
    ): Promise<Result<PagedContent<ExperienceModel[]>>> {
        const url = new URL(this.experienceBasePath);
        url.searchParams.append("filter", 'FAVS');
        url.searchParams.append("userId", userId.toString());
        if (typeof order === "string") {
            url.searchParams.append("order", order);
        }
        return getPagedFetch<ExperienceModel[]>(url.toString(), page);
    }

    public async getUserViewedExperiences(userId: number | undefined): Promise<Result<ExperienceModel[]>> {
        const url = new URL(this.experienceBasePath);
        url.searchParams.append("filter", 'VIEWED');
        // @ts-ignore
        url.searchParams.append("userId", userId.toString());
        return resultFetch<ExperienceModel[]>(url.toString(), {
            method: "GET"
        })
    }

    public async getUserRecommendationsByFavs(userId: number | undefined): Promise<Result<ExperienceModel[]>> {
        const url = new URL(this.experienceBasePath);
        url.searchParams.append("filter", 'RECOMMENDED_BY_FAVS');
        // @ts-ignore
        url.searchParams.append("userId", userId.toString());
        return resultFetch<ExperienceModel[]>(url.toString(), {
            method: "GET"
        })
    }

    public async getUserRecommendationsByReviews(userId: number | undefined): Promise<Result<ExperienceModel[]>> {
        const url = new URL(this.experienceBasePath);
        url.searchParams.append("filter", 'RECOMMENDED_BY_REVIEWS');
        // @ts-ignore
        url.searchParams.append("userId", userId.toString());
        return resultFetch<ExperienceModel[]>(url.toString(), {
            method: "GET"
        })
    }

    public async verifyUser(
        email?: string,
        token?: string
    ) : Promise<Result<PutResponse>> {
        const credentials = email + ":" + token;
        const hash = btoa(credentials);
        try {
            const response = await fetch(paths.BASE_URL + paths.EXPERIENCES + '/categories', {
                method: "GET",
                headers: {
                    Authorization: "Basic " + hash,
                },
            });
            const parsedResponse = await checkValidJWT<any>(response);
            return Result.ok(parsedResponse, response.status);
        } catch (err: any) {
            return Result.failed(
                new ErrorResponse(parseInt(err.message), err.title, err.message)
            );
        }

        // const url = new URL(this.userBasePath + "/emailToken" );
        // if (typeof token === "string") {
        //     url.searchParams.append("token", token);
        // }
        // return resultFetch<PutResponse>(url.toString(), {
        //     method: "PUT",
        //     headers: {},
        //     body: {},
        // });
    }

    // public async sendNewVerifyUserEmail() : Promise<Result<PostResponse>> {
    //     const url = new URL(this.userBasePath + "/emailToken" );
    //     return resultFetch<PostResponse>(url.toString(), {
    //         method: "POST",
    //         headers: {},
    //         body: {},
    //     });
    // }

    public async resetPassword(
        token?: string,
        password?: string
    ) {
        const url = new URL(this.userBasePath);

        const newPassword = JSON.stringify({
            password: password,
        });

        if (typeof token === "string") {
            url.searchParams.append("token", token);
        }

        return resultFetch(url.toString(), {
            method: "PATCH",
            headers: {
                "Content-Type": USER_PASSWORD_V1,
            },
            body: newPassword,
        });
    }

    public async sendPasswordResetEmail(
        email?: string
    ) : Promise<Result<PostResponse>> {
        const url = new URL(this.userBasePath);

        const emailToSend = JSON.stringify({
            email: email
        });

        return resultFetch<PostResponse>(url.toString(), {
            method: "POST",
            headers: {
                "Content-Type": USER_PASSWORD_EMAIL_V1,
            },
            body: emailToSend,
        });
    }
}
