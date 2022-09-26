package ar.edu.itba.getaway.webapp.controller.forms;

import ar.edu.itba.getaway.exceptions.CityNotFoundException;
import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.services.*;
import ar.edu.itba.getaway.exceptions.CategoryNotFoundException;
import ar.edu.itba.getaway.exceptions.UserNotFoundException;
import ar.edu.itba.getaway.webapp.forms.ExperienceForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ExperienceFormController {

    @Autowired
    private ExperienceService exp;
    @Autowired
    private CityService cityService;
    @Autowired
    private CountryService countryService;;
    @Autowired
    private UserService userService;
    @Autowired
    private ImageService imageService;

    private static final List<String> contentTypes = Arrays.asList("image/png", "image/jpeg", "image/gif", "image/jpg");

    @RequestMapping(value = "/create_experience", method = {RequestMethod.GET})
    public ModelAndView createActivityForm(@ModelAttribute("experienceForm") final ExperienceForm form) {
        final ModelAndView mav = new ModelAndView("experience_form");

        final ExperienceCategory[] categoryModels = ExperienceCategory.values();
        final List<String> categories = new ArrayList<>();
        for (ExperienceCategory categoryModel : categoryModels) {
            categories.add(categoryModel.getName());
        }

        final List<CountryModel> countries = countryService.listAll();
        final List<CityModel> cities = cityService.listAll();

        mav.addObject("title", "createExperience.title");
        mav.addObject("description", "createExperience.description");
        mav.addObject("endpoint", "/create_experience");
        mav.addObject("cancelBtn", "/");
        mav.addObject("categories", categories);
        mav.addObject("cities", cities);
        mav.addObject("countries", countries);
        mav.addObject("formCity", form.getActivityCity());
        mav.addObject("formCategory", form.getActivityCategory());

        return mav;
    }

    @RequestMapping(value = "/create_experience", method = {RequestMethod.POST})
    public ModelAndView createActivity(@Valid @ModelAttribute("experienceForm") final ExperienceForm form,
                                       final BindingResult errors,
                                       Principal principal) throws Exception {

        if (errors.hasErrors()) {
            return createActivityForm(form);
        }

        final long categoryId = form.getActivityCategoryId();
        if (categoryId < 0) {
            throw new CategoryNotFoundException();
        }

        final CityModel cityModel = cityService.getIdByName(form.getActivityCity()).orElseThrow(CityNotFoundException::new);
        final long cityId = cityModel.getId();

        final UserModel user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        final Long userId = user.getId();
        final Double price = (form.getActivityPrice().isEmpty()) ? null : Double.parseDouble(form.getActivityPrice());
        final String description = (form.getActivityInfo().isEmpty()) ? null : form.getActivityInfo();
        final String url = (form.getActivityUrl().isEmpty()) ? null : form.getActivityUrl();
        final ExperienceModel experienceModel;
        final MultipartFile activityImg = form.getActivityImg();

        //TODO estar haciendo esto no mg nada, la logica de si se debe crear una imagen
        //o no deberia estar en los services /daos
        if (!activityImg.isEmpty()) {
            if (contentTypes.contains(activityImg.getContentType())) {
                experienceModel = exp.create(form.getActivityName(), form.getActivityAddress(),
                        description, url, price, cityId, categoryId + 1, userId, true);
                final ImageExperienceModel imageModel = imageService.createExperienceImg(
                        form.getActivityImg().getBytes(), experienceModel.getId(), true);
            } else {
                //Todo: Enviar mensaje de formato de imagen inválido
                errors.rejectValue("activityImg", "experienceForm.validation.imageFormat");
                return createActivityForm(form);
            }
        } else {
            experienceModel = exp.create(form.getActivityName(), form.getActivityAddress(),
                    description, url, price, cityId, categoryId + 1, userId, false);
        }

        return new ModelAndView("redirect:/experiences/" + experienceModel.getCategoryName() + "/" + experienceModel.getId());
    }

}
