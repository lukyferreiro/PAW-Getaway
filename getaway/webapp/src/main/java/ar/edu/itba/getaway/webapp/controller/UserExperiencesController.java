package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.exceptions.*;
import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.services.*;
import ar.edu.itba.getaway.webapp.forms.DeleteForm;
import ar.edu.itba.getaway.webapp.forms.ExperienceForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
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

    private static final List<String> contentTypes = Arrays.asList("image/png", "image/jpeg", "image/gif", "image/jpg");

    @RequestMapping(value = "/user/experiences", method = {RequestMethod.GET})
    public ModelAndView experience(Principal principal) {
        final ModelAndView mav = new ModelAndView("user_experiences");

        final UserModel user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        final List<ExperienceModel> experienceList = experienceService.getByUserId(user.getId());

        final List<Long> avgReviews = new ArrayList<>();
        for(ExperienceModel experience : experienceList){
            avgReviews.add(experienceService.getAvgReviews(experience.getId()).get());
        }

        mav.addObject("activities", experienceList);
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
        final List<String> categories = new ArrayList<>();
        for (ExperienceCategory categoryModel : categoryModels) {
            categories.add(categoryModel.getName());
        }

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
            form.setActivityName(experience.getName());
            if(experience.getPrice() != null){
                form.setActivityPrice(experience.getPrice().toString());
            }
            form.setActivityInfo(experience.getDescription());
            //Email TODO
            form.setActivityUrl(experience.getSiteUrl());
            form.setActivityAddress(experience.getAddress());
        } else {
            form.setActivityName(form.getActivityName());
            if(experience.getPrice() != null){
                form.setActivityPrice(form.getActivityPrice());
            }
            form.setActivityInfo(form.getActivityInfo());
            //Email TODO
            form.setActivityUrl(form.getActivityUrl());
            form.setActivityAddress(form.getActivityAddress());
        }

        final String endpoint = "/user/experiences/edit/" + experience.getId();

        mav.addObject("title", "editExperience.title");
        mav.addObject("description", "editExperience.description");
        mav.addObject("endpoint", endpoint);
        mav.addObject("cancelBtn", "/user/experiences");
        mav.addObject("categories", categories);
        mav.addObject("cities", cityModels);
        mav.addObject("countries", countryModels);
        mav.addObject("formCity", cityName);
        mav.addObject("formCategory", experience.getCategoryName());

        return mav;
    }

    @RequestMapping(value = "/user/experiences/edit/{experienceId}", method = {RequestMethod.POST})
    public ModelAndView experienceEditPost(@PathVariable(value="experienceId") final long experienceId,
                                           @Valid @ModelAttribute("experienceForm") final ExperienceForm form,
                                           final BindingResult errors) throws IOException {
        if (errors.hasErrors()) {
            return experienceEdit(experienceId, form);
        }

        long categoryId = form.getActivityCategoryId();
        if (categoryId < 0) {
            throw new CategoryNotFoundException();
        }

        final CityModel city = cityService.getIdByName(form.getActivityCity()).orElseThrow(CityNotFoundException::new);
        final long cityId = city.getId();

        final ExperienceModel experience = experienceService.getById(experienceId).orElseThrow(ExperienceNotFoundException::new);
        final long userId = experience.getUserId();

        //TODO estar haciendo esto no mg nada, la logica de si se debe crear una imagen
        //o no deberia estar en los services /daos
        final MultipartFile activityImg = form.getActivityImg();
        boolean hasImg = experience.isHasImage();
        if(!hasImg) {
            if (!activityImg.isEmpty()) {
                if (contentTypes.contains(activityImg.getContentType())) {
                    hasImg = true;
                    final ImageExperienceModel img = imageService.createExperienceImg(
                            form.getActivityImg().getBytes(), experience.getId(), true);
                } else {
                    //Todo: Enviar mensaje de formato de imagen inválido
                    errors.rejectValue("activityImg", "experienceForm.validation.imageFormat");
                    return experienceEdit(experienceId, form);
                }
            }
        } else {
            final ImageModel img = imageService.getImgByExperienceId(experienceId).orElseThrow(ImageNotFoundException::new);
            imageService.updateImg(form.getActivityImg().getBytes(), img.getId());
        }

        final Double price = (form.getActivityPrice().isEmpty()) ? null : Double.parseDouble(form.getActivityPrice());
        final String description = (form.getActivityInfo().isEmpty()) ? null : form.getActivityInfo();
        final String url = (form.getActivityUrl().isEmpty()) ? null : form.getActivityUrl();

        final ExperienceModel experienceModel = new ExperienceModel(experienceId, form.getActivityName(), form.getActivityAddress(),
                description, url, price, cityId, categoryId + 1, userId, hasImg);
        experienceService.update(experienceId, experienceModel);

        return new ModelAndView("redirect:/experiences/" + experienceModel.getCategoryName() + "/" + experienceModel.getId());
    }

}
