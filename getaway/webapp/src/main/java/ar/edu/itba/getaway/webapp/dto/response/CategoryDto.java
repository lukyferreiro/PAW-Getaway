package ar.edu.itba.getaway.webapp.dto.response;

import ar.edu.itba.getaway.models.CategoryModel;

import javax.ws.rs.core.UriInfo;
import java.io.Serializable;
import java.net.URI;
import java.util.Collection;
import java.util.stream.Collectors;

public class CategoryDto implements Serializable {
    private long id;
    private String name;
    private URI self;

    public static Collection<CategoryDto> mapCategoriesToDto(Collection<CategoryModel> categories, UriInfo uriInfo) {
        return categories.stream().map(category -> new CategoryDto(category, uriInfo)).collect(Collectors.toList());
    }


    public CategoryDto() {
        // Used by Jersey
    }

    public CategoryDto(CategoryModel category, UriInfo uriInfo) {
        this.id = category.getCategoryId();
        this.name = category.getCategoryName();
        this.self = uriInfo.getBaseUriBuilder()
                .path("categories")
                .path(String.valueOf(category.getCategoryId()))
                .build();       // /categories/{categoryId}

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
    public URI getSelf() {
        return self;
    }
    public void setSelf(URI self) {
        this.self = self;
    }
}
