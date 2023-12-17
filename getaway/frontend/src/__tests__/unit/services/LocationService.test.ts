/**
 * @jest-environment jsdom
 */

import {locationService} from "../../../services";
import {
    successfullyMockResponse,
    countryModel,
    cityModel,
} from "../../Mocks";

describe('Location Service Test', () => {

    test("Should get all countries", async () => {
        successfullyMockResponse(200, [countryModel]);

        return locationService.getCountries()
            .then((response) => {
                expect(response.hasFailed()).toBeFalsy();
                expect(response.getData()[0]).toBe(countryModel);
                expect(response.getData().length).toBe(1);

            });
    });

    test("Should get city with id 1", async () => {
        successfullyMockResponse(200, cityModel);

        return locationService.getCityById(1)
            .then((response) => {
                expect(response.hasFailed()).toBeFalsy();
                expect(response.getData()).toBe(cityModel);
            });
    });

    test("Should get country with id 14", async () => {
        successfullyMockResponse(200, countryModel);

        return locationService.getCountryById(14)
            .then((response) => {
                expect(response.hasFailed()).toBeFalsy();
                expect(response.getData()).toBe(countryModel);
            });
    });

    test("Should get cities of country with id 14", async () => {
        successfullyMockResponse(200, [cityModel]);

        return locationService.getCitiesByCountry(14)
            .then((response) => {
                expect(response.hasFailed()).toBeFalsy();
                expect(response.getData()[0]).toBe(cityModel);
                expect(response.getData().length).toBe(1);
            });
    });

});