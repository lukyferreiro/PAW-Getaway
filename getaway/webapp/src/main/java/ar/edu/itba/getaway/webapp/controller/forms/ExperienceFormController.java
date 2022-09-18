package ar.edu.itba.getaway.webapp.controller.forms;

import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.services.*;
import ar.edu.itba.getaway.webapp.auth.MyUserDetails;
import ar.edu.itba.getaway.webapp.exceptions.AccessDeniedException;
import ar.edu.itba.getaway.webapp.exceptions.CategoryNotFoundException;
import ar.edu.itba.getaway.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.getaway.webapp.forms.ExperienceForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ExperienceFormController {
    @Autowired
    ExperienceService exp;
    @Autowired
    CityService cityService;
    @Autowired
    CountryService countryService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    TagService tagService;
    @Autowired
    ImageService imageService;
    @Autowired
    ImageExperienceService imageExperienceService;
    @Autowired
    UserService userService;

    @RequestMapping(value = "/create_experience", method = {RequestMethod.GET})
    public ModelAndView createActivityForm(@ModelAttribute("experienceForm") final ExperienceForm form) {
        final ModelAndView mav = new ModelAndView("createExperience");
        ExperienceCategory[] categoryModels = ExperienceCategory.values();
        List<String> categories = new ArrayList<>();
        for (ExperienceCategory categoryModel : categoryModels) {
            categories.add(categoryModel.getName());
        }

        List<CountryModel> countryModels = countryService.listAll();
        List<TagModel> tagModels = tagService.listAll();
        List<CityModel> cityModels = cityService.listAll();

//        List<CityModel> cityModels = new ArrayList<>();
//        boolean flag = true;
//        for (int j = 0; j<countryModels.size() && flag ; j++){
//            if(countryModels.get(j).getName().equals(form.getActivityCountry())){
//                cityModels = cityService.getByCountryId(j + 1);
//                flag=false;
//            }
//        }

        mav.addObject("categories", categories);
        mav.addObject("cities", cityModels);
        mav.addObject("countries", countryModels);
        mav.addObject("tags", tagModels);
        return mav;
    }

//   @RequestMapping(path = "/create_experience",produces = MediaType.APPLICATION_JSON_VALUE)
//   @ResponseBody
//    public List<CityModel> getCities(@RequestParam String country){
//        List<CountryModel> countryModels = countryService.listAll();
//        List<CityModel> cityModels = new ArrayList<>();
//        boolean flag = true;
//        for (int j = 0; j<countryModels.size() && flag ; j++){
//            if(countryModels.get(j).getName().equals(country)){
//                return cityService.getByCountryId(j + 1);
//            }
//        }
//
//        return cityModels;
//    }


    @RequestMapping(value = "/create_experience", method = {RequestMethod.POST})
    public ModelAndView createActivity(@Valid @ModelAttribute("experienceForm") final ExperienceForm form,
                                       final BindingResult errors,
                                       @AuthenticationPrincipal MyUserDetails userDetails) throws Exception {

        if (errors.hasErrors()) {
            return createActivityForm(form);
        }

        long categoryId = form.getActivityCategoryId();
        if (categoryId < 0) {
            throw new CategoryNotFoundException();
        }

        long cityId = cityService.getIdByName(form.getActivityCity()).get().getId();

        long userId;
        try {
            String email = userDetails.getUsername();
            UserModel userModel = userService.getUserByEmail(email).orElseThrow(UserNotFoundException::new);
            userId = userModel.getId();
        }catch (NullPointerException e){
            throw new AccessDeniedException();
        }

        Double price = (form.getActivityPrice().isEmpty()) ? null : Double.parseDouble(form.getActivityPrice());
        String description = (form.getActivityInfo().isEmpty()) ? null : form.getActivityInfo();
        String url = (form.getActivityUrl().isEmpty()) ? null : form.getActivityUrl();
        final ExperienceModel experienceModel;

        if (!form.getActivityImg().isEmpty()) {
            experienceModel = exp.create(form.getActivityName(), form.getActivityAddress(), description, url, price, cityId, categoryId + 1, userId, true);
            final ImageModel imageModel = imageService.create(form.getActivityImg().getBytes());
            imageExperienceService.create(imageModel.getId(), experienceModel.getId(), true);
        }
        else {
            experienceModel = exp.create(form.getActivityName(), form.getActivityAddress(), description, url, price, cityId, categoryId + 1, userId, false);
        }

        return new ModelAndView("redirect:/experiences/" + experienceModel.getCategoryName() + "/");
    }

}
