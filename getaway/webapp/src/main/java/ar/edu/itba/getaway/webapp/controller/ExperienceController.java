package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.models.pagination.Page;
import ar.edu.itba.getaway.services.*;
import ar.edu.itba.getaway.exceptions.CategoryNotFoundException;
import ar.edu.itba.getaway.exceptions.ExperienceNotFoundException;
import ar.edu.itba.getaway.webapp.controller.forms.ExperienceFormController;
import ar.edu.itba.getaway.webapp.forms.FilterForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceController.class);

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
                                   @RequestParam Optional<Boolean> set,
                                   @RequestParam (value="pageNum", defaultValue = "1") final int pageNum
    ) {
        final ModelAndView mav = new ModelAndView("experiences");

        LOGGER.debug("ACA LLEGA");
        LOGGER.debug(categoryName);
        LOGGER.debug(String.format("PAGENUM : %d", pageNum));


        // Ordinal empieza en 0
        final ExperienceCategory category;
        try {
            category = ExperienceCategory.valueOf(categoryName);
        } catch (Exception e) {
            throw new CategoryNotFoundException();
        }

        final String dbCategoryName = category.getName();
        final int id = category.ordinal() + 1;
        Page<ExperienceModel> currentPage;
        final List<CityModel> cityModels = cityService.listAll();

        final Optional<Double> maxPriceOpt = experienceService.getMaxPrice(id);
        double max = maxPriceOpt.get();
        if(maxPrice.isPresent()){
            max = maxPrice.get();
        }

        long scoreVal = (long) 0.0;
        if (score.isPresent() && score.get() != -1) {
            scoreVal = score.get();
        }
        String order = "";

        if (orderBy.isPresent()) {
            order = " ORDER BY " + orderBy.get() + " " + direction.get();
        }

        if (cityId.isPresent()) {
            currentPage = experienceService.listByFilterWithCity(id, max, cityId.get(), scoreVal, order, pageNum);
        } else {
            currentPage = experienceService.listByFilter(id, max, scoreVal, order, pageNum);
        }

        if (principal != null) {
            final Optional<UserModel> user = userService.getUserByEmail(principal.getName());

            if (user.isPresent()) {
                final long userId = user.get().getId();
                setFav(userId, set, experience);
                final List<Long> favExperienceModels = favExperienceService.listByUserId(userId);

                mav.addObject("favExperienceModels", favExperienceModels);
            }
        } else {
            mav.addObject("favExperienceModels", new ArrayList<>());
        }

        List<ExperienceModel> currentExperiences = currentPage.getContent();

        final List<Long> avgReviews = new ArrayList<>();
        for (ExperienceModel exp : currentExperiences) {
            avgReviews.add(experienceService.getAvgReviews(exp.getExperienceId()).get());
        }

        LOGGER.debug(String.format("Current page: %d", currentPage.getCurrentPage()));
        LOGGER.debug(String.format("Total pages: %d", currentPage.getMaxPage()));

        mav.addObject("cities", cityModels);
        mav.addObject("dbCategoryName", dbCategoryName);
        mav.addObject("categoryName", categoryName);
        mav.addObject("experiences", currentExperiences);
        mav.addObject("avgReviews", avgReviews);
        mav.addObject("totalPages", currentPage.getMaxPage());
        mav.addObject("currentPage", currentPage.getCurrentPage());

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

        if (principal != null) {
            final Optional<UserModel> user = userService.getUserByEmail(principal.getName());

            if (user.isPresent()) {
                final long userId = user.get().getId();
                setFav(userId, set, Optional.of(experienceId));
                final List<Long> favExperienceModels = favExperienceService.listByUserId(userId);

                mav.addObject("favExperienceModels", favExperienceModels);
            }
        } else {
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
            return experience(categoryName, form, principal, Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty() , Optional.empty(), Optional.empty(), 1);
        }

        final Optional<CityModel> cityModel = cityService.getIdByName(form.getActivityCity());
        if (cityModel.isPresent()) {
            final long cityId = cityModel.get().getId();
            mav.addObject("cityId", cityId);
        }

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
