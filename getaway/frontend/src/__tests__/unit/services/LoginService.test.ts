/**
 * @jest-environment jsdom
 */
import {loginService} from "../../../services";
import {successfullyMockResponse, userModel} from "../../Mocks";

//TODO check este
test("Should return a valid token", async () => {
    const headers = new window.Headers();
    headers.set("Authorization", "Bearer token");
    successfullyMockResponse(200, userModel, headers);

    return loginService.login("username", "password")
        .then((response) => {
            expect(response.hasFailed()).toBeFalsy();
            expect(response.getData().token).toBe("token");
        });
});

//TODO check este
test("Should not return a valid token", async () => {
    successfullyMockResponse(403, userModel);
    return loginService.login("username", "password")
        .then((response) => {
            expect(response.hasFailed()).toBeTruthy();
            expect(response.getError().getStatus()).toBe(403);
        });
});
