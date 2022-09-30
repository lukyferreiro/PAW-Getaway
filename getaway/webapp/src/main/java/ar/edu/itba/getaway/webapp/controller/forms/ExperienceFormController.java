package ar.edu.itba.getaway.webapp.controller.forms;

import ar.edu.itba.getaway.exceptions.CityNotFoundException;
import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.services.*;
import ar.edu.itba.getaway.exceptions.UserNotFoundException;
import ar.edu.itba.getaway.webapp.forms.ExperienceForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Controller
public class ExperienceFormController {

    @Autowired
    private ExperienceService experienceService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceFormController.class);

    private static final List<String> contentTypes = Arrays.asList("image/png", "image/jpeg", "image/gif", "image/jpg");

    @RequestMapping(value = "/create_experience", method = {RequestMethod.GET})
    public ModelAndView createActivityForm(@ModelAttribute("experienceForm") final ExperienceForm form) {
        final ModelAndView mav = new ModelAndView("experience_form");

        final ExperienceCategory[] categoryModels = ExperienceCategory.values();
//        final List<String> categories = new ArrayList<>();
//        for (ExperienceCategory categoryModel : categoryModels) {
//            categories.add(categoryModel.getName());
//        }

        final List<CityModel> cities = locationService.listAllCities();
        final String country = locationService.getCountryByName("Argentina").get().getName();

        mav.addObject("title", "createExperience.title");
        mav.addObject("description", "createExperience.description");
        mav.addObject("endpoint", "/create_experience");
        mav.addObject("cancelBtn", "/");
        mav.addObject("categories", categoryModels);
        mav.addObject("cities", cities);
        mav.addObject("country", country);
        mav.addObject("formCity", form.getActivityCity());
        mav.addObject("formCategory", form.getActivityCategory());

        LOGGER.debug("Creado el form de experienceForm");

        return mav;
    }

    @RequestMapping(value = "/create_experience", method = {RequestMethod.POST})
    public ModelAndView createActivity(@Valid @ModelAttribute("experienceForm") final ExperienceForm form,
                                       final BindingResult errors,
                                       Principal principal) throws Exception {

        LOGGER.debug("Entro a create_experience");
        LOGGER.debug(String.format("Category id: %d", form.getActivityCategory()));

        if (errors.hasErrors()) {
            return createActivityForm(form);
        }

        final Integer categoryId = form.getActivityCategory();
//        if (categoryId < 0) {
//            throw new CategoryNotFoundException();
//        }

        final CityModel cityModel = locationService.getCityByName(form.getActivityCity()).orElseThrow(CityNotFoundException::new);
        final Long cityId = cityModel.getId();

        final UserModel user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        final Long userId = user.getId();
        final Double price = (form.getActivityPrice().isEmpty()) ? null : Double.parseDouble(form.getActivityPrice());
        final String description = (form.getActivityInfo().isEmpty()) ? null : form.getActivityInfo();
        final String url = (form.getActivityUrl().isEmpty()) ? null : form.getActivityUrl();
        final ExperienceModel experienceModel;

        final MultipartFile experienceImg = form.getActivityImg();
        if(!experienceImg.isEmpty()) {
            if (!contentTypes.contains(experienceImg.getContentType())) {
                errors.rejectValue("activityImg", "experienceForm.validation.imageFormat");
                return createActivityForm(form);
            }
        }

        final byte[] image = (experienceImg.isEmpty()) ? null : experienceImg.getBytes();

        experienceModel = experienceService.create(form.getActivityName(), form.getActivityAddress(), description,
                form.getActivityMail(), url, price, cityId, categoryId + 1, userId, image);

        return new ModelAndView("redirect:/experiences/" + experienceModel.getCategoryName() + "/" + experienceModel.getExperienceId());
    }

}
