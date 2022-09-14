package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.services.*;
import ar.edu.itba.getaway.webapp.exceptions.CategoryNotFoundException;
import ar.edu.itba.getaway.webapp.exceptions.ExperienceNotFoundException;
import ar.edu.itba.getaway.webapp.forms.FilterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ExperienceController {

    @Autowired
    ExperienceService exp;
//    @Autowired
//    CategoryService category;
    @Autowired
    CityService cityService;
    @Autowired
    ImageService imageService;

    @RequestMapping(value = "/{categoryName}", method = {RequestMethod.GET})
    public ModelAndView experience(@PathVariable("categoryName") final String categoryName,
                                   @ModelAttribute("filterForm") final FilterForm form,
                                   @RequestParam Optional<Long> cityId,
                                   @RequestParam Optional<Long> maxPrice) {
        final ModelAndView mav = new ModelAndView("experiences");

        // Ordinal empieza en 0
        ExperienceCategory category;
        try {
             category = ExperienceCategory.valueOf(categoryName);
        } catch (Exception e) {
            throw new CategoryNotFoundException();
        }

        String dbCategoryName = category.getName();
        int id = category.ordinal() + 1 ;
        List<ExperienceModel> experienceList;
        List<CityModel> cityModels = cityService.listAll();

        //Filtros
        if(cityId.isPresent()){
            if(maxPrice.isPresent() && maxPrice.get() > 0){
                experienceList = exp.listByCategoryPriceAndCity(id, maxPrice.get(), cityId.get());
            } else {
                experienceList = exp.listByCategoryAndCity(id, cityId.get());
            }
        } else if (maxPrice.isPresent() && maxPrice.get() > 0){
            experienceList = exp.listByCategoryAndPrice(id,maxPrice.get());
        } else {
            experienceList = exp.listByCategory(id);
        }

        mav.addObject("cities", cityModels);
        mav.addObject("dbCategoryName", dbCategoryName);
        mav.addObject("categoryName", categoryName);
        mav.addObject("activities", experienceList);
        return mav;
    }

    @RequestMapping("/{categoryName}/{categoryId}")
    public ModelAndView experienceView(@PathVariable("categoryName") final String categoryName,
                                       @PathVariable("categoryId") final long categoryId){
        final ModelAndView mav = new ModelAndView("experienceDetails");

        final ExperienceModel experience = exp.getById(categoryId).orElseThrow(ExperienceNotFoundException::new);
        String dbCategoryName = ExperienceCategory.valueOf(categoryName).getName();

        mav.addObject("dbCategoryName", dbCategoryName);
        mav.addObject("activity", experience);
        return mav;
    }

    @RequestMapping(value = "/{categoryName}", method = {RequestMethod.POST})
    public ModelAndView experienceCity(@PathVariable("categoryName") final String categoryName,
                                       @Valid @ModelAttribute("filterForm") final FilterForm form,
                                       final BindingResult errors){
        final ModelAndView mav = new ModelAndView("redirect:/" + categoryName);

        if(errors.hasErrors()){
            return experience(categoryName, form, Optional.empty(), Optional.empty());
        }

        Optional<CityModel> cityModel = cityService.getIdByName(form.getActivityCity());
//        List<ExperienceModel> experienceModel = exp.listByCategoryAndPrice(form.getActivityPriceMax());
        if(cityModel.isPresent()){
            long cityId = cityModel.get().getId();
            mav.addObject("cityId", cityId);
        }

//        if (!experienceModel.isEmpty()){
            mav.addObject("maxPrice", form.getActivityPriceMax());
//        }

        return mav;
    }

    @RequestMapping(path = "/experiences/images/{experienceId}",
            produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE},
            method = RequestMethod.GET)
    @ResponseBody
    public byte[] getProfileImage(@PathVariable("experienceId") long experienceId) {
        Optional<ImageModel> optImageModel = imageService.getByExperienceId(experienceId);
        if (optImageModel.isPresent()){
            ImageModel image = optImageModel.get();
            return image.getImage();
        }
        return null;
    }
}
