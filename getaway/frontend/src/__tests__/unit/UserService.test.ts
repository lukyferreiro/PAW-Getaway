/**
 * @jest-environment jsdom
 */

import {userService} from "../../services";
import {
    successfullyMockResponse,
    userModel,
} from "../Mocks";

test("Should get user with id 1", async () => {
    successfullyMockResponse(200, userModel);

    return userService.getUserById(1).then((response) => {
        expect(response.hasFailed()).toBeFalsy();
        expect(response.getData()).toBe(userModel);
    });
});

test("Should create a new user", async () => {
    successfullyMockResponse(204, []);

    return userService.createUser(
        "name",
        "surname",
        "email@example.com",
        "password",
        "password",
    )
        .then((response) => {
            expect(response.hasFailed()).toBeTruthy();
            expect(response.getError().getStatus()).toBe(204);
        });
});

test("Should not create a new user due to password not match confirm password", async () => {
    return userService.createUser(
        "name",
        "surname",
        "email@example.com",
        "password",
        "badPassword",
    )
        .then((response) => {
            expect(response.hasFailed()).toBeTruthy();
            expect(response.getError().getStatus()).toBe(409);
        });
});