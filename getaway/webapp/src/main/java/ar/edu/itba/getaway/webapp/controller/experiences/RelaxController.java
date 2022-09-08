package ar.edu.itba.getaway.webapp.controller.experiences;

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
public class RelaxController {
    @Autowired
    ExperienceService exp;
    @Autowired
    CategoryService category;


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

}
