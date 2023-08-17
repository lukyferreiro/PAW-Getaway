import {ExperienceService} from "./ExperienceService";
import {LocationService} from "./LocationService";
import {LoginService} from "./LoginService";
import {ReviewService} from "./ReviewService";
import {UserService} from "./UserService";

const experienceService = new ExperienceService();
const locationService = new LocationService();
const loginService = new LoginService();
const reviewService = new ReviewService();
const userService = new UserService();

export {
    experienceService,
    locationService,
    loginService,
    reviewService,
    userService,
};