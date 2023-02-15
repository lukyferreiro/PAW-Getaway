/**
 * @jest-environment jsdom
 */

import {categoryService} from "../../services";
import {
    successfullyMockResponse,
    categoryModel1,
    categoryModel2
} from "../Mocks";

test("Should get all categories", async () => {
    successfullyMockResponse(200, [categoryModel1, categoryModel2]);
    return categoryService.getCategories()
        .then((response) => {
            expect(response.hasFailed()).toBeFalsy();
            expect(response.getData()[0]).toBe(categoryModel1);
            expect(response.getData()[1]).toBe(categoryModel2);
        });
});