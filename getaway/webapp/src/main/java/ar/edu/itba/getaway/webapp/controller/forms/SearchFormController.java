package ar.edu.itba.getaway.webapp.controller.forms;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.OrderByModel;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.models.pagination.Page;
import ar.edu.itba.getaway.interfaces.services.ExperienceService;
import ar.edu.itba.getaway.interfaces.services.FavExperienceService;
import ar.edu.itba.getaway.interfaces.services.ReviewService;
import ar.edu.itba.getaway.interfaces.services.UserService;
import ar.edu.itba.getaway.webapp.forms.SearchForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchFormController.class);

    @RequestMapping(value = "/search_result", method = {RequestMethod.GET})
    public ModelAndView createSearchForm(@ModelAttribute("searchForm") final SearchForm searchForm,
                                         @RequestParam("set") final Optional<Boolean> set,
                                         @RequestParam("experience") final Optional<Long> experience,
                                         @RequestParam Optional<String> query,
                                         @RequestParam Optional<OrderByModel> orderBy,
                                         @RequestParam(value = "pageNum", defaultValue = "1") final Integer pageNum,
                                         Principal principal,
                                         HttpServletRequest request) {
        LOGGER.debug("Endpoint GET /search_result?query={}", query);
        final Page<ExperienceModel> currentPage = experienceService.listExperiencesByName(searchForm.getQuery(), orderBy, pageNum);
        final ModelAndView mav = new ModelAndView("searchResult");

        LOGGER.debug("Pagination");
        LOGGER.debug("CurrentPage {}", currentPage.getCurrentPage());
        LOGGER.debug("TotalPages {}", currentPage.getMaxPage());

        mav.addObject("favExperienceModels", new ArrayList<>());
        if (principal != null) {
            final Optional<UserModel> user = userService.getUserByEmail(principal.getName());
            if(user.isPresent()){
                if(experience.isPresent()){
                    final Optional<ExperienceModel> addFavExperience = experienceService.getExperienceById(experience.get());
                    favExperienceService.setFav(user.get(), set, addFavExperience);
                }
                final List<Long> favExperienceModels = favExperienceService.listFavsByUser(user.get());
                mav.addObject("favExperienceModels", favExperienceModels);
            }else if(set.isPresent()){
                return new ModelAndView("redirect:/login");
            }
        }else if(set.isPresent()){
            return new ModelAndView("redirect:/login");
        }

        final OrderByModel[] orderByModels = OrderByModel.values();
        final List<ExperienceModel> experienceModels = currentPage.getContent();
        final List<Long> avgReviews = reviewService.getListOfAverageScoreByExperienceList(experienceModels);
        final List<Long> listReviewsCount = reviewService.getListOfReviewCountByExperienceList(experienceModels);

        if(query.isPresent()){
            request.setAttribute("query", query);
            mav.addObject("query", query.get());
        }
        if (orderBy.isPresent()){
            request.setAttribute("orderBy", orderBy);
            mav.addObject("orderBy", orderBy.get());
        }

        mav.addObject("path", request.getServletPath());
        mav.addObject("orderByModels", orderByModels);
        mav.addObject("currentPage", currentPage.getCurrentPage());
        mav.addObject("totalPages", currentPage.getTotalPages());
        mav.addObject("minPage", currentPage.getMinPage());
        mav.addObject("maxPage", currentPage.getMaxPage());
        mav.addObject("avgReviews", avgReviews);
        mav.addObject("set", set);
        mav.addObject("experience", experience);
        mav.addObject("experiences", experienceModels);
        mav.addObject("listReviewsCount", listReviewsCount);
        mav.addObject("isEditing", false);

        return mav;
    }

    @RequestMapping(value = "/search_result", method = {RequestMethod.POST})
    public ModelAndView searchByName(@Valid @ModelAttribute("searchForm") final SearchForm searchForm,
                                     final BindingResult errors,
                                     Principal principal,
                                     HttpServletRequest request) {

        if (errors.hasErrors()) {
            LOGGER.debug("Error in the search input");
            return createSearchForm(searchForm,Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty(),1,principal,request);
        }

        LOGGER.debug("Endpoint POST /search_result");
        final ModelAndView mav = new ModelAndView("redirect:/search_result");

        mav.addObject("query", searchForm.getQuery());

        return mav;
    }

}



