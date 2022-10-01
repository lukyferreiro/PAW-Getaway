package ar.edu.itba.getaway.webapp.auth.forms;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.services.ExperienceService;
import ar.edu.itba.getaway.webapp.forms.SearchForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class SearchFormController {

    @Autowired
    private ExperienceService experienceService;

    //TODO Agregar loggers

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchFormController.class);

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public ModelAndView createSearchForm(@PathVariable("activityName") final String activityName,
                                         @ModelAttribute("searchForm") final SearchForm searchForm){

        return new ModelAndView("navbar");
    }

    @RequestMapping(value = "/", method = {RequestMethod.POST})
    public ModelAndView searchByName(@PathVariable("activityName") final String activityName,
                                     @Valid @ModelAttribute("searchForm") final SearchForm searchForm){

        final List<ExperienceModel> experienceModel = experienceService.getByName(searchForm.getActivityName());

        return new ModelAndView("navbar");

    }


}
