package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.interfaces.exceptions.CategoryNotFoundException;
import ar.edu.itba.getaway.interfaces.services.CategoryService;
import ar.edu.itba.getaway.models.CategoryModel;
import ar.edu.itba.getaway.webapp.dto.response.CategoryDto;
import ar.edu.itba.getaway.webapp.security.api.CustomMediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.Collection;
import java.util.List;

@Path("categories")
@Component
public class CategoryController {

    @Context
    private UriInfo uriInfo;
    private final CategoryService categoryService;
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);


    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GET
    @Produces(value = {CustomMediaType.CATEGORY_LIST_V1})
    public Response getCategories(){
        LOGGER.info("Called /categories GET");
        final List<CategoryModel> categories = categoryService.listAllCategories();
        final Collection<CategoryDto> categoriesDtos = CategoryDto.mapCategoriesToDto(categories, uriInfo);
        return Response.ok(new GenericEntity<Collection<CategoryDto>>(categoriesDtos) {}).build();
    }

    @GET
    @Path("/{categoryId:[0-9]+}")
    @Produces(value = {CustomMediaType.CATEGORY_V1})
    public Response getCategoryById(
            @PathParam("categoryId") final long id
    ) {
        LOGGER.info("Called /categories/{} GET", id);
        final CategoryModel category = categoryService.getCategoryById(id).orElseThrow(CategoryNotFoundException::new);
        return Response.ok(new CategoryDto(category, uriInfo)).build();
    }
}
