/**
 * @jest-environment jsdom
 */

import {locationService} from "../../../services";
import {
    successfullyMockResponse,
    countryModel,
    cityModel,
} from "../../Mocks";

//TODO check este
test("Should get all countries", async () => {
    successfullyMockResponse(200, [countryModel]);

    return locationService.getCountries()
        .then((response) => {
            expect(response.hasFailed()).toBeFalsy();
            expect(response.getData()[0]).toBe(countryModel);
        });
});

//TODO check este
test("Should get city with id 1", async () => {
    successfullyMockResponse(200, cityModel);

    return locationService.getCityById(1)
        .then((response) => {
            expect(response.hasFailed()).toBeFalsy();
            expect(response.getData()).toBe(cityModel);
        });
});