// /**
//  * @jest-environment jsdom
//  */
//
import {experienceService, userService} from "../../services";
// import {
//     successfullyMockResponse,
//     userModel,
//     experienceModelNoFav,
//     experienceModelFav,
//     experienceModelCategory,
//     reviewModel1, reviewModel2,
// } from "../Mocks";
//
// test("Should create a new user", async () => {
//     successfullyMockResponse(204, []);
//
//     return userService.createUser(
//         "name",
//         "surname",
//         "email@example.com",
//         "password",
//         "password",
//     )
//         .then((response) => {
//             expect(response.hasFailed()).toBeTruthy();
//             expect(response.getError().getStatus()).toBe(204);
//         });
// });
//
// test("Should not create a new user due to password not match with confirm password", async () => {
//     return userService.createUser(
//         "name",
//         "surname",
//         "email@example.com",
//         "password",
//         "badPassword",
//     )
//         .then((response) => {
//             expect(response.hasFailed()).toBeTruthy();
//             expect(response.getError().getStatus()).toBe(409);
//         });
// });
//
// test("Should get user with id 1", async () => {
//     successfullyMockResponse(200, userModel);
//
//     return userService.getUserById(1)
//         .then((response) => {
//             expect(response.hasFailed()).toBeFalsy();
//             expect(response.getData()).toBe(userModel);
//         });
// });
//
// test("Should update user profile image", async () => {
//     successfullyMockResponse(204, {});
//
//     return userService.updateUserProfileImage(1, new window.File([], "image"))
//         .then((response) => {
//             expect(response.hasFailed()).toBeTruthy();
//             expect(response.getError().getStatus()).toBe(204);
//         });
// });
//
// test("Should get experiences of user with id 1", async () => {
//     const headers = new window.Headers();
//     headers.set("X-Total-Pages", "1");
//
//     successfullyMockResponse(200, [experienceModelNoFav, experienceModelFav, experienceModelCategory], headers);
//
//     return userService.getUserExperiences(1)
//         .then((response) => {
//             expect(response.hasFailed()).toBeFalsy();
//             expect(response.getData().getContent()[0]).toBe(experienceModelNoFav);
//             expect(response.getData().getContent()[1]).toBe(experienceModelFav);
//             expect(response.getData().getContent()[2]).toBe(experienceModelCategory);
//         });
// });
//
// test("Should get fav experiences of user with id 1", async () => {
//     const headers = new window.Headers();
//     headers.set("X-Total-Pages", "1");
//
//     successfullyMockResponse(200, [experienceModelFav, experienceModelCategory], headers);
//
//     return userService.getUserFavExperiences(1)
//         .then((response) => {
//             expect(response.hasFailed()).toBeFalsy();
//             expect(response.getData().getContent()[0]).toBe(experienceModelFav);
//             expect(response.getData().getContent()[1]).toBe(experienceModelCategory);
//         });
// });
//
// test("Should get reviews of user with id 1", async () => {
//     const headers = new window.Headers();
//     headers.set("X-Total-Pages", "1");
//
//     successfullyMockResponse(200, [reviewModel1, reviewModel2], headers);
//
//     return userService.getUserReviews(1)
//         .then((response) => {
//             expect(response.hasFailed()).toBeFalsy();
//             expect(response.getData().getContent()[0]).toBe(reviewModel1);
//             expect(response.getData().getContent()[1]).toBe(reviewModel2);
//         });
// });