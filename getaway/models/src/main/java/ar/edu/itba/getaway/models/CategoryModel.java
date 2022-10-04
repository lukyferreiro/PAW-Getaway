package ar.edu.itba.getaway.models;

public class CategoryModel{
    private final Long categoryId;
    private final String categoryName;

    public CategoryModel(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public Long getCategoryId() {
        return categoryId;
    }
    public String getCategoryName() {
        return categoryName;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if (!(o instanceof CategoryModel)){
            return false;
        }
        CategoryModel other = (CategoryModel) o;
        return this.categoryId.equals(other.categoryId);
    }
}
