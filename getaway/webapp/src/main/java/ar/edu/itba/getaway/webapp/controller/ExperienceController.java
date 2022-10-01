package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.models.pagination.Page;
import ar.edu.itba.getaway.services.*;
import ar.edu.itba.getaway.exceptions.CategoryNotFoundException;
import ar.edu.itba.getaway.exceptions.ExperienceNotFoundException;
import ar.edu.itba.getaway.webapp.forms.FilterForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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
    private ImageService imageService;
    @Autowired
    private ExperienceService experienceService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private FavExperienceService favExperienceService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceController.class);

    @RequestMapping(value = "/experiences/{categoryName:[A-Za-z_]+}", method = {RequestMethod.GET})
    public ModelAndView experience(@PathVariable("categoryName") final String categoryName,
                                   @ModelAttribute("filterForm") final FilterForm form,
                                   Principal principal,
                                   HttpServletRequest request,
                                   @RequestParam Optional<String> orderBy,
                                   @RequestParam Optional<String> direction,
                                   @RequestParam Optional<Long> cityId,
                                   @RequestParam Optional<Double> maxPrice,
                                   @RequestParam Optional<Long> score,
                                   @RequestParam Optional<Long> experience,
                                   @RequestParam Optional<Boolean> set,
                                   @RequestParam (value="pageNum", defaultValue = "1") final int pageNum) {
        final ModelAndView mav = new ModelAndView("experiences");
        final Page<ExperienceModel> currentPage;

        // Category
        // Ordinal empieza en 0
        final ExperienceCategory category;
        try {
            category = ExperienceCategory.valueOf(categoryName);
        } catch (Exception e) {
            throw new CategoryNotFoundException();
        }

        final String dbCategoryName = category.toString();
        final int id = category.ordinal() + 1;

        // Order By
        final OrderByModel[] orderByModels = OrderByModel.values();

        String orderByQuery = "";

        if (orderBy.isPresent()) {
            orderByQuery = " ORDER BY " + orderBy.get() + " " + direction.get();
        }

        // Price
        final Optional<Double> maxPriceOpt = experienceService.getMaxPrice(id);
        double max = maxPriceOpt.get();
        mav.addObject("max", max);
        if(maxPrice.isPresent()){
            max = maxPrice.get();
        }
        mav.addObject("maxPrice", max);

        // Score
        long scoreVal = 0;
        if (score.isPresent() && score.get() != -1) {
            scoreVal = score.get();
        }
        mav.addObject("score", scoreVal);

        // City
        final List<CityModel> cityModels = locationService.listAllCities();

        if (cityId.isPresent()) {
            currentPage = experienceService.listByFilter(id, max, scoreVal, cityId.get(), orderByQuery, pageNum);
            mav.addObject("cityId", cityId.get());
        } else {
            currentPage = experienceService.listByFilter(id, max, scoreVal, new Long(-1), orderByQuery, pageNum);
            mav.addObject("cityId", -1);
        }

        // FavExperiences
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
        final List<Integer> listReviewsCount = new ArrayList<>();
        for (ExperienceModel exp : currentExperiences) {
            avgReviews.add(reviewService.getAverageScore(exp.getExperienceId()));
            listReviewsCount.add(reviewService.getReviewCount(exp.getExperienceId()));
        }

        // Http Query
        StringBuilder requestURL = new StringBuilder(request.getRequestURL().toString());
        String queryString = request.getQueryString();
        String path;

        if (queryString == null) {
            path = requestURL.append("?").toString();
        } else {
            path = requestURL.append('?').append(queryString).append("&").toString();
        }

        // mav info
        mav.addObject("path", path);
        mav.addObject("orderByModels", orderByModels);
        mav.addObject("cities", cityModels);
        mav.addObject("dbCategoryName", dbCategoryName);
        mav.addObject("categoryName", categoryName);
        mav.addObject("experiences", currentExperiences);
        mav.addObject("avgReviews", avgReviews);
        mav.addObject("listReviewsCount", listReviewsCount);
        mav.addObject("totalPages", currentPage.getMaxPage());
        mav.addObject("currentPage", currentPage.getCurrentPage());
        mav.addObject("isEditing", false);

        return mav;
    }

    @RequestMapping("/experiences/{categoryName:[A-Za-z_]+}/{experienceId:[0-9]+}")
    public ModelAndView experienceView(Principal principal,
                                       @PathVariable("categoryName") final String categoryName,
                                       @PathVariable("experienceId") final long experienceId,
                                       @RequestParam Optional<Boolean> set) {
        final ModelAndView mav = new ModelAndView("experience_details");

        final ExperienceModel experience = experienceService.getById(experienceId).orElseThrow(ExperienceNotFoundException::new);
        final String dbCategoryName = ExperienceCategory.valueOf(categoryName).name();
        final List<ReviewUserModel> reviews = reviewService.getReviewAndUser(experienceId);

        final List<Boolean> listReviewsHasImages = new ArrayList<>();
        for(ReviewUserModel review : reviews){
            listReviewsHasImages.add(imageService.getImgById(review.getImgId()).get().getImage() != null);
        }

        final Long avgScore = reviewService.getAverageScore(experienceId);
        final Integer reviewCount = reviewService.getReviewCount(experienceId);
        final CityModel cityModel = locationService.getCityById(experience.getCityId()).get();
        final String city = cityModel.getName();
        final String country = locationService.getCountryById(cityModel.getCountryId()).get().getName();

        LOGGER.debug("AVGSCORE reviewService {}", avgScore);
//        experienceAvgReview.ifPresent(aLong -> mav.addObject("reviewAvg", aLong));

        mav.addObject("reviewAvg", avgScore);
        mav.addObject("dbCategoryName", dbCategoryName);
        mav.addObject("experience", experience);
        mav.addObject("reviews", reviews);
        mav.addObject("isEditing", false);
        mav.addObject("listReviewsHasImages", listReviewsHasImages);
        mav.addObject("avgScore", avgScore);
        mav.addObject("reviewCount", reviewCount);
        mav.addObject("city", city);
        mav.addObject("country", country);

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

    @RequestMapping(value = "/experiences/{categoryName:[A-Za-z_]+}", method = {RequestMethod.POST})
    public ModelAndView experienceCity(@PathVariable("categoryName") final String categoryName,
                                       HttpServletRequest request,
                                       @Valid @ModelAttribute("filterForm") final FilterForm form,
                                       Principal principal,
                                       final BindingResult errors) {
        final ModelAndView mav = new ModelAndView("redirect:/experiences/" + categoryName);

        if (errors.hasErrors()) {
            return experience(categoryName, form, principal, request, Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty() , Optional.empty(), Optional.empty(), 1);
        }

        final Optional<CityModel> cityModel = locationService.getCityByName(form.getActivityCity());
        if (cityModel.isPresent()) {
            final long cityId = cityModel.get().getId();
            mav.addObject("cityId", cityId);
        }

        final Double priceMax = form.getActivityPriceMax();
        if (priceMax != null) {
            mav.addObject("maxPrice", priceMax);
        }

        final Long score = form.getScoreVal();
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
