package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.interfaces.services.*;
import ar.edu.itba.getaway.models.*;
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

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(UserExperiencesController.class);

    private static final List<String> contentTypes = Arrays.asList("image/png", "image/jpeg", "image/gif", "image/jpg");

    @RequestMapping(value = "/user/favourites")
    public ModelAndView favourites(Principal principal,
                                   @RequestParam Optional<OrderByModel> orderBy,
                                   @RequestParam Optional<Long> experience,
                                   @RequestParam Optional<Boolean> set,
                                   @Valid @ModelAttribute("searchForm") final SearchForm searchForm){
        final ModelAndView mav = new ModelAndView("userFavourites");

        final UserModel user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);

        favExperienceService.setFav(user.getUserId(), set, experience);
        final List<Long> favExperienceModels = favExperienceService.listFavsByUserId(user.getUserId());
        mav.addObject("favExperienceModels", favExperienceModels);

        final OrderByModel[] orderByModels = OrderByModel.values();

        final List<ExperienceModel> experienceList = experienceService.listExperiencesFavsByUserId(user.getUserId(), orderBy);

        final List<Long> avgReviews = new ArrayList<>();
        final List<Integer> listReviewsCount = new ArrayList<>();
        for (ExperienceModel exp : experienceList) {
            avgReviews.add(reviewService.getReviewAverageScore(exp.getExperienceId()));
            listReviewsCount.add(reviewService.getReviewCount(exp.getExperienceId()));
        }

        mav.addObject("orderByModels", orderByModels);
        mav.addObject("experiences", experienceList);
        mav.addObject("avgReviews", avgReviews);
        mav.addObject("listReviewsCount", listReviewsCount);
        mav.addObject("isEditing", false);

        return mav;
    }

    @RequestMapping(value = "/user/experiences")
    public ModelAndView experience(Principal principal,
                                   @RequestParam Optional<OrderByModel> orderBy,
                                   @RequestParam Optional<Long> experience,
                                   @RequestParam Optional<Boolean> set,
                                   @Valid @ModelAttribute("searchForm") final SearchForm searchForm) {
        final ModelAndView mav = new ModelAndView("userExperiences");

        final UserModel user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);

        favExperienceService.setFav(user.getUserId(), set, experience);
        final List<Long> favExperienceModels = favExperienceService.listFavsByUserId(user.getUserId());
        mav.addObject("favExperienceModels", favExperienceModels);

        final OrderByModel[] orderByModels = OrderByModel.values();

        final List<ExperienceModel> experienceList = experienceService.listExperiencesByUserId(user.getUserId(), orderBy);

        final List<Long> avgReviews = new ArrayList<>();
        final List<Integer> listReviewsCount = new ArrayList<>();
        for (ExperienceModel exp : experienceList) {
            avgReviews.add(reviewService.getReviewAverageScore(exp.getExperienceId()));
            listReviewsCount.add(reviewService.getReviewCount(exp.getExperienceId()));
        }

        mav.addObject("orderByModels", orderByModels);
        mav.addObject("experiences", experienceList);
        mav.addObject("avgReviews", avgReviews);
        mav.addObject("listReviewsCount", listReviewsCount);
        mav.addObject("isEditing", true);

        return mav;
    }

    @PreAuthorize("@antMatcherVoter.canDeleteExperienceById(authentication, #experienceId)")
    @RequestMapping(value = "/user/experiences/delete/{experienceId:[0-9]+}", method = {RequestMethod.GET})
    public ModelAndView experienceDelete(@PathVariable("experienceId") final long experienceId,
                                         @ModelAttribute("deleteForm") final DeleteForm form,
                                         @ModelAttribute("searchForm") final SearchForm searchForm) {
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
                                             @ModelAttribute("searchForm") final SearchForm searchForm) {
        if (errors.hasErrors()) {
            return experienceDelete(experienceId, form, searchForm);
        }

        experienceService.deleteExperience(experienceId);
        return new ModelAndView("redirect:/user/experiences");
    }

    @PreAuthorize("@antMatcherVoter.canEditExperienceById(authentication, #experienceId)")
    @RequestMapping(value = "/user/experiences/edit/{experienceId:[0-9]+}", method = {RequestMethod.GET})
    public ModelAndView experienceEdit(@PathVariable("experienceId") final long experienceId,
                                       @ModelAttribute("experienceForm") final ExperienceForm form,
                                       @ModelAttribute("searchForm") final SearchForm searchForm) {

        final ModelAndView mav = new ModelAndView("experienceForm");

        final ExperienceCategory[] categoryModels = ExperienceCategory.values();
        final String country = locationService.getCountryByName().get().getCountryName();
        final List<CityModel> cityModels = locationService.listAllCities();
        final ExperienceModel experience = experienceService.getExperienceById(experienceId).orElseThrow(ExperienceNotFoundException::new);
        final CityModel city = locationService.getCityById(experience.getCityId()).get();
        final String cityName = city.getCityName();

        //TODO no se como hacer para detectar que la primera vez que entro a la edicion
        //me traiga los datos de la experiencia, pero si cuando le doy a guardar
        //y tengo datos mal y me vuelve a llevar al form de edicion, me ponga los datos que
        //estaban bien y que cambie durante la edicion y NO me traiga los datos de la
        //experiencia otra vez :(
        if(form == null){
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

        final String endpoint = "/user/experiences/edit/" + experience.getExperienceId();

        mav.addObject("title", "editExperience.title");
        mav.addObject("description", "editExperience.description");
        mav.addObject("endpoint", endpoint);
        mav.addObject("cancelBtn", "/user/experiences");
        mav.addObject("categories", categoryModels);
        mav.addObject("cities", cityModels);
        mav.addObject("country", country);
        mav.addObject("formCity", cityName);
        mav.addObject("formCategory", experience.getCategoryId());

        return mav;
    }

    @PreAuthorize("@antMatcherVoter.canEditExperienceById(authentication, #experienceId)")
    @RequestMapping(value = "/user/experiences/edit/{experienceId:[0-9]+}", method = {RequestMethod.POST})
    public ModelAndView experienceEditPost(@PathVariable(value="experienceId") final long experienceId,
                                           @Valid @ModelAttribute("experienceForm") final ExperienceForm form,
                                           final BindingResult errors,
                                           @ModelAttribute("searchForm") final SearchForm searchForm) throws IOException {
        if (errors.hasErrors()) {
            return experienceEdit(experienceId, form, searchForm);
        }

        final Long categoryId = form.getExperienceCategory();

        final CityModel city = locationService.getCityByName(form.getExperienceCity()).get();
        final Long cityId = city.getCityId();

        final ExperienceModel experience = experienceService.getExperienceById(experienceId).orElseThrow(ExperienceNotFoundException::new);
        final Long userId = experience.getUserId();

        final Double price = (form.getExperiencePrice().isEmpty()) ? null : Double.parseDouble(form.getExperiencePrice());
        final String description = (form.getExperienceInfo().isEmpty()) ? null : form.getExperienceInfo();
        final String url = (form.getExperienceUrl().isEmpty()) ? null : form.getExperienceUrl();

        final ImageModel imageModel = imageService.getImgByExperienceId(experienceId).orElseThrow(ImageNotFoundException::new);
        final Long imgId = imageModel.getImageId();

        final MultipartFile experienceImg = form.getExperienceImg();
        if(!experienceImg.isEmpty()) {
            if (!contentTypes.contains(experienceImg.getContentType())) {
                errors.rejectValue("experienceImg", "experienceForm.validation.imageFormat");
                return experienceEdit(experienceId, form, searchForm);
            }
        }

        final byte[] image = (experienceImg.isEmpty()) ? null : experienceImg.getBytes();

        final ExperienceModel experienceModel = new ExperienceModel(experienceId, form.getExperienceName(), form.getExperienceAddress(),
                description, form.getExperienceMail(), url, price, cityId, categoryId + 1, userId, imgId, image!=null);

        experienceService.updateExperience(experienceModel, image);

        return new ModelAndView("redirect:/experiences/" + experienceModel.getCategoryName() + "/" + experienceModel.getExperienceId());
    }
}
