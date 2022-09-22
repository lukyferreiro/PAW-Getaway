package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.services.*;
import ar.edu.itba.getaway.webapp.exceptions.CategoryNotFoundException;
import ar.edu.itba.getaway.webapp.exceptions.ExperienceNotFoundException;
import ar.edu.itba.getaway.webapp.forms.FavExperienceForm;
import ar.edu.itba.getaway.webapp.forms.FilterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private ExperienceService experienceService;
    @Autowired
    private CityService cityService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private FavExperienceService favExperienceService;

    @RequestMapping(value = "/experiences/{categoryName}", method = {RequestMethod.GET})
    public ModelAndView experience(@PathVariable("categoryName") final String categoryName,
                                   @ModelAttribute("filterForm") final FilterForm form,
                                   @ModelAttribute("favExperienceForm") final FavExperienceForm favForm,
                                   @ModelAttribute("loggedUser") final UserModel loggedUser,
                                   @RequestParam Optional<Long> cityId,
                                   @RequestParam Optional<Double> maxPrice,
                                   @RequestParam Optional<Long> score) {
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
                if(score.isPresent()){
                    experienceList = experienceService.listByCategoryPriceCityAndScore(id, maxPrice.get(), cityId.get(), score.get());
                }else{
                    experienceList = experienceService.listByCategoryPriceAndCity(id, maxPrice.get(), cityId.get());
                }
            } else {
                if(score.isPresent()){
                    experienceList = experienceService.listByCategoryCityAndScore(id, cityId.get(), score.get());
                }else {
                    experienceList = experienceService.listByCategoryAndCity(id, cityId.get());
                }
            }
        } else if (maxPrice.isPresent() && maxPrice.get() > 0) {
            if(score.isPresent()){
                experienceList = experienceService.listByCategoryPriceAndScore(id, maxPrice.get(), score.get());
            }else{
                experienceList = experienceService.listByCategoryAndPrice(id, maxPrice.get());
            }
        } else if(score.isPresent()){
            experienceList = experienceService.listByCategoryAndScore(id, score.get());
        } else {
            experienceList = experienceService.listByCategory(id);
        }

        try {
            mav.addObject("loggedUser", loggedUser.hasRole(Roles.USER));
        } catch (NullPointerException e) {
            mav.addObject("loggedUser", false);
        }

        List<Long> avgReviews = new ArrayList<>();
        for(ExperienceModel experience : experienceList){
            avgReviews.add(experienceService.getAvgReviews(experience.getId()).get());
        }



        mav.addObject("cities", cityModels);
        mav.addObject("dbCategoryName", dbCategoryName);
        mav.addObject("categoryName", categoryName);
        mav.addObject("activities", experienceList);
        mav.addObject("avgReviews", avgReviews);

        return mav;
    }


    @RequestMapping(path = "/{experienceId}/image", method = RequestMethod.GET,
            produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public byte[] getExperiencesImages(@PathVariable("experienceId") final long experienceId) {
        Optional<ImageModel> optionalImageModel = imageService.getByExperienceId(experienceId);

        return optionalImageModel.map(ImageModel::getImage).orElse(null);
    }

    @RequestMapping("/experiences/{categoryName}/{experienceId}")
    public ModelAndView experienceView(@PathVariable("categoryName") final String categoryName,
                                       @PathVariable("experienceId") final long experienceId,
                                       @ModelAttribute("loggedUser") final UserModel loggedUser) {
        final ModelAndView mav = new ModelAndView("experience_details");

        final ExperienceModel experience = experienceService.getById(experienceId).orElseThrow(ExperienceNotFoundException::new);
        String dbCategoryName = ExperienceCategory.valueOf(categoryName).getName();
        final List<ReviewUserModel> reviews = reviewService.getReviewAndUser(experienceId);
        final Double avgScore = reviewService.getAverageScore(experienceId);
        final Integer reviewCount = reviewService.getReviewCount(experienceId);
        final String countryCity = experienceService.getCountryCity(experienceId);
        final Optional<Long> experienceAvgReview = experienceService.getAvgReviews(experienceId);

        if(experienceAvgReview.isPresent()){
            mav.addObject("reviewAvg", experienceAvgReview.get());
        }

        try {
            mav.addObject("loggedUser", loggedUser.hasRole(Roles.USER));
        } catch (NullPointerException e) {
            mav.addObject("loggedUser", false);
        }

        mav.addObject("dbCategoryName", dbCategoryName);
        mav.addObject("activity", experience);
        mav.addObject("reviews", reviews);
        mav.addObject("avgScore", avgScore);
        mav.addObject("reviewCount", reviewCount);
        mav.addObject("countryCity", countryCity);
        return mav;
    }

    @RequestMapping(value = "/experiences/{categoryName}", method = {RequestMethod.POST})
    public ModelAndView experienceCity(@PathVariable("categoryName") final String categoryName,
                                       @Valid @ModelAttribute("filterForm") final FilterForm form,
                                       @ModelAttribute("favExperienceForm") final FavExperienceForm favForm,
                                       @ModelAttribute("loggedUser") final UserModel loggedUser,
                                       final BindingResult errors) {
        final ModelAndView mav = new ModelAndView("redirect:/experiences/" + categoryName);

        if (errors.hasErrors()) {
            return experience(categoryName, form, favForm, loggedUser, Optional.empty(),Optional.empty(), Optional.empty());
        }

        if(form != null){
            Optional<CityModel> cityModel = cityService.getIdByName(form.getActivityCity());

            if (cityModel.isPresent()) {
                long cityId = cityModel.get().getId();
                mav.addObject("cityId", cityId);
            }

            Double priceMax = form.getActivityPriceMax();
            if (priceMax != null) {
                mav.addObject("maxPrice", priceMax);
            }

            Long score = form.getScore();
            if(score != -1){
                mav.addObject("score", score);
            }
            try {
                mav.addObject("loggedUser", loggedUser.hasRole(Roles.USER));
            } catch (NullPointerException e) {
                mav.addObject("loggedUser", false);
            }

        }

//        if(favForm.getFavExp()){
//            favExperienceService.create(loggedUser.getId(), favForm.getExperienceId());
//        }else{
//            favExperienceService.delete(loggedUser.getId(), favForm.getExperienceId());
//        }
//
//        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
//        System.out.println(favForm.getFavExp());
//        System.out.println(favForm.getExperienceId());
        return mav;
    }

}
