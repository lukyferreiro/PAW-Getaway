import { paths } from "../common/";
import { checkError } from "../scripts/checkError";
import { ErrorResponse, Result, UserModel } from "../types";
import { setCookie } from "../scripts/cookies";

export class LoginService {
    public async login(
        email: string,
        password: string
    ): Promise<Result<UserModel>> {
        const credentials = email + ":" + password;
        const hash = btoa(credentials);
        setCookie("basic-token", hash, 7);
        try {
            const response = await fetch(paths.BASE_URL + paths.USERS + "/currentUser", {
                method: "GET",
                headers: {
                    Authorization: "Basic " + hash,
                },
            });
            const parsedResponse = await checkError<UserModel>(response);
            parsedResponse.token = response.headers.get("Authorization")?.toString().split(" ")[1];
            return Result.ok(parsedResponse as UserModel);
        } catch (error: any) {
            return Result.failed(
                new ErrorResponse(parseInt(error.message), error.message)
            );
        }
    }
}
