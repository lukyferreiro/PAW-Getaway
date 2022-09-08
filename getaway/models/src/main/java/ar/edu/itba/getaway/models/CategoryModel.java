package ar.edu.itba.getaway.models;

public class CategoryModel{
    private final long categoryId;
    private final String categoryName;

    public CategoryModel(long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public long getId() {
        return categoryId;
    }

    public String getName() {
        return categoryName;
    }
}
