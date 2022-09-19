package ar.edu.itba.getaway.webapp.controller.forms;

import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.services.*;
import ar.edu.itba.getaway.webapp.exceptions.AccessDeniedException;
import ar.edu.itba.getaway.webapp.exceptions.CategoryNotFoundException;
import ar.edu.itba.getaway.webapp.forms.ExperienceForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
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
    private TagService tagService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ImageExperienceService imageExperienceService;

    private static final List<String> contentTypes = Arrays.asList("image/png", "image/jpeg", "image/gif");

    @RequestMapping(value = "/create_experience", method = {RequestMethod.GET})
    public ModelAndView createActivityForm(@ModelAttribute("experienceForm") final ExperienceForm form,
                                           @ModelAttribute("loggedUser") final UserModel loggedUser) {
        final ModelAndView mav = new ModelAndView("experience_form");

        ExperienceCategory[] categoryModels = ExperienceCategory.values();
        List<String> categories = new ArrayList<>();
        for (ExperienceCategory categoryModel : categoryModels) {
            categories.add(categoryModel.getName());
        }

        List<CountryModel> countryModels = countryService.listAll();
        List<TagModel> tagModels = tagService.listAll();
        List<CityModel> cityModels = cityService.listAll();

        try {
            mav.addObject("loggedUser", loggedUser.hasRole(Roles.USER));
        } catch (NullPointerException e) {
            mav.addObject("loggedUser", false);
        }

        mav.addObject("categories", categories);
        mav.addObject("cities", cityModels);
        mav.addObject("countries", countryModels);
        mav.addObject("tags", tagModels);
        return mav;
    }


    @RequestMapping(value = "/create_experience", method = {RequestMethod.POST})
    public ModelAndView createActivity(@Valid @ModelAttribute("experienceForm") final ExperienceForm form,
                                       final BindingResult errors,
                                       @ModelAttribute("loggedUser") final UserModel loggedUser) throws Exception {

        if (errors.hasErrors()) {
            return createActivityForm(form, loggedUser);
        }

        long categoryId = form.getActivityCategoryId();
        if (categoryId < 0) {
            throw new CategoryNotFoundException();
        }

        long cityId = cityService.getIdByName(form.getActivityCity()).get().getId();

        long userId;

        try {
            userId = loggedUser.getId();
        } catch (NullPointerException e) {
            throw new AccessDeniedException();
        }

        Double price = (form.getActivityPrice().isEmpty()) ? null : Double.parseDouble(form.getActivityPrice());
        String description = (form.getActivityInfo().isEmpty()) ? null : form.getActivityInfo();
        String url = (form.getActivityUrl().isEmpty()) ? null : form.getActivityUrl();
        ExperienceModel experienceModel;
        MultipartFile activityImg = form.getActivityImg();

        if (!activityImg.isEmpty()) {

            if (contentTypes.contains(activityImg.getContentType())) {
                experienceModel = exp.create(form.getActivityName(), form.getActivityAddress(),
                        description, url, price, cityId, categoryId + 1, userId, true);
                final ImageModel imageModel = imageService.create(form.getActivityImg().getBytes());
                imageExperienceService.create(imageModel.getId(), experienceModel.getId(), true);
            } else {
                return createActivityForm(form, loggedUser);
            }
        } else {
            experienceModel = exp.create(form.getActivityName(), form.getActivityAddress(),
                    description, url, price, cityId, categoryId + 1, userId, false);
        }

        final ModelAndView mav = new ModelAndView("redirect:/experiences/" + experienceModel.getCategoryName() + "/" + experienceModel.getId());

        try {
            mav.addObject("loggedUser", loggedUser.hasRole(Roles.USER));
        } catch (NullPointerException e) {
            mav.addObject("loggedUser", false);
        }

        return mav;
    }

}
