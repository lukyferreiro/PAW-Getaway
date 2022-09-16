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

import javax.swing.text.html.Option;
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
                                   @RequestParam Optional<Double> maxPrice) {
        final ModelAndView mav = new ModelAndView("experiences");

        // Ordinal empieza en 0
        ExperienceCategory category;
        try {
            category = ExperienceCategory.valueOf(categoryName);
        } catch (Exception e) {
            throw new CategoryNotFoundException();
        }

        String dbCategoryName = category.getName();
        int id = category.ordinal() + 1;
        List<ExperienceModel> experienceList;
        List<CityModel> cityModels = cityService.listAll();

        //Filtros
        if (cityId.isPresent()) {
            if (maxPrice.isPresent() && maxPrice.get() > 0) {
                experienceList = exp.listByCategoryPriceAndCity(id, maxPrice.get(), cityId.get());
            } else {
                experienceList = exp.listByCategoryAndCity(id, cityId.get());
            }
        } else if (maxPrice.isPresent() && maxPrice.get() > 0) {
            experienceList = exp.listByCategoryAndPrice(id, maxPrice.get());
        } else {
            experienceList = exp.listByCategory(id);
        }

        mav.addObject("cities", cityModels);
        mav.addObject("dbCategoryName", dbCategoryName);
        mav.addObject("categoryName", categoryName);
        mav.addObject("activities", experienceList);
        return mav;
    }

    @RequestMapping(path = "/{experienceId}/image",
            produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE},
            method = RequestMethod.GET)
    @ResponseBody
    public byte[] getExperiencesImages(@PathVariable("experienceId") final long experienceId) {
        Optional<ImageModel> optionalImageModel = imageService.getByExperienceId(experienceId);
        final ModelAndView mav = new ModelAndView("experiences");

        if(optionalImageModel.isPresent()){
            mav.addObject("expImage", "true");
            return optionalImageModel.get().getImage();
        }

        mav.addObject("expImage", "false");
        return null;
    }

    @RequestMapping("/{categoryName}/{experienceId}")
    public ModelAndView experienceView(@PathVariable("categoryName") final String categoryName,
                                       @PathVariable("experienceId") final long categoryId) {
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
                                       final BindingResult errors) {
        final ModelAndView mav = new ModelAndView("redirect:/" + categoryName);

        if (errors.hasErrors()) {
            return experience(categoryName, form, Optional.empty(), Optional.empty());
        }

        Optional<CityModel> cityModel = cityService.getIdByName(form.getActivityCity());

        if (cityModel.isPresent()) {
            long cityId = cityModel.get().getId();
            mav.addObject("cityId", cityId);
        }

        Double priceMax = form.getActivityPriceMax();
        if (priceMax != null) {
            mav.addObject("maxPrice", priceMax);
        }

        return mav;
    }

}
