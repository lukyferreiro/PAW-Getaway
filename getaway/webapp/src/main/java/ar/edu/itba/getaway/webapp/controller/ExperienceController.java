package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.exceptions.CityNotFoundException;
import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.services.*;
import ar.edu.itba.getaway.exceptions.CategoryNotFoundException;
import ar.edu.itba.getaway.exceptions.ExperienceNotFoundException;
import ar.edu.itba.getaway.webapp.forms.FilterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
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
    private ReviewService reviewService;
    @Autowired
    private FavExperienceService favExperienceService;

    @RequestMapping(value = "/experiences/{categoryName}", method = {RequestMethod.GET})
    public ModelAndView experience(@PathVariable("categoryName") final String categoryName,
                                   @ModelAttribute("filterForm") final FilterForm form,
                                   Principal principal,
                                   @RequestParam Optional<String> orderBy,
                                   @RequestParam Optional<String> direction,
                                   @RequestParam Optional<Long> cityId,
                                   @RequestParam Optional<Double> maxPrice,
                                   @RequestParam Optional<Long> score,
                                   @RequestParam Optional<Long> experience,
                                   @RequestParam Optional<Boolean> set) {
        final ModelAndView mav = new ModelAndView("experiences");

        // Ordinal empieza en 0
        final ExperienceCategory category;
        try {
            category = ExperienceCategory.valueOf(categoryName);
        } catch (Exception e) {
            throw new CategoryNotFoundException();
        }

        final String dbCategoryName = category.getName();
        final int id = category.ordinal() + 1;
        List<ExperienceModel> experienceList = new ArrayList<>();
        final List<CityModel> cityModels = cityService.listAll();

        if (cityId.isPresent()) {
            if (maxPrice.isPresent() && maxPrice.get() > 0) {
                if (score.isPresent()) {
                    if(orderBy.isPresent()){
                        if (direction.isPresent()){
                            if(direction.get().equals("asc")){
                                experienceList = experienceService.listByCategoryPriceCityAndScoreOrderBy(id, maxPrice.get(), cityId.get(), score.get(), orderBy.get());
                            }else{
                                experienceList = experienceService.listByCategoryPriceCityAndScoreOrderByDesc(id, maxPrice.get(), cityId.get(), score.get(), orderBy.get());
                            }
                        }
                    }else{
                        experienceList = experienceService.listByCategoryPriceCityAndScore(id, maxPrice.get(), cityId.get(), score.get());
                    }
                } else {
                    if(orderBy.isPresent()){
                        if (direction.isPresent()){
                            if(direction.get().equals("asc")){
                                experienceList = experienceService.listByCategoryPriceAndCityOrderBy(id, maxPrice.get(), cityId.get(), orderBy.get());
                            }else{
                                experienceList = experienceService.listByCategoryPriceAndCityOrderByDesc(id, maxPrice.get(), cityId.get(), orderBy.get());
                            }
                        }
                    }else {
                        experienceList = experienceService.listByCategoryPriceAndCity(id, maxPrice.get(), cityId.get());
                    }
                }
            } else {
                if (score.isPresent()) {
                    if(orderBy.isPresent()){
                        if (direction.isPresent()){
                            if(direction.get().equals("asc")){
                                experienceList = experienceService.listByCategoryCityAndScoreOrderBy(id, cityId.get(), score.get(), orderBy.get());
                            }else{
                                experienceList = experienceService.listByCategoryCityAndScoreOrderByDesc(id, cityId.get(), score.get(), orderBy.get());
                            }
                        }
                    }else {
                        experienceList = experienceService.listByCategoryCityAndScore(id, cityId.get(), score.get());
                    }
                } else {
                    if(orderBy.isPresent()){
                        if (direction.isPresent()){
                            if(direction.get().equals("asc")){
                                experienceList = experienceService.listByCategoryAndCityOrderBy(id, cityId.get(), orderBy.get());
                            }else{
                                experienceList = experienceService.listByCategoryAndCityOrderByDesc(id, cityId.get(), orderBy.get());
                            }
                        }
                    }else {
                        experienceList = experienceService.listByCategoryAndCity(id, cityId.get());
                    }
                }
            }
        } else if (maxPrice.isPresent() && maxPrice.get() > 0) {
            if (score.isPresent()) {
                if(orderBy.isPresent()){
                    if (direction.isPresent()){
                        if(direction.get().equals("asc")){
                            experienceList = experienceService.listByCategoryPriceAndScoreOrderBy(id, maxPrice.get(), score.get(), orderBy.get());
                        }else{
                            experienceList = experienceService.listByCategoryPriceAndScoreOrderByDesc(id, maxPrice.get(), score.get(), orderBy.get());
                        }
                    }
                }else {
                    experienceList = experienceService.listByCategoryPriceAndScore(id, maxPrice.get(), score.get());
                }
            } else {
                if(orderBy.isPresent()){
                    if (direction.isPresent()){
                        if(direction.get().equals("asc")){
                            experienceList = experienceService.listByCategoryAndPriceOrderBy(id, maxPrice.get(), orderBy.get());
                        }else{
                            experienceList = experienceService.listByCategoryAndPriceOrderByDesc(id, maxPrice.get(), orderBy.get());
                        }
                    }
                }else {
                    experienceList = experienceService.listByCategoryAndPrice(id, maxPrice.get());
                }
            }
        } else if (score.isPresent()) {
            if(orderBy.isPresent()){
                if (direction.isPresent()){
                    if(direction.get().equals("asc")){
                        experienceList = experienceService.listByCategoryAndScoreOrderBy(id, score.get(), orderBy.get());
                    }else{
                        experienceList = experienceService.listByCategoryAndScoreOrderByDesc(id, score.get(), orderBy.get());
                    }
                }
            }else {
                experienceList = experienceService.listByCategoryAndScore(id, score.get());
            }
        } else {
            if(orderBy.isPresent()){
                if (direction.isPresent()){
                    if(direction.get().equals("asc")){
                        experienceList = experienceService.listByCategoryOrderBy(id,orderBy.get());
                    }else{
                        experienceList = experienceService.listByCategoryOrderByDesc(id,orderBy.get());
                    }
                }
            }else {
                experienceList = experienceService.listByCategory(id);
            }
        }

        if(principal!=null){
            final Optional<UserModel> user = userService.getUserByEmail(principal.getName());

            if (user.isPresent()) {
                final long userId = user.get().getId();
                setFav(userId, set, experience);
                final List<Long> favExperienceModels = favExperienceService.listByUserId(userId);

                mav.addObject("favExperienceModels", favExperienceModels);
                mav.addObject("loggedUser", true);
            }
        }else{
            mav.addObject("loggedUser", false);
            mav.addObject("favExperienceModels", new ArrayList<>());
        }

        final List<Long> avgReviews = new ArrayList<>();
        for (ExperienceModel exp : experienceList) {
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
    public ModelAndView experienceView(Principal principal,
                                       @PathVariable("categoryName") final String categoryName,
                                       @PathVariable("experienceId") final long experienceId,
                                       @RequestParam Optional<Boolean> set) {
        final ModelAndView mav = new ModelAndView("experience_details");

        final ExperienceModel experience = experienceService.getById(experienceId).orElseThrow(ExperienceNotFoundException::new);
        final String dbCategoryName = ExperienceCategory.valueOf(categoryName).getName();
        final List<ReviewUserModel> reviews = reviewService.getReviewAndUser(experienceId);
        final Double avgScore = reviewService.getAverageScore(experienceId);
        final Integer reviewCount = reviewService.getReviewCount(experienceId);
        final String countryCity = experienceService.getCountryCity(experienceId);

        final Optional<Long> experienceAvgReview = experienceService.getAvgReviews(experienceId);
        experienceAvgReview.ifPresent(aLong -> mav.addObject("reviewAvg", aLong));

        mav.addObject("dbCategoryName", dbCategoryName);
        mav.addObject("experience", experience);
        mav.addObject("reviews", reviews);
        mav.addObject("avgScore", avgScore);
        mav.addObject("reviewCount", reviewCount);
        mav.addObject("countryCity", countryCity);

        if(principal!=null){
            final Optional<UserModel> user = userService.getUserByEmail(principal.getName());

            if (user.isPresent()) {
                final long userId = user.get().getId();
                setFav(userId, set, Optional.of(experienceId));
                final List<Long> favExperienceModels = favExperienceService.listByUserId(userId);

                mav.addObject("favExperienceModels", favExperienceModels);
                mav.addObject("loggedUser", true);
            }
        }else{
            mav.addObject("loggedUser", false);
            mav.addObject("favExperienceModels", new ArrayList<>());
        }


        return mav;
    }

    @RequestMapping(value = "/experiences/{categoryName}", method = {RequestMethod.POST})
    public ModelAndView experienceCity(@PathVariable("categoryName") final String categoryName,
                                       @Valid @ModelAttribute("filterForm") final FilterForm form,
                                       Principal principal,
                                       final BindingResult errors) {
        final ModelAndView mav = new ModelAndView("redirect:/experiences/" + categoryName);

        if (errors.hasErrors()) {
            return experience(categoryName, form, principal, Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty() , Optional.empty(), Optional.empty());

        }

//        Optional<CityModel> cityModel = cityService.getIdByName(form.getActivityCity());
//        if (cityModel.isPresent()) {
//            long cityId = cityModel.get().getId();
//            mav.addObject("cityId", cityId);
//        }
        final CityModel cityModel = cityService.getIdByName(form.getActivityCity()).orElseThrow(CityNotFoundException::new);
        final long cityId = cityModel.getId();
        mav.addObject("cityId", cityId);

        final Double priceMax = form.getActivityPriceMax();
        if (priceMax != null) {
            mav.addObject("maxPrice", priceMax);
        }

        final Long score = form.getScore();
        if (score != -1) {
            mav.addObject("score", score);
        }

        return mav;
    }

    private void setFav(long userId, Optional<Boolean> set, Optional<Long> experience){
        final List<Long> favExperienceModels = favExperienceService.listByUserId(userId);

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
