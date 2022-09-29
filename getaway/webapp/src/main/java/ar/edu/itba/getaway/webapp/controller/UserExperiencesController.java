package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.exceptions.*;
import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.services.*;
import ar.edu.itba.getaway.webapp.forms.DeleteForm;
import ar.edu.itba.getaway.webapp.forms.ExperienceForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private CityService cityService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private FavExperienceService favExperienceService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserExperiencesController.class);

    private static final List<String> contentTypes = Arrays.asList("image/png", "image/jpeg", "image/gif", "image/jpg");

    @RequestMapping(value = "/user/favourites")
    public ModelAndView favourites(Principal principal,
                                   @RequestParam Optional<String> direction,
                                   @RequestParam Optional<String> orderBy,
                                   @RequestParam Optional<Long> experience,
                                   @RequestParam Optional<Boolean> set){
        final ModelAndView mav = new ModelAndView("user_favourites");

        final UserModel user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        setFav(user.getId(), set, experience);
        final List<Long> favExperienceModels = favExperienceService.listByUserId(user.getId());
        mav.addObject("favExperienceModels", favExperienceModels);

        final OrderByModel[] orderByModels = OrderByModel.values();

        //TODO NO ME TRAE LAS FAVORITAS
        List<ExperienceModel> experienceList = new ArrayList<>();
        String order = "";
        if (orderBy.isPresent())
            order = " ORDER BY " + orderBy.get() + " " +direction.get();

        experienceList = experienceService.listAll(order);

        final List<Long> avgReviews = new ArrayList<>();
        for (ExperienceModel exp : experienceList) {
            avgReviews.add(experienceService.getAvgReviews(exp.getExperienceId()).get());
        }

        mav.addObject("orderByModels", orderByModels);
        mav.addObject("experiences", experienceList);
        mav.addObject("avgReviews", avgReviews);
        return mav;
    }

    @RequestMapping(value = "/user/experiences")
    public ModelAndView experience(Principal principal,
                                   @RequestParam Optional<String> direction,
                                   @RequestParam Optional<String> orderBy,
                                   @RequestParam Optional<Long> experience,
                                   @RequestParam Optional<Boolean> set) {
        final ModelAndView mav = new ModelAndView("user_experiences");

        final OrderByModel[] orderByModels = OrderByModel.values();

        final UserModel user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);

        setFav(user.getId(), set, experience);
        final List<Long> favExperienceModels = favExperienceService.listByUserId(user.getId());
        mav.addObject("favExperienceModels", favExperienceModels);

        List<ExperienceModel> experienceList = new ArrayList<>();

        String order = "";
        if (orderBy.isPresent())
            order = " ORDER BY " +orderBy.get() + " " +direction.get();

        experienceList = experienceService.listByUserId(user.getId(), order);


        final List<Long> avgReviews = new ArrayList<>();

        for (ExperienceModel exp : experienceList) {
            avgReviews.add(experienceService.getAvgReviews(exp.getExperienceId()).get());
        }

        mav.addObject("orderByModels", orderByModels);
        mav.addObject("experiences", experienceList);
        mav.addObject("avgReviews", avgReviews);

        return mav;
    }


    @RequestMapping(value = "/user/experiences/delete/{experienceId}", method = {RequestMethod.GET})
    public ModelAndView experienceDelete(@PathVariable("experienceId") final long experienceId,
                                         @ModelAttribute("deleteForm") final DeleteForm form) {
        final ModelAndView mav = new ModelAndView("deleteExperience");
        final ExperienceModel experience = experienceService.getById(experienceId).orElseThrow(ExperienceNotFoundException::new);

        mav.addObject("experience", experience);
        return mav;
    }

    @RequestMapping(value = "/user/experiences/delete/{experienceId}", method = {RequestMethod.POST})
    public ModelAndView experienceDeletePost(@PathVariable(value = "experienceId") final long experienceId,
                                             @ModelAttribute("deleteForm") final DeleteForm form,
                                             final BindingResult errors) {
        if (errors.hasErrors()) {
            return experienceDelete(experienceId, form);
        }

        experienceService.delete(experienceId);
        return new ModelAndView("redirect:/user/experiences");
    }

    @RequestMapping(value = "/user/experiences/edit/{experienceId}", method = {RequestMethod.GET})
    public ModelAndView experienceEdit(@PathVariable("experienceId") final long experienceId,
                                       @ModelAttribute("experienceForm") final ExperienceForm form) {

        final ModelAndView mav = new ModelAndView("experience_form");

        final ExperienceCategory[] categoryModels = ExperienceCategory.values();
//        final List<String> categories = new ArrayList<>();
////        for (ExperienceCategory categoryModel : categoryModels) {
////            categories.add(categoryModel.getName());
////        }

        final List<CountryModel> countryModels = countryService.listAll();
        final List<CityModel> cityModels = cityService.listAll();
        final ExperienceModel experience = experienceService.getById(experienceId).orElseThrow(ExperienceNotFoundException::new);
        final CityModel city = cityService.getById(experience.getCityId()).orElseThrow(CityNotFoundException::new);
        final String cityName = city.getName();

        //TODO no se como hacer para detectar que la primera vez que entro a la edicion
        //me traiga los datos de la experiencia, pero si cuando le doy a guardar
        //y tengo datos mal y me vuelve a llevar al form de edicion, me ponga los datos que
        //estaban bien y que cambie durante la edicion y NO me traiga los datos de la
        //experiencia otra vez :(
        if(form == null){
            form.setActivityName(experience.getExperienceName());
            if(experience.getPrice() != null){
                form.setActivityPrice(experience.getPrice().toString());
            }
            form.setActivityInfo(experience.getDescription());
            form.setActivityMail(experience.getEmail());
            form.setActivityUrl(experience.getSiteUrl());
            form.setActivityAddress(experience.getAddress());
        } else {
            form.setActivityName(form.getActivityName());
            if(experience.getPrice() != null){
                form.setActivityPrice(form.getActivityPrice());
            }
            form.setActivityInfo(form.getActivityInfo());
            form.setActivityMail(form.getActivityMail());
            form.setActivityUrl(form.getActivityUrl());
            form.setActivityAddress(form.getActivityAddress());
        }

        final String endpoint = "/user/experiences/edit/" + experience.getExperienceId();

        mav.addObject("title", "editExperience.title");
        mav.addObject("description", "editExperience.description");
        mav.addObject("endpoint", endpoint);
        mav.addObject("cancelBtn", "/user/experiences");
        mav.addObject("categories", categoryModels);
        mav.addObject("cities", cityModels);
        mav.addObject("countries", countryModels);
        mav.addObject("formCity", cityName);
        mav.addObject("formCategory", experience.getCategoryId());

        return mav;
    }

    @RequestMapping(value = "/user/experiences/edit/{experienceId}", method = {RequestMethod.POST})
    public ModelAndView experienceEditPost(@PathVariable(value="experienceId") final long experienceId,
                                           @Valid @ModelAttribute("experienceForm") final ExperienceForm form,
                                           final BindingResult errors) throws IOException {
        if (errors.hasErrors()) {
            return experienceEdit(experienceId, form);
        }

        long categoryId = form.getActivityCategory();

        final CityModel city = cityService.getIdByName(form.getActivityCity()).orElseThrow(CityNotFoundException::new);
        final long cityId = city.getId();

        final ExperienceModel experience = experienceService.getById(experienceId).orElseThrow(ExperienceNotFoundException::new);
        final long userId = experience.getUserId();

        final Double price = (form.getActivityPrice().isEmpty()) ? null : Double.parseDouble(form.getActivityPrice());
        final String description = (form.getActivityInfo().isEmpty()) ? null : form.getActivityInfo();
        final String url = (form.getActivityUrl().isEmpty()) ? null : form.getActivityUrl();

        final ImageModel imageModel = imageService.getImgByExperienceId(experienceId).orElseThrow(ImageNotFoundException::new);
        final Long imgId = imageModel.getId();

        final MultipartFile experienceImg = form.getActivityImg();
        if(!experienceImg.isEmpty()) {
            if (!contentTypes.contains(experienceImg.getContentType())) {
                errors.rejectValue("activityImg", "experienceForm.validation.imageFormat");
                return experienceEdit(experienceId, form);
            }
        }

        final byte[] image = (experienceImg.isEmpty()) ? null : experienceImg.getBytes();

        final ExperienceModel experienceModel = new ExperienceModel(experienceId, form.getActivityName(), form.getActivityAddress(),
                description, form.getActivityMail(), url, price, cityId, categoryId + 1, userId, imgId, image!=null);

        experienceService.update(experienceModel, image);

        return new ModelAndView("redirect:/experiences/" + experienceModel.getCategoryName() + "/" + experienceModel.getExperienceId());
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
