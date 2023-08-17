import { paths } from "../common/";
import { checkValidJWT } from "../scripts/checkError";
import {ErrorResponse, Result} from "../types";

export class LoginService {
    public async login(
        email: string,
        password: string
    ): Promise<Result<any>> {
        const credentials = email + ":" + password;
        const hash = btoa(credentials);
        try {
            const response = await fetch(paths.BASE_URL + paths.EXPERIENCES + '/categories', {
                method: "GET",
                headers: {
                    Authorization: "Basic " + hash,
                },
            });
            const parsedResponse = await checkValidJWT<any>(response);
            return Result.ok(parsedResponse);
        } catch (err: any) {
            return Result.failed(
                new ErrorResponse(parseInt(err.message), err.title, err.message)
            );
        }
    }
}
