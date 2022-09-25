package ar.edu.itba.getaway.webapp.controller.forms;

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

    private static final List<String> contentTypes = Arrays.asList("image/png", "image/jpeg", "image/gif");

    @RequestMapping(value = "/create_experience", method = {RequestMethod.GET})
    public ModelAndView createActivityForm(@ModelAttribute("experienceForm") final ExperienceForm form) {
        final ModelAndView mav = new ModelAndView("experience_form");

        ExperienceCategory[] categoryModels = ExperienceCategory.values();
        List<String> categories = new ArrayList<>();
        for (ExperienceCategory categoryModel : categoryModels) {
            categories.add(categoryModel.getName());
        }

        List<CountryModel> countryModels = countryService.listAll();
        List<CityModel> cityModels = cityService.listAll();

        mav.addObject("categories", categories);
        mav.addObject("cities", cityModels);
        mav.addObject("countries", countryModels);
        return mav;
    }

    @RequestMapping(value = "/create_experience", method = {RequestMethod.POST})
    public ModelAndView createActivity(@Valid @ModelAttribute("experienceForm") final ExperienceForm form,
                                       final BindingResult errors,
                                       Principal principal) throws Exception {

        if (errors.hasErrors()) {
            return createActivityForm(form);
        }

        long categoryId = form.getActivityCategoryId();
        if (categoryId < 0) {
            throw new CategoryNotFoundException();
        }

        long cityId = cityService.getIdByName(form.getActivityCity()).get().getId();

        final UserModel user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        final Long userId = user.getId();
        final Double price = (form.getActivityPrice().isEmpty()) ? null : Double.parseDouble(form.getActivityPrice());
        final String description = (form.getActivityInfo().isEmpty()) ? null : form.getActivityInfo();
        final String url = (form.getActivityUrl().isEmpty()) ? null : form.getActivityUrl();
        final ExperienceModel experienceModel;
        final MultipartFile activityImg = form.getActivityImg();

        if (!activityImg.isEmpty()) {
            if (contentTypes.contains(activityImg.getContentType())) {
                experienceModel = exp.create(form.getActivityName(), form.getActivityAddress(),
                        description, url, price, cityId, categoryId + 1, userId, true);
                final ImageExperienceModel imageModel = imageService.createExperienceImg(
                        form.getActivityImg().getBytes(), experienceModel.getId(), true);
            } else {
                return createActivityForm(form);
            }
        } else {
            experienceModel = exp.create(form.getActivityName(), form.getActivityAddress(),
                    description, url, price, cityId, categoryId + 1, userId, false);
        }

        return new ModelAndView("redirect:/experiences/" + experienceModel.getCategoryName() + "/" + experienceModel.getId());
    }

}
