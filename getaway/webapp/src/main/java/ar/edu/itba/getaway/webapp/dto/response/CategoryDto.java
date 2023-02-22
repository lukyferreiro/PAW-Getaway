package ar.edu.itba.getaway.webapp.dto.response;

import ar.edu.itba.getaway.models.CategoryModel;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

public class CategoryDto implements Serializable {
    private long id;
    private String name;

    public static Collection<CategoryDto> mapCategoriesToDto(Collection<CategoryModel> categories) {
        return categories.stream().map(category -> new CategoryDto(category)).collect(Collectors.toList());
    }


    public CategoryDto() {
        // Used by Jersey
    }

    public CategoryDto(CategoryModel category) {
        this.id = category.getCategoryId();
        this.name = category.getCategoryName();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
