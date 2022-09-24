package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.services.*;
import ar.edu.itba.getaway.webapp.exceptions.CategoryNotFoundException;
import ar.edu.itba.getaway.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.getaway.webapp.forms.DeleteForm;
import ar.edu.itba.getaway.webapp.forms.ExperienceForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserExperiencesController {

    @Autowired
    private UserService userService;
    @Autowired
    private ExperienceService experienceService;
    @Autowired
    private CityService cityService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private ImageExperienceService imageExperienceService;
    @Autowired
    private ImageService imageService;


    @RequestMapping(value = "/user/experiences", method = {RequestMethod.GET})
    public ModelAndView experience(@ModelAttribute("loggedUser") final UserModel loggedUser) {
        final ModelAndView mav = new ModelAndView("user_experiences");

        List<ExperienceModel> experienceList = experienceService.getByUserId(loggedUser.getId());
        mav.addObject("activities", experienceList);
        List<Long> avgReviews = new ArrayList<>();
        for(ExperienceModel experience : experienceList){
            avgReviews.add(experienceService.getAvgReviews(experience.getId()).get());
        }
        mav.addObject("avgReviews", avgReviews);

        return mav;
    }
    @RequestMapping(value = "/user/experiences/delete/{experienceId}", method = {RequestMethod.GET})
    public ModelAndView experienceDelete(@PathVariable("experienceId") final long experienceId,
                                         @ModelAttribute("deleteForm") final DeleteForm form) {
        final ModelAndView mav = new ModelAndView("deleteExperience");
        ExperienceModel experience = experienceService.getById(experienceId).get();

        mav.addObject("experience", experience);
        return mav;
    }

    @RequestMapping(value = "/user/experiences/delete/{experienceId}", method = {RequestMethod.POST})
    public ModelAndView experienceDeletePost(@PathVariable(value = "experienceId") final long experienceId,
                                             @ModelAttribute("deleteForm") final DeleteForm form,
                                             final BindingResult errors) {
        if (errors.hasErrors()) {
            return experienceDelete(experienceId, form);
        }

        experienceService.delete(experienceId);
        return new ModelAndView("redirect:/user/experiences");
    }

    @RequestMapping(value = "/user/experiences/edit/{experienceId}", method = {RequestMethod.GET})
    public ModelAndView experienceEdit(@PathVariable("experienceId") final long experienceId,
                                       @ModelAttribute("experienceForm") final ExperienceForm form) {
        final ModelAndView mav = new ModelAndView("experience_edit_form");

        ExperienceCategory[] categoryModels = ExperienceCategory.values();
        List<String> categories = new ArrayList<>();
        for (ExperienceCategory categoryModel : categoryModels) {
            categories.add(categoryModel.getName());
        }

        List<CountryModel> countryModels = countryService.listAll();
        List<CityModel> cityModels = cityService.listAll();
        ExperienceModel experience = experienceService.getById(experienceId).get();

        form.setActivityName(experience.getName());
        form.setActivityAddress(experience.getAddress());
        form.setActivityInfo(experience.getDescription());
        if(experience.getPrice()!=null){
            form.setActivityPrice(experience.getPrice().toString());
        }
        form.setActivityUrl(experience.getSiteUrl());

        mav.addObject("categories", categories);
        mav.addObject("cities", cityModels);
        mav.addObject("countries", countryModels);
        mav.addObject("experience", experience);
        mav.addObject("formCountry", "Argentina");
        mav.addObject("formCity", cityService.getById(experience.getCityId()).get().getId());

        return mav;
    }

    @RequestMapping(value = "/user/experiences/edit/{experienceId}", method = {RequestMethod.POST})
    public ModelAndView experienceEditPost(@PathVariable(value = "experienceId") final long experienceId,
                                           @ModelAttribute("experienceForm") final ExperienceForm form,
                                           @ModelAttribute("loggedUser") final UserModel loggedUser,
                                           final BindingResult errors) throws IOException {
        if (errors.hasErrors()) {
            return experienceEdit(experienceId, form);
        }

        long categoryId = form.getActivityCategoryId();
        if (categoryId < 0) {
            throw new CategoryNotFoundException();
        }

        long cityId = cityService.getIdByName(form.getActivityCity()).get().getId();

        long userId;
        try {
            userId = loggedUser.getId();
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException();
        }

        boolean hasImg = false;
        if (!form.getActivityImg().isEmpty()) {
            hasImg = true;
            final ImageModel imageModel = imageService.create(form.getActivityImg().getBytes());
            imageExperienceService.create(imageModel.getId(), experienceId, true);
        }
        Double price = (form.getActivityPrice().isEmpty()) ? null : Double.parseDouble(form.getActivityPrice());
        String description = (form.getActivityInfo().isEmpty()) ? null : form.getActivityInfo();
        String url = (form.getActivityUrl().isEmpty()) ? null : form.getActivityUrl();

        ExperienceModel experienceModel = new ExperienceModel(experienceId, form.getActivityName(), form.getActivityAddress(),
                description, url, price, cityId, categoryId + 1, userId, hasImg);
        experienceService.update(experienceId, experienceModel);

        return new ModelAndView("redirect:/experiences/" + experienceModel.getCategoryName() + "/" + experienceModel.getId());
    }

}
