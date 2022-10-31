package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.interfaces.exceptions.CityNotFoundException;
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
    private CategoryService categoryService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private FavAndViewExperienceService favAndViewExperienceService;

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

        final CategoryModel categoryModel = categoryService.getCategoryByName(categoryName).orElseThrow(CategoryNotFoundException::new);

        // Order By
        final OrderByModel[] orderByModels = OrderByModel.getUserOrderByModel();
        mav.addObject("orderBy", OrderByModel.OrderByRankDesc);
        orderBy.ifPresent(orderByModel -> mav.addObject("orderBy", orderByModel));

        // Price
        Double max = experienceService.getMaxPriceByCategory(categoryModel).orElse(0D);
        mav.addObject("max", max);
        if(maxPrice.isPresent()){
            max = maxPrice.get();
        }
        mav.addObject("maxPrice", max);

        // Score
        Long scoreVal = 0L;
        if (score.isPresent() && score.get() != -1) {
            scoreVal = score.get();
        }
        mav.addObject("score", scoreVal);

        // City
        final List<CityModel> cityModels = locationService.listAllCities();

        UserModel owner = null;
        if (principal != null) {
            final Optional<UserModel> user = userService.getUserByEmail(principal.getName());
            if(user.isPresent()){
                owner = user.get();
            }
        }

        // FavExperiences
        if (owner != null) {
            if(experience.isPresent()){
                final Optional<ExperienceModel> addFavExperience = experienceService.getVisibleExperienceById(experience.get(),owner);
                favAndViewExperienceService.setFav(owner, set, addFavExperience);
            }
        }else if(set.isPresent()){
            return new ModelAndView("redirect:/login");
        }

        if (cityId.isPresent() && cityId.get()>0) {
            final CityModel city = locationService.getCityById(cityId.get()).orElseThrow(CityNotFoundException::new);
            currentPage = experienceService.listExperiencesByFilter(categoryModel, max, scoreVal, city, orderBy, pageNum, owner);
            mav.addObject("cityId", cityId.get());
        } else {
            currentPage = experienceService.listExperiencesByFilter(categoryModel, max, scoreVal, null, orderBy, pageNum, owner);
            mav.addObject("cityId", -1);
        }

        final List<ExperienceModel> currentExperiences = currentPage.getContent();

        request.setAttribute("pageNum", pageNum);
        final String path = request.getServletPath();

        mav.addObject("path", path);
        mav.addObject("orderByModels", orderByModels);
        mav.addObject("cities", cityModels);
        mav.addObject("dbCategoryName", categoryModel.getCategoryName());
        mav.addObject("categoryName", categoryName);
        mav.addObject("experiences", currentExperiences);
        mav.addObject("totalPages", currentPage.getTotalPages());
        mav.addObject("currentPage", currentPage.getCurrentPage());
        mav.addObject("minPage", currentPage.getMinPage());
        mav.addObject("maxPage", currentPage.getMaxPage());

        return mav;
    }

    @RequestMapping(value = "/experiences/{categoryName:[A-Za-z_]+}", method = {RequestMethod.POST})
    public ModelAndView experienceFilter(@PathVariable("categoryName") final String categoryName,
                                       HttpServletRequest request,
                                       @RequestParam Optional<OrderByModel> orderBy,
                                       @Valid @ModelAttribute("searchForm") final SearchForm searchForm,
                                       @Valid @ModelAttribute("filterForm") final FilterForm form,
                                       final BindingResult errors,
                                       Principal principal) {
        LOGGER.debug("Endpoint POST {}", request.getServletPath());
        final ModelAndView mav = new ModelAndView("redirect:/experiences/" + categoryName);

        if (errors.hasErrors()) {
            return experienceGet(categoryName, form, searchForm, principal, request, orderBy, Optional.empty(), Optional.empty(), Optional.empty() , Optional.empty(), Optional.empty(), 1);
        }

        //TODO: check add only city and then access cityid. Multiple db access
        final Optional<CityModel> cityModel = locationService.getCityByName(form.getExperienceCity());
        cityModel.ifPresent(model -> mav.addObject("cityId", model.getCityId()));

        final Double priceMax = form.getExperiencePriceMax();
        if (priceMax != null) {
            mav.addObject("maxPrice", priceMax);
        }

        final Long score = form.getScoreVal();
        if (score != -1) {
            mav.addObject("score", score);
        }

        orderBy.ifPresent(orderByModel -> mav.addObject("orderBy", orderByModel));

        return mav;
    }

    @RequestMapping("/experiences/{categoryName:[A-Za-z_]+}/{experienceId:[0-9]+}")
    public ModelAndView experienceView(Principal principal,
                                       @PathVariable("categoryName") final String categoryName,
                                       @PathVariable("experienceId") final Long experienceId,
                                       @RequestParam Optional<Boolean> view,
                                       @RequestParam Optional<Boolean> set,
                                       @RequestParam Optional<Boolean> setObs,
                                       @RequestParam Optional<Boolean> success,
                                       @RequestParam Optional<Boolean> successReview,
                                       @Valid @ModelAttribute("searchForm") final SearchForm searchForm,
                                       HttpServletRequest request,
                                       @RequestParam(value = "pageNum", defaultValue = "1") final int pageNum) {
        LOGGER.debug("Endpoint GET {}", request.getServletPath());
        final ModelAndView mav = new ModelAndView("experienceDetails");

        //This declaration of category is in order to check if the categoryName is valid
        final CategoryModel category = categoryService.getCategoryByName(categoryName).orElseThrow(CategoryNotFoundException::new);
        UserModel owner = null;
        if (principal != null) {
            final Optional<UserModel> user = userService.getUserByEmail(principal.getName());
            if(user.isPresent()){
                owner = user.get();
            }
        }
        final ExperienceModel experience = experienceService.getVisibleExperienceById(experienceId, owner).orElseThrow(ExperienceNotFoundException::new);

        final Page<ReviewModel> currentPage = reviewService.getReviewAndUser(experience, pageNum);
        final List<ReviewModel> reviews = currentPage.getContent();

        LOGGER.debug("Experience with id {} has an average score of {}", experienceId, experience.getAverageScore());

        request.setAttribute("pageNum", pageNum);

        mav.addObject("dbCategoryName", category.getCategoryName());
        mav.addObject("experience", experience);
        mav.addObject("reviews", reviews);
        mav.addObject("success", success.isPresent());
        mav.addObject("successReview", successReview.isPresent());
        mav.addObject("currentPage", currentPage.getCurrentPage());
        mav.addObject("minPage", currentPage.getMinPage());
        mav.addObject("maxPage", currentPage.getMaxPage());
        mav.addObject("totalPages", currentPage.getTotalPages());

        if (owner != null) {
            if (view.isPresent() || setObs.isPresent()) {
                //Si soy el usuario owner puedo editar la visibilidad
                if (experience.getUser().equals(owner)) {
                    setObs.ifPresent(aBoolean -> experienceService.changeVisibility(experience, aBoolean));
                } else { //El owner no suma visualizaciones
                    if (view.isPresent()) {
                        favAndViewExperienceService.setViewed(owner, experience);
                        experienceService.increaseViews(experience);
                    }
                }
            }
            favAndViewExperienceService.setFav(owner, set, Optional.of(experience));
            set.ifPresent(experience::setIsFav);
            mav.addObject("isEditing", experienceService.experienceBelongsToUser(owner, experience));
        } else {
            if (set.isPresent()) {
                return new ModelAndView("redirect:/login");
            } else if (view.isPresent()) {
                experienceService.increaseViews(experience);
            }
            mav.addObject("isEditing", false);
        }

        return mav;
    }
}
