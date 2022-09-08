package ar.edu.itba.getaway.webapp.controller.experiences;

import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.services.*;
import ar.edu.itba.getaway.webapp.exceptions.ExperienceNotFoundException;
import ar.edu.itba.getaway.webapp.forms.CityForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AdventureController {
    @Autowired
    ExperienceService exp;
    @Autowired
    CategoryService category;
    @Autowired
    CityService cityService;

    @RequestMapping(value = "/adventures", method = {RequestMethod.GET})
    public ModelAndView adventures( @ModelAttribute("cityForm") final CityForm form) {
        final ModelAndView mav = new ModelAndView("experiences");

        // Ordinal empieza en 0
        int id_adventure = ExperienceCategory.adventures.ordinal() + 1;
        String categoryName = ExperienceCategory.adventures.getName();
        List<ExperienceModel> experienceList = exp.listByCategory(id_adventure);
        String dbCategoryName = ExperienceCategory.adventures.getDatabaseName();

        //Filtros
        List<CityModel> cityModels = cityService.listAll();

        mav.addObject("cities", cityModels);
        mav.addObject("dbCategoryName", dbCategoryName);
        mav.addObject("categoryName", categoryName);
        mav.addObject("activities", experienceList);
        return mav;
    }

    @RequestMapping("/adventures/{adventureId}")
    public ModelAndView adventuresView(@PathVariable("adventureId") final long adventureId){
        final ModelAndView mav = new ModelAndView("experienceDetails");

        final ExperienceModel experience = exp.getById(adventureId).orElseThrow(ExperienceNotFoundException::new);
        String dbCategoryName = ExperienceCategory.adventures.getDatabaseName();

        mav.addObject("dbCategoryName", dbCategoryName);
        mav.addObject("activity", experience);
        return mav;
    }

    @RequestMapping("/adventures/{cityId}")
    public ModelAndView adventuresCity(@PathVariable("cityId") final long cityId){
        final ModelAndView mav = new ModelAndView("experiences");

        // Ordinal empieza en 0
        int id_adventure = ExperienceCategory.adventures.ordinal() + 1;
        String categoryName = ExperienceCategory.adventures.getName();
        List<ExperienceModel> experienceList = exp.listByCategoryAndCity(id_adventure, cityId);
        String dbCategoryName = ExperienceCategory.adventures.getDatabaseName();

        //Filtros
        List<CityModel> cityModels = cityService.listAll();

        mav.addObject("cities", cityModels);
        mav.addObject("dbCategoryName", dbCategoryName);
        mav.addObject("categoryName", categoryName);
        mav.addObject("activities", experienceList);
        return mav;
    }


    @RequestMapping(value = "/adventures", method = {RequestMethod.POST})
    public ModelAndView adventureCity(@Valid @ModelAttribute("cityForm") final CityForm form, final BindingResult errors){
        if(errors.hasErrors()){
            return adventures(form);
        }

        long cityId = cityService.getIdByName(form.getActivityCity()).get().getId();


        return new ModelAndView("redirect:/adventure/"  + cityId);

    }
}
