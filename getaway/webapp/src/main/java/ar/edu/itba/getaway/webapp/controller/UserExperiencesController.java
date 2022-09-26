package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.services.*;
import ar.edu.itba.getaway.exceptions.CategoryNotFoundException;
import ar.edu.itba.getaway.exceptions.UserNotFoundException;
import ar.edu.itba.getaway.webapp.forms.DeleteForm;
import ar.edu.itba.getaway.webapp.forms.ExperienceForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private ImageService imageService;
    @Autowired
    private FavExperienceService favExperienceService;


    @RequestMapping(value = "/user/favourites")
    public ModelAndView favourites(Principal principal,
                                   @RequestParam Optional<Long> experience,
                                   @RequestParam Optional<Boolean> set){
        final ModelAndView mav = new ModelAndView("user_favourites");

        final UserModel user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        setFav(user.getId(), set, experience);
        List<Long> favExperienceModels = favExperienceService.listByUserId(user.getId());
        mav.addObject("favExperienceModels", favExperienceModels);

        List<ExperienceModel> experienceList = experienceService.listAll();
        List<Long> avgReviews = new ArrayList<>();
        for(ExperienceModel exp : experienceList){
            avgReviews.add(experienceService.getAvgReviews(exp.getId()).get());
        }

        mav.addObject("experiences", experienceList);
        mav.addObject("avgReviews", avgReviews);
        return mav;
    }

    @RequestMapping(value = "/user/experiences")
    public ModelAndView experience(Principal principal,
                                   @RequestParam Optional<Long> experience,
                                   @RequestParam Optional<Boolean> set) {
        final ModelAndView mav = new ModelAndView("user_experiences");


        final UserModel user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);

        setFav(user.getId(), set, experience);
        List<Long> favExperienceModels = favExperienceService.listByUserId(user.getId());
        mav.addObject("favExperienceModels", favExperienceModels);

        List<ExperienceModel> experienceList = experienceService.getByUserId(user.getId());
        List<Long> avgReviews = new ArrayList<>();
        for(ExperienceModel exp : experienceList){
            avgReviews.add(experienceService.getAvgReviews(exp.getId()).get());
        }

        mav.addObject("experiences", experienceList);
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
                                           Principal principal,
                                           final BindingResult errors) throws IOException {
        if (errors.hasErrors()) {
            return experienceEdit(experienceId, form);
        }

        long categoryId = form.getActivityCategoryId();
        if (categoryId < 0) {
            throw new CategoryNotFoundException();
        }

        long cityId = cityService.getIdByName(form.getActivityCity()).get().getId();

        final UserModel user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        final Long userId = user.getId();

        boolean hasImg = false;
        if (!form.getActivityImg().isEmpty()) {
            hasImg = true;
            final ImageExperienceModel imageModel = imageService.createExperienceImg(
                    form.getActivityImg().getBytes(), experienceId, true);
        }
        Double price = (form.getActivityPrice().isEmpty()) ? null : Double.parseDouble(form.getActivityPrice());
        String description = (form.getActivityInfo().isEmpty()) ? null : form.getActivityInfo();
        String url = (form.getActivityUrl().isEmpty()) ? null : form.getActivityUrl();

        ExperienceModel experienceModel = new ExperienceModel(experienceId, form.getActivityName(), form.getActivityAddress(),
                description, url, price, cityId, categoryId + 1, userId, hasImg);
        experienceService.update(experienceId, experienceModel);

        return new ModelAndView("redirect:/experiences/" + experienceModel.getCategoryName() + "/" + experienceModel.getId());
    }


    private void setFav(long userId, Optional<Boolean> set, Optional<Long> experience){
        List<Long> favExperienceModels = favExperienceService.listByUserId(userId);

        if (set.isPresent() && experience.isPresent()) {
            if (set.get()) {
                if (!favExperienceModels.contains(experience.get()))
                    favExperienceService.create(userId, experience.get());
            } else {
                favExperienceService.delete(userId, experience.get());
            }
        }
    }

}
