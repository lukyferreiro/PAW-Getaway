package ar.edu.itba.getaway.webapp.auth.forms;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.models.pagination.Page;
import ar.edu.itba.getaway.services.ExperienceService;
import ar.edu.itba.getaway.services.FavExperienceService;
import ar.edu.itba.getaway.services.ReviewService;
import ar.edu.itba.getaway.services.UserService;
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
public class SearchFormController {

    @Autowired
    private ExperienceService experienceService;
    @Autowired
    private UserService userService;

    @Autowired
    private FavExperienceService favExperienceService;

    @Autowired
    private ReviewService reviewService;

    //TODO Agregar loggers ,PAGINARLO

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchFormController.class);

    @RequestMapping(value = "/search_result", method = {RequestMethod.GET})
    public ModelAndView createSearchForm(@ModelAttribute("searchForm") final SearchForm searchForm,
                                         @RequestParam("set") final Optional<Boolean> set,
                                         @RequestParam("experience") final Optional<Long> experience,
                                         @RequestParam Optional<String> query,
                                         @RequestParam(value = "pageNum", defaultValue = "1") final int pageNum,
                                         Principal principal,
                                         HttpServletRequest request) {

        final Page<ExperienceModel> currentPage = experienceService.getByName(searchForm.getQuery(), pageNum);
        final ModelAndView mav = new ModelAndView("search_result");

        LOGGER.debug("Pagination");
        LOGGER.debug("CurrentPage {}", currentPage.getCurrentPage());
        LOGGER.debug("TotalPages {}", currentPage.getMaxPage());

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

//        List<ExperienceModel> currentExperiences = currentPage.getContent();

        final List<ExperienceModel> experienceModels = currentPage.getContent();
        final List<Long> avgReviews = new ArrayList<>();
        for (ExperienceModel exp : experienceModels) {
            avgReviews.add(reviewService.getAverageScore(exp.getExperienceId()));
        }

        if(query.isPresent()){
            request.setAttribute("query", query);
            mav.addObject("query", query.get());
        }

        mav.addObject("currentPage", currentPage.getCurrentPage());
        mav.addObject("totalPages", currentPage.getMaxPage());
        mav.addObject("avgReviews", avgReviews);
        mav.addObject("set", set);
        mav.addObject("experience", experience);
        mav.addObject("experiences", experienceModels);
        mav.addObject("isEditing", false);

        return mav;
    }


    @RequestMapping(value = "/search_result", method = {RequestMethod.POST})
    public ModelAndView searchByName(
                                     @Valid @ModelAttribute("searchForm") final SearchForm searchForm,
                                     @RequestParam("set") final Optional<Boolean> set,
                                     @RequestParam("experience") final Optional<Long> experience,
                                     @RequestParam Optional<String> query,
                                     @RequestParam(value = "pageNum", defaultValue = "1") final int pageNum,
                                     final BindingResult errors,
                                     Principal principal,
                                     HttpServletRequest request) {

        final ModelAndView mav = new ModelAndView("/search_result");

        if (errors.hasErrors()) {
            return createSearchForm(searchForm,set,experience,query,pageNum,principal,request);
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

//        List<ExperienceModel> currentExperiences = currentPage.getContent();

        Page<ExperienceModel> currentPage = experienceService.getByName(searchForm.getQuery(), pageNum);
        final List<ExperienceModel> experienceModels = currentPage.getContent();

        final List<Long> avgReviews = new ArrayList<>();
        for (ExperienceModel exp : experienceModels) {
            avgReviews.add(reviewService.getAverageScore(exp.getExperienceId()));
        }

        if(query.isPresent()){
            request.setAttribute("query", query);
            mav.addObject("query", query.get());
        }

        mav.addObject("currentPage", currentPage.getCurrentPage());
        mav.addObject("totalPages", currentPage.getMaxPage());
        mav.addObject("avgReviews", avgReviews);
        mav.addObject("set", set);
        mav.addObject("experience", experience);
        mav.addObject("experiences", experienceModels);
        mav.addObject("isEditing", false);

        return mav;
    }


    private void setFav(long userId, Optional<Boolean> set, Optional<Long> experience) {
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



