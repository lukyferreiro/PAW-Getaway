package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.interfaces.exceptions.CategoryNotFoundException;
import ar.edu.itba.getaway.interfaces.services.*;
import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.models.pagination.Page;
import ar.edu.itba.getaway.webapp.forms.DeleteForm;
import ar.edu.itba.getaway.webapp.forms.ExperienceForm;
import ar.edu.itba.getaway.webapp.forms.SearchForm;
import ar.edu.itba.getaway.interfaces.exceptions.ExperienceNotFoundException;
import ar.edu.itba.getaway.interfaces.exceptions.ImageNotFoundException;
import ar.edu.itba.getaway.interfaces.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class UserExperiencesController {

    @Autowired
    private UserService userService;
    @Autowired
    private ExperienceService experienceService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private FavExperienceService favExperienceService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private CategoryService categoryService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserExperiencesController.class);

    private static final List<String> contentTypes = Arrays.asList("image/png", "image/jpeg", "image/gif", "image/jpg");
    private static final int MAX_SIZE_PER_FILE = 10000000;

    @RequestMapping(value = "/user/favourites")
    public ModelAndView favourites(Principal principal,
                                   @RequestParam Optional<OrderByModel> orderBy,
                                   @RequestParam Optional<Long> experience,
                                   @RequestParam Optional<Boolean> set,
                                   @RequestParam(value = "pageNum", defaultValue = "1") final int pageNum,
                                   @Valid @ModelAttribute("searchForm") final SearchForm searchForm,
                                   HttpServletRequest request) {
        LOGGER.debug("Endpoint GET {}", request.getServletPath());

        final ModelAndView mav = new ModelAndView("userFavourites");
        final UserModel user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);

        if(experience.isPresent()){
            final Optional<ExperienceModel> addFavExperience = experienceService.getExperienceById(experience.get());
            favExperienceService.setFav(user, set, addFavExperience);
        }

        final List<Long> favExperienceModels = favExperienceService.listFavsByUser(user);
        final OrderByModel[] orderByModels = OrderByModel.values();

        final Page<ExperienceModel> currentPage = experienceService.listExperiencesFavsByUser(user, orderBy, pageNum);
        final List<ExperienceModel> experienceList = currentPage.getContent();
        final List<Long> avgReviews = reviewService.getListOfAverageScoreByExperienceList(experienceList);
        final List<Long> listReviewsCount = reviewService.getListOfReviewCountByExperienceList(experienceList);

        if(orderBy.isPresent()){
            request.setAttribute("orderBy", orderBy);
            mav.addObject("orderBy", orderBy.get());
        }

        mav.addObject("path", request.getServletPath());
        mav.addObject("currentPage", currentPage.getCurrentPage());
        mav.addObject("minPage", currentPage.getMinPage());
        mav.addObject("maxPage", currentPage.getMaxPage());
        mav.addObject("totalPages", currentPage.getTotalPages());
        mav.addObject("favExperienceModels", favExperienceModels);
        mav.addObject("orderByModels", orderByModels);
        mav.addObject("experiences", experienceList);
        mav.addObject("avgReviews", avgReviews);
        mav.addObject("listReviewsCount", listReviewsCount);
        mav.addObject("isEditing", false);

        return mav;
    }

    @RequestMapping(value = "/user/experiences")
    public ModelAndView experience(Principal principal,
                                   @RequestParam Optional<Long> experience,
                                   @RequestParam Optional<Boolean> set,
                                   @Valid @ModelAttribute("searchForm") final SearchForm searchForm,
                                   HttpServletRequest request,
                                   Optional<Boolean> delete,
                                   @RequestParam Optional<OrderByModel> orderBy,
                                   @RequestParam(value = "pageNum", defaultValue = "1") final Integer pageNum) {
        LOGGER.debug("Endpoint GET {}", request.getServletPath());
        final Page<ExperienceModel> currentPage;


        final ModelAndView mav = new ModelAndView("userExperiences");
        final UserModel user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);

        //Order by
        final OrderByModel[] orderByModels = OrderByModel.values();
        orderBy.ifPresent(orderByModel -> mav.addObject("orderBy", orderByModel));

        //Observable
        if(experience.isPresent() && set.isPresent()){
            ExperienceModel myExperience = experienceService.getExperienceById(experience.get()).get();
            final ExperienceModel toUpdateExperience = new ExperienceModel(myExperience.getExperienceId(),myExperience.getExperienceName(), myExperience.getAddress(), myExperience.getDescription(),
                    myExperience.getEmail(), myExperience.getSiteUrl(), myExperience.getPrice(), myExperience.getCity(), myExperience.getCategory(), user, myExperience.getExperienceImage(), set.get(), myExperience.getViews() );

            experienceService.updateExperienceWithoutImg(toUpdateExperience);

        }

        currentPage = experienceService.getExperiencesListByUserId(user, orderBy, pageNum);
        final List<ExperienceModel> currentExperiences = currentPage.getContent();
        final List<Integer> viewsAmount = experienceService.getViewAmountList(currentExperiences);
        final List<Long> avgReviews = reviewService.getListOfAverageScoreByExperienceList(currentExperiences);
        final List<Long> listReviewsCount = reviewService.getListOfReviewCountByExperienceList(currentExperiences);

        final boolean hasExperiences = experienceService.hasExperiencesByUser(user);

        mav.addObject("hasExperiences", hasExperiences);
        mav.addObject("experienceList", currentExperiences);
        request.setAttribute("pageNum", pageNum);
        mav.addObject("totalPages", currentPage.getTotalPages());
        mav.addObject("currentPage", currentPage.getCurrentPage());
        mav.addObject("minPage", currentPage.getMinPage());
        mav.addObject("maxPage", currentPage.getMaxPage());
        mav.addObject("avgReviews", avgReviews);
        mav.addObject("viewsAmount", viewsAmount);
        mav.addObject("listReviewsCount", listReviewsCount);
        mav.addObject("isEditing", true);
        mav.addObject("delete", delete.isPresent());

        return mav;
    }

    @PreAuthorize("@antMatcherVoter.canDeleteExperienceById(authentication, #experienceId)")
    @RequestMapping(value = "/user/experiences/delete/{experienceId:[0-9]+}", method = {RequestMethod.GET})
    public ModelAndView experienceDelete(@PathVariable("experienceId") final long experienceId,
                                         @ModelAttribute("deleteForm") final DeleteForm form,
                                         @ModelAttribute("searchForm") final SearchForm searchForm,
                                         HttpServletRequest request) {
        LOGGER.debug("Endpoint GET {}", request.getServletPath());

        final ModelAndView mav = new ModelAndView("deleteExperience");
        final ExperienceModel experience = experienceService.getExperienceById(experienceId).orElseThrow(ExperienceNotFoundException::new);

        mav.addObject("experience", experience);
        return mav;
    }

    @PreAuthorize("@antMatcherVoter.canDeleteExperienceById(authentication, #experienceId)")
    @RequestMapping(value = "/user/experiences/delete/{experienceId:[0-9]+}", method = {RequestMethod.POST})
    public ModelAndView experienceDeletePost(@PathVariable(value = "experienceId") final long experienceId,
                                             @ModelAttribute("deleteForm") final DeleteForm form,
                                             final BindingResult errors,
                                             @ModelAttribute("searchForm") final SearchForm searchForm,
                                             HttpServletRequest request) {
        LOGGER.debug("Endpoint POST {}", request.getServletPath());

        if (errors.hasErrors()) {
            return experienceDelete(experienceId, form, searchForm, request);
        }

        final ExperienceModel toDeleteExperience = experienceService.getExperienceById(experienceId).orElseThrow(ExperienceNotFoundException::new);
        experienceService.deleteExperience(toDeleteExperience);
        final ModelAndView mav = new ModelAndView("redirect:/user/experiences");
        mav.addObject("delete", true);
        return mav;
    }

    @PreAuthorize("@antMatcherVoter.canEditExperienceById(authentication, #experienceId)")
    @RequestMapping(value = "/user/experiences/edit/{experienceId:[0-9]+}", method = {RequestMethod.GET})
    public ModelAndView experienceEdit(@PathVariable("experienceId") final long experienceId,
                                       @ModelAttribute("experienceForm") final ExperienceForm form,
                                       @ModelAttribute("searchForm") final SearchForm searchForm,
                                       HttpServletRequest request) {
        LOGGER.debug("Endpoint GET {}", request.getServletPath());

        final ModelAndView mav = new ModelAndView("experienceForm");

        final ExperienceCategory[] categoryModels = ExperienceCategory.values();
        final String country = locationService.getCountryByName().get().getCountryName();
        final List<CityModel> cityModels = locationService.listAllCities();
        final ExperienceModel experience = experienceService.getExperienceById(experienceId).orElseThrow(ExperienceNotFoundException::new);
        final CityModel city = experience.getCity();
        final String cityName = city.getCityName();

        if(form.getExperienceName() == null){
            form.setExperienceName(experience.getExperienceName());
            if(experience.getPrice() != null){
                form.setExperiencePrice(experience.getPrice().toString());
            }
            form.setExperienceInfo(experience.getDescription());
            form.setExperienceMail(experience.getEmail());
            form.setExperienceUrl(experience.getSiteUrl());
            form.setExperienceAddress(experience.getAddress());
        } else {
            form.setExperienceName(form.getExperienceName());
            if(experience.getPrice() != null){
                form.setExperiencePrice(form.getExperiencePrice());
            }
            form.setExperienceInfo(form.getExperienceInfo());
            form.setExperienceMail(form.getExperienceMail());
            form.setExperienceUrl(form.getExperienceUrl());
            form.setExperienceAddress(form.getExperienceAddress());
        }

        mav.addObject("title", "editExperience.title");
        mav.addObject("description", "editExperience.description");
        mav.addObject("endpoint", request.getServletPath());
        mav.addObject("cancelBtn", "/user/experiences");
        mav.addObject("categories", categoryModels);
        mav.addObject("cities", cityModels);
        mav.addObject("country", country);
        mav.addObject("formCity", cityName);
        mav.addObject("formCategory", experience.getCategory().getCategoryId());

        return mav;
    }

    @PreAuthorize("@antMatcherVoter.canEditExperienceById(authentication, #experienceId)")
    @RequestMapping(value = "/user/experiences/edit/{experienceId:[0-9]+}", method = {RequestMethod.POST})
    public ModelAndView experienceEditPost(@PathVariable(value="experienceId") final long experienceId,
                                           @Valid @ModelAttribute("experienceForm") final ExperienceForm form,
                                           final BindingResult errors,
                                           @ModelAttribute("searchForm") final SearchForm searchForm,
                                           HttpServletRequest request) throws IOException {
        LOGGER.debug("Endpoint POST {}", request.getServletPath());

        if (errors.hasErrors()) {
            return experienceEdit(experienceId, form, searchForm, request);
        }

        final MultipartFile experienceImg = form.getExperienceImg();
        if(!experienceImg.isEmpty()) {
            if (!contentTypes.contains(experienceImg.getContentType())) {
                errors.rejectValue("experienceImg", "experienceForm.validation.imageFormat");
                return experienceEdit(experienceId, form, searchForm ,request);
            }
            if(experienceImg.getSize() > MAX_SIZE_PER_FILE){
                errors.rejectValue("experienceImg", "experienceForm.validation.imageSize");
                return experienceEdit(experienceId, form, searchForm ,request);
            }
        }

        final ExperienceModel experience = experienceService.getExperienceById(experienceId).orElseThrow(ExperienceNotFoundException::new);
//        final ImageExperienceModel imageExperienceModel = imageService.getImgByExperience(experience).orElseThrow(ImageNotFoundException::new);
        final ImageModel imageModel = experience.getExperienceImage();
        final UserModel user = experience.getUser();
        final CategoryModel category = categoryService.getCategoryById(form.getExperienceCategory()+1).orElseThrow(CategoryNotFoundException::new);
        final CityModel cityModel = locationService.getCityByName(form.getExperienceCity()).get();
        final Double price = (form.getExperiencePrice().isEmpty()) ? null : Double.parseDouble(form.getExperiencePrice());
        final String description = (form.getExperienceInfo().isEmpty()) ? null : form.getExperienceInfo();
        final String url = (form.getExperienceUrl().isEmpty()) ? null : form.getExperienceUrl();
        final byte[] image = (experienceImg.isEmpty()) ? imageModel.getImage() : experienceImg.getBytes();

        final ExperienceModel toUpdateExperience = new ExperienceModel(experienceId,form.getExperienceName(), form.getExperienceAddress(), description,
                form.getExperienceMail(), url, price, cityModel, category, user, imageModel, experience.getObservable(), experience.getViews());

        experienceService.updateExperience(toUpdateExperience, image);


        final ModelAndView mav = new ModelAndView("redirect:/experiences/" + toUpdateExperience.getCategory().getCategoryName() + "/" + toUpdateExperience.getExperienceId());
        mav.addObject("success", true);
        return mav;
    }
}
