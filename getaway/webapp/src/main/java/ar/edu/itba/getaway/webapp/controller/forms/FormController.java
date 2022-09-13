package ar.edu.itba.getaway.webapp.controller.forms;

import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.services.*;
import ar.edu.itba.getaway.webapp.forms.ExperienceForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FormController {
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

    @RequestMapping(value = "/create_experience", method = {RequestMethod.GET})
    public ModelAndView createActivityForm(@ModelAttribute("experienceForm") final ExperienceForm form){
        final ModelAndView mav = new ModelAndView("form");
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

    public List<CityModel> getCities(String country){
        List<CountryModel> countryModels = countryService.listAll();

        List<CityModel> cityModels = new ArrayList<>();
        boolean flag = true;
        for (int j = 0; j<countryModels.size() && flag ; j++){
            if(countryModels.get(j).getName().equals(country)){
                return cityService.getByCountryId(j + 1);
            }
        }

        return cityModels;
    }


    @RequestMapping(value = "/create_experience", method = {RequestMethod.POST})
    public ModelAndView createActivity(@Valid @ModelAttribute("experienceForm") final ExperienceForm form, final BindingResult errors) throws Exception {

        if(errors.hasErrors()){
            return createActivityForm(form);
        }

        long categoryId = form.getActivityCategoryId() ;
        if(categoryId < 0){
            throw new Exception();
        }

        //TODO NO FUNCIONA PQ ESTA BUSCANDO NOMBRES EN CASTELLANO Y ESTAN GUARDADOS EN INGLES
        // long categoryId = categoryService.getByName(form.getActivityCategory()).get().getId();
        long cityId = cityService.getIdByName(form.getActivityCity()).get().getId();

        //TODO usuario forzado
        int userId = 1 ;
        double price = Double.parseDouble(form.getActivityPrice());
        String description = (form.getActivityInfo().isEmpty()) ? null : form.getActivityInfo();
        String url = (form.getActivityUrl().isEmpty()) ? null : form.getActivityUrl();
        final ExperienceModel experienceModel = exp.create(form.getActivityName(),form.getActivityAddress(),
                description, url, price, cityId, categoryId + 1, userId);

    // return new ModelAndView("redirect:/" + experienceModel.getCategoryName() + "/" + experienceModel.getId());
        return new ModelAndView("redirect:/" + experienceModel.getCategoryName() + "/");
    }

}
