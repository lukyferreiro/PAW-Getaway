/**
 * @jest-environment jsdom
 */

import {loginService} from "../../../services";
import {successfullyMockResponse, userModel} from "../../Mocks";

describe('Login Service Test', () => {

    test("Should login with user", async () => {
        const headers = new window.Headers();
        headers.set("Authorization", "Bearer token");
        successfullyMockResponse(200, userModel);

        return loginService.login("lferreiro@itba.edu.ar", "password")
            .then((response) => {
                expect(response.hasFailed()).toBeFalsy();
                expect(response.getData().name).toBe("Lucas");
                expect(response.getData().surname).toBe("Ferreiro");
            });
    });

    test("Should not return a valid token", async () => {
        successfullyMockResponse(403, userModel);
        return loginService.login("username", "password")
            .then((response) => {
                expect(response.hasFailed()).toBeTruthy();
                expect(response.getError().getStatus()).toBe(403);
            });
    });

});
