import { paths } from "../common";
import {CategoryModel, Result} from "../types";
import {resultFetch} from "../scripts/resultFetch";

export class CategoryService {
    private readonly basePath = paths.BASE_URL + paths.CATEGORIES;

    public async getCategories(): Promise<Result<CategoryModel[]>> {
        return resultFetch<CategoryModel[]>(this.basePath, {
            method: "GET",
        });
    }
}