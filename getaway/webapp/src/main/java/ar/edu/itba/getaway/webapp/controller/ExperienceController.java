package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.interfaces.services.*;
import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.models.pagination.Page;
import ar.edu.itba.getaway.interfaces.exceptions.CategoryNotFoundException;
import ar.edu.itba.getaway.interfaces.exceptions.ExperienceNotFoundException;
import ar.edu.itba.getaway.webapp.forms.FilterForm;
import ar.edu.itba.getaway.webapp.forms.SearchForm;
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
    private ExperienceService experienceService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private FavExperienceService favExperienceService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceController.class);

    @RequestMapping(value = "/experiences/{categoryName:[A-Za-z_]+}", method = {RequestMethod.GET})
    public ModelAndView experienceGet(@PathVariable("categoryName") final String categoryName,
                                      @ModelAttribute("filterForm") final FilterForm form,
                                      @Valid @ModelAttribute("searchForm") final SearchForm searchForm,
                                      Principal principal,
                                      HttpServletRequest request,
                                      @RequestParam Optional<OrderByModel> orderBy,
                                      @RequestParam Optional<Long> cityId,
                                      @RequestParam Optional<Double> maxPrice,
                                      @RequestParam Optional<Long> score,
                                      @RequestParam Optional<Long> experience,
                                      @RequestParam Optional<Boolean> set,
                                      @RequestParam(value = "pageNum", defaultValue = "1") final Integer pageNum) {
        LOGGER.debug("Endpoint GET {}", request.getServletPath());
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
        final Long categoryId = (long) (category.ordinal() + 1);

        // Order By
        final OrderByModel[] orderByModels = OrderByModel.values();

        if (orderBy.isPresent()){
            request.setAttribute("orderBy", orderBy);
            mav.addObject("orderBy", orderBy.get());
        }

        // Price
        final Optional<Double> maxPriceOpt = experienceService.getMaxPriceByCategoryId(categoryId);
        Double max = maxPriceOpt.get();
        mav.addObject("max", max);
        if(maxPrice.isPresent()){
            request.setAttribute("maxPrice", max);
            max = maxPrice.get();
        }
        mav.addObject("maxPrice", max);

        // Score
        Long scoreVal = 0L;
        if (score.isPresent() && score.get() != -1) {
            request.setAttribute("score", score.get());
            scoreVal = score.get();
        }
        mav.addObject("score", scoreVal);

        // City
        final List<CityModel> cityModels = locationService.listAllCities();

        if (cityId.isPresent()) {
            currentPage = experienceService.listExperiencesByFilter(categoryId, max, scoreVal, cityId.get(), orderBy, pageNum);
            request.setAttribute("cityId", cityId.get());
            mav.addObject("cityId", cityId.get());
        } else {
            currentPage = experienceService.listExperiencesByFilter(categoryId, max, scoreVal, (long) -1, orderBy, pageNum);
            request.setAttribute("cityId", -1);
            mav.addObject("cityId", -1);
        }

        // FavExperiences
        if (principal != null) {
            final Optional<UserModel> user = userService.getUserByEmail(principal.getName());
            if (user.isPresent()) {
                final Long userId = user.get().getUserId();
                favExperienceService.setFav(userId, set, experience);
                final List<Long> favExperienceModels = favExperienceService.listFavsByUserId(userId);
                mav.addObject("favExperienceModels", favExperienceModels);
            }
        } else {
            mav.addObject("favExperienceModels", new ArrayList<>());
        }

        final List<ExperienceModel> currentExperiences = currentPage.getContent();
        final List<Long> avgReviews = reviewService.getListOfAverageScoreByExperienceList(currentExperiences);
        final List<Integer> listReviewsCount = reviewService.getListOfReviewCountByExperienceList(currentExperiences);

        request.setAttribute("pageNum", pageNum);
        final String path = request.getServletPath();

        mav.addObject("path", path);
        mav.addObject("orderByModels", orderByModels);
        mav.addObject("cities", cityModels);
        mav.addObject("dbCategoryName", dbCategoryName);
        mav.addObject("categoryName", categoryName);
        mav.addObject("experiences", currentExperiences);
        mav.addObject("avgReviews", avgReviews);
        mav.addObject("listReviewsCount", listReviewsCount);
        mav.addObject("totalPages", currentPage.getTotalPages());
        mav.addObject("currentPage", currentPage.getCurrentPage());
        mav.addObject("minPage", currentPage.getMinPage());
        mav.addObject("maxPage", currentPage.getMaxPage());
        mav.addObject("isEditing", false);

        return mav;
    }

    @RequestMapping(value = "/experiences/{categoryName:[A-Za-z_]+}", method = {RequestMethod.POST})
    public ModelAndView experiencePost(@PathVariable("categoryName") final String categoryName,
                                       HttpServletRequest request,
                                       @Valid @ModelAttribute("searchForm") final SearchForm searchForm,
                                       @Valid @ModelAttribute("filterForm") final FilterForm form,
                                       final BindingResult errors,
                                       Principal principal) {
        LOGGER.debug("Endpoint POST {}", request.getServletPath());
        final ModelAndView mav = new ModelAndView("redirect:/experiences/" + categoryName);

        if (errors.hasErrors()) {
            return experienceGet(categoryName, form, searchForm, principal, request, Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty() , Optional.empty(), Optional.empty(), 1);
        }

        final Optional<CityModel> cityModel = locationService.getCityByName(form.getExperienceCity());
        if (cityModel.isPresent()) {
            final Long cityId = cityModel.get().getCityId();
            mav.addObject("cityId", cityId);
        }

        final Double priceMax = form.getExperiencePriceMax();
        if (priceMax != null) {
            mav.addObject("maxPrice", priceMax);
        }

        final Long score = form.getScoreVal();
        if (score != -1) {
            mav.addObject("score", score);
        }

        return mav;
    }

    @RequestMapping("/experiences/{categoryName:[A-Za-z_]+}/{experienceId:[0-9]+}")
    public ModelAndView experienceView(Principal principal,
                                       @PathVariable("categoryName") final String categoryName,
                                       @PathVariable("experienceId") final Long experienceId,
                                       @RequestParam Optional<Boolean> set,
                                       @Valid @ModelAttribute("searchForm") final SearchForm searchForm,
                                       HttpServletRequest request) {
        LOGGER.debug("Endpoint GET {}", request.getServletPath());
        final ModelAndView mav = new ModelAndView("experienceDetails");

        final ExperienceModel experience = experienceService.getExperienceById(experienceId).orElseThrow(ExperienceNotFoundException::new);
        final String dbCategoryName = ExperienceCategory.valueOf(categoryName).name();
        final List<ReviewUserModel> reviews = reviewService.getReviewAndUser(experienceId);
        final List<Boolean> listReviewsHasImages = reviewService.getListOfReviewHasImages(reviews);
        final Long avgScore = reviewService.getReviewAverageScore(experienceId);
        final Integer reviewCount = reviewService.getReviewCount(experienceId);
        final CityModel cityModel = locationService.getCityById(experience.getCityId()).get();
        final String city = cityModel.getCityName();
        final String country = locationService.getCountryById(cityModel.getCountryId()).get().getCountryName();

        LOGGER.debug("Experience with id {} has an average score of {}", experienceId, avgScore);

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
                final Long userId = user.get().getUserId();
                favExperienceService.setFav(userId, set, Optional.of(experienceId));
                final List<Long> favExperienceModels = favExperienceService.listFavsByUserId(userId);

                mav.addObject("favExperienceModels", favExperienceModels);
            }
        } else {
            mav.addObject("favExperienceModels", new ArrayList<>());
        }

        return mav;
    }

}
