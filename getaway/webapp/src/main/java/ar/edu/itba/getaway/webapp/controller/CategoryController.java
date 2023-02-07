package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.interfaces.services.CategoryService;
import ar.edu.itba.getaway.models.CategoryModel;
import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.webapp.dto.response.AnonymousLandingPageDto;
import ar.edu.itba.getaway.webapp.dto.response.CategoryDto;
import ar.edu.itba.getaway.webapp.dto.response.CityDto;
import ar.edu.itba.getaway.webapp.dto.response.UserLandingPageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.List;

@Path("categories")
@Component
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getCategories(){
        List<CategoryModel> categories = categoryService.listAllCategories();

        Collection<CategoryDto> categoriesDtos = CategoryDto.mapCategoriesToDto(categories);

        return Response.ok(new GenericEntity<Collection<CategoryDto>>(categoriesDtos) {}).build();
    }
}
