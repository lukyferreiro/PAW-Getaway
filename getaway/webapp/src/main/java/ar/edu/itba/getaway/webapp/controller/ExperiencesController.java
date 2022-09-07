package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.models.ExperienceCategory;
import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.services.CategoryService;
import ar.edu.itba.getaway.services.ExperienceService;
import ar.edu.itba.getaway.webapp.exceptions.ExperienceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ExperiencesController {

    @Autowired
    ExperienceService exp;
    @Autowired
    CategoryService category;

    @RequestMapping("/adventures")
    public ModelAndView adventures() {
        final ModelAndView mav = new ModelAndView("experiences");

        // Ordinal empieza en 0
        int id_adventure = ExperienceCategory.adventures.ordinal() + 1;
        String categoryName = ExperienceCategory.adventures.getName();
        List<ExperienceModel> experienceList = exp.listByCategory(id_adventure);
        String dbCategoryName = ExperienceCategory.adventures.getDatabaseName();

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

    @RequestMapping("/gastronomy")
    public ModelAndView gastronomy() {
        final ModelAndView mav = new ModelAndView("experiences");

        // Ordinal empieza en 0
        int id_adventure = ExperienceCategory.gastronomy.ordinal() + 1;
        String categoryName = ExperienceCategory.gastronomy.getName();
        List<ExperienceModel> experienceList = exp.listByCategory(id_adventure);
        String dbCategoryName = ExperienceCategory.gastronomy.getDatabaseName();

        mav.addObject("dbCategoryName", dbCategoryName);
        mav.addObject("categoryName", categoryName);
        mav.addObject("activities", experienceList);
        return mav;
    }

    @RequestMapping("/gastronomy/{gastronomyId}")
    public ModelAndView gastronomyView(@PathVariable("gastronomyId") final long gastronomyId){
        final ModelAndView mav = new ModelAndView("experienceDetails");

        final ExperienceModel experience = exp.getById(gastronomyId).orElseThrow(ExperienceNotFoundException::new);
        String dbCategoryName = ExperienceCategory.gastronomy.getDatabaseName();

        mav.addObject("dbCategoryName", dbCategoryName);
        mav.addObject("activity", experience);
        return mav;
    }

    @RequestMapping("/hotels")
    public ModelAndView hotels(){
        final ModelAndView mav = new ModelAndView("experiences");

        // Ordinal empieza en 0
        int id_adventure = ExperienceCategory.hotels.ordinal() + 1;
        String categoryName = ExperienceCategory.hotels.getName();
        List<ExperienceModel> experienceList = exp.listByCategory(id_adventure);
        String dbCategoryName = ExperienceCategory.hotels.getDatabaseName();

        mav.addObject("dbCategoryName", dbCategoryName);
        mav.addObject("categoryName", categoryName);
        mav.addObject("activities", experienceList);
        return mav;
    }

    @RequestMapping("/hotels/{hotelsId}")
    public ModelAndView hotelsView(@PathVariable("hotelsId") final long hotelsId){
        final ModelAndView mav = new ModelAndView("experienceDetails");

        final ExperienceModel experience = exp.getById(hotelsId).orElseThrow(ExperienceNotFoundException::new);
        String dbCategoryName = ExperienceCategory.hotels.getDatabaseName();

        mav.addObject("dbCategoryName", dbCategoryName);
        mav.addObject("activity", experience);
        return mav;
    }

    @RequestMapping("/relax")
    public ModelAndView relax() {
        final ModelAndView mav = new ModelAndView("experiences");

        // Ordinal empieza en 0
        int id_adventure = ExperienceCategory.relax.ordinal() + 1;
        String categoryName = ExperienceCategory.relax.getName();
        List<ExperienceModel> experienceList = exp.listByCategory(id_adventure);
        String dbCategoryName = ExperienceCategory.relax.getDatabaseName();

        mav.addObject("dbCategoryName", dbCategoryName);
        mav.addObject("categoryName", categoryName);
        mav.addObject("activities", experienceList);
        return mav;
    }

    @RequestMapping("/relax/{relaxId}")
    public ModelAndView relaxView(@PathVariable("relaxId") final long relaxId){
        final ModelAndView mav = new ModelAndView("experienceDetails");

        final ExperienceModel experience = exp.getById(relaxId).orElseThrow(ExperienceNotFoundException::new);
        String dbCategoryName = ExperienceCategory.relax.getDatabaseName();

        mav.addObject("dbCategoryName", dbCategoryName);
        mav.addObject("activity", experience);
        return mav;
    }

    @RequestMapping("/night")
    public ModelAndView night() {
        final ModelAndView mav = new ModelAndView("experiences");

        // Ordinal empieza en 0
        int id_adventure = ExperienceCategory.night.ordinal() + 1;
        String categoryName = ExperienceCategory.night.getName();
        List<ExperienceModel> experienceList = exp.listByCategory(id_adventure);
        String dbCategoryName = ExperienceCategory.night.getDatabaseName();

        mav.addObject("dbCategoryName", dbCategoryName);
        mav.addObject("categoryName", categoryName);
        mav.addObject("activities", experienceList);
        return mav;
    }

    @RequestMapping("/night/{nightId}")
    public ModelAndView nightView(@PathVariable("nightId") final long nightId){
        final ModelAndView mav = new ModelAndView("experienceDetails");

        final ExperienceModel experience = exp.getById(nightId).orElseThrow(ExperienceNotFoundException::new);
        String dbCategoryName = ExperienceCategory.night.getDatabaseName();

        mav.addObject("dbCategoryName", dbCategoryName);
        mav.addObject("activity", experience);
        return mav;
    }

    @RequestMapping("/historic")
    public ModelAndView historic() {
        final ModelAndView mav = new ModelAndView("experiences");

        // Ordinal empieza en 0
        int id_adventure = ExperienceCategory.historic.ordinal() + 1;
        String categoryName = ExperienceCategory.historic.getName();
        List<ExperienceModel> experienceList = exp.listByCategory(id_adventure);
        String dbCategoryName = ExperienceCategory.historic.getDatabaseName();

        mav.addObject("dbCategoryName", dbCategoryName);
        mav.addObject("categoryName", categoryName);
        mav.addObject("activities", experienceList);
        return mav;
    }

    @RequestMapping("/historic/{historicId}")
    public ModelAndView historicView(@PathVariable("historicId") final long historicId){
        final ModelAndView mav = new ModelAndView("experienceDetails");

        final ExperienceModel experience = exp.getById(historicId).orElseThrow(ExperienceNotFoundException::new);
        String dbCategoryName = ExperienceCategory.historic.getDatabaseName();

        mav.addObject("dbCategoryName", dbCategoryName);
        mav.addObject("activity", experience);
        return mav;
    }

}
