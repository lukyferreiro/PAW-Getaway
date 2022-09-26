package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.services.*;
import ar.edu.itba.getaway.webapp.forms.FavExperienceForm;
import ar.edu.itba.getaway.exceptions.CategoryNotFoundException;
import ar.edu.itba.getaway.exceptions.ExperienceNotFoundException;
import ar.edu.itba.getaway.exceptions.ImageNotFoundException;
import ar.edu.itba.getaway.webapp.forms.FilterForm;
import com.sun.org.apache.xpath.internal.operations.Mod;
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
    private UserService userService;
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
                                   @ModelAttribute("loggedUser") final UserModel loggedUser,
                                   @RequestParam Optional<Long> cityId,
                                   @RequestParam Optional<Double> maxPrice,
                                   @RequestParam Optional<Long> score,
                                   @RequestParam Optional<Long> experience,
                                   @RequestParam Optional<Boolean> set) {
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


            if(set.isPresent() && experience.isPresent()){
                if(set.get()) {
                    favExperienceService.create(loggedUser.getId(), experience.get());
                }
                else{
                    favExperienceService.delete(loggedUser.getId(), experience.get());
                }
            }

            List<Long> favExperienceModels = favExperienceService.listByUserId(loggedUser.getId());
            mav.addObject("favExperienceModels", favExperienceModels);

        } catch (NullPointerException e) {
            mav.addObject("loggedUser", false);
            mav.addObject("favExperienceModels", new ArrayList<>());
        }


        List<Long> avgReviews = new ArrayList<>();
        for(ExperienceModel exp : experienceList){
            avgReviews.add(experienceService.getAvgReviews(exp.getId()).get());
        }


        mav.addObject("cities", cityModels);
        mav.addObject("dbCategoryName", dbCategoryName);
        mav.addObject("categoryName", categoryName);
        mav.addObject("experiences", experienceList);
        mav.addObject("avgReviews", avgReviews);

        return mav;
    }

    @RequestMapping("/experiences/{categoryName}/{experienceId}")
    public ModelAndView experienceView(@PathVariable("categoryName") final String categoryName,
                                       @PathVariable("experienceId") final long experienceId) {
        final ModelAndView mav = new ModelAndView("experience_details");

        final ExperienceModel experience = experienceService.getById(experienceId).orElseThrow(ExperienceNotFoundException::new);
        final String dbCategoryName = ExperienceCategory.valueOf(categoryName).getName();
        final List<ReviewUserModel> reviews = reviewService.getReviewAndUser(experienceId);
        final Double avgScore = reviewService.getAverageScore(experienceId);
        final Integer reviewCount = reviewService.getReviewCount(experienceId);
        final String countryCity = experienceService.getCountryCity(experienceId);
        final Optional<Long> experienceAvgReview = experienceService.getAvgReviews(experienceId);

        if(experienceAvgReview.isPresent()){
            mav.addObject("reviewAvg", experienceAvgReview.get());
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
                                       @ModelAttribute("loggedUser") final UserModel loggedUser,
                                       final BindingResult errors) {
        final ModelAndView mav = new ModelAndView("redirect:/experiences/" + categoryName);

        if (errors.hasErrors()) {
            return experience(categoryName, form, loggedUser,Optional.empty(), Optional.empty(), Optional.empty(),Optional.empty(), Optional.empty());

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
        Long score = form.getScore();
        if(score != -1){
            mav.addObject("score", score);
        }

        return mav;
    }

//
//    @RequestMapping(value = "/experiences/{categoryName}/{experienceId}", method = {RequestMethod.POST})
//    public ModelAndView experienceFav(@PathVariable("categoryName") final String categoryName,
//                                      @PathVariable("experienceId") final long experienceId,
//                                      @RequestParam Optional<Boolean> set,
//                                       @ModelAttribute("loggedUser") final UserModel loggedUser,
//                                       final BindingResult errors) {
//        final ModelAndView mav = new ModelAndView("redirect:/experiences/" + categoryName);
//
//        try {
//            mav.addObject("loggedUser", loggedUser.hasRole(Roles.USER));
//
//            if(set.isPresent()){
//                if(set.get()) {
//                    favExperienceService.create(loggedUser.getId(), experienceId);
//                }
//                else{
//                    favExperienceService.delete(loggedUser.getId(), experienceId);
//                }
//            }
//
//        } catch (NullPointerException e) {
//            mav.addObject("loggedUser", false);
//        }
//
//        return mav;
//    }

}
