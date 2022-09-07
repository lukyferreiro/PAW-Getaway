package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.services.*;
import ar.edu.itba.getaway.webapp.forms.ExperienceForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
    public ModelAndView createActivity(@Valid @ModelAttribute("experienceForm") final ExperienceForm form, final BindingResult errors) throws Exception {

        if(errors.hasErrors()){
            return createActivityForm(form);
        }

        long categoryId = form.getActivityCategoryId();
        if(categoryId < 0){
            throw new Exception();
        }

        List<CityModel> cityModels = cityService.listAll();

        int cityId = 0;
        boolean flag = true;
        for (int j = 0; j<cityModels.size() && flag ; j++){
            if(cityModels.get(j).getName().equals(form.getActivityCity())){
                cityId = j;
                flag=false;
            }
        }

        //TODO usuario forzado
        int userId = 1 ;
        final ExperienceModel experienceModel = exp.create(form.getActivityName(),form.getActivityAddress(),
                form.getActivityInfo(), form.getActivityUrl(), form.getActivityPrice(), cityId + 1 , categoryId + 1, userId);

        //TODO check pq ahora como agregue la flecha para volver hacias atras en los detalles de la actividad
        //y al terminar el formulario me redigire a los detalles de la actividad, si todo en la flecha de volver
        //hacia atras me lleva devuelta al formulario
//        return new ModelAndView("redirect:/" + experienceModel.getCategoryName() + "/" + experienceModel.getId());
        return new ModelAndView("redirect:/" + experienceModel.getCategoryName() + "/");
    }
}
